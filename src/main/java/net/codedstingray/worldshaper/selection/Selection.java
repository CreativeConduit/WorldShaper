package net.codedstingray.worldshaper.selection;

import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * A Selection is defined by an ordered set of control positions.
 */
@ParametersAreNonnullByDefault
public class Selection implements Iterable<Vector3i> {

    private UUID worldUUID;

    private final List<Vector3i> controlPositions = new ArrayList<>();
    private List<Vector3i> unmodifiableControlPositions;

    /**
     * The {@link UUID} of the {@link org.bukkit.World world} the selection is currently in. If a new Position is set
     * into a different world, all other positions will be deleted and this world UUID will be updated to the new world.
     *
     * @return The UUID of the current world
     */
    public UUID getWorldUUID() {
        return worldUUID;
    }

    /**
     * Adds a control position to the end of the list of control positions. If the given world UUID does not match the
     * current world UUID, the list of positions will be cleared before the new position gets added. The world UUID will
     * then be set to the given UUID.
     *
     * @param position The position to be set
     * @param world The UUID of the world the position has been set in
     * @return The index of the added position
     */
    public int addControlPosition(Vector3i position, UUID world) {
        Objects.requireNonNull(position, "A selection's control position must not be null.");
        Objects.requireNonNull(world, "A selection's world UUID must not be null.");

        if (world != this.worldUUID) {
            controlPositions.clear();
            this.worldUUID = world;
        }

        int index = controlPositions.indexOf(null);
        if (index == -1) {
            controlPositions.add(position);
            index = controlPositions.size() - 1;
        } else {
            controlPositions.set(index, position);
        }

        unmodifiableControlPositions = null;

        return index;
    }

    /**
     * Sets the control position at the given index to the given position. If the given world UUID does not match the
     * current world UUID, the list of positions will be cleared before the new position gets added. The world UUID will
     * then be set to the given UUID.
     *
     * @param index The index within the set of control positions at which the control position should be set
     * @param position The position to be set
     * @param world The UUID of the world the position has been set in
     */
    public void setControlPosition(int index, Vector3i position, UUID world) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Selection control position index must be at least 0.");
        }
        Objects.requireNonNull(position, "A selection's control position must not be null.");
        Objects.requireNonNull(world, "A selection's world UUID must not be null.");

        if (!world.equals(this.worldUUID)) {
            controlPositions.clear();
            this.worldUUID = world;
        }

        if (index >= controlPositions.size()) {
            for (int i = controlPositions.size(); i <= index; i++) {
                controlPositions.add(null);
            }
        }
        controlPositions.set(index, position);
        unmodifiableControlPositions = null;
    }

    public boolean removeControlPosition(int index) {
        if (index >= controlPositions.size() || controlPositions.get(index) == null) {
            return false;
        }
        controlPositions.set(index, null);
        while (controlPositions.size() > 0 && controlPositions.get(controlPositions.size() - 1) == null) {
            controlPositions.remove(controlPositions.size() - 1);
        }

        unmodifiableControlPositions = null;
        return true;
    }

    /**
     * Returns the control position at the given index within the set of control positions.
     *
     * @param index The index of the queried control position
     * @return The control position at the given index, or null if no control position is found at this index.
     */
    public Vector3i getControlPosition(int index) {
        return controlPositions.get(index);
    }

    /**
     * Returns the control positions.
     *
     * @return An unmodifiable copy of the control positions
     */
    public List<Vector3i> getControlPositions() {
        if (unmodifiableControlPositions == null) {
            unmodifiableControlPositions = Collections.unmodifiableList(controlPositions);
        }
        return unmodifiableControlPositions;
    }

    /**
     * Clears the control positions and world UUID.
     */
    public void clearControlPositions() {
        controlPositions.clear();
        unmodifiableControlPositions = null;
        worldUUID = null;
    }

    @Override
    public Iterator<Vector3i> iterator() {
        return controlPositions.iterator();
    }
}
