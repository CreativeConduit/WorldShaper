package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.util.chat.ChatFormattingUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendGroupedMessages;
import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperErrorMessage;

@ParametersAreNonnullByDefault
public class CommandPositions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperErrorMessage(sender, "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        Selection selection = playerData.getSelection();

        LinkedList<String> messages = new LinkedList<>();
        for (int i = 0; i < selection.getControlPositions().size(); i++) {
            Vector3i controlPosition = selection.getControlPosition(i);
            if (controlPosition != null) {
                messages.add("[" + (i + 1) + "] " + ChatFormattingUtils.vectorToString(controlPosition));
            } else {
                messages.add("[" + (i + 1) + "] - not set -");
            }
        }
        sendGroupedMessages(player, "Your current control positions", messages);

        return true;
    }
}
