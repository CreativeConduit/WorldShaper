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
