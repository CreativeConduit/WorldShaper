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

import com.google.common.base.Objects;

public class Vector3im implements Vector3i {

    public int x, y, z;

    public Vector3im(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3im(Vector3i v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public Vector3i setX(int x) {
        this.x = x;
        return this;
    }

    @Override
    public Vector3i setY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public Vector3i setZ(int z) {
        this.z = z;
        return this;
    }

    @Override
    public Vector3i set(Vector3i v) {
        x = v.getX();
        y = v.getY();
        z = v.getZ();
        return this;
    }

    @Override
    public Vector3i set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3i scale(Vector3i v) {
        x *= v.getX();
        y *= v.getY();
        z *= v.getZ();
        return this;
    }

    @Override
    public Vector3i scale(int scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public Vector3i scale(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public Vector3i cross(Vector3i v) {
        int tx = (y * v.getZ()) - (z * v.getY());
        int ty = (z * v.getX()) - (x * v.getZ());
        int tz = (x * v.getY()) - (y * v.getX());

        x = tx;
        y = ty;
        z = tz;

        return this;
    }

    @Override
    public Vector3im toMutable() {
        return this;
    }

    @Override
    public Vector3im toMutableCopy() {
        return  new Vector3im(this);
    }

    @Override
    public Vector3ii toImmutable() {
        return new Vector3ii(this);
    }

    @Override
    public Vector3ii toImmutableCopy() {
        return new Vector3ii(this);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Vector3i v)) {
            return false;
        }

        return v.getX() == x && v.getY() == y && v.getZ() == z;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y, z);
    }
}
