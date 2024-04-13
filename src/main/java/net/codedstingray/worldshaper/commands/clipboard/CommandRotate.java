package net.codedstingray.worldshaper.commands.clipboard;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.codedstingray.worldshaper.commands.CommandInputParseUtils.*;

@ParametersAreNonnullByDefault
public class CommandRotate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player player = playerFromCommandSender(sender);
            verifyArgumentSize(args, 1, 1);

            PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());
            Clipboard clipboard = getClipBoardFromPlayerData(playerData);

            int rotationValue = getRotationFromArgument(args[0]);
            playerData.setClipboard(rotateClipboard(clipboard, rotationValue));

            return true;
        } catch (CommandInputParseException e) {
            return handleCommandInputParseException(sender, e);
        }
    }

    //TODO: move to ClipboardUtils
    private int getRotationFromArgument(String argument) throws CommandInputParseException {
        try {
            int rotationValue = Integer.parseInt(argument);
            if (rotationValue % 90 != 0) {
                throw new CommandInputParseException("Rotation must be a multiple of 90.", true, WarningLevel.ERROR);
            }
            return rotationValue % 360;
        } catch (NumberFormatException e) {
            throw new CommandInputParseException("Rotation must be a multiple of 90.", true, WarningLevel.ERROR);
        }
    }

    private Clipboard rotateClipboard(Clipboard clipboard, int rotationValue) {
        Vector3ii vector = clipboard.getOriginPosition();
        BlockData[][][] data = clipboard.getData();

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
