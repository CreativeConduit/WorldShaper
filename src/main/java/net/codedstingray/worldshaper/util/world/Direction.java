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

package net.codedstingray.worldshaper.util.world;

import org.joml.Vector3i;

public enum Direction {

    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    SOUTH(0, 0, 1),
    WEST(-1, 0, 0),
    UP(0, 1, 0),
    DOWN(0, -1, 0);

    public final Vector3i baseVector;

    Direction(int x, int y, int z) {
        this.baseVector = new Vector3i(x, y, z);
    }

    public static Direction calculateFromRelativeDirection(Direction base, String relativeDirection) {
        if (base == UP || base == DOWN) {
            return base;
        }
        return switch (relativeDirection.toLowerCase()) {
            case "forward" -> base;
            case "right" -> base.rotate();
            case "back" -> base.rotate().rotate();
            case "left" -> base.rotate().rotate().rotate();
            default -> throw new IllegalArgumentException("\"" + relativeDirection + "\" is not a valid relative direction");
        };
    }

    /**
     * Rotates 90° clockwise when viewed from the top.
     *
     * @return The rotated direction
     */
    private Direction rotate() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> this;
        };
    }

    /**
     * Returns whether the given {@link String} is a valid {@link Direction} name, such that calling
     * {@link #valueOf(String)} with this string will not throw an {@link IllegalArgumentException}.
     *
     * @param directionName The string to be checked
     * @return true if the given string is a valid direction name, false otherwise
     */
    public static boolean isValidDirectionName(String directionName) {
        return directionName.equals(NORTH.name()) ||
                directionName.equals(EAST.name()) ||
                directionName.equals(SOUTH.name()) ||
                directionName.equals(WEST.name()) ||
                directionName.equals(UP.name()) ||
                directionName.equals(DOWN.name());
    }
}
