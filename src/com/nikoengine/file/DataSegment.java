package com.nikoengine.file;

import java.io.Serializable;

/**
 * Helps organize data in the save file.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1205
 * @since 1.8
 */
public class DataSegment implements Serializable {

    /**
     * Defines the segments name.
     */
    private final String segmentName;

    /**
     * Controls all the data fields in the segment.
     */
    private DataField[] dataFields;

    /**
     * Keeps track how many data fields there are in the segment.
     */
    private int amountOfDataFields = 0;

    /**
     * Creates a data segment which is used to put data in a SaveFile.
     *
     * <p>
     * Take when created.
     *
     * @param segmentName Defines a name for this data segment.
     * @param key Key string value to the data field.
     * @param data Saved data itself.
     */
    DataSegment(String segmentName, String key, Object data) {
        this.segmentName = segmentName;
        dataFields = new DataField[SaveFile.TREHESOLD];
        saveDataSegment(key, data);
    }

    /**
     * Gets data field with corresponding name.
     *
     * @param key Key string value to the data field.
     * @return Data field.
     */
    public DataField getDataField(String key) {

        for (int i = 0; i < amountOfDataFields; i++) {
            if (dataFields[i].getKey().equals(key)) {
                return dataFields[i];
            }
        }

        return null;
    }

    /**
     * Saves data to data segment with key string.
     *
     * @param key Key to data.
     * @param data Saved data itself.
     */
    public final void saveDataSegment(String key, Object data) {

        DataField df = getDataField(key);

        if (df != null) {
            df.setData(data);
        } else {

            if (amountOfDataFields >= dataFields.length - 1) {
                DataField[] tmp = new DataField[dataFields.length
                        + SaveFile.TREHESOLD];

                System.arraycopy(dataFields, 0, tmp, 0, dataFields.length);
                dataFields = tmp;
            } else {
                dataFields[amountOfDataFields] = new DataField(key, data);
            }

            amountOfDataFields++;
        }
    }

    /**
     * Returns segments name.
     *
     * @return Segment name.
     */
    public String getSegmentName() {

        return segmentName;
    }

    /**
     * Gets array of data fields from the data segment.
     *
     * @return Array of data fields.
     */
    DataField[] getDataFields() {
        return this.dataFields;
    }

    /**
     * Returns the amount of the data fields in the data segment.
     *
     * @return Amount of data fields.
     */
    public int getAmountOfDataFields() {
        return amountOfDataFields;
    }
}
