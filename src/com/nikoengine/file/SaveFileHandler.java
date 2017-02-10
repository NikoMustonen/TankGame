package com.nikoengine.file;

import java.io.ObjectInputStream;
import java.io.*;

/**
 * Handles save files.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1205
 * @since 1.8
 */
public class SaveFileHandler {

    /**
     * Loads data from the save file and returns it in a SaveFile format.
     * 
     * @param fileName Path to file.
     * @return SaveFile instance.
     * @throws IOException Is thrown when file is not found.
     */
    public static SaveFile loadFile(String fileName) throws IOException {

        SaveFile save = new SaveFile(fileName);

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(save.getFile()))) {

            save = (SaveFile) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return save;
    }

    /**
     * Saves all the data stored in a save file.
     * 
     * @param save Save file to be saved.
     */
    public static void saveFile(SaveFile save) {

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(save.getFile()))) {

            oos.writeObject(save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
