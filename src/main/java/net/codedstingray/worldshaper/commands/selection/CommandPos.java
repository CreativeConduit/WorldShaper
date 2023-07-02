package net.codedstingray.worldshaper.commands.selection;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.data.PlayerData;
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
import java.util.UUID;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;
import static net.codedstingray.worldshaper.util.world.LocationUtils.locationToBlockVector;

@ParametersAreNonnullByDefault
public class CommandPos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }

        PlayerData playerData = WorldShaper.getInstance().getPlayerData();
        Selection selection = playerData.getPlayerSelectionMap().getSelection(player.getUniqueId());

        int index;
        if (args.length > 0) {
            try {
                index = Integer.parseInt(args[0]) - 1;
            } catch (NumberFormatException e) {
                sendWorldShaperMessage(player, TextColor.RED + "Index for /pos must be an integer.");
                return false;
            }

            if (index < 0) {
                sendWorldShaperMessage(player, TextColor.RED + "Index for /pos must be 1 or higher.");
                return false;
            }
        } else {
            index = selection.getControlPositions().size();
        }

        Location playerLocation = player.getLocation();
        Vector3i playerPosition = locationToBlockVector(playerLocation);
        UUID world = Objects.requireNonNull(playerLocation.getWorld()).getUID();

        selection.setControlPosition(index, playerPosition, world);
        sendWorldShaperMessage(player, ChatFormattingUtils.positionSetMessage(index, playerPosition));

        return true;
    }
}
