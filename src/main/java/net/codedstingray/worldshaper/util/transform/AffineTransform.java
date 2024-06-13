package net.codedstingray.worldshaper.util.transform;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3f;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3fi;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record AffineTransform(
        // coefficients for x coordinate.
        double m00, double m01, double m02, double m03,
        // coefficients for y coordinate.
        double m10, double m11, double m12, double m13,
        // coefficients for z coordinate.
        double m20, double m21, double m22, double m23
) implements Transform {

    public AffineTransform() {
        this(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0
        );
    }

    @Override
    public boolean isIdentity() {
        return m00 == m11 && m11 == m22 && m22 == 1
                && m01 == m02 && m02 == m03 && m03 == 0
                && m10 == m12 && m12 == m13 && m13 == 0
                && m20 == m21 && m21 == m23 && m23 == 0;
    }

    /**
     * Computes the determinant of this transform. Can be zero.
     *
     * @return the determinant of the transform.
     */
    private double determinant() {
        return m00 * (m11 * m22 - m12 * m21) - m01 * (m10 * m22 - m20 * m12)
                + m02 * (m10 * m21 - m20 * m11);
    }

    @Override
    public Vector3f apply(Vector3f v) {
        return new Vector3fi(
                (float) (v.getX() * m00 + v.getY() * m01 + v.getZ() * m02 + m03),
                (float) (v.getX() * m10 + v.getY() * m11 + v.getZ() * m12 + m13),
                (float) (v.getX() * m20 + v.getY() * m21 + v.getZ() * m22 + m23));
    }

    @Override
    public Transform inverse() {
        if (isIdentity()) {
            return this;
        }
        double det = this.determinant();
        return new AffineTransform(
                (m11 * m22 - m21 * m12) / det,
                (m21 * m02 - m01 * m22) / det,
                (m01 * m12 - m11 * m02) / det,
                (m01 * (m22 * m13 - m12 * m23) + m02 * (m11 * m23 - m21 * m13)
                        - m03 * (m11 * m22 - m21 * m12)) / det,
                (m20 * m12 - m10 * m22) / det,
                (m00 * m22 - m20 * m02) / det,
                (m10 * m02 - m00 * m12) / det,
                (m00 * (m12 * m23 - m22 * m13) - m02 * (m10 * m23 - m20 * m13)
                        + m03 * (m10 * m22 - m20 * m12)) / det,
                (m10 * m21 - m20 * m11) / det,
                (m20 * m01 - m00 * m21) / det,
                (m00 * m11 - m10 * m01) / det,
                (m00 * (m21 * m13 - m11 * m23) + m01 * (m10 * m23 - m20 * m13)
                        - m03 * (m10 * m21 - m20 * m11)) / det);
    }

    @Override
    public Transform combine(Transform other) {
        if (other instanceof AffineTransform otherTransform) {
            return concatenate(otherTransform);
        } else {
            return new CombinedTransform(this, other);
        }
    }

    /**
     * Returns the affine transform created by applying first the affine
     * transform given by the parameters, then this affine transform.
     *
     * @return the composition this * that
     */
    public AffineTransform concatenate(double o00, double o01, double o02, double o03,
                                       double o10, double o11, double o12, double o13,
                                       double o20, double o21, double o22, double o23) {
        double n00 = m00 * o00 + m01 * o10 + m02 * o20;
        double n01 = m00 * o01 + m01 * o11 + m02 * o21;
        double n02 = m00 * o02 + m01 * o12 + m02 * o22;
        double n03 = m00 * o03 + m01 * o13 + m02 * o23 + m03;
        double n10 = m10 * o00 + m11 * o10 + m12 * o20;
        double n11 = m10 * o01 + m11 * o11 + m12 * o21;
        double n12 = m10 * o02 + m11 * o12 + m12 * o22;
        double n13 = m10 * o03 + m11 * o13 + m12 * o23 + m13;
        double n20 = m20 * o00 + m21 * o10 + m22 * o20;
        double n21 = m20 * o01 + m21 * o11 + m22 * o21;
        double n22 = m20 * o02 + m21 * o12 + m22 * o22;
        double n23 = m20 * o03 + m21 * o13 + m22 * o23 + m23;
        return new AffineTransform(
                n00, n01, n02, n03,
                n10, n11, n12, n13,
                n20, n21, n22, n23);
    }

    /**
     * Returns the affine transform created by applying first the affine
     * transform given by {@code that}, then this affine transform.
     *
     * @param that the transform to apply first
     * @return the composition this * that
     */
    public AffineTransform concatenate(AffineTransform that) {
        return concatenate(
                that.m00, that.m01, that.m02, that.m03,
                that.m10, that.m11, that.m12, that.m13,
                that.m20, that.m21, that.m22, that.m23
        );
    }

    /**
     * Return the affine transform created by applying first this affine
     * transform, then the affine transform given by {@code that}.
     *
     * @param that the transform to apply in a second step
     * @return the composition that * this
     */
    public AffineTransform preConcatenate(AffineTransform that) {
        double n00 = that.m00 * m00 + that.m01 * m10 + that.m02 * m20;
        double n01 = that.m00 * m01 + that.m01 * m11 + that.m02 * m21;
        double n02 = that.m00 * m02 + that.m01 * m12 + that.m02 * m22;
        double n03 = that.m00 * m03 + that.m01 * m13 + that.m02 * m23 + that.m03;
        double n10 = that.m10 * m00 + that.m11 * m10 + that.m12 * m20;
        double n11 = that.m10 * m01 + that.m11 * m11 + that.m12 * m21;
        double n12 = that.m10 * m02 + that.m11 * m12 + that.m12 * m22;
        double n13 = that.m10 * m03 + that.m11 * m13 + that.m12 * m23 + that.m13;
        double n20 = that.m20 * m00 + that.m21 * m10 + that.m22 * m20;
        double n21 = that.m20 * m01 + that.m21 * m11 + that.m22 * m21;
        double n22 = that.m20 * m02 + that.m21 * m12 + that.m22 * m22;
        double n23 = that.m20 * m03 + that.m21 * m13 + that.m22 * m23 + that.m23;
        return new AffineTransform(
                n00, n01, n02, n03,
                n10, n11, n12, n13,
                n20, n21, n22, n23);
    }

    public AffineTransform translate(Vector3f vec) {
        return translate(vec.getX(), vec.getY(), vec.getZ());
    }

    public AffineTransform translate(double x, double y, double z) {
        return concatenate(1, 0, 0, x, 0, 1, 0, y, 0, 0, 1, z);
    }

    public AffineTransform rotateX(double theta) {
        double cot = Math.cos(Math.toRadians(theta));
        double sit = Math.sin(Math.toRadians(theta));
        return concatenate(
                1, 0, 0, 0,
                0, cot, -sit, 0,
                0, sit, cot, 0
        );
    }

    public AffineTransform rotateY(double theta) {
        double cot = Math.cos(Math.toRadians(theta));
        double sit = Math.sin(Math.toRadians(theta));
        return concatenate(
                cot, 0, sit, 0,
                0, 1, 0, 0,
                -sit, 0, cot, 0
        );
    }

    public AffineTransform rotateZ(double theta) {
        double cot = Math.cos(Math.toRadians(theta));
        double sit = Math.sin(Math.toRadians(theta));
        return concatenate(
                cot, -sit, 0, 0,
                sit, cot, 0, 0,
                0, 0, 1, 0
        );
    }

    public AffineTransform scale(double s) {
        return scale(s, s, s);
    }

    public AffineTransform scale(double sx, double sy, double sz) {
        return concatenate(sx, 0, 0, 0, 0, sy, 0, 0, 0, 0, sz, 0);
    }

    public AffineTransform scale(Vector3f vec) {
        return scale(vec.getX(), vec.getY(), vec.getZ());
    }

    /**
     * Returns if this affine transform represents a horizontal flip.
     */
    public boolean isHorizontalFlip() {
        // use the determinant of the x-z submatrix to check if this is a horizontal flip
        return m00 * m22 - m02 * m20 < 0;
    }

    /**
     * Returns if this affine transform represents a vertical flip.
     */
    public boolean isVerticalFlip() {
        return m11 < 0;
    }

    @Override
    public String toString() {
        return String.format("Affine[%g %g %g %g, %g %g %g %g, %g %g %g %g]}", m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23);
    }
}
