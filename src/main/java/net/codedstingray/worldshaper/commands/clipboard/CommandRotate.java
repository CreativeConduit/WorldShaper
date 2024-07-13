/*
 * WorldShaper, a powerful in-game map editing addon for WorldEdit
 * Copyright (C) 2024 CreativeConduit
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

package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.chat.TextColor;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.commands.CommandInputParseUtils;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.Axis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.ACCENT_COLOR;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperMessage;
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandRotate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 2);

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Clipboard clipboard = getClipBoardFromPlayerData(playerData);

            int rotationValue = CommandInputParseUtils.getRotationFromArgument(args[0]);
            Axis axis = args.length == 1 ? Axis.Y : CommandInputParseUtils.getAxisFromArgument(args[1]);

            Vector3i bv = axis.baseVector;

            clipboard.rotate(bv.getX() * rotationValue, bv.getY() * rotationValue, bv.getZ() * rotationValue);

            sendWorldShaperMessage(player, "Clipboard rotated by " + ACCENT_COLOR + rotationValue + "\u00B0" +
                    TextColor.RESET+ " on the " + ACCENT_COLOR + axis.toString().toLowerCase() + TextColor.RESET + "-axis");

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
