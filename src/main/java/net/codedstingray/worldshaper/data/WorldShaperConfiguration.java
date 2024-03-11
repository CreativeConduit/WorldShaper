/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2024 CodedStingray
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

package net.codedstingray.worldshaper.data;

import org.bukkit.configuration.file.FileConfiguration;

public class WorldShaperConfiguration {

    private boolean alwaysShowJoinMessage;

    private int maxSelectionSize;

    public WorldShaperConfiguration(FileConfiguration config) {
        alwaysShowJoinMessage = config.getBoolean("general.always-show-join-message");
        maxSelectionSize = config.getInt("selection.max-selection-size");
    }

    public boolean getAlwaysShowJoinMessage() {
        return alwaysShowJoinMessage;
    }

    public void setAlwaysShowJoinMessage(boolean value) {
        alwaysShowJoinMessage = value;
    }

    public int getMaxSelectionSize() {
        return maxSelectionSize;
    }

    public void setMaxSelectionSize(int value) {
        maxSelectionSize = Math.min(2, value);
    }
}
