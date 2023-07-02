package net.codedstingray.worldshaper.selection.type;

import org.bukkit.entity.Player;
import org.joml.Vector3i;

/**
 * The Selection Type defines the behavior of the Selection Wand.
 */
public interface SelectionType {

    String getName();

    /**
     * Selection Type behavior on a left click with the selection wand.
     *
     * @param player The player who performed the interaction
     * @param clickedPosition The block position that was clicked
     * @return The index at which the position was added
     */
    int onLeftClick(Player player, Vector3i clickedPosition);

    /**
     * Selection Type behavior on a right click with the selection wand.
     *
     * @param player The player who performed the interaction
     * @param clickedPosition The block position that was clicked
     * @return The index at which the position was added
     */
    int onRightClick(Player player, Vector3i clickedPosition);
}
