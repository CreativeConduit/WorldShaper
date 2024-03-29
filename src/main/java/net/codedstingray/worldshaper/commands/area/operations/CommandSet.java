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

package net.codedstingray.worldshaper.commands.area.operations;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.operation.Operation;
import net.codedstingray.worldshaper.operation.OperationPlace;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.UUID;

import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandSet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 1);

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Area area = getAreaFromPlayerData(playerData);

            UUID worldUUID = getWorldAndCheckWithSelection(player, playerData);

            Pattern pattern = getPatternFromArgument(args[0]);


            World world = Objects.requireNonNull(Bukkit.getWorld(worldUUID));
            Operation operation = new OperationPlace(pattern);
            Action action = operation.performOperation(area, world);

            ActionStack playerActionStack = playerData.getActionStack();
            WorldShaper.getInstance().getActionController().performAction(playerActionStack, action);

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
