package com.nikoengine.geometry;

/**
 * Class for making three dimensional game objects.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1215
 * @since 1.8
 */
public class GameObject3D {

    /**
     * Stores origin point of the object.
     */
    public Point3D origin;
    
    /**
     * Stores all the points of the object.
     */
    Point3D[] points;
    
    /**
     * Stores direction the object.
     */
    Vector3D direction;

    /**
     * Creates new 3D object.
     * 
     * @param origin Objects origin point.
     * @param direction Objects direction.
     * @param points Point where object is made of.
     */
    public GameObject3D(Point3D origin, Vector3D direction, Point3D[] points) {

        this.origin = origin;
        this.points = points;
        this.direction = direction;
    }

    /**
     * Defines objects rotation.
     */
    public Vector3D rotVec = new Vector3D(0, 0, 0);

    /**
     * Rotates object on the XZ plane.
     * 
     * @param direction Rotation direction.
     * @return Returns rotation vector.
     */
    public Vector3D rotateXZ(int direction) {

        for (Point3D point : points) {
            rotVec.setDirection(origin, point);
            rotVec.rotateXZ(direction);
            point.setPosition(origin.getX()
                    + rotVec.getX(), origin.getY()
                    + rotVec.getY(), origin.getZ()
                    + rotVec.getZ());
        }

        return rotVec;
    }

    /**
     * Rotates the object on the XY plane.
     * 
     * @param direction Rotation direction.
     */
    public void rotateXY(int direction) {

        for (Point3D point : points) {
            rotVec.setDirection(origin, point);
            rotVec.rotateYZ(direction);
            point.setPosition(origin.getX()
                    + rotVec.getX(), origin.getY()
                    + rotVec.getY(), origin.getZ()
                    + rotVec.getZ());
        }
    }

    /**
     * Moves the object.
     *
     * @param vec Moving speed and direction.
     */
    public void move(Vector3D vec) {
        origin.addVectorToPoint(vec, 0.3);

        for (Point3D point : points) {
            point.addVectorToPoint(vec, 0.3);
        }
    }

    /**
     * Updates objects state.
     */
    public void update() {
        if (isLeft) {
            rotateXZ(-4);
            direction.rotateXZ(-4);
        } else if (isRight) {
            rotateXZ(4);
            direction.rotateXZ(4);
        }

        if (isMoving) {
            origin.addVectorToPoint(direction, 0.3);
            
            for (Point3D p : points) {
                p.addVectorToPoint(direction, 0.3);
            }
        }
    }

    /**
     * Defines whether object is turning left.
     */
    boolean isLeft = false;
    
    /**
     * Defines whether object is turning right.
     */
    boolean isRight = false;
    
    /**
     * Defines whether object is moving.
     */
    boolean isMoving = false;

    /**
     * Sets object turning.
     *
     * <p>
     * If argument is under zero then the object will turn left. Larger than
     * zero turns right. Zero stops turning.
     *
     * @param direction New turning direction.
     */
    public void turn(int direction) {

        if (direction < 0) {
            isLeft = true;
        } else if (direction > 0) {
            isRight = true;
        } else {
            isLeft = false;
            isRight = false;
        }
    }

    /**
     * Sets object moving or stops it.
     *
     * @param isMoving Whether object will be set moving or not.
     */
    public void move(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * Returns objects direction.
     *
     * @return Direction.
     */
    public Vector3D getDirection() {
        return this.direction;
    }

    /**
     * Sets position for the object.
     *
     * @param p Point to set position.
     */
    public void setPosition(Point3D p) {
        Vector3D v = p.getVectorFromPoint(origin);

        origin.addVectorToPoint(v);

        for (Point3D point : points) {
            point.addVectorToPoint(v);
        }
    }

    /**
     * Sets position for the object.
     *
     * @param x Object position on the x-plane.
     * @param y Object position on the y-plane.
     * @param z Object position on the z-plane.
     */
    public void setPosition(double x, double y, double z) {
        Point3D p = new Point3D(x, y, z);
        Vector3D v = p.getVectorFromPoint(origin);

        origin.addVectorToPoint(v);

        for (Point3D point : points) {
            point.addVectorToPoint(v);
        }
    }

    /**
     * Sets direction of the object.
     *
     * <p>
     * Sets direction for current object and set scale for the vector. Scale is
     * used for defining movement speed.
     *
     * @param dir Direction vector.
     * @param scale Direction vectors scale.
     */
    public void setDirection(Vector3D dir, double scale) {

        this.direction = new Vector3D(dir.getX() * scale,
                dir.getY() * scale,
                dir.getZ() * scale);
    }
}
