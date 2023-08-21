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

package net.codedstingray.worldshaper.commands.action;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.ActionController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperErrorMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperWarningMessage;

@ParametersAreNonnullByDefault
public class CommandUndo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return true;
        }

        ActionController actionController = WorldShaper.getInstance().getActionController();

        if (actionController.isMainStackEmpty()) {
            sendWorldShaperWarningMessage(player, "No action to undo");
            return true;
        }

        Action lastAction = actionController.peekMainStack();
        if (!lastAction.worldUUID.equals(player.getWorld().getUID())) {
            sendWorldShaperErrorMessage(player, "Cannot perform undo: the last action took place in a different world from the one you are in.");
            return true;
        }

        actionController.undoAction();

        return true;
    }
}
