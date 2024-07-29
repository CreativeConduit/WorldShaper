/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
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
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;
import java.util.List;

@ParametersAreNonnullByDefault
public class OperationPaste implements Operation {

    private final Clipboard clipboard;
    private final Location playerLocation;

    public OperationPaste(Clipboard clipboard, Location playerLocation) {
        this.clipboard = clipboard;
        this.playerLocation = playerLocation;
    }

    @Override
    public Action performOperation(Area area, World world) {
        clipboard.applyTransform();

        List<ActionItem> actionItems = new LinkedList<>();
        Vector3i offset = clipboard.getAppliedOriginBlockOffset().add(LocationUtils.locationToBlockVector(playerLocation));

        clipboard.forEach(positionedBlockData ->
                ClipboardUtils.createActionItem(world, offset, positionedBlockData).ifPresent(actionItems::add));

        return new Action(world.getUID(), actionItems);
    }
}
