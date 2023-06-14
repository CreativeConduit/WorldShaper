package net.codedstingray.worldshaper.util.chat;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper enumeration providing an easy way of adding Minecraft color and formatting codes to texts. <br>
 * This enum's {@link #toString()}-method returns the string code required to color text within Minecraft's chat.
 * Any code can thus be added to any string by way of concatenation, e.g.
 * <pre>
 * "This is a " + TextColor.AQUA + " partially colored " + TextColor.RESET + " text!"
 * </pre>
 * Note that when providing these as parameter to a method or when trying to concatenate multiple TextColor objects
 * at the beginning of a String, a call to {@link #toString()} might be necessary.
 */
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

    /**
     * The color code escape character, '{@code §}'.
     */
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

    /**
     * Returns this TextColor's color code as {@code §<code>}, e.g. {@code §0}, {@code §1}, etc.
     * @return This TextColor's color code
     */
    @Override
    public String toString() {
        return stringValue;
    }

    /**
     * Returns the TextColor instance corresponding the given color / formatting code.
     * @param code The color / formatting code
     * @return The TextColor instance, or null if no instance exists with the given code.
     */
    public static TextColor getByChar(char code) {
        return byChar.get(code);
    }

    static {
        for (TextColor color : values()) {
            byChar.put(color.code, color);
        }
    }
}
