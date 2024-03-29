package mancala;

public class InvalidMoveException extends Exception {
    private final static long serialVersionUID = 1L;
    public InvalidMoveException() {
        super("Invalid Move");
    }

    public InvalidMoveException(final String message) {
        super(message);
    }
}