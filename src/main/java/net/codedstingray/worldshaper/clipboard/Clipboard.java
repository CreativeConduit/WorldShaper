package net.codedstingray.worldshaper.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.util.transform.AffineTransform;
import net.codedstingray.worldshaper.util.transform.Transform;
import net.codedstingray.worldshaper.util.vector.VectorUtils;
import net.codedstingray.worldshaper.util.vector.vector3.*;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import net.codedstingray.worldshaper.util.world.PositionedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * A Clipboard contains {@link BlockData block data} which can be placed in the world. It is always represented by a
 * cuboid, even if the {@link Area} used to create the clipboard is not.
 * It has the abstract concept of the origin point. All blocks in the clipboard are positioned relative to this origin
 * point. In practice, this is usually the player's position relative to their selection's bounding box when
 * {@code /copy} or {@code /cut} was called.
 */
@ParametersAreNonnullByDefault
public class Clipboard implements Iterable<PositionedBlockData> {

    /**
     * Represents the lowest position of the bounding box. This position is relative to the origin point.
     */
    private final Vector3fi originOffset;
    private final Vector3i originBlockOffset;
    private final BlockData[][][] rawData;

    private AffineTransform transform = new AffineTransform();

    private Vector3i appliedOriginBlockOffset;
    private BlockData[][][] appliedBlockData;

    private boolean wasTransformNotApplied = false;


    public Clipboard(Vector3f originOffset, Vector3i originBlockOffset, BlockData[][][] rawData) {
        this.originOffset = originOffset.toImmutable();
        this.originBlockOffset = originBlockOffset.toImmutable();
        this.rawData = rawData;
    }

    @Nonnull
    public Vector3fi getOriginOffset() {
        return originOffset.toImmutable();
    }

    @Nonnull
    public Vector3ii getAppliedOriginBlockOffset() {
        if (appliedOriginBlockOffset == null) {
            throw new IllegalStateException("Clipboard transform must be applied before the applied origin offset can be accessed. Call Clipboard#applyTransform() before querying the applied origin offset.");
        }
        return appliedOriginBlockOffset.toImmutable();
    }

    @Nonnull
    public BlockData[][][] getRawData() {
        return rawData;
    }

    @Nonnull
    public Optional<BlockData[][][]> getAppliedBlockData() {
        return Optional.ofNullable(appliedBlockData);
    }

    /**
     * Creates a Clipboard. It uses the given world and area to determine the block data to be stored and uses the
     * absolute origin position to calculate the relative origin position
     *
     * @param world The {@link World} to draw the {@link BlockData} from
     * @param area The {@link Area}
     * @param absoluteOriginPosition The origin position in the world; This is used to calculate
     *                               the relative {@link #originOffset} of the clipboard
     * @return The clipboard instance
     */
    @Nonnull
    public static Clipboard createFromArea(World world, Area area, Vector3f absoluteOriginPosition, Vector3i absoluteBlockOriginPosition) {
        Vector3i boundingBoxMin = area.getBoundingBoxMin();
        Vector3i boundingBoxMax = area.getBoundingBoxMax();

        Vector3f originOffset = VectorUtils.createVector3f(boundingBoxMin).sub(absoluteOriginPosition).add(0.5f, 0.5f, 0.5f);
        Vector3i originBlockOffset = boundingBoxMin.sub(absoluteBlockOriginPosition);

        BlockData[][][] data = new BlockData
                [boundingBoxMax.getX() - boundingBoxMin.getX() + 1]
                [boundingBoxMax.getY() - boundingBoxMin.getY() + 1]
                [boundingBoxMax.getZ() - boundingBoxMin.getZ() + 1];

        area.forEach(areaPosition -> {
            int dx = areaPosition.getX() - boundingBoxMin.getX();
            int dy = areaPosition.getY() - boundingBoxMin.getY();
            int dz = areaPosition.getZ() - boundingBoxMin.getZ();
            data[dx][dy][dz] = world.getBlockData(LocationUtils.vectorToLocation(areaPosition, world));
        });

        return new Clipboard(originOffset, originBlockOffset, data);
    }

    /**
     * Rotates the Clipboard around the given angles in the zxy(roll-pitch-yaw)-order.
     *
     * @param x The rotation around the x-axis
     * @param y The rotation around the y-axis
     * @param z The rotation around the z-axis
     */
    public void rotate(double x, double y, double z) {
        transform = transform
                .rotateZ(z)
                .rotateX(x)
                .rotateY(y);

        WorldShaper.getInstance().getLogger().info(transform.toString());

        clearTransformedData();
    }

    private void clearTransformedData() {
        appliedOriginBlockOffset = null;
        appliedBlockData = null;
    }

    public void applyTransform() {
        applyTransform(false, false);
    }

