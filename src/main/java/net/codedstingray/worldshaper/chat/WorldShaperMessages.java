/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
 * Copyright (C) 2024 CreativeConduit
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
import net.codedstingray.worldshaper.chat.ChatMessageFormatter.MessageLevel;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;

import java.util.LinkedList;
import java.util.List;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.accent;
import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.messageBuilder;

/**
 * Utility class providing some specific WorldShaper messages.
 */
public class WorldShaperMessages {

    /**
     * Creates a position set message.
     *
     * @param index The index at which the position has been set
     * @param position The position that has been set
     * @return The created message
     */
    public static String positionSetMessage(int index, Vector3i position, boolean changed, int areaSize) {
        return messageBuilder(MessageLevel.INFO, true)
                .t("Position ").a(index + 1)
                .t(changed ? " set to " : " was already at ")
                .t(WorldShaperObjectFormatter.vectorToString(position)).t(".")
                .t(areaSize >= 0 ? " (%d)".formatted(areaSize) : "")
                .build();
    }

    /**
     * Creates a position removed message.
     *
     * @param index The index from which the position has been removed
     * @return The created message
     */
    public static String positionRemovedMessage(int index, boolean changed, int areaSize) {
        return messageBuilder(MessageLevel.INFO, true)
                .t("Control position ").a(index + 1)
                .t(changed ? " removed." : " was already not set.")
                .t(areaSize >= 0 ? " (%d)".formatted(areaSize) : "")
                .build();
    }

    public static String actionPerformedMessage(int actionSize) {
        return messageBuilder(MessageLevel.INFO, true)
                .t("Done. ").a(actionSize).t(" blocks modified.").build();
    }

    /**
     * Creates the message that is displayed on player join.
     *
     * @return The created message
     */
    public static String playerJoinMessage() {
        List<String> messages = new LinkedList<>();
        messages.add("Using " + accent("WorldShaper") + " version " + accent(WorldShaperManifest.PLUGIN_VERSION) + ".");
        messages.add("");
        messages.add("Created by " + accent("CreativeConduit"));
        messages.add("Join our Discord at " + accent(TextColor.UNDERLINE + "https://discord.gg/BDbVGBmCY7"));

        return ChatMessageFormatter.groupedMessages("WorldShaper", messages);
    }

    /**
     * Creates the message that is displayed when the {@code /worldshaper} command is called.
     *
     * @return The created message
     */
    public static String worldShaperInfoMessage() {
        List<String> messages = new LinkedList<>();
        messages.add("Using " + accent("WorldShaper") + " version " + accent(WorldShaperManifest.PLUGIN_VERSION) + ".");
        messages.add("Native Minecraft version is " + accent(WorldShaperManifest.NATIVE_MC_VERSION) + ".");
        messages.add("");
        messages.add("Created by " + accent("CreativeConduit"));
        messages.add("Join our Discord at " + accent(TextColor.UNDERLINE + "https://discord.gg/BDbVGBmCY7"));

        return ChatMessageFormatter.groupedMessages("WorldShaper", messages);
    }
}
