package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.chat.ChatFormattingUtils;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.StringJoiner;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.*;

@ParametersAreNonnullByDefault
public class CommandPositions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();
        StringJoiner joiner = new StringJoiner("\n")
                .add(GROUPING_PIPE + " === " + TextColor.AQUA + "Your current control positions" + TextColor.RESET + " ===");
        for (int i = 0; i < selection.getControlPositions().size(); i++) {
            Vector3i controlPosition = selection.getControlPosition(i);
            if (controlPosition != null) {
                joiner.add(GROUPING_PIPE + " [" + (i + 1) + "] " + ChatFormattingUtils.toString(controlPosition));
            } else {
                joiner.add(GROUPING_PIPE + " [" + (i + 1) + "] - not set -");
            }
        }
        player.sendMessage(joiner.add(GROUPING_END).toString());

        return true;
    }
}
