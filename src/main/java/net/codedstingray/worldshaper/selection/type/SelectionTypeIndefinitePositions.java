package net.codedstingray.worldshaper.selection.type;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.Selection;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

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
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();
        selection.clearControlPositions();
        return 0;
    }

    @Override
    public int onRightClick(Player player, Vector3i clickedPosition) {
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();
        return selection.getControlPositions().size();
    }
}
