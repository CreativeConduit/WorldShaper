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

package net.codedstingray.worldshaper.commands.utility;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.worldShaperInfoMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendRawMessage;
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;
import static net.codedstingray.worldshaper.permission.Permissions.EDIT_PERMISSIONS;

/**
 * {@link CommandExecutor} for the /worldshaper command.
 */
@ParametersAreNonnullByDefault
public class CommandWorldShaper implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            checkPermissionsAnyOf(sender, EDIT_PERMISSIONS);
            verifyArgumentSize(args, 0, 0);

            sendRawMessage(sender, worldShaperInfoMessage());

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
