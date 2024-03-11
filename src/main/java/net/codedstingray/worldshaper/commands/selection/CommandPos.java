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
import net.codedstingray.worldshaper.chat.TextColor;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.chat.ChatMessageFormatter;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.UUID;

import static net.codedstingray.worldshaper.chat.MessageSender.*;
import static net.codedstingray.worldshaper.util.world.LocationUtils.locationToBlockVector;

@ParametersAreNonnullByDefault
public class CommandPos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }

        PluginData pluginData = WorldShaper.getInstance().getPluginData();
        PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());
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

        int maxSelectionSize = pluginData.getWorldShaperConfiguration().getMaxSelectionSize();
        if (index >= maxSelectionSize) {
            sendWorldShaperWarningMessage(player, "Max selection size exceeded. Tried to set index " +
                    ChatMessageFormatter.ACCENT_COLOR + (index + 1) + TextColor.RESET + ", but max is " +
                    ChatMessageFormatter.ACCENT_COLOR + maxSelectionSize + TextColor.RESET + ".");
            return true;
        }

        Location playerLocation = player.getLocation();
        Vector3i playerPosition = locationToBlockVector(playerLocation);
        UUID world = Objects.requireNonNull(playerLocation.getWorld()).getUID();

        boolean changed = selection.setControlPosition(index, playerPosition, world);
        sendWorldShaperMessage(player, ChatMessageFormatter.positionSetMessage(index, playerPosition, changed));

        return true;
    }
}
