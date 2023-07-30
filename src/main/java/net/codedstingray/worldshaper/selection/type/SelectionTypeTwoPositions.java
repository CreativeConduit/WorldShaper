package net.codedstingray.worldshaper.selection.type;

import org.bukkit.entity.Player;
import org.joml.Vector3i;

/**
 * A Selection Type representing the selection of only 2 control positions, the first position being selected with a
 * left-click, the second position being selected with a right-click.
 */
public class SelectionTypeTwoPositions implements SelectionType {

    public static final String NAME = "twoPositions";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int onLeftClick(Player player, Vector3i clickedPosition) {
        return 0;
    }

    @Override
    public int onRightClick(Player player, Vector3i clickedPosition) {
        return 1;
    }
}
