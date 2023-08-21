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
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import net.codedstingray.worldshaper.block.pattern.PatternParseException;
import net.codedstingray.worldshaper.block.pattern.PatternParser;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.*;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperErrorMessage;

@ParametersAreNonnullByDefault
public class CommandSet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        if (args.length == 0) {
            sendWorldShaperErrorMessage(player, "You need to provide a pattern to set; Usage:");
            return false;
        }
        if (args.length > 1) {
            sendWorldShaperErrorMessage(player, "Too many arguments; Usage:");
            return false;
        }

        Area area = playerData.getArea();
        if (area == null || !area.isValid()) {
            sendWorldShaperErrorMessage(player, "Set an area before using this command");
            return true;
        }

        UUID worldUUID = playerData.getSelection().getWorldUUID();
        if (!player.getWorld().getUID().equals(worldUUID)) {
            sendWorldShaperErrorMessage(player, "Area is in a different world. Switch to that world or create a new area in this world to use this command");
            return true;
        }

        Pattern pattern;
        try {
            pattern = PatternParser.parsePattern(args[0]);
        } catch (PatternParseException e) {
            sendWorldShaperErrorMessage(player, "Unable to parse pattern: " + e.getMessage());
            return false;
        }

        World world = Objects.requireNonNull(Bukkit.getWorld(worldUUID));
        List<Action.ActionItem> actionItems = new LinkedList<>();

        for(Vector3i position: area) {
            Optional<BlockData> toOpt = pattern.getRandomBlockData();
            if (toOpt.isEmpty()) {
                continue;
            }

            Location location = LocationUtils.vectorToLocation(position, world);
            Block block = world.getBlockAt(location);
            BlockData from = block.getBlockData();
            BlockData to = toOpt.get();

            Action.ActionItem actionItem = new Action.ActionItem(location, from, to);
            actionItems.add(actionItem);
        }

        Action action = new Action(worldUUID, actionItems);
        WorldShaper.getInstance().getActionController().performAction(action);

        return true;
    }
}
