package net.codedstingray.worldshaper.clipboard;

import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.util.transform.AffineTransform;
import net.codedstingray.worldshaper.util.vector.VectorUtils;
import net.codedstingray.worldshaper.util.vector.vector3.*;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import net.codedstingray.worldshaper.util.world.PositionedBlockData;
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
    private final Vector3f originOffset;
    private final Vector3i originBlockOffset;
    private final BlockData[][][] rawData;

    private final AffineTransform transform = new AffineTransform();
    private TransformedBlockData[][][] transformedData;

    private Vector3i appliedOriginBlockOffset;
    private BlockData[][][] appliedBlockData;

    private boolean wasTransformNotApplied = false;


    public Clipboard(Vector3f originOffset, Vector3i originBlockOffset, BlockData[][][] rawData) {
        this.originOffset = originOffset.toImmutable();
        this.originBlockOffset = originBlockOffset;
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

        Vector3f originOffset = VectorUtils.createVector3f(boundingBoxMin).sub(absoluteOriginPosition);
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
        transform.rotateZ(z);
        transform.rotateX(x);
        transform.rotateY(y);

        clearTransformedData();
    }

    private void clearTransformedData() {
        transformedData = null;
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
                //TODO calculate
                appliedBlockData = rawData;
                appliedOriginBlockOffset = originBlockOffset;
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
