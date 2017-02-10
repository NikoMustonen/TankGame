package com.nikoengine.geometry;

/**
 * Class for creating and handling two dimensional vectors.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1125
 * @since 1.8
 */
public class Vector2D {

    /**
     * Holds the direction and the magnitude of the vector.
     */
    private double[] tuple;

    /**
     * Creates 2D vector with desired direction.
     *
     * @param x Direction and magnitude on the x-plane.
     * @param y Direction and magnitude on the y-plane.
     */
    public Vector2D(double x, double y) {
        tuple = new double[2];
        setDirection(x, y);
    }

    /**
     * Sets a new direction for the vector.
     *
     * @param x Direction and magnitude on the x-plane.
     * @param y Direction and magnitude on the y-plane.
     */
    public final void setDirection(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Sets a new direction for the vector.
     *
     * @param x Direction and magnitude on the x-plane.
     */
    public void setX(double x) {
        tuple[0] = x;
    }

    /**
     * Sets a new direction for the vector.
     *
     * @param y Direction and magnitude on the y-plane.
     */
    public void setY(double y) {
        tuple[1] = y;
    }

    /**
     * Returns the x direction and magnitude of the vector.
     *
     * @return Direction and magnitude on the x-plane.
     */
    public double getX() {
        return tuple[0];
    }

    /**
     * Returns the y direction and magnitude of the vector.
     *
     * @return Direction and magnitude on the y-plane.
     */
    public double getY() {
        return tuple[1];
    }

    /**
     * Adds a vector to this vector.
     *
     * @param vector Vector to be added.
     */
    public void addVectorToVector(Vector3D vector) {
        setDirection(getX() + vector.getX(), getY() + vector.getY());
    }

    /**
     * Subtracts a vector from this vector.
     *
     * @param vector Vector to be added.
     */
    public void subtractVectorFromVector(Vector2D vector) {
        setDirection(getX() - vector.getX(), getY() - vector.getY());
    }

    /**
     * Sets the vector new position from one point to another.
     *
     * @param p1 Start point.
     * @param p2 End point.
     */
    public void setVectorFromPointToPoint(Point2D p1, Point2D p2) {
        setX(p2.getX() - p1.getX());
        setY(p2.getY() - p1.getY());
    }

    /**
     * Calculates a dot product of the vector by itself.
     *
     * @return Dot product.
     */
    public double getDotProduct() {
        return getX() * getX() + getY() * getY();
    }

    /**
     * Calculates a dot product by given values.
     *
     * @param x1 First point position on the x-plane.
     * @param y1 First point position on the y-plane.
     * @param x2 Second point position on the x-plane.
     * @param y2 Second point position on the y-plane.
     * @return Dot product.
     */
    public double getDotProduct(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }

    /**
     * Calculates vectors magnitude.
     *
     * @return Magnitude of the vector.
     */
    public double getMagnitude() {
        return Math.sqrt(getDotProduct());
    }

    /**
     * Normalizes the vector.
     */
    public void normalize() {
        double magnitude = getMagnitude();
        setX(getX() / magnitude);
        setY(getY() / magnitude);
    }

    /**
     * Creates a new 2D vector which is normalized version of current vector.
     *
     * @return Normalized vector.
     */
    public Vector2D getNormalizedVector() {
        double magnitude = getMagnitude();
        double x = getX() / magnitude;
        double y = getX() / magnitude;
        return new Vector2D(x, y);
    }

    /**
     * Calculates the cross product from itself.
     *
     * @return Cross product.
     */
    public Vector2D getCrossProduct() {
        double x = getY() + getY();
        double y = getX() + getX();
        return new Vector2D(x, y);
    }

    /**
     * Sets the direction inverted.
     */
    public void invertVector() {
        setDirection(-getX(), -getY());
    }

    /**
     * Creates a new vector which is inverted version of the vector.
     *
     * @return Inverted vector.
     */
    public Vector2D getInvertedVector() {
        return new Vector2D(-getX(), -getY());
    }

    /**
     * Calculates th acos value of the vector.
     *
     * @return acos.
     */
    public double getACOS() {
        Vector2D normV = getNormalizedVector();
        return getDotProduct(normV.getX(), normV.getY(), 1, 0);
    }

    /**
     * Rotates the vector on the XY-plane.
     *
     * <p>
     * Negative speed rotates counterclockwise and vice versa.
     *
     * @param speed Rotation speed.
     */
    public void rotate(int speed) {
        double r = Math.toRadians(speed);

        double tmpX = ((Math.cos(r) * tuple[0]) - (Math.sin(r) * tuple[1]));
        double tmpY = ((Math.sin(r) * tuple[0]) + (Math.cos(r) * tuple[1]));

        tuple[1] = tmpY;
        tuple[0] = tmpX;
    }

    /**
     * Prints all the data from the vector for debugging.
     *
     * @return Data from the vector.
     */
    @Override
    public String toString() {
        return "x: " + getX() + " y: " + getY();
    }
}
