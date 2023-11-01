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

package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.chat.ChatMessageFormatter;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;

import static net.codedstingray.worldshaper.chat.MessageSender.sendGroupedMessages;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperErrorMessage;

@ParametersAreNonnullByDefault
public class CommandPositions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();

        LinkedList<String> messages = new LinkedList<>();
        for (int i = 0; i < selection.getControlPositions().size(); i++) {
            Vector3i controlPosition = selection.getControlPosition(i);
            if (controlPosition != null) {
                messages.add("[" + (i + 1) + "] " + ChatMessageFormatter.vectorToString(controlPosition));
            } else {
                messages.add("[" + (i + 1) + "] - not set -");
            }
        }
        sendGroupedMessages(player, "Your current control positions", messages);

        return true;
    }
}
