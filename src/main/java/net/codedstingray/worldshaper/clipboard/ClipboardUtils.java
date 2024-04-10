package net.codedstingray.worldshaper.clipboard;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import net.codedstingray.worldshaper.util.world.PositionedBlockData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.util.Optional;

/**
 * Provides utility methods related to the Clipboard system.
 */
public class ClipboardUtils {

    /**
     * Creates a single {@link Action.ActionItem ActionItem} for the placement of a single block from the clipboard into
     * the given world.
     *
     * @param world The world for the Block to be placed in
     * @param offset The clipboard's absolute offset in the world
     * @param positionedBlockData The Object containing information about the location within the clipboard to be set as
     *                            well as the block data this location should be set to.
     * @return The {@link Action.ActionItem ActionItem} containing the change to the clipboard block
     */
    public static Optional<Action.ActionItem> createActionItem(World world, Vector3i offset, PositionedBlockData positionedBlockData) {
        BlockData blockDataTo = positionedBlockData.blockData();
        Vector3ii position = positionedBlockData.position();

        if (blockDataTo == null) {
            return Optional.empty();
        }

        Location blockLocation = LocationUtils.vectorToLocation(position.add(offset), world);
        BlockData blockDataFrom = world.getBlockData(blockLocation);

        return Optional.of(new Action.ActionItem(blockLocation, blockDataFrom, blockDataTo));
    }
}
