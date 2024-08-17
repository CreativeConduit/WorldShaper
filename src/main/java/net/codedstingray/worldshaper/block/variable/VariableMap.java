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

package net.codedstingray.worldshaper.block.variable;

import net.codedstingray.worldshaper.block.BlockState;
import net.codedstingray.worldshaper.block.mask.Mask;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;

import static net.codedstingray.worldshaper.util.world.LocationUtils.vectorToLocation;

public class VariableMap {

    private final Map<String, Material> blockTypeVariableMap = new HashMap<>();
    private final Map<String, String> blockPropertyVariableMap = new HashMap<>();

    private VariableMap() {
        //private constructor to enforce creation via #create(Mask, World, Vector3i)
    }

    public Material getBlockTypeVariable(String name) {
        return blockTypeVariableMap.get(name);
    }

    public String getBlockTraitVariable(String name) {
        return blockPropertyVariableMap.get(name);
    }

    public void setBlockTypeVariable(String name, Material material) {
        blockTypeVariableMap.put(name, material);
    }

    public void setBlockPropertyVariable(String name, String value) {
        blockPropertyVariableMap.put(name, value);
    }

    public static VariableMap create(Mask mask, World world, Vector3i position) {
        //TODO: mask is currently ignored as extracting variables from it comes with the advanced masks
        VariableMap variables = new VariableMap();
        Block block = world.getBlockAt(vectorToLocation(position, world));
        BlockData blockData = block.getBlockData();

        variables.setBlockTypeVariable("self.blockType", block.getType());
        BlockState blockState = BlockState.fromBlockData(blockData);
        blockState.getBlockProperties().forEach(entry -> variables.setBlockPropertyVariable("self.blockProperty." + entry.getKey(), entry.getValue()));

        return variables;
    }
}
