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

import org.bukkit.command.CommandSender;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.*;

public class MessageSender {

    public static void sendRawMessage(CommandSender receiver, String message) {
        receiver.sendMessage(message);
    }

    /**
     * Sends a message in the WorldShaper message format to the given message receiver.
     * @param receiver The message receiver
     * @param message The message
     */
    public static void sendWorldShaperMessage(CommandSender receiver, String message) {
        receiver.sendMessage(worldShaperMessage(message));
    }

    /**
     * Sends a message in the WorldShaper error message format to the given message receiver.
     * @param receiver The message receiver
     * @param message The error message
     */
    public static void sendWorldShaperErrorMessage(CommandSender receiver, String message) {
        receiver.sendMessage(worldShaperErrorMessage(TextColor.RED + message));
    }

    /**
     * Sends the given list of messages in the WorldShaper grouped messages format to the given message receiver.
     * @param receiver The message receiver
     * @param header The grouping header
     * @param messages The grouped messages
     */
    public static void sendGroupedMessages(CommandSender receiver, String header, String... messages) {
        receiver.sendMessage(groupedMessages(header, messages));
    }

    /**
     * Sends the given list of messages in the WorldShaper grouped messages format to the given message receiver.
     * @param receiver The message receiver
     * @param header The grouping header
     * @param messages The grouped messages
     */
    public static void sendGroupedMessages(CommandSender receiver, String header, Iterable<String> messages) {
        receiver.sendMessage(groupedMessages(header, messages));
    }
}
