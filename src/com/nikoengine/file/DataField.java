package com.nikoengine.file;

import java.io.Serializable;

/**
 * Holds data with key string.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1205
 * @since 1.8
 */
public class DataField implements Serializable {

    /**
     * Helps find correct data field.
     */
    private String key;
    
    /**
     * Stores data field data.
     */
    private Object data;

    /**
     * Creates a new data field which has a key string and saved data.
     * 
     * @param key Key to the data.
     * @param data Saved data.
     */
    protected DataField(String key, Object data) {
        this.key = key;
        this.data = data;
    }

    /**
     * Returns the key value of the data field.
     * 
     * @return Key value to data field.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the data of the data field.
     * 
     * @return Saved data.
     */
    public Object getData() {
        return data;
    }

    /**
     * Replaces data with new data.
     * 
     * @param data New data.
     */
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Key: " + key + ", Data: " + data.toString();
    }
}
