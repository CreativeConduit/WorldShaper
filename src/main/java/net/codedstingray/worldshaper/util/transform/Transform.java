package net.codedstingray.worldshaper.util.transform;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3f;

public interface Transform {

    boolean isIdentity();

    Vector3f apply(Vector3f v);

    Transform inverse();

    Transform combine(Transform other);
}
