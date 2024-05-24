package net.codedstingray.worldshaper.util.vector.vector3;

import com.google.common.base.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Vector3fi implements Vector3f {

    public final float x, y, z;

    public Vector3fi(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3fi(Vector3f v) {
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
        return new Vector3fi(x, this.y, this.z);
    }

    @Override
    public Vector3f setY(float y) {
        return new Vector3fi(this.x, y, this.z);
    }

    @Override
    public Vector3f setZ(float z) {
        return new Vector3fi(this.x, this.y, z);
    }

    @Override
    public Vector3f set(Vector3f v) {
        return new Vector3fi(v);
    }

    @Override
    public Vector3f set(float x, float y, float z) {
        return new Vector3fi(x, y, z);
    }

    @Override
    public Vector3f add(float x, float y, float z) {
        return new Vector3fi(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    @Override
    public Vector3f scale(Vector3i v) {
        return new Vector3fi(
                x * v.getX(),
                y * v.getY(),
                z * v.getZ()
        );
    }

    @Override
    public Vector3f scale(float scalar) {
        return new Vector3fi(
                x * scalar,
                y * scalar,
                z * scalar
        );
    }

    @Override
    public Vector3f cross(Vector3f v) {
        return new Vector3fi(
                (y * v.getZ()) - (z * v.getY()),
                (z * v.getX()) - (x * v.getZ()),
                (x * v.getY()) - (y * v.getX())
        );
    }

    @Override
    public Vector3fm toMutable() {
        return new Vector3fm(this);
    }

    @Override
    public Vector3fm toMutableCopy() {
        return new Vector3fm(this);
    }

    @Override
    public Vector3fi toImmutable() {
        return this;
    }

    @Override
    public Vector3fi toImmutableCopy() {
        return new Vector3fi(this);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Vector3f v)) {
            return false;
        }

        return v.getX() == x && v.getY() == y && v.getZ() == z;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y, z);
    }
}
