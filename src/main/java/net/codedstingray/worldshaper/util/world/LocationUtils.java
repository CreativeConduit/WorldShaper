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

import org.bukkit.Location;
import org.bukkit.World;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class LocationUtils {

    public static Vector3i locationToBlockVector(Location location) {
        return new Vector3i(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }

    public static Vector3d locationToEntityVector(Location location) {
        return new Vector3d(
                location.getX(),
                location.getY(),
                location.getZ()
        );
    }

    public static Location vectorToLocation(Vector3ic vector, @Nullable World world) {
        return new Location(
                world,
                vector.x() + 0.5d,
                vector.y() + 0.5d,
                vector.z() + 0.5d
        );
    }

    public static Location vectorToLocation(Vector3dc vector, @Nullable World world) {
        return new Location(
                world,
                vector.x(),
                vector.y(),
                vector.z()
        );
    }
}
