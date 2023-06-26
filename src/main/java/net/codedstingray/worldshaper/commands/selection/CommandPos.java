package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.chat.ChatFormattingUtils;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class CommandPos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextColor.RED + "This command can only be used by a player.");
            return false;
        }

        Selection selection = WorldShaper.getInstance().getPlayerSelectionMap().getSelection(player.getUniqueId());

        if (args.length > 0) {
            try {
                int index = Integer.parseInt(args[0]) - 1;
                Location playerLocation = player.getLocation();
                Vector3i playerPosition = new Vector3i(
                        playerLocation.getBlockX(),
                        playerLocation.getBlockY(),
                        playerLocation.getBlockZ()
                );

                if (index >= 0) {
                    selection.setControlPosition(index, playerPosition, Objects.requireNonNull(playerLocation.getWorld()).getUID());
                    player.sendMessage("Position " + TextColor.AQUA + (index + 1) + TextColor.RESET + " set to " +
                            ChatFormattingUtils.toString(playerPosition) + ".");
                } else {
                    player.sendMessage(TextColor.RED + "Index for /pos must be 1 or higher.");
                    return false;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(TextColor.RED + "Index for /pos must be an integer.");
                return false;
            }
        } else {
            Location playerLocation = player.getLocation();
            Vector3i playerPosition = new Vector3i(
                    playerLocation.getBlockX(),
                    playerLocation.getBlockY(),
                    playerLocation.getBlockZ()
            );
            int index = selection.addControlPosition(playerPosition, Objects.requireNonNull(playerLocation.getWorld()).getUID());
            player.sendMessage("Position " + TextColor.AQUA + (index + 1) + TextColor.RESET + " set to " +
                    ChatFormattingUtils.toString(playerPosition) + ".");
        }

        return true;
    }
}
