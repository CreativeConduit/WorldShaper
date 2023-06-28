package net.codedstingray.worldshaper.util.chat;

import org.bukkit.command.CommandSender;
import org.joml.Vector3i;

public class ChatFormattingUtils {

    public static final String GROUPING_PIPE = TextColor.AQUA + "|" + TextColor.RESET;
    public static final String GROUPING_END = TextColor.AQUA + "\\" + TextColor.RESET;

    public static final String WORLDSHAPER_MESSAGE_PREFIX = TextColor.AQUA + "> " + TextColor.RESET;

    public static String toString(Vector3i vector) {
        return TextColor.AQUA + "[" + TextColor.RESET + vector.x + ", " + vector.y + ", " + vector.z + TextColor.AQUA + "]" + TextColor.RESET;
    }

    public static void sendWorldShaperMessage(CommandSender commandSender, String message) {
        commandSender.sendMessage(WORLDSHAPER_MESSAGE_PREFIX + message);
    }
}
