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
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockState;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

import static net.codedstingray.worldshaper.chat.MessageSender.sendWorldShaperMessage;
import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandSet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 1);

            BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
            LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(wePlayer);

            Region selection = localSession.getSelection();

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(localSession.getSelectionWorld())) {
                Pattern pattern = getPatternFromArgument(args[0]);

                for (BlockVector3 v: selection) {
                    Optional<BlockData> toOpt = pattern.getRandomBlockData();
                    if (toOpt.isEmpty()) {
                        continue;
                    }

                    BlockState blockState = BukkitAdapter.adapt(toOpt.get());
                    editSession.setBlock(v, blockState);
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
