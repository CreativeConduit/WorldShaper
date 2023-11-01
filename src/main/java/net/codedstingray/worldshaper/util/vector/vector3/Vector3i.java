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

package net.codedstingray.worldshaper.util.vector.vector3;

public interface Vector3i {

    int getX();
    int getY();
    int getZ();

    Vector3i setX(int x);
    Vector3i setY(int y);
    Vector3i setZ(int z);

    Vector3i set(Vector3i v);
    Vector3i set(int x, int y, int z);

    default Vector3i invert() {
        return scale(-1);
    }

    Vector3i add(int x, int y, int z);
    default Vector3i add(Vector3i v) {
        return add(v.getX(), v.getY(), v.getZ());
    }
    default Vector3i sub(int x, int y, int z) {
        return add(-x, -y, -z);
    }
    default Vector3i sub(Vector3i v) {
        return add(-v.getX(), -v.getY(), -v.getZ());
    }

    Vector3i scale(Vector3i v);
    Vector3i scale(int scalar);
    Vector3i scale(float scalar);

    Vector3i cross(Vector3i v);
    default float dot(Vector3i v) {
        return (this.getX() * v.getX()) + (this.getY() * v.getY()) + (this.getZ() * v.getZ());
    }

    default float length() {
        return (float) Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
    }

    Vector3im toMutable();
    Vector3im toMutableCopy();
    Vector3ii toImmutable();
    Vector3ii toImmutableCopy();
}
