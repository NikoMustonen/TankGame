package com.nikoengine.image;

import com.nikoengine.resources.Resources;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for creating images for visual presentation.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1121
 * @since 1.8
 */
public class Image extends Drawable {

    /**
     * Creates a new image.
     *
     * @param fileName Path to file.
     * @param x Images position on the x-plane.
     * @param y Images position on the y-plane.
     */
    public Image(String fileName, int x, int y) {
        img = Resources.loadImage(fileName);
        setPosition(x, y);
    }

    /**
     * Creates a new image with default (0, 0) position.
     *
     * <p>
     * Used for creating sub images.
     *
     * @param img Creates a new Image for given image.
     */
    private Image(BufferedImage img) {
        this.img = img;
    }

    @Override
    public void draw(Graphics2D g) {
        double rotationAnchorX = getX() + offSetX + (getWidth() * scaleX) / 2;
        double rotationAnchorY = getY() + offSetY + (getHeight() * scaleY) / 2;

        g.rotate(radians, rotationAnchorX, rotationAnchorY);

        g.drawImage(img, getX() + getOffSetX(), getY() + getOffSetY(),
                (int) (getWidth() * scaleX), (int) (getHeight() * scaleY),
                null);

        g.rotate(-radians, rotationAnchorX, rotationAnchorY); //Reset g
    }

    /**
     * Creates a sub image from the image.
     *
     * @param x Starting position on the x-plane.
     * @param y Starting position on the y-plane.
     * @param width Sub images width.
     * @param height Sub images height.
     * @return Sub image from original image.
     */
    public Image getSubImage(int x, int y, int width, int height) {

        if (width > img.getWidth()) {
            width = img.getWidth();
        }

        if (height > img.getHeight()) {
            height = img.getHeight();
        }

        return new Image(this.img.getSubimage(x, y, width, height));
    }

    @Override
    public void update(float delta) {
        radians = Math.toRadians(rotation);
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }
}
