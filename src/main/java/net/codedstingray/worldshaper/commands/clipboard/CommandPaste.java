package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.operation.Operation;
import net.codedstingray.worldshaper.operation.OperationPaste;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandPaste implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 0, 0);

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Clipboard clipboard = getClipBoardFromPlayerData(playerData);

            World world = player.getWorld();

            Operation operation = new OperationPaste(clipboard, player.getLocation());
            Action action = operation.performOperation(playerData.getArea(), world);

            ActionStack playerActionStack = playerData.getActionStack();
            WorldShaper.getInstance().getActionController().performAction(playerActionStack, action);

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
