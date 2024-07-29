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
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandSelectionType implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 0, 1);

            PluginData pluginData = WorldShaper.getInstance().getPluginData();
            PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());

            if (args.length == 0) {
                SelectionType selectionType = playerData.getSelectionType();
                Set<String> allSelectionTypes = pluginData.getAllRegisteredSelectionTypes();

                player.sendMessage(messageBuilder(MessageLevel.INFO, true)
                        .t("Your current selection type is ").a("\"" + selectionType.getName() + "\"").t(".")
                        .build());
                player.sendMessage(groupedMessages("The following selection types are available", allSelectionTypes));

                return true;
            }

            String selectionTypeName = args[0];
            SelectionType selectionType = pluginData.getSelectionTypeByName(selectionTypeName);
            if (selectionType == null) {
                player.sendMessage(asWorldShaperMessage(MessageLevel.ERROR, "Selection Type \"" + selectionTypeName + "\" does not exist."));
                return false;
            }

            playerData.setSelectionType(selectionType);
            player.sendMessage(messageBuilder(MessageLevel.INFO, true)
                    .t("Selection Type set to ").a("\"" + selectionType.getName() + "\"").a(".")
                    .build());

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
