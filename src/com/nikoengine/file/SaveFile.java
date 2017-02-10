package com.nikoengine.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Creates a save file instance where developer can place saved data.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1205
 * @since 1.8
 */
public class SaveFile implements Serializable {

    /**
     * Holds upper level data segments which hold data fields.
     */
    private DataSegment[] dataSegments;

    /**
     * Defines how many different data segments there is in the file.
     */
    private int amountOfDataSegments;

    /**
     * Defines the threshold value for increasing the array size.
     */
    final static int TREHESOLD = 5;

    /**
     * Reference to actual save file.
     */
    private File file;

    /**
     * Creates a save file instance where developer can place saved data.
     *
     * @param fileName Path to file.
     */
    public SaveFile(String fileName) {
        this.file = new File(fileName);

        if (!file.isFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dataSegments = new DataSegment[TREHESOLD];
    }

    /**
     * Returns a data segment with corresponding name from save file.
     *
     * @param segmentName Name of the data segment.
     * @return Corresponding data segment.
     */
    public DataSegment getSegment(String segmentName) {

        for (int i = 0; i < amountOfDataSegments; i++) {
            if (dataSegments[i].getSegmentName().equals(segmentName)) {
                return dataSegments[i];
            }
        }

        return null;
    }

    /**
     * Saves data to data segment with key string.
     *
     * @param segmentName Name of the data segment.
     * @param fieldKey Key string to data.
     * @param data Data itself.
     */
    public void save(String segmentName, String fieldKey, Object data) {

        DataSegment ds = getSegment(segmentName);
        
        if (ds != null) {
            
            ds.saveDataSegment(fieldKey, data);
        } else {

            if (amountOfDataSegments >= dataSegments.length - 1) {
                DataSegment[] tmp = new DataSegment[dataSegments.length
                        + TREHESOLD];
                System.arraycopy(dataSegments, 0, tmp, 0, amountOfDataSegments);
                dataSegments = tmp;
            } else {
                dataSegments[amountOfDataSegments] = new DataSegment(
                        segmentName, fieldKey, data);
            }

            amountOfDataSegments++;
        }
    }

    /**
     * Gets the file reference to actual save file.
     *
     * @return File reference to actual save file.
     */
    public File getFile() {
        return this.file;
    }

    @Override
    public String toString() {
        String saveFileDataAsAString = "";

        for (int i = 0; i < amountOfDataSegments; i++) {
            System.out.println(dataSegments[i].getAmountOfDataFields());

            for (int j = 0; j < dataSegments[i].getAmountOfDataFields(); j++) {

                saveFileDataAsAString += dataSegments[i].
                        getDataFields()[j] + "\n";
            }
        }

        return saveFileDataAsAString;
    }
}
