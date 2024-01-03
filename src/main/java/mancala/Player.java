package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private final static long serialVersionUID = 1L;
    private String playerName;
    private Store theStore;
    private UserProfile profile;

    public Player() {
        this.playerName = "Player";
        this.profile = new UserProfile(this.playerName);
    }

    public Player(final String startingName) {
        this.playerName = startingName;
        this.profile = new UserProfile(this.playerName);
    }

    public UserProfile getUserProfile() {
        return profile;
    }

    public void setUserProfile(final UserProfile newProfile) {
        profile = newProfile;
    }

    public String getName() {
        return playerName;
    }

    public Store getStore() {
        return theStore;
    }

    public int getStoreCount() {
        return theStore.getStoneCount();
    }

    public void setName(final String name) {
        this.playerName = name;
    }

    /* default */ void setStore(final Store store) {
        this.theStore = store;
    }

    @Override
    public String toString() {
        return "" + getName() + ": " + getStoreCount();
    }
}