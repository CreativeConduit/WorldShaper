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
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Set;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.*;
import static net.codedstingray.worldshaper.chat.MessageSender.*;

@ParametersAreNonnullByDefault
public class CommandSelectionType implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }
        PluginData pluginData = WorldShaper.getInstance().getPluginData();
        PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());

        if (args.length == 0) {
            SelectionType selectionType = playerData.getSelectionType();
            Set<String> allSelectionTypes = pluginData.getAllRegisteredSelectionTypes();

            sendWorldShaperMessage(player, "Your current selection type is " + ACCENT_COLOR + "\"" + selectionType.getName() + "\"");
            sendGroupedMessages(player, "The following selection types are available", allSelectionTypes);

            return true;
        }

        String selectionTypeName = args[0];
        SelectionType selectionType = pluginData.getSelectionTypeByName(selectionTypeName);
        if (selectionType == null) {
            sendWorldShaperErrorMessage(player, "Selection Type \"" + selectionTypeName + "\" does not exist.");
            return false;
        }

        playerData.setSelectionType(selectionType);
        sendWorldShaperMessage(player, "Selection Type set to " + ACCENT_COLOR + "\"" + selectionType.getName() + "\"");
        return true;
    }
}
