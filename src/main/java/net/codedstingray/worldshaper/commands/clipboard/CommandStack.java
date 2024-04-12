package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.operation.Operation;
import net.codedstingray.worldshaper.operation.OperationStack;
import net.codedstingray.worldshaper.util.world.Direction;
import net.codedstingray.worldshaper.util.world.DirectionUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandStack implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 2);

            int amount = Integer.parseInt(args[0]);
            Direction direction = DirectionUtils.determineDirectionFromPlayer(player, args.length > 1 ? args[1] : null);
            if (direction == null) {
                return false;
            }

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Area area = getAreaFromPlayerData(playerData);
            World world = player.getWorld();

            Operation operation = new OperationStack(player.getLocation(), amount, direction);
            Action action = operation.performOperation(area, world);

            ActionStack playerActionStack = playerData.getActionStack();
            WorldShaper.getInstance().getActionController().performAction(playerActionStack, action);

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
