package com.nikoengine.geometry;

/**
 * Class for creating and handling three dimensional points.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1125
 * @since 1.8
 */
public class Point3D {

    /**
     * Holds points x, y and z position values.
     */
    private double[] tuple;

    /**
     * Creates a new 3D point to desired position.
     *
     * @param x Position on the x-plane.
     * @param y Position on the y-plane.
     * @param z Position on the z-plane.
     */
    public Point3D(double x, double y, double z) {
        tuple = new double[3];
        setPosition(x, y, z);
    }

    /**
     * Sets new position to point.
     *
     * @param x Position on the x-plane.
     * @param y Position on the y-plane.
     * @param z Position on the z-plane.
     */
    public void setPosition(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * Sets new position to point.
     *
     * @param p New position.
     */
    public final void setPosition(Point3D p) {
        setX(p.getX());
        setY(p.getY());
        setZ(p.getZ());
    }

    /**
     * Sets new x position value.
     *
     * @param x Position on the x-plane.
     */
    public void setX(double x) {
        tuple[0] = x;
    }

    /**
     * Sets new y position value.
     *
     * @param y Position on the y-plane.
     */
    public void setY(double y) {
        tuple[1] = y;
    }

    /**
     * Sets new z position value.
     *
     * @param z Position on the z-plane.
     */
    public void setZ(double z) {
        tuple[2] = z;
    }

    /**
     * Returns points x position.
     *
     * @return Position on the x-plane.
     */
    public double getX() {
        return tuple[0];
    }

    /**
     * Returns points y position.
     *
     * @return Position on the y-plane.
     */
    public double getY() {
        return tuple[1];
    }

    /**
     * Returns points z position.
     *
     * @return Position on the z-plane.
     */
    public double getZ() {
        return tuple[2];
    }

    /**
     * Moves point to desired position determined by 3D vector.
     *
     * @param vector Direction and speed.
     */
    public void addVectorToPoint(Vector3D vector) {
        setPosition(getX() + vector.getX(),
                getY() + vector.getY(),
                getZ() + vector.getZ());
    }

    /**
     * Moves point to desired position determined by scaled 3D vector.
     *
     * @param vector Direction and speed.
     * @param scale Scale factor.
     */
    public void addVectorToPoint(Vector3D vector, double scale) {
        setPosition(getX() + vector.getX() * scale,
                getY() + vector.getY() * scale,
                getZ() + vector.getZ() * scale);
    }
    
    /**
     * Adds vector values to point.
     * 
     * @param x Vector value on x plane.
     * @param y Vector value on y plane.
     * @param z Vector value on z plane.
     */
    public void addVectorToPoint(double x, double y, double z) {
        setPosition(getX() + x,
                getY() + y,
                getZ() + x);
    }

    /**
     * Moves point to desired position determined by 3D vector.
     *
     * @param vector Direction and speed.
     */
    public void subtractVectorFromPoint(Vector3D vector) {
        setPosition(getX() - vector.getX(),
                getY() - vector.getY(),
                getZ() - vector.getZ());
    }

    /**
     * Moves point to desired position determined by scaled 3D vector.
     *
     * @param vector Direction and speed.
     * @param scale Scale factor.
     */
    public void subtractVectorFromPoint(Vector3D vector, double scale) {
        setPosition(getX() - vector.getX() * scale,
                getY() - vector.getY() * scale,
                getZ() - vector.getZ() * scale);
    }

    /**
     * Returns a new 3D vector from two points.
     *
     * @param p Point to be compared with this instance.
     * @return Vector between two points.
     */
    public Vector3D getVectorFromPoint(Point3D p) {
        double x = getX() - p.getX();
        double y = getY() - p.getY();
        double z = getZ() - p.getZ();

        return new Vector3D(x, y, z);
    }

    /**
     * Prints all the needed data from the point.
     *
     * @return Position data from the point.
     */
    @Override
    public String toString() {
        return "X: " + getX() + " Y: " + getY() + " Z: " + getZ();
    }
}
