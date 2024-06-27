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
