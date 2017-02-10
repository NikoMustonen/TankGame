package com.nikoengine.resources;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Helper class for resource loading.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1121
 * @since 1.8
 */
public class Resources {

    /**
     * Holds default file path root folder.
     */
    private static String defaultFilePath = "";

    /**
     * Creates a new Buffered image.
     *
     * @param fileName File path.
     * @return New image.
     */
    public static BufferedImage loadImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(defaultFilePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    /**
     * Creates a new audio clip.
     * 
     * @param fileName File name and path.
     * @return New sound clip.
     */
    public static AudioClip loadSoundClip(String fileName) {
        File f = new File(defaultFilePath + fileName);
        URI uri = f.getAbsoluteFile().toURI();
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return Applet.newAudioClip(url);
    }

    /**
     * Sets default path.
     *
     * @param path Path name.
     */
    public void setDefaultPath(String path) {
        defaultFilePath = path;
    }
}
