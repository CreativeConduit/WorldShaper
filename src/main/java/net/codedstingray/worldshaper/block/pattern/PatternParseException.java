package net.codedstingray.worldshaper.block.pattern;

public class PatternParseException extends Exception {

    static final long serialVersionUID = 4897000110446767803L;

    public PatternParseException() {
        super();
    }

    public PatternParseException(String message) {
        super(message);
    }

    public PatternParseException(Throwable cause) {
        super(cause);
    }

    public PatternParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
