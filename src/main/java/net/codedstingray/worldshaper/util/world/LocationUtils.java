package net.codedstingray.worldshaper.util.world;

import org.bukkit.Location;
import org.joml.Vector3i;

public class LocationUtils {

    public static Vector3i locationToBlockVector(Location playerLocation) {
        return new Vector3i(
                playerLocation.getBlockX(),
                playerLocation.getBlockY(),
                playerLocation.getBlockZ()
        );
    }
}
