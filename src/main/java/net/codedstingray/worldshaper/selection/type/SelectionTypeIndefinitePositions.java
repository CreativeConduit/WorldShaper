/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2023 CodedStingray
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

package net.codedstingray.worldshaper.selection.type;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.Selection;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

/**
 * A Selection Type representing the selection of an indefinite number of control positions, a right click adding the
 * clicked position to the first unset index, a left click clearing the list of control points before adding the
 * clicked position.
 */
public class SelectionTypeIndefinitePositions implements SelectionType {

    public static final String NAME = "indefinitePositions";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int onLeftClick(Player player, Vector3i clickedPosition) {
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();
        selection.clearControlPositions();
        return 0;
    }

    @Override
    public int onRightClick(Player player, Vector3i clickedPosition) {
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();
        return selection.getControlPositions().size();
    }
}
