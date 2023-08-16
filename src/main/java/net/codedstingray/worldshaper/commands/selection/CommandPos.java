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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.UUID;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperErrorMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperMessage;
import static net.codedstingray.worldshaper.util.world.LocationUtils.locationToBlockVector;

@ParametersAreNonnullByDefault
public class CommandPos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }

        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
        Selection selection = playerData.getSelection();

        int index;
        if (args.length > 0) {
            try {
                index = Integer.parseInt(args[0]) - 1;
            } catch (NumberFormatException e) {
                sendWorldShaperErrorMessage(player, "Index for /pos must be an integer.");
                return false;
            }

            if (index < 0) {
                sendWorldShaperErrorMessage(player, "Index for /pos must be 1 or higher.");
                return false;
            }
        } else {
            index = selection.getControlPositions().size();
        }

        Location playerLocation = player.getLocation();
        Vector3i playerPosition = locationToBlockVector(playerLocation);
        UUID world = Objects.requireNonNull(playerLocation.getWorld()).getUID();

        boolean changed = selection.setControlPosition(index, playerPosition, world);
        sendWorldShaperMessage(player, ChatMessageFormatter.positionSetMessage(index, playerPosition, changed));

        return true;
    }
}
