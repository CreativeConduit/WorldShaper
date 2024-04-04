package net.codedstingray.worldshaper.clipboard;

import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.util.vector.VectorUtils;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3im;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import net.codedstingray.worldshaper.util.world.PositionedBlockData;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Clipboard contains {@link BlockData block data} which can be placed in the world. It is always represented by a
 * cuboid, even if the {@link Area} used to create the clipboard is not.
 * It has the abstract concept of the origin point. All blocks in the clipboard are positioned relative to this origin
 * point. In practice, this is usually the player's position relative to their selection's bounding box when
 * {@code /copy} or {@code /cut} was called.
 */
public class Clipboard implements Iterable<PositionedBlockData> {

    /**
     * Represents the lowest position of the bounding box. This position is relative to the origin point.
     */
    private final Vector3i originPosition;

    private final BlockData[][][] data;

    private Clipboard(Vector3i originPosition, BlockData[][][] data) {
        this.originPosition = originPosition.toImmutable();
        this.data = data;
    }

    public Vector3ii getOriginPosition() {
        return originPosition.toImmutable();
    }

    public BlockData[][][] getData() {
        return data;
    }

    /**
     * Creates a Clipboard. It uses the given world and area to determine the block data to be stored and uses the
     * absolute origin position to calculate the relative origin position
     *
     * @param world The {@link World} to draw the {@link BlockData} from
     * @param area The {@link Area}
     * @param absoluteOriginPosition The origin position in the world; This is used to calculate
     *                               the relative {@link #originPosition} of the clipboard
     * @return The clipboard instance
     */
    public static Clipboard createFromArea(World world, Area area, Vector3i absoluteOriginPosition) {
        Vector3i boundingBoxMin = area.getBoundingBoxMin();
        Vector3i boundingBoxMax = area.getBoundingBoxMax();

        Vector3i basePosition = boundingBoxMin.toImmutable().sub(absoluteOriginPosition);

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

        return new Clipboard(basePosition, data);
    }

    @Override
    public Iterator<PositionedBlockData> iterator() {
        return new Iterator<>() {
            private final Vector3im current = VectorUtils.ZERO.toMutable();
            private final Vector3ii maxPos = new Vector3ii(data.length -1, data[0].length - 1, data[0][0].length - 1);

            @Override
            public boolean hasNext() {
                return current.y <= maxPos.y;
            }

            @Override
            public PositionedBlockData next() {
                if(!hasNext()) throw new NoSuchElementException();
                PositionedBlockData result = new PositionedBlockData(new Vector3ii(current), data[current.x][ current.y][ current.z]);

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
}
