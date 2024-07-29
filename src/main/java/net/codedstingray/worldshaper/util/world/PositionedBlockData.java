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

package net.codedstingray.worldshaper.util.world;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import org.bukkit.block.data.BlockData;

/**
 * Associates a {@link BlockData} instance with a grid position.
 *
 * @param position The grid position
 * @param blockData The BlockData instance
 */
public record PositionedBlockData(Vector3ii position, BlockData blockData) {
}
