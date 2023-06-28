package net.codedstingray.worldshaper.commands.utility;

import net.codedstingray.worldshaper.items.SelectionWand;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;

/**
 * {@link CommandExecutor} for the /wand command.
 */
@ParametersAreNonnullByDefault
public class CommandWand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }

        player.getInventory().addItem(SelectionWand.SELECTION_WAND);

        return true;
    }
}
