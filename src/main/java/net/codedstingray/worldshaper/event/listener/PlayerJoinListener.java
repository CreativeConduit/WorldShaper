package net.codedstingray.worldshaper.event.listener;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.WorldShaperManifest;
import net.codedstingray.worldshaper.selection.PlayerSelectionMap;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.UUID;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;

/**
 * A {@link Listener Bukkit event listener} handling behavior on player join.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendWorldShaperMessage(event.getPlayer(),
                "Using " +
                TextColor.AQUA + "WorldShaper" +
                TextColor.RESET + " version " +
                TextColor.AQUA + WorldShaperManifest.PLUGIN_VERSION +
                TextColor.RESET + ".");

        PlayerSelectionMap playerSelectionMap = WorldShaper.getInstance().getPlayerSelectionMap();

        if (playerSelectionMap.getSelection(event.getPlayer().getUniqueId()) == null) {
            WorldShaper.getInstance().getPlayerSelectionMap().addNewPlayerSelection(event.getPlayer().getUniqueId());
        }

        Map<UUID, SelectionType> playerSelectionTypeMap = WorldShaper.getInstance().getPlayerSelectionTypeMap();

        if (playerSelectionTypeMap.get(event.getPlayer().getUniqueId()) == null) {
            WorldShaper.getInstance().getPlayerSelectionTypeMap().put(
                    event.getPlayer().getUniqueId(),
                    WorldShaper.getInstance().getDefaultSelectionType()
            );
        }
    }
}
