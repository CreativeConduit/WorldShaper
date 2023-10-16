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

package net.codedstingray.worldshaper.area;

import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.world.Direction;
import org.joml.Vector3i;

public interface Area extends Iterable<Vector3i> {
    String getName();
    boolean isValid();

    void updateArea(Selection selection);

    void move(Direction direction, int distance);

    void expand(Direction direction, int amount);
    void retract(Direction direction, int amount);
}
