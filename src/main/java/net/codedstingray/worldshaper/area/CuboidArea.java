package net.codedstingray.worldshaper.area;

import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.world.VectorUtils;
import org.joml.Vector3i;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CuboidArea implements Area {

    public static final String NAME = "cuboid";

    private Vector3i minPos;
    private Vector3i maxPos;

    private boolean isValid;

    @Override
    public void updateArea(Selection selection) {
        Vector3i pos1 = selection.getControlPosition(0);
        Vector3i pos2 = selection.getControlPosition(1);

        if (pos1 != null && pos2 != null) {
            minPos = new Vector3i(
                    Math.min(pos1.x, pos2.x),
                    Math.min(pos1.y, pos2.y),
                    Math.min(pos1.z, pos2.z)
            );
            maxPos = new Vector3i(
                    Math.max(pos1.x, pos2.x),
                    Math.max(pos1.y, pos2.y),
                    Math.max(pos1.z, pos2.z)
            );
            isValid = true;
        } else {
            minPos = null;
            maxPos = null;
            isValid = false;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public Iterator<Vector3i> iterator() {
        return  new Iterator<>() {
            private final Vector3i current = new Vector3i(minPos);

            @Override
            public boolean hasNext() {
                return current.y <= maxPos.y;
            }

            @Override
            public Vector3i next() {
                if(!hasNext()) throw new NoSuchElementException();
                Vector3i result = new Vector3i(current);

                //move pointer
                current.add(VectorUtils.BASE_X);
                if(current.x > maxPos.x) {
                    current.x = minPos.x;
                    current.add(VectorUtils.BASE_Z);
                    if(current.z > maxPos.z) {
                        current.z = minPos.z;
                        current.add(VectorUtils.BASE_Y);
                    }
                }

                return result;
            }
        };
    }
}
