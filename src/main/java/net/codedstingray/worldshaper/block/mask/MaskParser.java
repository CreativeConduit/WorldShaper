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

package net.codedstingray.worldshaper.block.mask;

import org.bukkit.Material;

public class MaskParser {

    public static Mask parseMask(String maskString) throws MaskParseException {
        String[] maskEntryStrings = maskString.split(",");

        Mask.Builder builder = Mask.builder();

        for (String maskEntryString: maskEntryStrings) {
            char[] chars = maskEntryString.toCharArray();
            char first = chars[0];
            int blockOffset = 0;
            boolean not = false;
            if (first == '>') {
                blockOffset = -1;
            } else if (first == '<') {
                blockOffset = 1;
            } else if (first == '!') {
                not = true;
            }

            String materialString = blockOffset == 0 ? maskEntryString : maskEntryString.substring(1);
            Material material = Material.matchMaterial(materialString);

            if (material == null) {
                throw new MaskParseException("Unable to parse material \"" + materialString + "\"");
            }

            builder.with(material, blockOffset, not);
        }

        return builder.build();
    }
}
