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
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.chat.ChatMessageFormatter.MessageLevel;
import net.codedstingray.worldshaper.chat.WorldShaperMessages;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.data.PluginData;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.UUID;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.asWorldShaperMessage;
import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.messageBuilder;
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;
import static net.codedstingray.worldshaper.util.world.LocationUtils.locationToBlockVector;

@ParametersAreNonnullByDefault
public class CommandPos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 0, 1);

            PluginData pluginData = WorldShaper.getInstance().getPluginData();
            PlayerData playerData = pluginData.getPlayerDataForPlayer(player.getUniqueId());
            Selection selection = playerData.getSelection();
            Area area = playerData.getArea();

            int index;
            if (args.length > 0) {
                try {
                    index = Integer.parseInt(args[0]) - 1;
                } catch (NumberFormatException e) {
                    player.sendMessage(asWorldShaperMessage(MessageLevel.ERROR, "Index for /pos must be an integer."));
                    return false;
                }

                if (index < 0) {
                    player.sendMessage(asWorldShaperMessage(MessageLevel.ERROR, "Index for /pos must be 1 or higher."));
                    return false;
                }
            } else {
                index = selection.getControlPositions().size();
            }

            int maxSelectionSize = pluginData.getWorldShaperConfiguration().getMaxSelectionSize();
            if (index >= maxSelectionSize) {
                player.sendMessage(messageBuilder(MessageLevel.WARNING, true)
                        .t("Max selection size exceeded. Tried to set index ").a(index + 1)
                        .t(", but max is ").a(maxSelectionSize).t(".")
                        .build());
                return true;
            }

            Location playerLocation = player.getLocation();
            Vector3i playerPosition = locationToBlockVector(playerLocation);
            UUID world = Objects.requireNonNull(playerLocation.getWorld()).getUID();

            boolean changed = selection.setControlPosition(index, playerPosition, world);
            player.sendMessage(WorldShaperMessages.positionSetMessage(index, playerPosition, changed, area.getSize()));

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
