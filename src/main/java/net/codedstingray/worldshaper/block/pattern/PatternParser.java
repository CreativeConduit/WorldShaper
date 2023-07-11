package net.codedstingray.worldshaper.block.pattern;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import java.util.Arrays;

public class PatternParser {

    public static Pattern parsePattern(String patternString) throws PatternParseException {
        int index = 0;
        char[] patternStringChars = patternString.toCharArray();
        Pattern.Builder patternBuilder = Pattern.builder();

        while (index < patternStringChars.length) {
            try {
                index = parsePatternEntry(patternStringChars, index, patternBuilder);
            } catch (IndexOutOfBoundsException e) {
                throw new PatternParseException("Premature end of pattern string at entry: \"" + patternString.substring(index) + "\"", e);
            } catch (Throwable t) {
                throw new PatternParseException("Pattern parsing failed due to an unknown error", t);
            }
        }

        return patternBuilder.build();
    }

    private static int parsePatternEntry(char[] patternStringChars, int index, Pattern.Builder builder) throws PatternParseException {
        int mainIndex = index;

        int percentageValue;
        BlockData blockData;

        if (Character.isDigit(patternStringChars[mainIndex])) {
            int subIndex = scanPercentage(patternStringChars, mainIndex);
            if (patternStringChars[subIndex] != '%') {
                throw new PatternParseException("Digit or '%' expected, but got '" + patternStringChars[subIndex] + "'");
            }

            percentageValue = getPercentageValue(patternStringChars, mainIndex, subIndex);
            mainIndex = subIndex + 1;
        } else {
            percentageValue = 0;
        }

        if (Character.isLetter(patternStringChars[mainIndex])) {
            int subIndex = scanBlockId(patternStringChars, mainIndex);
            if (subIndex < patternStringChars.length && patternStringChars[subIndex] == '[') {
                subIndex = scanBlockProperties(patternStringChars, subIndex);
            }

            if (subIndex < patternStringChars.length && patternStringChars[subIndex] != ',') {
                throw new PatternParseException("Expected ',', but got " + patternStringChars[subIndex]);
            }

            blockData = createBlockData(patternStringChars, mainIndex, subIndex);

            mainIndex = subIndex + 1;
        } else {
            throw new PatternParseException("Expected letter" + (percentageValue == 0 ? " or digit" : "") + ", but got '" + patternStringChars[mainIndex] + "'");
        }

        builder.with(percentageValue, blockData);

        return mainIndex + 1;
    }

    private static int scanPercentage(char[] patternStringChars, int mainIndex) {
        int subIndex = mainIndex;
        while (Character.isDigit(patternStringChars[subIndex])) {
            subIndex++;
        }
        return subIndex;
    }

    private static int scanBlockId(char[] patternStringChars, int mainIndex) {
        int subIndex = mainIndex;
        while (subIndex < patternStringChars.length && (patternStringChars[subIndex] == '_' || Character.isLetter(patternStringChars[subIndex]))) {
            subIndex++;
        }
        return subIndex;
    }

    private static int scanBlockProperties(char[] patternStringChars, int mainIndex) {
        int subIndex = mainIndex;
        while (patternStringChars[subIndex] != ']') {
            subIndex++;
        }
        return subIndex + 1;
    }

    private static int getPercentageValue(char[] patternStringChars, int mainIndex, int subIndex) throws PatternParseException {
        int percentageValue;
        String percentageString = new String(Arrays.copyOfRange(patternStringChars, mainIndex, subIndex));

        try {
            percentageValue = Integer.parseInt(percentageString);
        } catch (NumberFormatException e) {
            throw new PatternParseException("Unable to parse percentage value \"" + percentageString + "\"", e);
        }

        return percentageValue;
    }

    private static BlockData createBlockData(char[] patternStringChars, int mainIndex, int subIndex) throws PatternParseException {
        BlockData blockData;
        String blockDataString  = new String(Arrays.copyOfRange(patternStringChars, mainIndex, subIndex));
        try {
            blockData = Bukkit.createBlockData(blockDataString);
        } catch (IllegalArgumentException e) {
            throw new PatternParseException("Unable to parse block data: \"" + blockDataString + "\"", e);
        }
        return blockData;
    }
}
