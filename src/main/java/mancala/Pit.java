package mancala;

import java.io.Serializable;

public class Pit implements Countable, Serializable {
    private final static long serialVersionUID = 1L;
    private int stones;

    public Pit() {
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

     @Override
    public int getStoneCount() {
        return stones;
    }

    @Override
    public int removeStones() {
        final int oldStones = getStoneCount();
        stones = 0;
        return oldStones;
    }

    @Override
    public String toString() {
        return "" + getStoneCount();
    }
}