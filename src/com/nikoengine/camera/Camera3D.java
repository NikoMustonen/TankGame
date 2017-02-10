package com.nikoengine.camera;

import com.nikoengine.application.Application;
import com.nikoengine.geometry.*;
import java.awt.Graphics2D;
import java.util.Arrays;

/**
 * Class for creating a camera to view a 3D environment.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1118
 * @since 1.8
 */
public class Camera3D {

    /**
     * Holds all the polygons in the game.
     */
    public static Polygon3D[] polygons = new Polygon3D[10000];

    /**
     * Holds the value of the polygons in the game.
     */
    public static int polygonAmount = 0;

    /**
     * Controls the origin point of the camera.
     */
    private Point3D origin;

    /**
     * Controls cameras direction.
     */
    private Vector3D direction;

    /**
     * Controls cameras side direction.
     */
    private Vector3D right;

    /**
     * Controls cameras y directions.
     */
    private Vector3D up;

    /**
     * Holds view width.
     */
    private int screenWidth;

    /**
     * Holds view height.
     */
    private int screenHeight;

    /**
     * Holds the field of view for the camera.
     */
    private double fieldOfView;

    /**
     * Controls movement speed.
     */
    private double moveFactor = 0.3;

    /**
     * Creates a 3D camera by using Applications default width and height.
     *
     * @param startPosition Cameras starting position.
     * @param direction Cameras starting direction.
     */
    public Camera3D(Point3D startPosition, Vector3D direction) {

        this(startPosition, direction,
                Application.getScreenWidth(),
                Application.getScreenHeight());
    }

    /**
     * Creates a 3D camera.
     *
     * @param startPosition Cameras starting position.
     * @param direction Cameras starting direction.
     * @param screenWidth Camera draw area width.
     * @param screenHeight Camera draw area height.
     */
    public Camera3D(Point3D startPosition, Vector3D direction,
            int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.origin = startPosition;
        this.direction = direction;
        this.right = new Vector3D(direction.getZ(), 0, -direction.getX());
        this.up = new Vector3D(-direction.getX(),
                -direction.getZ(),
                -direction.getY());
        this.fieldOfView = 4;
    }

    int rotationSpeed = 0;

    /**
     * Rotates the camera size ways.
     *
     * @param speed Rotation speed.
     */
    public void rotateXZ(int speed) {

        double r = Math.toRadians(speed);

        double tmpX = ((Math.cos(r) * direction.getX())
                + (Math.sin(r) * direction.getZ()));

        double tmpZ = (-(Math.sin(r) * direction.getX())
                + (Math.cos(r) * direction.getZ()));

        direction.setZ(tmpZ);
        direction.setX(tmpX);

        updateRightAndUp();
    }

    /**
     * Returns cameras current position.
     *
     * @return Camera position.
     */
    public Point3D getPosition() {
        return this.origin;
    }

    /**
     * Returns cameras current direction.
     *
     * @return Camera direction.
     */
    public Vector3D getDirection() {
        return this.direction;
    }

    /**
     * Returns cameras right direction for projection calculation.
     *
     * @return Normalized side direction of the camera.
     */
    public Vector3D getRight() {
        return this.right;
    }

    /**
     * Returns cameras up direction for projection calculation.
     *
     * @return Normalized up direction of the camera.
     */
    public Vector3D getUp() {
        return this.up;
    }

    /**
     * Updates cameras right and up direction from the current direction.
     */
    public void updateRightAndUp() {
        right.setX(direction.getZ());
        right.setZ(-direction.getX());
    }

    /**
     * Moves the camera to desired direction.
     *
     * @param v Desired direction and speed.
     */
    public void move(Vector3D v) {
        origin.addVectorToPoint(v);
    }

    /**
     * Holds poorly designed camera movement stuff at the moment.
     */
    private final int LEFT = 1;

    /**
     * Holds poorly designed camera movement stuff at the moment.
     */
    private final int RIGHT = 2;

    /**
     * Holds poorly designed camera movement stuff at the moment.
     */
    private final int STILL = 0;

    /**
     * Holds poorly designed camera movement stuff at the moment.
     */
    private final int FORWARD = 3;

