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

import net.codedstingray.worldshaper.util.vector.vector3.*;

import java.util.Arrays;

public class VectorUtils {
    public static final Vector3i ZERO = new Vector3ii(0, 0, 0);
    public static final Vector3i ONE = new Vector3ii(1, 1, 1);
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


    public static Vector3i createVector3i(Vector3i v) {
        return new Vector3ii(v);
    }

    public static Vector3i createVector3i(Vector3f v) {
        return new Vector3ii((int) v.getX(), (int) v.getY(), (int) v.getZ());
    }

    public static Vector3i roundToVector3i(Vector3i v) {
        return new Vector3ii(v);
    }

    public static Vector3i roundToVector3i(Vector3f v) {
        return new Vector3ii(Math.round(v.getX()), Math.round(v.getY()), Math.round(v.getZ()));
    }


    public static Vector3f createVector3f(Vector3f v) {
        return new Vector3fi(v);
    }

    public static Vector3f createVector3f(Vector3i v) {
        return new Vector3fi(v.getX(), v.getY(), v.getZ());
    }

    public static Vector3f createBlockVector3f(Vector3i v) {
        return new Vector3fi(v.getX() + 0.5f, v.getY() + 0.5f, v.getZ() + 0.5f);
    }

    public static Vector3i getBoundingBoxMinVector(Vector3i... vectors) {
        Vector3im min = new Vector3im(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        Arrays.stream(vectors).forEach(v -> {
            min.x = Math.min(min.x, v.getX());
            min.y = Math.min(min.y, v.getY());
            min.z = Math.min(min.z, v.getZ());
        });

        return min.toImmutable();
    }

    public static Vector3i getBoundingBoxMaxVector(Vector3i... vectors) {
        Vector3im max = new Vector3im(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        Arrays.stream(vectors).forEach(v -> {
            max.x = Math.max(max.x, v.getX());
            max.y = Math.max(max.y, v.getY());
            max.z = Math.max(max.z, v.getZ());
        });

        return max.toImmutable();
    }

    public static Vector3f getBoundingBoxMinVector(Vector3f... vectors) {
        Vector3fm min = new Vector3fm(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        Arrays.stream(vectors).forEach(v -> {
            min.x = Math.min(min.x, v.getX());
            min.y = Math.min(min.y, v.getY());
            min.z = Math.min(min.z, v.getZ());
        });

        return min.toImmutable();
    }

    public static Vector3f getBoundingBoxMaxVector(Vector3f... vectors) {
        Vector3fm max = new Vector3fm(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        Arrays.stream(vectors).forEach(v -> {
            max.x = Math.max(max.x, v.getX());
            max.y = Math.max(max.y, v.getY());
            max.z = Math.max(max.z, v.getZ());
        });

        return max.toImmutable();
    }
}
