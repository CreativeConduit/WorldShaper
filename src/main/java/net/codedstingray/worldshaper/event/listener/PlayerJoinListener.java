/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2023 CodedStingray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.codedstingray.worldshaper.event.listener;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.permission.PermissionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.playerJoinMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendRawMessage;
import static net.codedstingray.worldshaper.permission.Permissions.ALL_PERMISSIONS;

/**
 * A {@link Listener Bukkit event listener} handling behavior on player join.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginData pluginData = WorldShaper.getInstance().getPluginData();

        if (pluginData.getWorldShaperConfiguration().getAlwaysShowJoinMessage() ||
                PermissionUtil.hasAnyOf(event.getPlayer(), ALL_PERMISSIONS)) {
            sendRawMessage(event.getPlayer(), playerJoinMessage());
        }

        UUID playerUUID = event.getPlayer().getUniqueId();
        pluginData.createPlayerDataIfNotPresent(playerUUID);
    }
}
