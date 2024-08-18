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

package net.codedstingray.worldshaper.block;

import net.codedstingray.worldshaper.block.mask.Mask;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is currently a bit of a Frankenstein's monster, mostly as a workaround for my problem with getting all
 * block traits in {@link net.codedstingray.worldshaper.block.variable.VariableMap#create(Mask, World, Vector3i) VariableMap#create}.
 * <p>
 * This needs to be replaced / extended by a proper, feature complete BlockType/BlockProperty/BlockState system that is
 * standalone and independent of any plugin API. This needs to happen *SOON*, but will not happen in this branch.
 * <p>
 * I will also not document anything in this class since it's super temporary.
 */
public class BlockState {

    private final Material blockType;

    // Using Strings as keys and values with no type restrictions here is EXTREMELY dirty
    // In the long run, this has to be replaced with proper generic Property classes
    private final Map<String, String> blockProperties;

    private BlockState(Material blockType, Map<String, String> blockProperties) {
        this.blockType = blockType;
        this.blockProperties = blockProperties;
    }

    public Material getBlockType() {
        return blockType;
    }

    public Set<Map.Entry<String, String>> getBlockProperties() {
        return blockProperties.entrySet();
    }


    // These methods need to be put into their own Parser / BukkitAdapter classes. Won't do it now to keep this
    // quick and dirty thing as lightweight as possible
    public static BlockState fromBlockData(BlockData data) {
        return parse(data.getAsString());
    }

    public static BlockState parse(String blockStateString) {
        Material blockType = Bukkit.createBlockData(blockStateString).getMaterial();

        // This whole next code has 0 safety checks to make sure that values are actually reasonable,
        // but since I parse the Bukkit BlockData up top that one should already throw an exception
        // if something's not alright. Again super dirty, will be fixed later.
        Map<String, String> blockProperties = new HashMap<>();
        String[] split = blockStateString.split("\\[");

        if (split.length > 1) {
            String propertiesString = split[1].substring(0, split[1].length() - 1);
            String[] properties = propertiesString.split(",");
            for (String property : properties) {
                String[] keyValuePair = property.split("=");
                blockProperties.put(keyValuePair[0], keyValuePair[1]);
            }
        }

        return new BlockState(blockType, blockProperties);
    }
}
