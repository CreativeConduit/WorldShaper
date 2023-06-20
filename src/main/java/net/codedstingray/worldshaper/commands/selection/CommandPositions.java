package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringJoiner;

@ParametersAreNonnullByDefault
public class CommandPositions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextColor.RED + "This command can only be used by a player.");
            return false;
        }

        Selection selection = WorldShaper.getInstance().getPlayerSelectionMap().getSelection(player.getUniqueId());
        StringJoiner joiner = new StringJoiner("\n")
                .add("| === " + TextColor.AQUA + "Your current control positions" + TextColor.RESET + " ===");
        for (int i = 0; i < selection.getControlPositions().size(); i++) {
            Vector3i controlPosition = selection.getControlPosition(i);
            joiner.add("| [" + (i + 1) + "] " + controlPosition.toString(NumberFormat.getIntegerInstance(Locale.US)));
        }
        player.sendMessage(joiner.toString());

        return true;
    }
}
