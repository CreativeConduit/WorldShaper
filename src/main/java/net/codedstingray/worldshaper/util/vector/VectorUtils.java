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

package net.codedstingray.worldshaper.util.vector;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;

public class VectorUtils {
    public static final Vector3i BASE_X = new Vector3ii(1, 0, 0);
    public static final Vector3i BASE_Y = new Vector3ii(0, 1, 0);
    public static final Vector3i BASE_Z = new Vector3ii(0, 0, 1);

    public static Vector3i getDirectionVectorByName(String name) {
        return switch (name.toLowerCase()) {
            case "north" -> new Vector3ii(0, 0, -1);
            case "south" -> new Vector3ii(0, 0, 1);
            case "east" -> new Vector3ii(1, 0, 0);
            case "west" -> new Vector3ii(-1, 0, 0);
            case "up" -> new Vector3ii(0, 1, 0);
            case "down" -> new Vector3ii(0, -1, 0);
            default -> new Vector3ii(0, 0, 0);
        };
    }
}
