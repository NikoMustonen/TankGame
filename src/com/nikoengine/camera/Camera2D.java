package com.nikoengine.camera;

import com.nikoengine.application.Application;

/**
 * Helper class for resource loading.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1201
 * @since 1.8
 */
public class Camera2D {

    /**
     * Cameras middle x position.
     */
    public int x = 0;

    /**
     * Cameras middle y position.
     */
    public int y = 0;

    /**
     * Cameras upper left x position.
     */
    public int topX;

    /**
     * Cameras upper left y position.
     */
    public int topY;

    /**
     * Cameras lower right x position.
     */
    public int bottomX = Application.getScreenWidth();

    /**
     * Cameras lower right y position.
     */
    public int bottomY = Application.getScreenHeight();

    /**
     * Returns upper left point´s x value.
     *
     * @return Position on the X plane.
     */
    public int getTopX() {
        return topX;
    }

    /**
     * Returns upper left point´s y value.
     *
     * @return Position on the Y plane.
     */
    public int getTopY() {
        return topY;
    }

    /**
     * Returns lower right point´s x value.
     *
     * @return Position on the X plane.
     */
    public int getBottomX() {
        return bottomX;
    }

    /**
     * Returns lower right point´s y value.
     *
     * @return Position on the Y plane.
     */
    public int getBottomY() {
        return bottomY;
    }

    /**
     * Sets cameras position.
     *
     * @param x Position on the x plane.
     * @param y Position on the y plane.
     */
    public void setCameraPosition(int x, int y) {
        int newX = x - getX();
        int newY = y - getY();
        moveCameraX(newX);
        moveCameraY(newY);
    }

    /**
     * Moves camera on the x plane.
     *
     * @param speedX Horizontal move speed.
     */
    public void moveCameraX(int speedX) {
        this.x += speedX;
        this.topX += speedX;
        this.bottomX += speedX;
    }

    /**
     * Returns the x position of the camera.
     *
     * @return Position value on the x plane.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y position of the camera.
     *
     * @return Position value on the y plane.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Moves camera on the y plane.
     *
     * @param speedY Vertical move speed.
     */
    public void moveCameraY(int speedY) {
        this.y += speedY;
        this.topY += speedY;
        this.bottomY += speedY;
    }
}
