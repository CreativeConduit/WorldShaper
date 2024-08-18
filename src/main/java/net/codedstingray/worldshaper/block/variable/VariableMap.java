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

/**
 * The Variable Map is used to transfer variable values between masks and patterns.
 * It distinguishes between BlockType variables and BlockProperty variables.
 */
public class VariableMap {

    private final Map<String, Material> blockTypeVariableMap = new HashMap<>();
    private final Map<String, String> blockPropertyVariableMap = new HashMap<>();

    private VariableMap() {
        //private constructor to enforce creation via #create(Mask, World, Vector3i)
    }

    /**
     * Returns the value of the BlockType variable with the given name.
     *
     * @param name The name of the BlockType variable
     * @return The value stored in the variable, or {@code null} if the variable was not set
     */
    public Material getBlockTypeVariable(String name) {
        return blockTypeVariableMap.get(name);
    }

    /**
     * Returns the value of the BlockProperty variable with the given name.
     *
     * @param name The name of the BlockProperty variable
     * @return The value stored in the variable, or {@code null} if the variable was not set
     */
    public String getBlockPropertyVariable(String name) {
        return blockPropertyVariableMap.get(name);
    }

    /**
     * Sets the BlockType variable with the given name to the given value.
     *
     * @param name The name of the BlockType variable
     * @param material The value to be set
     */
    public void setBlockTypeVariable(String name, Material material) {
        blockTypeVariableMap.put(name, material);
    }

    /**
     * Sets the BlockProperty variable with the given name to the given value.
     *
     * @param name The name of the BlockProperty variable
     * @param value The value to be set
     */

    public void setBlockPropertyVariable(String name, String value) {
        blockPropertyVariableMap.put(name, value);
    }

    /**
     * Creates a new {@link VariableMap} instance from the given parameters.
     * It reads the mask to determine the variables that shall be created and uses the world to determine the values for
     * these variables.
     * <br>
     * All positions referred to in the mask are relative to the given base position, which is the
     * position of the block currently being checked by the mask and about to be (potentially) modified.
     *
     * @param mask The {@link Mask} to read the variables from
     * @param world The {@link World} to read values from
     * @param position The position of the block currently being checked / modified
     * @return The created {@link VariableMap}
     */
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
