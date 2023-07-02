package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;

@ParametersAreNonnullByDefault
public class CommandClearPositions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPlayerData();

        playerData.getPlayerSelectionMap().getSelection(player.getUniqueId()).clearControlPositions();
        sendWorldShaperMessage(player, "Cleared all selection control positions.");
        return true;
    }
}
