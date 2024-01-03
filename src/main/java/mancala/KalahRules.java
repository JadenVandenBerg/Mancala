package mancala;

import java.io.Serializable;

public class KalahRules extends GameRules implements Serializable {
    private final static long serialVersionUID = 1L;
    private int addedToStore = 0;

    private boolean forceStayTurn;

    public KalahRules() {
        super();
        forceStayTurn = false;
    }

    private boolean getForceStayTurn() {
        return forceStayTurn;
    }

    private void setForceStayTurn(final boolean force) {
        forceStayTurn = force;
    }

    private int getPlayerNum() {
        return getPlayer();
    }

    /**
     * Gets the game rules in string
     *
     * @return game rules
     */

     @Override
    public String getGameRules() {
        return "Kalah";
    }
    
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        addedToStore = 0;
        if (playerNum == 1 && (startPit >= 7 || startPit <= 0)) {
            throw new InvalidMoveException();
        } else if (playerNum == 2 && (startPit >= 13 || startPit <= 6)) {
            throw new InvalidMoveException();
        }

        final MancalaDataStructure board = getDataStructure();
        board.setIterator(startPit, playerNum, false);

        distributeStones(startPit);

        if (!getForceStayTurn()) {
            //Flip players
            if (playerNum == 1) {
                setPlayer(2);
            }
            else {
                setPlayer(1);
            }
        }

        System.out.println("Kalah moveStones return: " + addedToStore);
        return addedToStore;
    }

    /* default */ @Override int distributeStones(final int startPit) {

        setForceStayTurn(false);
        addedToStore = 0;
        int numStones = 0;

        final MancalaDataStructure board = getDataStructure();
        board.setIterator(startPit, getPlayer(), false);
        numStones = getNumStones(startPit);
        removeStones(startPit);
        int positionsMoved = numStones;

        for (int i = 1; i <= numStones; i++) {
            final Countable toAddStone = board.next();
            final Store store = new Store();

            if (i == numStones && toAddStone.getClass() != store.getClass() && toAddStone.getStoneCount() == 0) {
                toAddStone.addStone();
                int capturer = (startPit + positionsMoved) % 12;
                if (capturer == 0) {
                    capturer = 12;
                }
                captureStones(capturer);
            } else if (i == numStones) {
                //Last position is store
                final int preStoreCount = board.getStoreCount(getPlayerNum());
                toAddStone.addStone();
                if (board.getStoreCount(getPlayerNum()) > preStoreCount) {
                    setForceStayTurn(true);
                }
            } else {
                final int preStoreCount = board.getStoreCount(getPlayerNum());
                toAddStone.addStone();
                if (board.getStoreCount(getPlayerNum()) > preStoreCount) {
                    positionsMoved -= 1;
                }
            }

            if (toAddStone.getClass() == store.getClass()) {
                addedToStore++;
            }
        }

        System.out.println("Kalah distributeStones return: " + numStones);
        return numStones;
    }

    /* default */ @Override int captureStones(final int stoppingPoint) {
        addedToStore = 0;
        final int opposingSide = 13 - stoppingPoint;
        boolean canCapture = false;

        if (getPlayer() == 1 && stoppingPoint >= 1 && stoppingPoint <= 6) {
            canCapture = true;
        } else if (getPlayer() == 2 && stoppingPoint >= 7 && stoppingPoint <= 12) {
            canCapture = true;
        }

        int stonesToCapture = 0;

        if (canCapture && getNumStones(opposingSide) != 0) {
            stonesToCapture = getNumStones(opposingSide) + 1;
            removeStones(stoppingPoint);
            removeStones(opposingSide);

            final MancalaDataStructure board = getDataStructure();
            board.addToStore(getPlayer(), stonesToCapture);
        }

        addedToStore += stonesToCapture;

        System.out.println("Kalah captureStones return: " + stonesToCapture);
        return stonesToCapture;
    }
}