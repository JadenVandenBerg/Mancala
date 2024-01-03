package mancala;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import mancala.MancalaGame;
import mancala.UserProfile;

public class Saver {
    public static void saveObject(final Serializable toSave, final String filename) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOut.writeObject(toSave);
        }
    }

    public static Serializable loadObject(final String filename) throws IOException {
        MancalaGame loadedMancala;

        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            loadedMancala = (MancalaGame) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }

        return loadedMancala;
    }

    public static Serializable loadProfile(final String filename) throws IOException {
        UserProfile loadedMancala;

        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            loadedMancala = (UserProfile) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }

        return loadedMancala;
    }
}