    public void applyTransform(boolean forceUpdate, boolean noApply) {
        if (forceUpdate || wasTransformNotApplied != noApply || appliedBlockData == null) {
            if (noApply) {
                appliedBlockData = rawData;
                appliedOriginBlockOffset = originBlockOffset;
            } else {
                TransformedBlockData[][][] transformedData = new TransformedBlockData[rawData.length][rawData[0].length][rawData[0][0].length];

                // calculate transformed center points for all blocks in the raw clipboard data
                for (int x = 0; x < rawData.length; x++) {
                    for (int y = 0; y < rawData[0].length; y++) {
                        for (int z = 0; z < rawData[0][0].length; z++) {
                            Vector3f blockCenterVector = new Vector3fm(x + 0.5f, y + 0.5f, z + 0.5f).add(originOffset);
                            Vector3f rotatedBlockCenterVector = transform.apply(blockCenterVector);

                            transformedData[x][y][z] = new TransformedBlockData(rawData[x][y][z], rotatedBlockCenterVector);

                            if (x == 0 && y == 0 && z == 0) {
                                WorldShaper.getInstance().getLogger().info("(0, 0, 0) Block position: " + rotatedBlockCenterVector);
                            }

                            if (x == rawData.length - 1 && y == rawData[0].length - 1 && z == rawData[0][0].length - 1) {
                                WorldShaper.getInstance().getLogger().info("(max, max, max) Block position: " + rotatedBlockCenterVector);
                            }
                        }
                    }
                }

                Vector3f transformedOriginOffset = transform.apply(originOffset);

                // calculate transformed corners
                Vector3f[] corners = {
                        transformedOriginOffset,

                        transform.apply(new Vector3fi(originOffset.x + rawData.length - 1, originOffset.y, originOffset.z)),
                        transform.apply(new Vector3fi(originOffset.x, originOffset.y + rawData[0].length - 1, originOffset.z)),
                        transform.apply(new Vector3fi(originOffset.x, originOffset.y, originOffset.z + rawData[0][0].length - 1)),

                        transform.apply(new Vector3fi(originOffset.x, originOffset.y + rawData[0].length - 1, originOffset.z + rawData[0][0].length - 1)),
                        transform.apply(new Vector3fi(originOffset.x + rawData.length - 1, originOffset.y, originOffset.z + rawData[0][0].length - 1)),
                        transform.apply(new Vector3fi(originOffset.x + rawData.length - 1, originOffset.y + rawData[0].length - 1, originOffset.z)),

                        transform.apply(new Vector3fi(originOffset.x + rawData.length - 1, originOffset.y + rawData[0].length - 1, originOffset.z + rawData[0][0].length - 1))
                };

                // calculate bounding box for applied data
                Vector3i appliedTransformMinPosition = VectorUtils.roundToVector3i(VectorUtils.getBoundingBoxMinVector(corners));
                Vector3i appliedTransformMaxPosition = VectorUtils.roundToVector3i(VectorUtils.getBoundingBoxMaxVector(corners)).add(VectorUtils.ONE);

                appliedOriginBlockOffset = appliedTransformMinPosition;

                // calculate applied block data
                appliedBlockData = new BlockData
                        [appliedTransformMaxPosition.getX() - appliedTransformMinPosition.getX()]
                        [appliedTransformMaxPosition.getY() - appliedTransformMinPosition.getY()]
                        [appliedTransformMaxPosition.getZ() - appliedTransformMinPosition.getZ()];


                Transform inverse = transform.inverse();

                for (int x = 0; x < appliedBlockData.length; x++) {
                    for (int y = 0; y < appliedBlockData[0].length; y++) {
                        for (int z = 0; z < appliedBlockData[0][0].length; z++) {
                            Vector3f centerPosition = VectorUtils.createVector3f(appliedTransformMinPosition.add(x, y, z))
                                    .add(0.5f, 0.5f, 0.5f);

                            Vector3f v = centerPosition.sub(transformedOriginOffset);
                            Vector3f blockCenterPositionInTransformedBlockData = inverse.apply(v);

                            int blockX = (int) blockCenterPositionInTransformedBlockData.getX();
                            int blockY = (int) blockCenterPositionInTransformedBlockData.getY();
                            int blockZ = (int) blockCenterPositionInTransformedBlockData.getZ();

                            if (blockX >= 0 && blockX < transformedData.length &&
                                    blockY >= 0 && blockY < transformedData[0].length &&
                                    blockZ >= 0 && blockZ < transformedData[0][0].length) {

                                BlockData foundBlockData = transformedData[blockX][blockY][blockZ].blockData;
                                appliedBlockData[x][y][z] = foundBlockData;
                            } else {
                                appliedBlockData[x][y][z] = Bukkit.createBlockData("glass");
                            }

                        }
                    }
                }
            }

            wasTransformNotApplied = noApply;
        }
    }

    @Override
    public Iterator<PositionedBlockData> iterator() {
        if (appliedBlockData == null) {
            throw new IllegalStateException("Clipboard transform must be applied before the iterator can be accessed. Call Clipboard#applyTransform() before using the iterator.");
        }

        return new Iterator<>() {
            private final Vector3im current = VectorUtils.ZERO.toMutable();
            private final Vector3ii maxPos = new Vector3ii(appliedBlockData.length -1, appliedBlockData[0].length - 1, appliedBlockData[0][0].length - 1);

            @Override
            public boolean hasNext() {
                return current.y <= maxPos.y;
            }

            @Override
            public PositionedBlockData next() {
                if(!hasNext()) throw new NoSuchElementException();
                PositionedBlockData result = new PositionedBlockData(new Vector3ii(current), appliedBlockData[current.x][current.y][current.z]);

                //move pointer
                current.add(VectorUtils.BASE_X);
                if(current.x > maxPos.x) {
                    current.x = 0;
                    current.add(VectorUtils.BASE_Z);
                    if(current.z > maxPos.z) {
                        current.z = 0;
                        current.add(VectorUtils.BASE_Y);
                    }
                }

                return result;
            }
        };
    }


    private record TransformedBlockData(BlockData blockData, Vector3f position) {
    }
}
