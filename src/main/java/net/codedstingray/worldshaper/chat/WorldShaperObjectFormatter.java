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

import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;

/**
 * Utility class containing methods to format certain objects with the WorldShaper format.
 */
public class WorldShaperObjectFormatter {

    /**
     * Returns a String representation of the given vector in WorldShaper vector format.
     *
     * @param vector The vector
     * @return The formatted String representation
     */
    public static String vectorToString(Vector3i vector) {
        return ChatMessageFormatter.messageBuilder(ChatMessageFormatter.MessageLevel.INFO, false)
                .withMessagePrefix(false)
                .a("[").t(vector.getX() + ", " + vector.getY() + ", " + vector.getZ()).a("]")
                .build();
    }
}
