package mancala;

import java.io.Serializable;

public class Store implements Countable, Serializable {
    private final static long serialVersionUID = 1L;
    private int stones;
    private Player owner = null;

    public Store() {
        this.stones = 0;
    }

    @Override
    public void addStone() {
        stones += 1;
    }

    @Override
    public void addStones(final int numToAdd) {
        stones += numToAdd;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public int getStoneCount() {
        return stones;
    }

    /* default */ void setOwner(final Player player) {
        owner = player;
    }

    @Override
    public int removeStones() {
        final int oldStones = stones;
        stones = 0;

        return oldStones;
    }

    @Override
    public String toString() {
        return "" + getOwner().getName() + ": " + getStoneCount();
    }
}