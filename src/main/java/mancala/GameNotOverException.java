package mancala;

public class GameNotOverException extends Exception {
    private final static long serialVersionUID = 1L;
    public GameNotOverException() {
        super("Game Not Over");
    }

    public GameNotOverException(final String message) {
        super(message);
    }
}