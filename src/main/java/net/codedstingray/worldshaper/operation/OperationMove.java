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

package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.Action.ActionItem;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.clipboard.ClipboardUtils;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class OperationMove implements Operation {

    private final Location playerLocation;
    private final Location originLocation;

    public OperationMove(Location playerLocation, Vector3i moveVector) {
        this.playerLocation = playerLocation;
        originLocation = LocationUtils.vectorToLocation(
                LocationUtils.locationToBlockVector(playerLocation).add(moveVector),
                playerLocation.getWorld());
    }

    @Override
    public Action performOperation(Area area, World world) {
        Clipboard clipboard = Clipboard.createFromArea(world, area,
                LocationUtils.locationToEntityVector(playerLocation),
                LocationUtils.locationToBlockVector(playerLocation));
        clipboard.applyTransform(false, true);

        List<ActionItem> actionItems = new LinkedList<>();
        Set<Location> pasteLocations = new HashSet<>();
        Vector3i offset = clipboard.getAppliedOriginBlockOffset().add(LocationUtils.locationToBlockVector(originLocation));

        clipboard.forEach(positionedBlockData -> ClipboardUtils.createActionItem(world, offset, positionedBlockData).ifPresent(
                actionItem -> {
                    actionItems.add(actionItem);
                    pasteLocations.add(actionItem.location());
                }));

        area.forEach(position -> cutBlockFromArea(world, pasteLocations, position).ifPresent(actionItems::add));

        return new Action(world.getUID(), actionItems);
    }

    private Optional<ActionItem> cutBlockFromArea(World world, Set<Location> pasteLocations, Vector3i position) {
        Location location = LocationUtils.vectorToLocation(position, world);
        if (pasteLocations.contains(location)) {
            return Optional.empty();
        }
        Block block = world.getBlockAt(location);
        BlockData from = block.getBlockData();

        return Optional.of(new ActionItem(location, from, Bukkit.createBlockData(Material.AIR)));
    }
}
