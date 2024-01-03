package mancala;

import java.io.Serializable;

public class AyoRules extends GameRules implements Serializable {
    private static final long serialVersionUID = 1L;
    private int addedToStore = 0;

    private boolean forceStayTurn;

    public AyoRules() {
        super();
        forceStayTurn = false;
    }

    private boolean getForceStayTurn() {
        return forceStayTurn;
    }

    private void setForceStayTurn(final boolean force) {
        forceStayTurn = force;
    }

    /**
     * Gets the game rules in string
     *
     * @return game rules
     */
    @Override
    public String getGameRules() {
        return "Ayo";
    }

    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {

        if (playerNum == 1 && (startPit >= 7 || startPit <= 0)) {
            throw new InvalidMoveException();
        } else if (playerNum == 2 && (startPit >= 13 || startPit <= 6)) {
            throw new InvalidMoveException();
        }

        final MancalaDataStructure board = getDataStructure();
        board.setIterator(startPit, playerNum, true);
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

        System.out.println("Ayo moveStones return: " + addedToStore);
        return addedToStore;
    }

    /* default */ @Override int distributeStones(final int startPit) {

        setForceStayTurn(false);
        addedToStore = 0;

        final MancalaDataStructure board = getDataStructure();
        board.setIterator(startPit, getPlayer(), true);
        int numStones = getNumStones(startPit);
        removeStones(startPit);

        boolean pitNotEmpty = true;

        final Store storeForComp = new Store();

        int pitGetter = startPit % 12;
        if (pitGetter == 0) {
            pitGetter = 12;
        }
        final Pit sPit = board.getPits().get(pitGetter - 1);
        int iters = -1;

        while (pitNotEmpty) {
            iters++;
            int positionsMoved = numStones;
            for (int i = 1; i <= numStones; i++) {
                final Countable toAddStone = board.next();
                
                if (i == numStones) {
                    //Last Stone

                    if (storeForComp.getClass() == toAddStone.getClass()) {
                        toAddStone.addStone();
                        pitNotEmpty = false;
                    } else if (toAddStone.getStoneCount() == 0) {
                        toAddStone.addStone();
                        int capturer = (startPit + positionsMoved) % 12;
                        if (capturer == 0) {
                            capturer = 12;
                        }
                        captureStones(capturer);
                        pitNotEmpty = false;
                    } else {
                        toAddStone.addStone();

                        numStones = toAddStone.getStoneCount();
                        toAddStone.removeStones();
                    }

                    break;
                } else {
                    toAddStone.addStone();
                }

                if (storeForComp.getClass() == toAddStone.getClass()) {
                    positionsMoved -= 1;
                    addedToStore++;
                }

                if (sPit == toAddStone && iters != 0) {
                    positionsMoved += 1;
                }
            }
        }

        System.out.println("Ayo distributeStones return: " + numStones);
        return numStones;
    }

    /* default */ @Override int captureStones(final int stoppingPoint) {
        final int opposingSide = 13 - stoppingPoint;
        boolean canCapture = false;

        if (getPlayer() == 1 && stoppingPoint >= 1 && stoppingPoint <= 6) {
            canCapture = true;
        } else if (getPlayer() == 2 && stoppingPoint >= 7 && stoppingPoint <= 12) {
            canCapture = true;
        }

        int stonesToCapture = 0;

        if (canCapture) {
            stonesToCapture = getNumStones(opposingSide);
            removeStones(opposingSide);

            final MancalaDataStructure board = getDataStructure();
            board.addToStore(getPlayer(), stonesToCapture);
        }

        addedToStore += stonesToCapture;

        System.out.println("Ayo captureStones return: " + stonesToCapture);
        return stonesToCapture;
    }
}