    /**
     * Holds poorly designed camera movement stuff at the moment.
     */
    private final int BACKWARD = 4;

    /**
     * Controls camera´s forward and backwards direction.
     */
    private int move = STILL;

    /**
     * Controls camera´s turning direction.
     */
    private int turn = STILL;

    /**
     * Controls camera´s left and right direction.
     */
    private int strafe = STILL;

    /**
     * Updates cameras movement.
     */
    public void update() {
        if (move != STILL) {
            if (move == FORWARD) {
                this.origin.addVectorToPoint(direction, moveFactor);
            } else {
                this.origin.subtractVectorFromPoint(direction, moveFactor);
            }
        }

        if (turn != STILL) {
            if (turn == LEFT) {
                rotateXZ(-2);
            } else {
                rotateXZ(2);
            }
        }

        if (strafe != STILL) {
            if (strafe == LEFT) {
                this.origin.subtractVectorFromPoint(right, moveFactor);
            } else {
                this.origin.addVectorToPoint(right, moveFactor);
            }
        }

        for (int i = 0; i < polygonAmount; i++) {
            polygons[i].updateDrawingPosition(this);
        }

        Arrays.sort(polygons, 0, polygonAmount);
    }

    /**
     * Sets forward movement.
     */
    public void forward() {
        move = FORWARD;
    }

    /**
     * Sets backwards movement.
     */
    public void backWards() {
        move = BACKWARD;
    }

    /**
     * Stops backwards and forward movement.
     */
    public void stopMove() {
        move = STILL;
    }

    /**
     * Sets side ways left movement.
     */
    public void strafeLeft() {
        strafe = LEFT;
    }

    /**
     * Sets side ways right movement.
     */
    public void strafeRight() {
        strafe = RIGHT;
    }

    /**
     * Stops side ways left and right movement.
     */
    public void stopStrafe() {
        strafe = STILL;
    }

    /**
     * Sets turning left movement.
     */
    public void turnLeft() {
        turn = LEFT;
    }

    /**
     * Sets turning right movement.
     */
    public void turnRight() {
        turn = RIGHT;
    }

    /**
     * Stops turning.
     */
    public void stopTurn() {
        turn = STILL;
    }

    /**
     * Returns screen width value from the camera.
     *
     * @return Drawing width of the screen.
     */
    public int getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * Returns screen height value from the camera.
     *
     * @return Drawing height of the screen.
     */
    public int getScreenHeight() {
        return this.screenHeight;
    }

    /**
     * Sets the field of view value for the camera.
     *
     * @param fov Field of View value.
     */
    public void setFOV(double fov) {
        this.fieldOfView = fov;
    }

    /**
     * Returns the field of view value of the camera.
     *
     * @return Field of view value of the camera.
     */
    public double getFOV() {
        return this.fieldOfView;
    }

    /**
     * Determines whether to draw wire frame.
     */
    private boolean isWireFrame = true;

    /**
     * Renders cameras view.
     *
     * @param g Graphics2D drawing element.
     */
    public void renderCameraView(Graphics2D g) {

        for (int i = 0; i < Camera3D.polygonAmount; i++) {
            Camera3D.polygons[i].drawFilledPolygon(g);

            if (isWireFrame) {
                Camera3D.polygons[i].drawPolygon(g);
            }
        }
    }

    /**
     * Toggles wire frame view by given boolean value.
     *
     * @param isWireFrame Whether wire frame is on or off.
     */
    public void setWireFrame(boolean isWireFrame) {
        this.isWireFrame = isWireFrame;
    }

    /**
     * Toggles wire frame view on and off.
     */
    public void toggleWireFrame() {
        if (isWireFrame) {
            isWireFrame = false;
        } else {
            isWireFrame = true;
        }
    }

    /**
     * Sets cameras´s direction.
     *
     * @param p Direction vector.
     */
    public void setDirection(Point3D p) {
        direction.setDirection(p, origin);
        double mag = direction.getMagnitude();
        direction.setY(0);
        direction.normalize();
        updateRightAndUp();
    }

    /**
     * Sets movement speed.
     *
     * @param factor Move factor.
     */
    public void setMoveFactor(double factor) {

        this.moveFactor = factor;
    }
}
