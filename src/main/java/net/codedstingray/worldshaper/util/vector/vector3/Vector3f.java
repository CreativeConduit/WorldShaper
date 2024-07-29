/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
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

package net.codedstingray.worldshaper.util.vector.vector3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface Vector3f {

    float getX();
    float getY();
    float getZ();

    Vector3f setX(float x);
    Vector3f setY(float y);
    Vector3f setZ(float z);

    Vector3f set(Vector3f v);
    Vector3f set(float x, float y, float z);

    default Vector3f invert() {
        return scale(-1);
    }

    Vector3f add(float x, float y, float z);
    default Vector3f add(Vector3f v) {
        return add(v.getX(), v.getY(), v.getZ());
    }
    default Vector3f sub(float x, float y, float z) {
        return add(-x, -y, -z);
    }
    default Vector3f sub(Vector3f v) {
        return add(-v.getX(), -v.getY(), -v.getZ());
    }

    Vector3f scale(Vector3i v);
    Vector3f scale(float scalar);

    Vector3f cross(Vector3f v);
    default float dot(Vector3f v) {
        return (this.getX() * v.getX()) + (this.getY() * v.getY()) + (this.getZ() * v.getZ());
    }

    default float length() {
        return (float) Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
    }

    Vector3fm toMutable();
    Vector3fm toMutableCopy();
    Vector3fi toImmutable();
    Vector3fi toImmutableCopy();
}
