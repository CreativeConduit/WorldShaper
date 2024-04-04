package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.operation.Operation;
import net.codedstingray.worldshaper.operation.OperationPlace;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.UUID;

import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandCut implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 0, 0);

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Area area = getAreaFromPlayerData(playerData);

            UUID worldUUID = getWorldAndCheckWithSelection(player, playerData);

            playerData.setClipboard(Clipboard.createFromArea(Objects.requireNonNull(Bukkit.getWorld(worldUUID)),
                    area, LocationUtils.locationToBlockVector(player.getLocation())));

            World world = Objects.requireNonNull(Bukkit.getWorld(worldUUID));
            Operation operation = new OperationPlace(Pattern.ALL_AIR);
            Action action = operation.performOperation(area, world);

            ActionStack playerActionStack = playerData.getActionStack();
            WorldShaper.getInstance().getActionController().performAction(playerActionStack, action);

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
