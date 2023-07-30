package net.codedstingray.worldshaper.selection.type;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.Selection;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import java.util.UUID;

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
        int index = 0;
        setSelectionControlPosition(index, player, clickedPosition);
        return index;
    }

    @Override
    public int onRightClick(Player player, Vector3i clickedPosition) {
        int index = 1;
        setSelectionControlPosition(index, player, clickedPosition);
        return index;
    }

    private void setSelectionControlPosition(int index, Player player, Vector3i clickedPosition) {
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        UUID world = player.getWorld().getUID();
        Selection selection = playerData.getSelection();
        selection.setControlPosition(index, clickedPosition, world);
    }
}
