package mancala;
import java.util.ArrayList;

import java.io.Serializable;

public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private GameRules board;
    private Player currentPlayer;
    final private ArrayList<Player> players = new ArrayList<>();

    public MancalaGame() {
        //Default board in case the user does not provide a board
        this(new KalahRules());
    }

    public MancalaGame(final GameRules rules) {
        board = rules;
    }

    /**
     * Gets board
     *
     * @return board
     */
    public GameRules getBoard() {
        return board;
    }

    /**
     * Gets current player as Player
     *
     * @return 
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets number of stones in a pit
     *
     * @param pitNum pitnumber
     * @return num of stones
     * @throws PitNotFoundException if pit is not found
     */
    public int getNumStones(final int pitNum) throws PitNotFoundException {
        if (pitNum < 0 || pitNum > 11) {
            throw new PitNotFoundException();
        }

        return getStoneCountFromPit(pitNum);
    }

    /**
     * gets players
     *
     * @return arraylist of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * gets store count
     *
     * @param player player to get store count of
     * @return number of stones in players store
     * @throws NoSuchPlayerException if player doesnt exist
     */
    public int getStoreCount(final Player player) throws NoSuchPlayerException {
        if (!getPlayers().contains(player)) {
            throw new NoSuchPlayerException();
        }
        return player.getStore().getStoneCount();
    }

    /**
     * finds players scores (call at game over)
     *
     * @return array containing both players scores. 1: 0, 2: 1
     */
    public int[] findPlayerScores() {
        int[] arr = {0,0};
        try {
            arr[0] += getStoreCount(getPlayerFromIndex(0));
            arr[1] += getStoreCount(getPlayerFromIndex(1));
        } catch (NoSuchPlayerException p) {
            return null;
        }
        
        for (int i = 0; i < 6; i++) {
            arr[0] += getStoneCountFromPit(i);
        }

        for (int i = 6; i < 12; i++) {
            arr[1] += getStoneCountFromPit(i);
        }

        return arr;
    }

    /**
     * gets winner
     *
     * @return winner
     * @throws GameNotOverException if game is not over
     */
    public Player getWinner() throws GameNotOverException {
        if (!isGameOver()) {
            throw new GameNotOverException();
        }

        final int[] scores = findPlayerScores();
        Player returner;

        if (scores[0] > scores[1]) {
            addToPlayerWinGame(board.getGameRules(), 0);
            returner = getPlayerFromIndex(0);
        } else if (scores[1] > scores[0]) {
            addToPlayerWinGame(board.getGameRules(), 1);
            returner =  getPlayerFromIndex(1);
        } else {
            returner =  null;
        }

        return returner;
    }

    /**
     * adds to players win profile
     *
     * @param rules the rules as a string
     * @param winner the index of the winner (0 base)
     */
    public void addToPlayerWinGame(final String rules, final int winner) {
        if ("Kalah".equals(rules)) {
            getUserProfile(getPlayerFromIndex(0)).addKalahGame();
            getUserProfile(getPlayerFromIndex(1)).addKalahGame();

            getUserProfile(getPlayerFromIndex(winner)).addKalahWin();
        }
        else {
            getUserProfile(getPlayerFromIndex(0)).addAyoGame();
            getUserProfile(getPlayerFromIndex(1)).addAyoGame();

            getUserProfile(getPlayerFromIndex(winner)).addAyoWin();
        }
    }

    private UserProfile getUserProfile(Player player) {
        return player.getUserProfile();
    }

    /**
     * Checks if game is over
     * 
     * @return if the game is over
     */
    public boolean isGameOver() {
        boolean sideOne;
        boolean sideTwo;

        sideOne = getBoard().isSideEmpty(1);
        sideTwo = getBoard().isSideEmpty(10);


        return (sideOne || sideTwo);
    }

    /**
     * gets num of stones in pit
     *
     * @param index index
     * @return num of stones
     */

    public int getStoneCountFromPit(final int index) {
        final GameRules board = getBoard();
        return board.getDataStructure().getPits().get(index).getStoneCount();
    }

    /**
     * gets player from index
     *
     * @param index index
     * @return player
     */
    public Player getPlayerFromIndex(final int index) {
        return getPlayers().get(index);
    }

    /**
     * gets the index of the current player
     *
     * @return the index
     */
    private int getCurrentPlayerIndex() {
        int playerIndex;

        if (getPlayerFromIndex(0) == getCurrentPlayer()) {
            playerIndex = 1;
        }
        else {
            playerIndex = 2;
        }

        return playerIndex;
    }

    /**
     * caller for board.moveStones
     *
     * @return num of stones on the side that player moved from
     * @throws InvalidMoveException if move is invalid
     */
    public int move(final int startPit) throws InvalidMoveException {
        final int playerIndex = getCurrentPlayerIndex();

        getBoard().moveStones(startPit, playerIndex);
        //Find Stone Count (Return Value)
        int sideCount = 0;
        if (currentPlayer == getPlayerFromIndex(0)) {
            for (int i = 0; i < 6; i++) {
                sideCount += getStoneCountFromPit(i);
            }
        } else if (currentPlayer == getPlayerFromIndex(1)) {
            for (int i = 6; i < 12; i++) {
                sideCount +=getStoneCountFromPit(i);
            }
        }

        //Update Player
        currentPlayer = getPlayerFromIndex(getBoard().getPlayer() - 1);

        return sideCount;
    }

    /**
     * Sets board to a new board
     * 
     * @param theBoard the board
     */
    public void setBoard(final GameRules theBoard) {
        board = theBoard;
    }

    /**
     * Sets current player
     * 
     * @param player the player
     */
    public void setCurrentPlayer(final Player player) {
        currentPlayer = player;
    }

    /**
     * Sets players
     * 
     * @param onePlayer p1
     * @param twoPlayer p2
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        if (!players.isEmpty()) {
            players.clear();
        }

        addPlayer(onePlayer);
        addPlayer(twoPlayer);

        board.registerPlayers(onePlayer, twoPlayer);
        setCurrentPlayer(onePlayer);
    }

    /**
     * Adds player to players list
     * 
     * @param player the player
     */
    public void addPlayer(final Player player) {
        players.add(player);
    }

    /**
     * Sets up board for new game
     * 
     */
    public void startNewGame() {
        for (int i = 0; i < 12; i++) {
            board.removeStones(i + 1);
        }
        board.resetBoard();
        setCurrentPlayer(players.get(0));
        board.setPlayer(1);
    }

    @Override
    public String toString() {
        return getBoard().toString();
    }
}