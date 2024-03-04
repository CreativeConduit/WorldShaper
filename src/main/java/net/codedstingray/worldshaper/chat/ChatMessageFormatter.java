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

package net.codedstingray.worldshaper.chat;

import net.codedstingray.worldshaper.WorldShaperManifest;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class ChatMessageFormatter {

    public static final String ACCENT_COLOR = TextColor.AQUA.toString();
    public static final String WARNING_COLOR = TextColor.YELLOW.toString();
    public static final String ERROR_COLOR = TextColor.RED.toString();

    public static final String GROUPING_PIPE = ACCENT_COLOR + "|" + TextColor.RESET;
    public static final String GROUPING_END = ACCENT_COLOR + "\\" + TextColor.RESET;

    public static final String WORLDSHAPER_MESSAGE_PREFIX = ACCENT_COLOR + "> " + TextColor.RESET;


    /* ========================== *\
    |* message formatting methods *|
    \* ========================== */

    /**
     * Formats a message to the WorldShaper message format.
     * @param message The message
     */
    public static String worldShaperMessage(String message) {
        return WORLDSHAPER_MESSAGE_PREFIX + message;
    }

    /**
     * Formats a message to the WorldShaper warning message format.
     * @param message The warning message
     */
    public static String worldShaperWarningMessage(String message) {
        return WORLDSHAPER_MESSAGE_PREFIX + WARNING_COLOR + message;
    }

    /**
     * Formats a message to the WorldShaper error message format.
     * @param message The error message
     */
    public static String worldShaperErrorMessage(String message) {
        return WORLDSHAPER_MESSAGE_PREFIX + ERROR_COLOR + message;
    }

    /**
     * Formats the given list of messages to the WorldShaper grouped messages format.
     * @param header The grouping header
     * @param messages The grouped messages
     */
    public static String groupedMessages(String header, String... messages) {
        return groupedMessages(header, Arrays.stream(messages).toList());
    }

    /**
     * Formats the given list of messages to the WorldShaper grouped messages format.
     * @param header The grouping header
     * @param messages The grouped messages
     */
    public static String groupedMessages(String header, Iterable<String> messages) {
        StringJoiner joiner = new StringJoiner("\n")
                .add(GROUPING_PIPE + " === " + ACCENT_COLOR + header + TextColor.RESET + " ===");

        for (String message: messages) {
            joiner.add(GROUPING_PIPE + " " + message);
        }

        return joiner.add(GROUPING_END).toString();
    }


    /* ======================= *\
    |* generic utility methods *|
    \* ======================= */
    //TODO: extract these methods into their own ObjectChatFormatter

    /**
     * Returns a String representation of the given vector in WorldShaper vector format.
     * @param vector The vector
     * @return The formatted String representation
     */
    public static String vectorToString(Vector3i vector) {
        return ACCENT_COLOR + "[" + TextColor.RESET + vector.getX() + ", " + vector.getY() + ", " + vector.getZ() + ACCENT_COLOR + "]" + TextColor.RESET;
    }


    /* ================================ *\
    |* specific text formatting methods *|
    \* ================================ */
    //TODO extract these methods into their own String suppliers

    /**
     * Creates a position set message.
     * @param index The index at which the position has been set
     * @param position The position that has been set
     * @return The created message
     */
    public static String positionSetMessage(int index, Vector3i position, boolean changed) {
        return changed ?
                "Position " + ACCENT_COLOR + (index + 1) + TextColor.RESET +
                " set to " + ChatMessageFormatter.vectorToString(position) + "." :
                "Position " + ACCENT_COLOR + (index + 1) + TextColor.RESET +
                " was already at " + ChatMessageFormatter.vectorToString(position) + ".";
    }

    public static String playerJoinMessage() {
        List<String> messages = new LinkedList<>();
        messages.add("Using " +
                ACCENT_COLOR + "WorldShaper" +
                TextColor.RESET + " version " +
                ACCENT_COLOR + WorldShaperManifest.PLUGIN_VERSION +
                TextColor.RESET + ".");
        messages.add("Native Minecraft version is " +
                ACCENT_COLOR + WorldShaperManifest.NATIVE_MC_VERSION +
                TextColor.RESET + ".");
        messages.add("Created by " + ACCENT_COLOR + "CodedStingray");

        return groupedMessages("WorldShaper", messages);
    }

    public static String worldShaperInfoMessage() {
        List<String> messages = new LinkedList<>();
        messages.add("Using " +
                ACCENT_COLOR + "WorldShaper" +
                TextColor.RESET + " version " +
                ACCENT_COLOR + WorldShaperManifest.PLUGIN_VERSION +
                TextColor.RESET + ".");
        messages.add("Native Minecraft version is " +
                ACCENT_COLOR + WorldShaperManifest.NATIVE_MC_VERSION +
                TextColor.RESET + ".");
        messages.add("Created by " + ACCENT_COLOR + "CodedStingray");
        messages.add("Feel free to check out my YouTube channel at");
        messages.add(ACCENT_COLOR + TextColor.UNDERLINE + "https://youtube.com/@CodedStingray");

        return groupedMessages("WorldShaper", messages);
    }
}
