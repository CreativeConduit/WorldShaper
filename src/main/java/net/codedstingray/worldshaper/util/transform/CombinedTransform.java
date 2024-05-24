package net.codedstingray.worldshaper.util.transform;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ParametersAreNonnullByDefault
public class CombinedTransform implements Transform {

    private final Transform[] transforms;

    /**
     * Create a new combined transformation.
     *
     * @param transforms a list of transformations
     */
    public CombinedTransform(Transform... transforms) {
        this.transforms = Arrays.copyOf(transforms, transforms.length);
    }

    /**
     * Create a new combined transformation.
     *
     * @param transforms a list of transformations
     */
    public CombinedTransform(Collection<Transform> transforms) {
        this(transforms.toArray(new Transform[transforms.size()]));
    }

    @Override
    public boolean isIdentity() {
        for (Transform transform : transforms) {
            if (!transform.isIdentity()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Vector3f apply(Vector3f vector) {
        for (Transform transform : transforms) {
            vector = transform.apply(vector);
        }
        return vector;
    }

    @Override
    public Transform inverse() {
        List<Transform> list = new ArrayList<>();
        for (int i = transforms.length - 1; i >= 0; i--) {
            list.add(transforms[i].inverse());
        }
        return new CombinedTransform(list);
    }

    @Override
    public Transform combine(Transform other) {
        if (other instanceof CombinedTransform combinedOther) {
            Transform[] newTransforms = new Transform[transforms.length + combinedOther.transforms.length];
            System.arraycopy(transforms, 0, newTransforms, 0, transforms.length);
            System.arraycopy(combinedOther.transforms, 0, newTransforms, transforms.length, combinedOther.transforms.length);
            return new CombinedTransform(newTransforms);
        } else {
            return new CombinedTransform(this, other);
        }
    }
}
