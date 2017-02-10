package com.nikoengine.geometry;

/**
 * Class for creating and handling three dimensional vectors.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1125
 * @since 1.8
 */
public class Vector3D {

    /**
     * Holds the direction and the magnitude of the vector.
     */
    private double[] tuple;

    /**
     * Creates 3D vector with desired direction.
     *
     * @param x Direction and magnitude on the x-plane.
     * @param y Direction and magnitude on the y-plane.
     * @param z Direction and magnitude on the z-plane.
     */
    public Vector3D(double x, double y, double z) {
        tuple = new double[3];
        setDirection(x, y, z);
    }

    /**
     * Sets a new direction for the vector.
     *
     * @param x Direction and magnitude on the x-plane.
     * @param y Direction and magnitude on the y-plane.
     * @param z Direction and magnitude on the z-plane.
     */
    public final void setDirection(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * Sets a new direction from point to point.
     *
     * @param p1 Start point.
     * @param p2 End point.
     */
    public void setDirection(Point3D p1, Point3D p2) {
        setX(p2.getX() - p1.getX());
        setY(p2.getY() - p1.getY());
        setZ(p2.getZ() - p1.getZ());
    }

    /**
     * Sets a new direction for the vector.
     *
     * @param v New Direction.
     */
    public void setDirection(Vector3D v) {
        setX(v.getX());
        setY(v.getY());
        setZ(v.getZ());
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
     * Sets a new direction for the vector.
     *
     * @param z Direction and magnitude on the z-plane.
     */
    public void setZ(double z) {
        tuple[2] = z;
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
     * Returns the z direction and magnitude of the vector.
     *
     * @return Direction and magnitude on the z-plane.
     */
    public double getZ() {
        return tuple[2];
    }

    /**
     * Adds a vector to this vector.
     *
     * @param vector Vector to be added.
     */
    public void addVectorToVector(Vector3D vector) {
        setDirection(getX() + vector.getX(), getY()
                + vector.getY(), getZ() + vector.getZ());
    }

    /**
     * Subtracts a vector from this vector.
     *
     * @param vector Vector to be added.
     */
    public void subtractVectorFromVector(Vector3D vector) {
        setDirection(getX() - vector.getX(), getY()
                - vector.getY(), getZ() - vector.getZ());
    }

    /**
     * Calculates a dot product of the vector by itself.
     *
     * @return Dot product.
     */
    public double getDotProduct() {
        return getX() * getX() + getY() * getY() + getZ() * getZ();
    }

    /**
     * Calculates the dot product compared to another vector.
     *
     * @param v Compared vector.
     * @return Dot product.
     */
    public double getDotProduct(Vector3D v) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

    /**
     * Calculates the dot product compared to given point.
     *
     * @param p Point to be calculated with.
     * @return Dot product.
     */
    public double getDotProduct(Point3D p) {
        return getX() * p.getX() + getY() * p.getY() + getZ() * p.getZ();
    }

    /**
     * Calculates the dot product compared to another vector.
     *
     * @param x Direction and magnitude on the x-plane.
     * @param y Direction and magnitude on the y-plane.
     * @param z Direction and magnitude on the z-plane.
     * @return Dot product.
     */
    public double getDotProduct(double x, double y, double z) {
        return getX() * x + getY() * y + getZ() * z;
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
        setZ(getZ() / magnitude);
    }

    /**
     * Creates a new 3D vector which is normalized version of current vector.
     *
     * @return Normalized vector.
     */
    public Vector3D getNormalizedVector() {
        double magnitude = getMagnitude();
        double x = getX() / magnitude;
        double y = getX() / magnitude;
        double z = getX() / magnitude;
        return new Vector3D(x, y, z);
    }

    /**
     * Calculates the cross product compared to another vector.
     *
     * @param vector Vector to be compared with.
     * @return Cross product.
     */
    public Vector3D getCrossProduct(Vector3D vector) {
        double x = getY() * vector.getZ() - getZ() * vector.getY();
        double y = getZ() * vector.getX() - getX() * vector.getZ();
        double z = getX() * vector.getY() - getY() * vector.getX();
        return new Vector3D(x, y, z);
    }

    /**
     * Sets the direction inverted.
     */
    public void invertVector() {
        setDirection(-getX(), -getY(), -getZ());
    }

    /**
     * Creates a new vector which is inverted version of the vector.
     *
     * @return Inverted vector.
     */
    public Vector3D getInvertedVector() {
        return new Vector3D(-getX(), -getY(), -getZ());
    }

    /**
     * Calculates a projection from vector to vector.
     *
     * @param v Compared vector.
     * @return Projection between two vectors.
     */
    public double getProjection(Vector3D v) {
        double d = getDotProduct(v) / getMagnitude();
        return d;
    }

    /**
     * Sets scale for the vector.
     *
     * @param scale Scala value.
     */
    public void setScale(double scale) {
        setX(getX() * scale);
        setY(getY() * scale);
        setZ(getZ() * scale);
    }

    /**
     * Sets new direction and scale for the vector.
     *
     * @param v New direction and magnitude.
     * @param scale Scale factor.
     */
    public void setDirectionAndScale(Vector3D v, int scale) {
        setDirection(v);
        setScale(scale);
    }

    /**
     * Prints all the data from the vector for debugging.
     *
     * @return Data from the vector.
     */
    @Override
    public String toString() {
        return "X: " + getX() + " Y: " + getY() + " Z: " + getZ();
    }

    /**
     * Rotates vector on the XY plane.
     *
     * @param rotationSpeed Speed of the rotation.
     */
    public void rotateXY(double rotationSpeed) {
        double r = Math.toRadians(rotationSpeed);

        double tmpX = ((Math.cos(r) * tuple[0]) - (Math.sin(r) * tuple[1]));
        double tmpY = ((Math.sin(r) * tuple[0]) + (Math.cos(r) * tuple[1]));

        tuple[1] = tmpY;
        tuple[0] = tmpX;
    }

    /**
     * Rotates vector on the XZ plane.
     *
     * @param rotationSpeed Speed of the rotation.
     */
    public void rotateXZ(double rotationSpeed) {
        double r = Math.toRadians(rotationSpeed);

        double tmpX = ((Math.cos(r) * tuple[0]) + (Math.sin(r) * tuple[2]));
        double tmpZ = (-(Math.sin(r) * tuple[0]) + (Math.cos(r) * tuple[2]));

        tuple[2] = tmpZ;
        tuple[0] = tmpX;
    }

    /**
     * Rotates vector on the YZ plane.
     *
     * @param rotationSpeed Speed of the rotation.
     */
    public void rotateYZ(double rotationSpeed) {
        double r = Math.toRadians(rotationSpeed);

        double tmpY = ((Math.cos(r) * tuple[1]) - (Math.sin(r) * tuple[2]));
        double tmpZ = ((Math.sin(r) * tuple[1]) + (Math.cos(r) * tuple[2]));

        tuple[2] = tmpZ;
        tuple[1] = tmpY;
    }
}
