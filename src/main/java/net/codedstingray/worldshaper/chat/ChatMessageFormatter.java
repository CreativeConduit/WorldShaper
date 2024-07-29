/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
 * Copyright (C) 2023-2024 CreativeConduit
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class ChatMessageFormatter {

    public static final String ACCENT_COLOR = TextColor.AQUA.toString();
    public static final String MAIN_COLOR = TextColor.WHITE.toString();
    public static final String WARNING_COLOR = TextColor.YELLOW.toString();
    public static final String ERROR_COLOR = TextColor.RED.toString();

    public static final String GROUPING_PIPE = "|";
    public static final String GROUPING_END = "\\";

    public static final String WORLDSHAPER_MESSAGE_PREFIX = "> ";

    /* ========= *\
    |* Utilities *|
    \* ========= */

    public static String accent(String message) {
        return accent(MessageLevel.INFO, message);
    }

    public static String accent(MessageLevel messageLevel, String message) {
        return TextColor.RESET + ACCENT_COLOR + message + TextColor.RESET + messageLevel.color;
    }

    public enum MessageLevel {
        INFO(MAIN_COLOR),
        WARNING(WARNING_COLOR),
        ERROR(ERROR_COLOR);

        public final String color;

        MessageLevel(String color) {
            this.color = color;
        }
    }

    /* =========================== *\
    |* Grouped WorldShaper Message *|
    \* =========================== */

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
                .add(accent(GROUPING_PIPE) + " === " + accent(header) + " ===");

        for (String message: messages) {
            joiner.add(accent(GROUPING_PIPE) + " " + message);
        }

        return joiner.add(accent(GROUPING_END)).toString();
    }

    /* ============================ *\
    |* Standard WorldShaper Message *|
    \* ============================ */

    /**
     * Formats the given raw message into the WorldShaper format.<br>
     * Use this only when really necessary.
     * Normally, the use of {@link #messageBuilder(MessageLevel, boolean) worldShaperMessageBuilder} is preferable.
     *
     * @param messageLevel The {@link MessageLevel MessageLevel} to which the message should be formatted
     * @param message The message to be formatted
     * @return The formatted message
     */
    public static String asWorldShaperMessage(MessageLevel messageLevel, String message) {
        return accent(messageLevel, WORLDSHAPER_MESSAGE_PREFIX) + message;
    }

    /**
     * Creates a new WorldShaperMessageBuilder with the given message level.
     *
     * @param messageLevel The {@link MessageLevel MessageLevel} to be used by this builder
     * @return A new {@link WorldShaperMessageBuilder WorldShaperMessageBuilder} instance
     */
    public static WorldShaperMessageBuilder messageBuilder(MessageLevel messageLevel, boolean withMessagePrefix) {
        return new WorldShaperMessageBuilder()
                .withMessageLevel(messageLevel)
                .withMessagePrefix(withMessagePrefix);
    }

    /**
     * A builder used to create WorldShaper-formatted messages.<br>
     * This builder automatically handles message colors, meaning the color automatically returns to the normal text
     * color (which depends on the message level) after an accent color text has been inserted.
     */
    public static class WorldShaperMessageBuilder {
        private final List<Message> messages;

        private MessageLevel messageLevel = MessageLevel.INFO;
        private boolean prependMessagePrefix = true;

        private WorldShaperMessageBuilder() {
            this.messages = new LinkedList<>();
        }

        public WorldShaperMessageBuilder withMessageLevel(MessageLevel messageLevel) {
            this.messageLevel = messageLevel;
            return this;
        }

        public WorldShaperMessageBuilder withMessagePrefix(boolean prependMessagePrefix) {
            this.prependMessagePrefix = prependMessagePrefix;
            return this;
        }

        /**
         * Appends a text to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(String message) {
            messages.add(new Message(message, false));
            return this;
        }

        /**
         * Appends a byte to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(byte message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a short to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(short message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends an int to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(int message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a long to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(long message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a float to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(float message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a double to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(double message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a boolean to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(boolean message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a char to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder t(char message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a text with accent color to the builder
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(String message) {
            messages.add(new Message(message, true));
            return this;
        }

        /**
         * Appends a byte with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(byte message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a short with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(short message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends an int with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(int message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a long with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(long message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a float with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(float message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a double with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(double message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a boolean with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(boolean message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Appends a char with accent color to the builder.
         *
         * @param message The message to be appended
         * @return This instance, for chaining
         */
        public WorldShaperMessageBuilder a(char message) {
            messages.add(new Message(String.valueOf(message), false));
            return this;
        }

        /**
         * Builds the WorldShaper formatted message from the text elements that have been added before.
         *
         * @return The full, formatted message
         */
        public String build() {
            StringBuilder sb = new StringBuilder();
            if (prependMessagePrefix) {
                sb.append(ChatMessageFormatter.accent(messageLevel, WORLDSHAPER_MESSAGE_PREFIX));
            }
            messages.forEach(message -> sb.append(message.formatMessage(messageLevel)));
            return sb.toString();
        }

        private record Message(String message, boolean isAccent) {
            public String formatMessage(MessageLevel messageLevel) {
                return isAccent ?
                        ChatMessageFormatter.accent(messageLevel, this.message) :
                        this.message;
            }
        }
    }
}
