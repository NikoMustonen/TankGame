package com.nikoengine.image;

import com.nikoengine.camera.Camera2D;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Base class for all the drawable elements.
 *
 * Works as a base class for all the drawable elements like images and
 * animations.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1121
 * @since 1.8
 */
public abstract class Drawable {

    /**
     * Holds the image to be drawn.
     */
    public BufferedImage img;

    /**
     * Holds the position value on the x-plane.
     */
    int x;

    /**
     * Holds the position value on the y-plane.
     */
    int y;

    /**
     * Determines drawing width.
     */
    int width;

    /**
     * Determines drawing height.
     */
    int height;

    /**
     * Determines drawing offset on the x-plane.
     */
    int offSetX;

    /**
     * Determines drawing offset on the y-plane.
     */
    int offSetY;

    /**
     * Controls images rotation.
     */
    protected int rotation;

    /**
     * Holds rotation value in radians for drawing.
     */
    double radians = 0;

    /**
     * Controls images drawing width scale.
     */
    protected float scaleX = 1;

    /**
     * Controls images drawing height scale.
     */
    protected float scaleY = 1;

    /**
     * Draws image.
     *
     * @param g Graphics element used for drawing.
     */
    public void draw(Graphics2D g) {

        if (isVisible) {

            double rotationAnchorX = getX() + offSetX + (getWidth()
                    * scaleX) / 2;

            double rotationAnchorY = getY() + offSetY + (getHeight()
                    * scaleY) / 2;

            g.rotate(radians, rotationAnchorX, rotationAnchorY);

            g.drawImage(img, (int) (getX() + getOffSetX()),
                    getY() + getOffSetY(),
                    (int) (getWidth() * scaleX),
                    (int) (getHeight() * scaleY),
                    null);

            g.rotate(-radians, rotationAnchorX, rotationAnchorY); //Reset g
        }
    }

    /**
     * Renders image through Camera2D element.
     *
     * @param cam Camera2D element.
     * @param g Graphics element used for drawing.
     */
    public void renderThroughCamera(Camera2D cam, Graphics2D g) {

        if (isVisible) {

            int drawX = getX() - cam.getX();
            int drawY = getY() - cam.getY();

            double rotationAnchorX = drawX + offSetX + (getWidth() * scaleX)
                    / 2;

            double rotationAnchorY = drawY + offSetY + (getHeight() * scaleY)
                    / 2;

            g.rotate(radians, rotationAnchorX, rotationAnchorY);

            g.drawImage(img, (int) (drawX + getOffSetX()),
                    drawY + getOffSetY(),
                    (int) (getWidth() * scaleX),
                    (int) (getHeight() * scaleY),
                    null);

            g.rotate(-radians, rotationAnchorX, rotationAnchorY); //Reset g
        }
    }

    /**
     * Updates the drawable element.
     *
     * @param delta Delta time used for animation timing.
     */
    public abstract void update(float delta);

    /**
     * Sets position to the drawable element.
     *
     * @param x Position on the x-plane.
     * @param y Position on the y-plane.
     */
    public final void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Sets position to the drawable element.
     *
     * @param x Position on the x-plane.
     */
    public final void setX(int x) {
        this.x = x;
    }

    /**
     * Sets position to the drawable element.
     *
     * @param y Position on the y-plane.
     */
    public final void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the x position of the drawable element.
     *
     * @return Position on the x-plane.
     */
    public final int getX() {
        return this.x;
    }

    /**
     * Returns the y position of the drawable element.
     *
     * @return Position on the y-plane.
     */
    public final int getY() {
        return this.y;
    }

    /**
     * Sets rotation for the image.
     *
     * @param rotation New rotation.
     */
    public final void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Sets new drawing width for the image.
     *
     * @param width New drawing width.
     */
    public final void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets new drawing height for the image.
     *
     * @param height New drawing height.
     */
    public final void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the width of the drawable element.
     *
     * @return Drawable element´s width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the drawable element.
     *
     * @return Drawable element´s height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets scale to the drawable element.
     *
     * @param scale New scale.
     */
    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
    }

    /**
     * Sets scale to the drawable element.
     *
     * @param scaleX New width scale.
     */
    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    /**
     * Sets scale to the drawable element.
     *
     * @param scaleY New height scale.
     */
    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    /**
     * Returns the width scale of the element.
     *
     * @return Width scale.
     */
    public float getScaleX() {
        return scaleX;
    }

    /**
     * Returns the height scale of the element.
     *
     * @return Height scale.
     */
    public float getScaleY() {
        return scaleY;
    }

    /**
     * Sets offset value on the x-plane for the drawable element.
     *
     * @param offSetX New offset value.
     */
    public void setOffSetX(int offSetX) {
        this.offSetX = offSetX;
    }

    /**
     * Sets offset value on the y-plane for the drawable element.
     *
     * @param offSetY New offset value.
     */
    public void setOffSetY(int offSetY) {
        this.offSetY = offSetY;
    }

    /**
     * Returns offset value on the x-plane.
     *
     * @return Offset value on the x-plane.
     */
    public int getOffSetX() {
        return this.offSetX;
    }

    /**
     * Returns offset value on the y-plane.
     *
     * @return Offset value on the y-plane.
     */
    public int getOffSetY() {
        return this.offSetY;
    }

    /**
     * Determines if image is flipped on the x-plane.
     */
    private boolean isFlippedX = false;

    /**
     * Determines if image is flipped on the y-plane.
     */
    private boolean isFlippedY = false;

    /**
     * Flips image on the x-plane.
     *
     * <p>
     * If given parameter is negative, then direction is left and vise versa.
     *
     * @param x Direction.
     */
    public void flipX(int x) {

        if (x < 0 && !isFlippedX) {
            setScaleX(-scaleX);
            setOffSetX(getWidth());
            isFlippedX = true;
        } else if (x > 0 && isFlippedX) {
            setScaleX(-scaleX);
            setOffSetX(0);
            isFlippedX = false;
        }
    }

    /**
     * Flips image on the y-plane.
     *
     * <p>
     * If given parameter is negative, then direction is up and vise versa.
     *
     * @param y Direction.
     */
    public void flipY(int y) {

        if (y < 0 && !isFlippedY) {
            setScaleY(-scaleY);
            setOffSetY(getHeight());
            isFlippedY = true;
        } else if (y > 0 && isFlippedY) {
            setScaleY(-scaleY);
            setOffSetY(0);
            isFlippedY = false;
        }
    }

    /**
     * Defines whether image is visible for drawing.
     */
    protected boolean isVisible = true;

    /**
     * Sets image visibility for drawing.
     *
     * @param isVisible Whether is visible or not.
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
