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

import org.bukkit.entity.Player;
import org.joml.Vector3i;

/**
 * A Selection Type representing the selection of only 2 control positions, the first position being selected with a
 * left-click, the second position being selected with a right-click.
 */
public class SelectionTypeTwoPositions implements SelectionType {

    public static final String NAME = "twoPositions";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int onLeftClick(Player player, Vector3i clickedPosition) {
        return 0;
    }

    @Override
    public int onRightClick(Player player, Vector3i clickedPosition) {
        return 1;
    }
}
