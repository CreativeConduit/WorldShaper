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
import net.codedstingray.worldshaper.chat.ChatMessageFormatter;
import net.codedstingray.worldshaper.chat.TextColor;
import net.codedstingray.worldshaper.util.world.Direction;
import net.codedstingray.worldshaper.util.world.DirectionUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperErrorMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperMessage;

@ParametersAreNonnullByDefault
public class CommandExpand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }

        if (args.length == 0) {
            sendWorldShaperErrorMessage(player, "You must provide a distance");
            return false;
        }

        int amount = Integer.parseInt(args[0]);
        Direction direction = DirectionUtils.determineDirectionFromPlayer(player, args.length > 1 ? args[1] : null);

        if (direction == null) {
            return false;
        }

        Area area = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId()).getArea();
        area.expand(direction, amount);

        sendWorldShaperMessage(player, "Expanded area " + ChatMessageFormatter.ACCENT_COLOR + amount +
                TextColor.RESET + " blocks " + ChatMessageFormatter.ACCENT_COLOR + direction.name().toLowerCase());

        return true;
    }
}
