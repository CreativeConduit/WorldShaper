/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
 * Copyright (C) 2023-2024 CreativeConduit
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
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.chat.ChatMessageFormatter.MessageLevel;
import net.codedstingray.worldshaper.chat.WorldShaperMessages;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.items.SelectionWand;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.asWorldShaperMessage;
import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.messageBuilder;
import static net.codedstingray.worldshaper.permission.Permissions.PERMISSION_SELECTION;

public class SelectionWandListener implements Listener {

    @EventHandler
    public void onMouseClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (item == null || clickedBlock == null || !item.isSimilar(SelectionWand.SELECTION_WAND)) {
            return;
        }

        event.setCancelled(true);

        if (!player.hasPermission(PERMISSION_SELECTION)) {
            player.sendMessage(asWorldShaperMessage(MessageLevel.WARNING, "You do not have the permission to use the WorldShaper wand."));
            return;
        }

        PluginData pluginData = WorldShaper.getInstance().getPluginData();
        PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());
        SelectionType selectionType = playerData.getSelectionType();
        Area area = playerData.getArea();

        Vector3i clickedPosition = LocationUtils.locationToBlockVector(clickedBlock.getLocation());

        int index;
        switch (action) {
            case LEFT_CLICK_BLOCK -> index = selectionType.onLeftClick(player, clickedPosition);
            case RIGHT_CLICK_BLOCK -> index = selectionType.onRightClick(player, clickedPosition);
            default -> {
                return;
            }
        }

        int maxSelectionSize = pluginData.getWorldShaperConfiguration().getMaxSelectionSize();
        if (index >= maxSelectionSize) {
            player.sendMessage(messageBuilder(MessageLevel.WARNING, true)
                    .t("Max selection size exceeded. Tried to set index ").a(index + 1)
                    .t(", but max is ").a(maxSelectionSize).t(".")
                    .build());
            return;
        }

        boolean changed = playerData.getSelection().setControlPosition(index, clickedPosition, player.getWorld().getUID());
        player.sendMessage(WorldShaperMessages.positionSetMessage(index, clickedPosition, changed, area.getSize()));
    }
}
