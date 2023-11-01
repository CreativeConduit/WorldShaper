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

public class Vector3ii implements Vector3i {

    public final int x, y, z;

    public Vector3ii(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3ii(Vector3i v) {
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
        return new Vector3ii(x, this.y, this.z);
    }

    @Override
    public Vector3i setY(int y) {
        return new Vector3ii(this.x, y, this.z);
    }

    @Override
    public Vector3i setZ(int z) {
        return new Vector3ii(this.x, this.y, z);
    }

    @Override
    public Vector3i set(Vector3i v) {
        return new Vector3ii(v);
    }

    @Override
    public Vector3i set(int x, int y, int z) {
        return new Vector3ii(x, y, z);
    }

    @Override
    public Vector3i add(int x, int y, int z) {
        return new Vector3ii(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    @Override
    public Vector3i scale(Vector3i v) {
        return new Vector3ii(
                x * v.getX(),
                y * v.getY(),
                z * v.getZ()
        );
    }

    @Override
    public Vector3i scale(int scalar) {
        return new Vector3ii(
                x * scalar,
                y * scalar,
                z * scalar
        );
    }

    @Override
    public Vector3i scale(float scalar) {
        return new Vector3ii(
                (int) (x * scalar),
                (int) (y * scalar),
                (int) (z * scalar)
        );
    }

    @Override
    public Vector3i cross(Vector3i v) {
        return new Vector3ii(
                (y * v.getZ()) - (z * v.getY()),
                (z * v.getX()) - (x * v.getZ()),
                (x * v.getY()) - (y * v.getX())
        );
    }

    @Override
    public Vector3im toMutable() {
        return new Vector3im(this);
    }

    @Override
    public Vector3im toMutableCopy() {
        return new Vector3im(this);
    }

    @Override
    public Vector3ii toImmutable() {
        return this;
    }

    @Override
    public Vector3ii toImmutableCopy() {
        return new Vector3ii(this);
    }
}
