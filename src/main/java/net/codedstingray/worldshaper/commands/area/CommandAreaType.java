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
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Set;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.*;

@ParametersAreNonnullByDefault
public class CommandAreaType implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }
        PluginData pluginData = WorldShaper.getInstance().getPluginData();
        PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());

        if (args.length == 0) {
            Area area = playerData.getArea();
            Set<String> allAreaTypes = pluginData.getAllRegisteredAreaTypes();

            sendWorldShaperMessage(player, "Your current area is of type " + TextColor.AQUA + "\"" + area.getName() + "\"");
            sendGroupedMessages(player, "The following area types are available", allAreaTypes);

            return true;
        }

        String areaName = args[0];
        boolean success = playerData.setArea(areaName);

        if (success) {
            sendWorldShaperMessage(player, "Area set to type " + TextColor.AQUA + "\"" + areaName + "\"");
            return true;
        } else {
            sendWorldShaperErrorMessage(player, "Area tof type \"" + areaName + "\" does not exist.");
            return false;
        }
    }
}
