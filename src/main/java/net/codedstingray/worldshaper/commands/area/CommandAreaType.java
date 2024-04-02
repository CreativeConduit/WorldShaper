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

package net.codedstingray.worldshaper.commands.area;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Set;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.ACCENT_COLOR;
import static net.codedstingray.worldshaper.chat.MessageSender.*;
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandAreaType implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 0, 1);
            PluginData pluginData = WorldShaper.getInstance().getPluginData();
            PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());

            if (args.length == 0) {
                Area area = playerData.getArea();
                Set<String> allAreaTypes = pluginData.getAllRegisteredAreaTypes();

                sendWorldShaperMessage(player, "Your current area is of type " + ACCENT_COLOR + "\"" + area.getName() + "\"");
                sendGroupedMessages(player, "The following area types are available", allAreaTypes);

                return true;
            }

            String areaName = args[0];
            boolean success = playerData.setArea(areaName);

            if (success) {
                SelectionType selectionType = playerData.getArea().getDefaultSelectionType();
                playerData.setSelectionType(selectionType);

                sendWorldShaperMessage(player, "Area set to type " + ACCENT_COLOR + "\"" + areaName + "\"");
                sendWorldShaperMessage(player, "Selection Type set to " + ACCENT_COLOR + "\"" + selectionType.getName() + "\"");
                return true;
            } else {
                sendWorldShaperErrorMessage(player, "Area of type \"" + areaName + "\" does not exist.");
                return false;
            }
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
