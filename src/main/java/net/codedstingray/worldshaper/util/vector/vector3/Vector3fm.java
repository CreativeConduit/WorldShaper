package net.codedstingray.worldshaper.util.vector.vector3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Vector3fm implements Vector3f {

    public float x, y, z;

    public Vector3fm(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3fm(Vector3f v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public Vector3f setX(float x) {
        this.x = x;
        return this;
    }

    @Override
    public Vector3f setY(float y) {
        this.y = y;
        return this;
    }

    @Override
    public Vector3f setZ(float z) {
        this.z = z;
        return this;
    }

    @Override
    public Vector3f set(Vector3f v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        return this;
    }

    @Override
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3f scale(Vector3i v) {
        x *= v.getX();
        y *= v.getY();
        z *= v.getZ();
        return this;
    }

    @Override
    public Vector3f scale(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public Vector3f cross(Vector3f v) {
        float tx = (y * v.getZ()) - (z * v.getY());
        float ty = (z * v.getX()) - (x * v.getZ());
        float tz = (x * v.getY()) - (y * v.getX());

        x = tx;
        y = ty;
        z = tz;

        return this;
    }

    @Override
    public Vector3fm toMutable() {
        return this;
    }

    @Override
    public Vector3fm toMutableCopy() {
        return new Vector3fm(this);
    }

    @Override
    public Vector3fi toImmutable() {
        return new Vector3fi(this);
    }

    @Override
    public Vector3fi toImmutableCopy() {
        return new Vector3fi(this);
    }
}
