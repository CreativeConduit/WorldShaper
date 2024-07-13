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

package net.codedstingray.worldshaper.util.world;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;

import java.util.Optional;

public enum Axis {
    X(1, 0, 0),
    Y(0, 1, 0),
    Z(0, 0, 1);

    public final Vector3i baseVector;

    Axis(int x, int y, int z) {
        this.baseVector = new Vector3ii(x, y, z);
    }

    public static Optional<Axis> fromString(String axisString) {
        try {
            return Optional.of(Axis.valueOf(axisString.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
