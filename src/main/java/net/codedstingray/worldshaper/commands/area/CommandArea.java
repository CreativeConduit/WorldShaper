package net.codedstingray.worldshaper.commands.area;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;

@ParametersAreNonnullByDefault
public class CommandArea implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        if (args.length == 0) {
            Area area = playerData.getArea();
            sendWorldShaperMessage(player, "Your current area is of type " + TextColor.AQUA + "\"" + area.getName() + "\"");
            return true;
        }

        String areaName = args[0];
        boolean success = playerData.setArea(areaName);

        if (success) {
            sendWorldShaperMessage(player, "Area set to type " + TextColor.AQUA + "\"" + areaName + "\"");
            return true;
        } else {
            sendWorldShaperMessage(player, TextColor.RED + "Area tof type \"" + areaName + "\" does not exist.");
            return false;
        }
    }
}
