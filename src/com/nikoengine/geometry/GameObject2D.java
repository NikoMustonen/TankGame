package com.nikoengine.geometry;

import com.nikoengine.camera.Camera2D;
import java.awt.*;

import com.nikoengine.image.Drawable;

/**
 * Class for making two dimensional game objects.
 *
 * <p>
 * With this class developer is able to create a game object which can hold
 * several images and animations. Game object can also be scaled in size,
 * rotated and moved. Game objects can check collision between each other by
 * using features inherited from the Polygon2D class.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1127
 * @since 1.8
 */
public class GameObject2D extends Polygon2D {

    /**
     * Holds current drawable element to be drawn.
     */
    private Drawable currentDrawable;

    /**
     * Holds all the drawable elements for this object.
     */
    private Drawable[] drawables;

    /**
     * Holds the amount of the drawable elements.
     */
    private int amountOfDrawables = 0;

    /**
     * Controls the rotation of the element.
     */
    private int rotation = 0;

    /**
     * Creates a new game object.
     *
     * @param x Object position on x-axis.
     * @param y Object position on y-axis.
     * @param width Width of the object.
     * @param height Height of the object.
     */
    public GameObject2D(double x, double y, double width, double height) {
        super(createPoints(x, y, width, height));
        setOrigin(x, y);
    }

    /**
     * Adds a new drawable element for the game object.
     *
     * @param drawable New drawable element.
     */
    public void addDrawable(Drawable drawable) {

        if (drawables == null) {
            drawables = new Drawable[5];
        }

        drawables[amountOfDrawables] = drawable;
        amountOfDrawables++;

        if (currentDrawable == null) {
            setCurrentDrawable(0);
        }
    }

    /**
     * Sets current drawable element to be drawn for the object at the moment.
     *
     * @param i New drawable index number.
     */
    public void setCurrentDrawable(int i) {

        if (i >= 0 && i < amountOfDrawables) {

            if (currentDrawable != null) {

                drawables[i].setOffSetX(currentDrawable.getOffSetX());
                drawables[i].setOffSetY(currentDrawable.getOffSetY());
            }

            currentDrawable = drawables[i];
            currentDrawable.setPosition((int) points[0].getX(),
                    (int) points[0].getY());
        }
    }

    /**
     * Draws the chosen current drawable element.
     *
     * @param g Drawing element.
     */
    public void draw(Graphics2D g) {
        currentDrawable.draw(g);
    }

    /**
     * Draws the chosen current drawable element.
     *
     * @param cam Camera to view element.
     * @param g Drawing element.
     */
    public void render(Camera2D cam, Graphics2D g) {

        currentDrawable.renderThroughCamera(cam, g);
    }

    /**
     * Updates the game objects position and current image or animation.
     *
     * @param delta Delta value for animation timing.
     */
    public void update(float delta) {
        currentDrawable.setRotation(rotation);
        currentDrawable.update(delta);
        currentDrawable.setPosition((int) (getOrigin().getX()
                - currentDrawable.getWidth() * currentDrawable.getScaleX() / 2),
                (int) (getOrigin().getY() - currentDrawable.getHeight()
                * currentDrawable.getScaleY() / 2));
    }

    /**
     * Creates a 2D points for the game object for calculating collision.
     *
     * @param x Polygons x origin.
     * @param y Polygons y origin.
     * @param w Polygons width.
     * @param h Polygons height.
     * @return List of polygons points.
     */
    private static Point2D[] createPoints(double x, double y,
            double w, double h) {

        Point2D[] points = new Point2D[4];

        double pointX = w / 2.0;
        double pointY = h / 2.0;

        points[0] = new Point2D(x - pointX, y - pointY);
        points[1] = new Point2D(x + pointX, y - pointY);
        points[2] = new Point2D(x + pointX, y + pointY);
        points[3] = new Point2D(x - pointX, y + pointY);

        return points;
    }

    /**
     * Sets the offset x value for the current drawable.
     *
     * @param offSetX Offset value on x-axis.
     */
    public void setImagesOffSetX(int offSetX) {
        currentDrawable.setOffSetX(offSetX);
    }

    /**
     * Sets the offset y value for the current drawable.
     *
     * @param offSetY Offset value on y-axis.
     */
    public void setImagesOffSetY(int offSetY) {
        currentDrawable.setOffSetY(offSetY);
    }

    @Override
    public void rotate(int speed) {
        rotation += speed;
        super.rotate(speed);
    }

    /**
     * Returns the rotation of the object.
     *
     * @return Rotation value of the object
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Sets rotation for the object.
     *
     * @param rotation Desired rotation.
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns upper left point of the object.
     *
     * @return Upper left point of the object.
     */
    public Point2D getMinPoint() {
        return points[0];
    }
}
