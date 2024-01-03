package mancala;

import java.io.Serializable;
/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private final static long serialVersionUID = 1L;
    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        currentPlayer = 1;
        gameBoard = new MancalaDataStructure();
        resetBoard();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * removes stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones removed.
     */
    public int removeStones(final int pitNum) {
        return gameBoard.removeStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */

    /* default */ MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    
    /* default */ boolean isSideEmpty(final int pitNum) {
        // This method can be implemented in the abstract class.
    
        boolean isSideNotEmpty = false;
        if (pitNum <= 6) {
            for (int i = 1; i <= 6; i++) {
                if (getNumStones(i) > 0) {
                    isSideNotEmpty = true;
                }
            }
        }

        if (pitNum > 6) {
            for (int i = 7; i <= 12; i++) {
                if (getNumStones(i) > 0) {
                    isSideNotEmpty = true;
                }
            }
        }

        return !isSideNotEmpty;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Gets the current player idx
     *
     * @return current player
     */
    public int getPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the game rules in string
     *
     * @return game rules
     */
    public abstract String getGameRules();

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    /* default */ abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    /* default */ abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.

        final Store store = new Store();
        final Store store2 = new Store();

        store.setOwner(one);
        store2.setOwner(two);

        one.setStore(store);
        two.setStore(store2);

        gameBoard.setStore(store, 1);
        gameBoard.setStore(store2, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    @Override
    public String toString() {
        // Implement toString() method logic here.
        String string = "\n";
        for (int i = 1; i <= gameBoard.getPits().size(); i++) {
            if (i == 7) {
                string += "\n[" + gameBoard.getData().get(11) + "]\t\t\t\t\t\t[" + gameBoard.getData().get(6) + "]\n";
            }
            string += "\t" + "[" + gameBoard.getPits().get(i - 1) + "]";
        }
        return string + "\n";
    }
}
