package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;

@ParametersAreNonnullByDefault
public class CommandSelectionType implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPlayerData();

        if (args.length == 0) {
            SelectionType selectionType = playerData.getPlayerSelectionTypeMap().get(player.getUniqueId());
            sendWorldShaperMessage(player, "Your current selection type is " + TextColor.AQUA + "\"" + selectionType.getName() + "\"");

            return true;
        }

        String selectionTypeName = args[0];
        SelectionType selectionType = playerData.getSelectionTypeByName(selectionTypeName);
        if (selectionType == null) {
            sendWorldShaperMessage(player, TextColor.RED + "Selection Type \"" + selectionTypeName + "\" does not exist.");
            return false;
        }

        playerData.getPlayerSelectionTypeMap().put(player.getUniqueId(), selectionType);
        sendWorldShaperMessage(player, "Selection Type set to " + TextColor.AQUA + "\"" + selectionType.getName() + "\"");
        return true;
    }
}
