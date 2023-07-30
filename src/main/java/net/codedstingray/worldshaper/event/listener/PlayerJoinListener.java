package net.codedstingray.worldshaper.event.listener;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.WorldShaperManifest;
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

        PluginData pluginData = WorldShaper.getInstance().getPluginData();
        UUID playerUUID = event.getPlayer().getUniqueId();

        pluginData.createPlayerDataIfNotPresent(playerUUID);
    }
}
