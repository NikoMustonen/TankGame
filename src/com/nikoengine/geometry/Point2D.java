package com.nikoengine.geometry;

/**
 * Class for creating and handling two dimensional points.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1124
 * @since 1.8
 */
public class Point2D {

    /**
     * Holds points x and y position.
     */
    private double[] tuple;

    /**
     * Creates a 2D point in default (0, 0) position.
     */
    public Point2D() {
        this(0, 0);
    }

    /**
     * Creates a 2D point in desired position.
     *
     * @param x Position on x-axis.
     * @param y Position on y-axis.
     */
    public Point2D(double x, double y) {
        tuple = new double[2];
        setPosition(x, y);
    }

    /**
     * Sets points position.
     *
     * @param x Position on x-axis.
     * @param y Position on y-axis.
     */
    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Sets points position.
     *
     * @param p New position.
     */
    public void setPosition(Point2D p) {
        setX(p.getX());
        setY(p.getY());
    }

    /**
     * Sets x position.
     *
     * @param x Position on x-axis.
     */
    public void setX(double x) {
        tuple[0] = x;
    }

    /**
     * Sets y position.
     *
     * @param y Position on y-axis.
     */
    public void setY(double y) {
        tuple[1] = y;
    }

    /**
     * Returns the current x position.
     *
     * @return Position on the x-axis.
     */
    public double getX() {
        return tuple[0];
    }

    /**
     * Returns the current y position.
     *
     * @return Position on the y-axis.
     */
    public double getY() {
        return tuple[1];
    }

    /**
     * Moves point to desired direction on the x-plane.
     *
     * <p>
     * Smaller than zero is left and greater than zero is right.
     *
     * @param speed Direction and speed.
     */
    public void moveX(double speed) {
        setX(getX() + speed);
    }

    /**
     * Moves point to desired direction on the y-plane.
     *
     * <p>
     * Smaller than zero is up and greater than zero is down.
     *
     * @param speed Direction and speed.
     */
    public void moveY(double speed) {
        setY(getY() + speed);
    }

    /**
     * Moves this point to desired direction determined by a vector parameter.
     *
     * @param vector Desired direction and speed.
     */
    public void addVectorToPoint(Vector2D vector) {
        setPosition(getX() + vector.getX(), getY() + vector.getY());
    }

    /**
     * Moves this point to desired direction determined by a vector parameter.
     *
     * @param vector Desired direction and speed.
     */
    public void subtractVectorFromPoint(Vector2D vector) {
        setPosition(getX() - vector.getX(), getY() - vector.getY());
    }

    /**
     * Returns a new 2D vector from two points.
     *
     * @param p Point to be compared with this instance.
     * @return Vector between two points.
     */
    public Vector2D getVectorFromPoint(Point2D p) {
        return new Vector2D(p.getX() - getX(), p.getY() - getY());
    }

    /**
     * Prints all the needed data from the point.
     *
     * @return Position data from the point.
     */
    @Override
    public String toString() {
        return "X: " + getX() + " Y: " + getY();
    }
}
