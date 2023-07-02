package net.codedstingray.worldshaper.selection.type;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.selection.Selection;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import java.util.UUID;

/**
 * A Selection Type representing the selection of an indefinite number of control positions, a right click adding the
 * clicked position to the first unset index, a left click clearing the list of control points before adding the
 * clicked position.
 */
public class SelectionTypeIndefinitePositions implements SelectionType {

    public static final String NAME = "indefinitePositions";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int onLeftClick(Player player, Vector3i clickedPosition) {
        UUID world = player.getWorld().getUID();

        Selection selection = WorldShaper.getInstance().getPlayerSelectionMap().getSelection(player.getUniqueId());
        selection.clearControlPositions();
        return selection.addControlPosition(clickedPosition, world);
    }

    @Override
    public int onRightClick(Player player, Vector3i clickedPosition) {
        UUID world = player.getWorld().getUID();

        Selection selection = WorldShaper.getInstance().getPlayerSelectionMap().getSelection(player.getUniqueId());
        return selection.addControlPosition(clickedPosition, world);
    }
}
