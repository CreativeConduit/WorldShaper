package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.commands.CommandInputParseUtils;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.Axis;
import org.bukkit.block.data.BlockData;
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

    private Clipboard rotateClipboard(Clipboard clipboard, int rotationValue) {
        Vector3ii vector = clipboard.getOriginOffset();
        BlockData[][][] data = clipboard.getRawData();

        Vector3ii newOriginPosition = switch (rotationValue) {
            case 90 -> new Vector3ii(-vector.getZ() - data[0][0].length + 1, vector.getY(), vector.getX());
            case 180 -> new Vector3ii(-vector.getX() - data.length + 1, vector.getY(), -vector.getZ() - data[0][0].length + 1);
            case 270 -> new Vector3ii(vector.getZ(), vector.getY(), -vector.getX() - data.length + 1);
            default -> vector.toImmutable();
        };
        BlockData[][][] newData = rotateBlockDataY(data, rotationValue);

        return new Clipboard(newOriginPosition, newData);
    }

    private Vector3ii rotateVectorY(Vector3i vector, int rotationValue) {
        return switch (rotationValue) {
            case 90 -> new Vector3ii(-vector.getZ(), vector.getY(), vector.getX());
            case 180 -> new Vector3ii(-vector.getX(), vector.getY(), -vector.getZ());
            case 270 -> new Vector3ii(vector.getZ(), vector.getY(), -vector.getX());
            default -> vector.toImmutable();
        };
    }

    private BlockData[][][] rotateBlockDataY(BlockData[][][] data, int rotationValue) {
        if (rotationValue == 0) {
            return data;
        }

        BlockData[][][] newData = rotationValue == 90 || rotationValue == 270 ?
                new BlockData[data[0][0].length][data[0].length][data.length] :
                new BlockData[data.length][data[0].length][data[0][0].length];

        int sizeX = newData.length, sizeY = newData[0].length, sizeZ = newData[0][0].length;

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    switch (rotationValue) {
                        case 90 -> newData[x][y][z] = data[z][y][sizeX - x - 1];
                        case 180 -> newData[x][y][z] = data[sizeX - x - 1][y][sizeZ - z - 1];
                        case 270 -> newData[x][y][z] = data[sizeZ - z - 1][y][x];
                    }
                }
            }
        }

        return newData;
    }
}
