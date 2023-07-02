package net.codedstingray.worldshaper.area;

import net.codedstingray.worldshaper.selection.Selection;
import org.joml.Vector3i;

public interface Area extends Iterable<Vector3i> {
    void updateArea(Selection selection);

    String getName();
    boolean isValid();
}
