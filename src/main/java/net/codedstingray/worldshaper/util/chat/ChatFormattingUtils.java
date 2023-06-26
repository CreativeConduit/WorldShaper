package net.codedstingray.worldshaper.util.chat;

import org.joml.Vector3i;

public class ChatFormattingUtils {

    public static String toString(Vector3i vector) {
        return TextColor.AQUA + "[" + TextColor.RESET + vector.x + ", " + vector.y + ", " + vector.z + TextColor.AQUA + "]" + TextColor.RESET;
    }
}
