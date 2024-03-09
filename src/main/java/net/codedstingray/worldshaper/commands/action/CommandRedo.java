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
import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.permission.PermissionUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperErrorMessage;
import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperWarningMessage;
import static net.codedstingray.worldshaper.permission.Permissions.EDIT_PERMISSIONS;

@ParametersAreNonnullByDefault
public class CommandRedo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return true;
        }

        if (!PermissionUtil.hasAnyOf(player, EDIT_PERMISSIONS)) {
            sendWorldShaperWarningMessage(player, "You do not have the permission to use this command.");
            return true;
        }

        ActionController actionController = WorldShaper.getInstance().getActionController();
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
        ActionStack playerActionStack = playerData.getActionStack();

        if (playerActionStack.isUndoStackEmpty()) {
            sendWorldShaperWarningMessage(player, "No action to redo");
            return true;
        }

        Action lastAction = playerActionStack.peekUndoStack();
        if (!lastAction.worldUUID.equals(player.getWorld().getUID())) {
            sendWorldShaperErrorMessage(player, "Cannot perform redo: the last undone action took place in a different world from the one you are in.");
            return true;
        }

        actionController.redoAction(playerActionStack);

        return true;
    }
}
