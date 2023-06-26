package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CommandRemovePos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextColor.RED + "This command can only be used by a player.");
            return false;
        }

        Selection selection = WorldShaper.getInstance().getPlayerSelectionMap().getSelection(player.getUniqueId());
        int index = selection.getControlPositions().size() - 1;
        if (args.length > 0) {
            try {
                index = Integer.parseInt(args[0]) - 1;
            } catch (NumberFormatException e) {
                player.sendMessage(TextColor.RED + "Index for /removepos must be an integer.");
                return false;
            }
        }

        if (index >= 0) {
            boolean madeChange = selection.removeControlPosition(index);
            if (madeChange) {
                player.sendMessage("Control position " + TextColor.AQUA + (index + 1) + TextColor.RESET + " removed.");
            } else {
                player.sendMessage("Control position " + TextColor.AQUA + (index + 1) + TextColor.RESET + " was already not set.");
            }
            return true;
        } else {
            player.sendMessage(TextColor.RED + "Index for /removepos must be 1 or higher.");
            return false;
        }
    }
}
