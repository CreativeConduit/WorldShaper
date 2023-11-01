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

import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import org.bukkit.entity.Player;

/**
 * The Selection Type defines the behavior of the Selection Wand.
 */
public interface SelectionType {

    String getName();

    /**
     * Selection Type behavior on a left click with the selection wand.
     *
     * @param player The player who performed the interaction
     * @param clickedPosition The block position that was clicked
     * @return The index at which the position was added
     */
    int onLeftClick(Player player, Vector3i clickedPosition);

    /**
     * Selection Type behavior on a right click with the selection wand.
     *
     * @param player The player who performed the interaction
     * @param clickedPosition The block position that was clicked
     * @return The index at which the position was added
     */
    int onRightClick(Player player, Vector3i clickedPosition);
}
