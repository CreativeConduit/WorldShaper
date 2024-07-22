/*
 * WorldShaper, a powerful in-game map editing addon for WorldEdit
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

package net.codedstingray.worldshaper.commands.area.operations;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionKey;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.data.PlayerData;
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

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperMessage;
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandSet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 1);

//            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
//            Area area = getAreaFromPlayerData(playerData);
//
//            UUID worldUUID = getWorldAndCheckWithSelection(player, playerData);
//
//            Pattern pattern = getPatternFromArgument(args[0]);
//
//
//            World world = Objects.requireNonNull(Bukkit.getWorld(worldUUID));
//            Operation operation = new OperationPlace(pattern);
//            Action action = operation.performOperation(area, world);
//
//            ActionStack playerActionStack = playerData.getActionStack();
//            WorldShaper.getInstance().getActionController().performAction(playerActionStack, action);

            BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
            LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(wePlayer);

            Region selection = localSession.getSelection();

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(localSession.getSelectionWorld())) {
                for (BlockVector3 v: selection) {
                    //TODO: set proper blocks from pattern
                    editSession.setBlock(v, BlockTypes.STONE.getDefaultState());
                }
                localSession.remember(editSession);
            } catch (MaxChangedBlocksException e) {
                throw new RuntimeException(e);
            }

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        } catch (IncompleteRegionException e) {
            sendWorldShaperMessage(sender, e.getMessage());
            return true;
        }
    }
}
