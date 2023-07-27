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
