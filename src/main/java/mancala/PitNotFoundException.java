package mancala;

public class PitNotFoundException extends Exception {
    private final static long serialVersionUID = 1L;
    public PitNotFoundException() {
        super("No Such Player");
    }

    public PitNotFoundException(final String message) {
        super(message);
    }
}