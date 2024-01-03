package mancala;

public class NoSuchPlayerException extends Exception {
    private final static long serialVersionUID = 1L;
    public NoSuchPlayerException() {
        super("No Such Player");
    }

    public NoSuchPlayerException(final String message) {
        super(message);
    }
}