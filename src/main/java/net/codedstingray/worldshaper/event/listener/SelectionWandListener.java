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
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.items.SelectionWand;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.chat.ChatMessageFormatter;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperWarningMessage;
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

        if (!player.hasPermission(PERMISSION_SELECTION)) {
            sendWorldShaperWarningMessage(player, "You do not have the permission to use the WorldShaper wand.");
            return;
        }

        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
        SelectionType selectionType = playerData.getSelectionType();

        Vector3i clickedPosition = LocationUtils.locationToBlockVector(clickedBlock.getLocation());

        int index;
        switch (action) {
            case LEFT_CLICK_BLOCK -> index = selectionType.onLeftClick(player, clickedPosition);
            case RIGHT_CLICK_BLOCK -> index = selectionType.onRightClick(player, clickedPosition);
            default -> {
                return;
            }
        }

        boolean changed = playerData.getSelection().setControlPosition(index, clickedPosition, player.getWorld().getUID());
        sendWorldShaperMessage(player, ChatMessageFormatter.positionSetMessage(index, clickedPosition, changed));
        event.setCancelled(true);
    }
}
