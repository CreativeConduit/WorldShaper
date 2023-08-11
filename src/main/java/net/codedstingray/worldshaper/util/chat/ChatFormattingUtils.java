/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2023 CodedStingray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.codedstingray.worldshaper.util.chat;

import org.bukkit.command.CommandSender;
import org.joml.Vector3i;

import java.util.Arrays;
import java.util.StringJoiner;

public class ChatFormattingUtils {

    public static final String GROUPING_PIPE = TextColor.AQUA + "|" + TextColor.RESET;
    public static final String GROUPING_END = TextColor.AQUA + "\\" + TextColor.RESET;

    public static final String WORLDSHAPER_MESSAGE_PREFIX = TextColor.AQUA + "> " + TextColor.RESET;


    /* ======================= *\
    |* message sending methods *|
    \* ======================= */

    /**
     * Sends a message in the WorldShaper message format to the given message receiver.
     * @param receiver The message receiver
     * @param message The message
     */
    public static void sendWorldShaperMessage(CommandSender receiver, String message) {
        receiver.sendMessage(WORLDSHAPER_MESSAGE_PREFIX + message);
    }

    /**
     * Sends a message in the WorldShaper error message format to the given message receiver.
     * @param receiver The message receiver
     * @param message The error message
     */
    public static void sendWorldShaperErrorMessage(CommandSender receiver, String message) {
        receiver.sendMessage(WORLDSHAPER_MESSAGE_PREFIX + TextColor.RED + message);
    }

    /**
     * Sends the given list of messages in the WorldShaper grouped messages format to the given message receiver.
     * @param receiver The message receiver
     * @param header The grouping header
     * @param messages The grouped messages
     */
    public static void sendGroupedMessages(CommandSender receiver, String header, String... messages) {
        sendGroupedMessages(receiver, header, Arrays.stream(messages).toList());
    }

    /**
     * Sends the given list of messages in the WorldShaper grouped messages format to the given message receiver.
     * @param receiver The message receiver
     * @param header The grouping header
     * @param messages The grouped messages
     */
    public static void sendGroupedMessages(CommandSender receiver, String header, Iterable<String> messages) {
        StringJoiner joiner = new StringJoiner("\n")
                .add(GROUPING_PIPE + " === " + TextColor.AQUA + header + TextColor.RESET + " ===");

        for (String message: messages) {
            joiner.add(GROUPING_PIPE + " " + message);
        }

        receiver.sendMessage(joiner.add(GROUPING_END).toString());
    }


    /* ======================= *\
    |* generic utility methods *|
    \* ======================= */

    /**
     * Returns a String representation of the given vector in WorldShaper vector format.
     * @param vector The vector
     * @return The formatted String representation
     */
    public static String vectorToString(Vector3i vector) {
        return TextColor.AQUA + "[" + TextColor.RESET + vector.x + ", " + vector.y + ", " + vector.z + TextColor.AQUA + "]" + TextColor.RESET;
    }


    /* ================================ *\
    |* specific text formatting methods *|
    \* ================================ */

    /**
     * Creates a position set message.
     * @param index The index at which the position has been set
     * @param position The position that has been set
     * @return The created message
     */
    public static String positionSetMessage(int index, Vector3i position, boolean changed) {
        return changed ?
                "Position " + TextColor.AQUA + (index + 1) + TextColor.RESET +
                " set to " + ChatFormattingUtils.vectorToString(position) + "." :
                "Position " + TextColor.AQUA + (index + 1) + TextColor.RESET +
                " was already at " + ChatFormattingUtils.vectorToString(position) + ".";
    }
}
