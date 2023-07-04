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
