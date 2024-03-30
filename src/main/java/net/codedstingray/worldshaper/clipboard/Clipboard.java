package net.codedstingray.worldshaper.clipboard;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import net.codedstingray.worldshaper.area.Area;

/**
 * A Clipboard contains {@link BlockData block data} which can be placed in the world. It is always represented by a
 * cuboid, even if the {@link Area} used to create the clipboard is not.
 */
public class Clipboard {

    /**
     * Represents the lowest position of the bounding box. This position is relative to the origin point.
     */
    private final Vector3i basePosition;

    private final BlockData[][][] data;

    private Clipboard(Vector3i basePosition, BlockData[][][] data) {
        this.basePosition = basePosition.toImmutable();
        this.data = data;
    }

    public Vector3ii getBasePosition() {
        return basePosition.toImmutable();
    }

    public BlockData[][][] getData() {
        return data;
    }

    public static Clipboard createFromArea(World world, Area area, Vector3i originPosition) {
        Vector3i boundingBoxMin = area.getBoundingBoxMin();
        Vector3i boundingBoxMax = area.getBoundingBoxMax();

        Vector3i basePosition = boundingBoxMin.toImmutable().sub(originPosition);

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
}
