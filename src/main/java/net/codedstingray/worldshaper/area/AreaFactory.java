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

package net.codedstingray.worldshaper.area;

/**
 * A factory to create {@link Area Aeras}.<br>
 * Areas can't be directly mapped to their area type name in {@link net.codedstingray.worldshaper.data.PluginData PluginData}
 * since a new area needs to be created per player when they select an area type. Therefor, the PluginData maps these
 * factories instead.
 */
public interface AreaFactory {

    /**
     * Creates a new area instance.
     *
     * @return The created {@link Area}
     */
    Area create();
}
