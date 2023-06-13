package net.codedstingray.worldshaper.util.chat;

import java.util.HashMap;
import java.util.Map;

public enum TextColor {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),

    MAGIC('k',  true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),

    RESET('r');

    public static final char COLOR_CHAR = '\u00A7';

    private static final Map<Character, TextColor> byChar = new HashMap<>();

    private final char code;
    private final boolean isFormat;
    private final String stringValue;

    TextColor(char code) {
        this(code, false);
    }

    TextColor(char code, boolean isFormat) {
        this.code = code;
        this.isFormat = isFormat;
        stringValue = new String(new char[] {COLOR_CHAR, code});
    }

    public char getCode() {
        return code;
    }

    public boolean isFormat() {
        return isFormat;
    }

    public boolean isColor() {
        return !isFormat && this != RESET;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public static TextColor getByChar(char code) {
        return byChar.get(code);
    }

    static {
        for (TextColor color : values()) {
            byChar.put(color.code, color);
        }
    }
}
