package net.codedstingray.worldshaper.commands;

import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.block.mask.Mask;
import net.codedstingray.worldshaper.block.mask.MaskParseException;
import net.codedstingray.worldshaper.block.mask.MaskParser;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import net.codedstingray.worldshaper.block.pattern.PatternParseException;
import net.codedstingray.worldshaper.block.pattern.PatternParser;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.permission.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.codedstingray.worldshaper.chat.MessageSender.*;

public class CommandInputParseUtils {

    public static Player playerFromCommandSender(CommandSender sender) throws CommandInputParseException {
        if (!(sender instanceof Player player)) {
            throw new CommandInputParseException("This command can only be used by a player.", false, WarningLevel.ERROR);
        }
        return player;
    }

    public static void verifyArgumentSize(String[] args, int min, int max) throws CommandInputParseException {
        if (args.length < min) {
            throw new CommandInputParseException("Too few arguments; Usage:", true, WarningLevel.ERROR);
        }
        if (args.length > max) {
            throw new CommandInputParseException("Too many arguments; Usage:", true, WarningLevel.ERROR);
        }
    }

    public static Area getAreaFromPlayerData(PlayerData playerData) throws CommandInputParseException {
        Area area = playerData.getArea();
        if (area == null || !area.isValid()) {
            throw new CommandInputParseException("Set an area before using this command.", false, WarningLevel.ERROR);
        }
        return area;
    }

    public static UUID getWorldAndCheckWithSelection(Player player, PlayerData playerData) throws CommandInputParseException {
        UUID worldUUID = playerData.getSelection().getWorldUUID();
        if (!player.getWorld().getUID().equals(worldUUID)) {
            throw new CommandInputParseException("Area is in a different world. Switch to that world or create a new area in this world to use this command", false, WarningLevel.ERROR);
        }
        return worldUUID;
    }

    public static Mask getMaskFromArgument(String maskString) throws CommandInputParseException {
        try {
            return MaskParser.parseMask(maskString);
        } catch (MaskParseException e) {
            throw new CommandInputParseException("Unable to parse mask: " + e.getMessage(), false, WarningLevel.ERROR);
        }
    }

    public static Pattern getPatternFromArgument(String patternString) throws CommandInputParseException {
        try {
            return PatternParser.parsePattern(patternString);
        } catch (PatternParseException e) {
            throw new CommandInputParseException("Unable to parse pattern: " + e.getMessage(), false, WarningLevel.ERROR);
        }
    }

    public static void checkPermissionsAnyOf(CommandSender sender, String[] permissions) throws CommandInputParseException {
        if (sender instanceof Player player && !PermissionUtil.hasAnyOf(player, permissions)) {
            throw new CommandInputParseException("You do not have the permission to use this command.", false, WarningLevel.WARNING);
        }
    }

    public static Clipboard getClipBoardFromPlayerData(PlayerData playerData) throws CommandInputParseException {
        return playerData.getClipboard().orElseThrow(() -> new CommandInputParseException("You have nothing in your clipboard.", false, WarningLevel.WARNING));
    }


    public static boolean handleCommandInputParseException(CommandSender sender, CommandInputParseException e) {
        switch (e.getWarningLevel()) {
            case NORMAL -> sendWorldShaperMessage(sender, e.getMessage());
            case WARNING -> sendWorldShaperWarningMessage(sender, e.getMessage());
            case ERROR -> sendWorldShaperErrorMessage(sender, e.getMessage());
        }

        return !e.showUsage();
    }


    public enum WarningLevel {
        NORMAL,
        WARNING,
        ERROR
    }

    public static class CommandInputParseException extends Exception {
        private final boolean showUsage;
        private final WarningLevel warningLevel;

        public CommandInputParseException(String message, boolean showUsage, WarningLevel warningLevel) {
            super(message);
            this.showUsage = showUsage;
            this.warningLevel = warningLevel;
        }

        public boolean showUsage() {
            return showUsage;
        }

        public WarningLevel getWarningLevel() {
            return warningLevel;
        }
    }
}
