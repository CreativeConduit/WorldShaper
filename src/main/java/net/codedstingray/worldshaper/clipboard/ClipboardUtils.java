/*
 * WorldShaper, a powerful in-game map editing addon for WorldEdit
 * Copyright (C) 2024 CreativeConduit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
