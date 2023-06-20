package net.codedstingray.worldshaper.event.listener;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.WorldShaperManifest;
import net.codedstingray.worldshaper.selection.PlayerSelectionMap;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * A {@link Listener Bukkit event listener} handling behavior on player join.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Using " +
                TextColor.AQUA + "WorldShaper" +
                TextColor.RESET + " version " +
                TextColor.AQUA + WorldShaperManifest.PLUGIN_VERSION +
                TextColor.RESET + ".");

        PlayerSelectionMap playerSelectionMap = WorldShaper.getInstance().getPlayerSelectionMap();

        if (playerSelectionMap.getSelection(event.getPlayer().getUniqueId()) == null) {
            WorldShaper.getInstance().getPlayerSelectionMap().addNewPlayerSelection(event.getPlayer().getUniqueId());
        }
    }
}
