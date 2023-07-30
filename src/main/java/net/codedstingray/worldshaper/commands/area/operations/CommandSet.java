package net.codedstingray.worldshaper.commands.area.operations;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import net.codedstingray.worldshaper.block.pattern.PatternParseException;
import net.codedstingray.worldshaper.block.pattern.PatternParser;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.util.chat.TextColor;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Objects;
import java.util.UUID;

import static net.codedstingray.worldshaper.util.chat.ChatFormattingUtils.sendWorldShaperMessage;

@ParametersAreNonnullByDefault
public class CommandSet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sendWorldShaperMessage(sender, TextColor.RED + "This command can only be used by a player.");
            return false;
        }
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(player.getUniqueId());

        if (args.length == 0) {
            sendWorldShaperMessage(player, TextColor.RED + "You need to provide a pattern to set; Usage:");
            return false;
        }
        if (args.length > 1) {
            sendWorldShaperMessage(player, TextColor.RED + "Too many arguments; Usage:");
            return false;
        }

        Area area = playerData.getArea();
        if (area == null || !area.isValid()) {
            sendWorldShaperMessage(player, TextColor.RED + "Set an area before using this command");
            return true;
        }

        UUID worldUUID = playerData.getSelection().getWorldUUID();
        if (!player.getWorld().getUID().equals(worldUUID)) {
            sendWorldShaperMessage(player, TextColor.RED + "Area is in a different world. Switch to that world or create a new area in this world to use this command");
            return true;
        }

        Pattern pattern;
        try {
            pattern = PatternParser.parsePattern(args[0]);
        } catch (PatternParseException e) {
            sendWorldShaperMessage(player, TextColor.RED + "Unable to parse pattern: " + e.getMessage());
            return false;
        }

        World world = Objects.requireNonNull(Bukkit.getWorld(worldUUID));
        for(Vector3i position: area) {
            Block block = world.getBlockAt(LocationUtils.vectorToLocation(position, world));
            pattern.getRandomBlockData().ifPresent(block::setBlockData);
        }

        return true;
    }
}
