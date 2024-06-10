package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.commands.CommandInputParseUtils;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.Axis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

//TODO: Clipboard holds Transform
// Transform holds rotation
// Rotate Command rotates Transform
// Clipboard has apply() method
//   apply() takes raw Clipboard Data and creates transformed data which is cached
//   transform application will also call a rotateBlock()  function from a utility on every block to rotate the block
//     rotateBlock() will check Block Type (Directional, MultipleFacing, etc) and handle them accordingly
//   if any other modification is done (like scale / rotate), transformed data is discarded
// Paste command calls apply() method and then pastes blocks relative to player
@ParametersAreNonnullByDefault
public class CommandRotate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 2);

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Clipboard clipboard = getClipBoardFromPlayerData(playerData);

            int rotationValue = CommandInputParseUtils.getRotationFromArgument(args[0]);
            Axis axis = args.length == 1 ? Axis.Y : CommandInputParseUtils.getAxisFromArgument(args[1]);

            Vector3i bv = axis.baseVector;

            clipboard.rotate(bv.getX() * rotationValue, bv.getY() * rotationValue, bv.getZ() * rotationValue);

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }
}
