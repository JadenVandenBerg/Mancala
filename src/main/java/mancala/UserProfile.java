package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private int kalahGames;
    private int ayoGames;
    private int kalahWins;
    private int ayoWins;
    private String name;

    public UserProfile() {
        this("Generic Player");
    }

    public UserProfile(final String setName) {
        this.name = setName;
        this.kalahGames = 0;
        this.ayoGames = 0;
        this.kalahWins = 0;
        this.ayoWins = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String newName) {
        name = newName;
    }

    public int getKalahGames() {
        return kalahGames;
    }

    public int getAyoGames() {
        return ayoGames;
    }

    public void addAyoGame() {
        ayoGames++;
    }

    public void addKalahGame() {
        kalahGames++;
    }

    public int getKalahWin() {
        return kalahWins;
    }

    public int getAyoWin() {
        return ayoWins;
    }

    public void addAyoWin() {
        ayoWins++;
    }

    public void addKalahWin() {
        kalahWins++;
    }
}