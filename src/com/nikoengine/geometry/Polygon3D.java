package com.nikoengine.geometry;

import com.nikoengine.application.Application;
import java.awt.*;
import com.nikoengine.camera.*;
import java.awt.Color;

/**
 * Class for creating and handling three dimensional polygons.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1124
 * @since 1.8
 */
public class Polygon3D implements Comparable < Polygon3D > {

    /**
     * Holds polygons corner points.
     */
    private final Point3D[] points;

    /**
     * Holds polygons origin point.
     */
    private Point3D origin;

    /**
     * Holds points for drawing.
     */
    private final Point2D[] drawingPoints;

    /**
     * Determines polygons color.
     */
    private Color c;

    /**
     * Holds the distance from the view point.
     */
    private int distance = 0;

    /**
     * Creates a new 3d polygon and sets all the corner points to it.
     *
     * @param points Corner points to polygon.
     */
    public Polygon3D(Point3D... points) {
        this.points = points;
        this.origin = new Point3D(0, 0, 0);

        drawingPoints = new Point2D[10];

        for (int i = 0; i < 10; i++) {
            drawingPoints[i] = new Point2D(0, 0);
        }

        setThis();
    }

    /**
     * Adds the polygon to some place to be handled. Still under construction.
     */
    private void setThis() {
        Camera3D.polygons[Camera3D.polygonAmount] = this;
        Camera3D.polygonAmount++;
    }

    /**
     * Sets color for the polygon.
     *
     * @param color New Color value.
     */
    public void setColor(Color color) {
        this.c = color;
    }

    /**
     * Holds the amount of the drawing points.
     */
    int length;

    /**
     * Holds drawing correction on the x-plane value.
     */
    private static final int X_CORR = Application.getScreenWidth() / 2;

    /**
     * Holds drawing correction on the y-plane value.
     */
    private static final int Y_CORR = Application.getScreenHeight() / 2;

    /**
     * Draws polygons out lines.
     *
     * @param g Graphic element for drawing.
     */
    public void drawPolygon(Graphics g) {

        if (length > 0) {
            for (int i = 0; i < length - 1; i++) {
                g.drawLine(
                        (int) drawingPoints[i].getX() + X_CORR,
                        (int) drawingPoints[i].getY() + Y_CORR,
                        (int) drawingPoints[i + 1].getX() + X_CORR,
                        (int) drawingPoints[i + 1].getY() + Y_CORR);
            }

            g.drawLine(
                    (int) drawingPoints[length - 1].getX() + X_CORR,
                    (int) drawingPoints[length - 1].getY() + Y_CORR,
                    (int) drawingPoints[0].getX() + X_CORR,
                    (int) drawingPoints[0].getY() + Y_CORR);
        }
    }

    /**
     * Draws filled polygon.
     *
     * @param g Graphic element for drawing.
     */
    public void drawFilledPolygon(Graphics g) {
        if (this.c != null) {
            g.setColor(this.c);
        }

        g.fillPolygon(getDrawingxValues(), getDrawingyValues(), length);
        g.setColor(Color.PINK);
    }

    /**
     * Determines the direction and distance from camera to point.
     */
    Vector3D cameraToPoint;

    /**
     * Determines the clipping value for the polygons. UNDER CONSTRUCTION.
     */
    private final int CLIPPING = Application.getScreenWidth();

    /**
     * Converts 3D point to 2D point so it can be drawn to correct position.
     *
     * @param cam Used camera for calculating positions.
     */
    public void updateDrawingPosition(Camera3D cam) {

        int index = 0;
        double distance_min = 1000;
        double distance_max = 0;

        for (Point3D point : points) {

            cameraToPoint = cam.getPosition().getVectorFromPoint(point);
            distance_min = Math.min(distance_min,
                    cameraToPoint.getDotProduct());

            distance_max = Math.max(distance_max,
                    cameraToPoint.getDotProduct());

            double w1 = cam.getDirection().getProjection(cameraToPoint);
            double w2 = cam.getRight().getProjection(cameraToPoint);
            double w3 = cam.getUp().getProjection(cameraToPoint);

            double x = (cam.getFOV() / w1 * w2)
                    * (cam.getScreenWidth() / 2 / 3);

            double y = (cam.getFOV() / w1 * w3)
                    * (cam.getScreenHeight() / 2 / 1.5);

            if (w1 > 1 && x < CLIPPING && x > -CLIPPING
                    && y < CLIPPING && y > -CLIPPING) {

                drawingPoints[index].setPosition(x, y);
                index++;
            } else {

            }
        }

        distance = (int) ((distance_max + distance_min) / 2.0 * 2);
        length = index;
    }

    /**
     * Sets the origin point for the polygon.
     *
     * <p>
     * Polygons rotation works around this point.
     *
     * @param x Desired position on the x-plane.
     * @param y Desired position on the y-plane.
     * @param z Desired position on the z-plane.
     */
    public void setOrigin(double x, double y, double z) {
        this.origin.setPosition(x, y, z);
    }

    /**
     * Sets the origin point for the polygon.
     *
     * <p>
     * Polygons rotation works around this point.
     *
     * @param newOrigin New origin point.
     */
    public void setOrigin(Point3D newOrigin) {
        this.origin = newOrigin;
    }

    /**
     * Returns all the x values from polygons points in an array.
     *
     * @return X values.
     */
    private int[] getDrawingxValues() {
        int[] list = new int[length];

        for (int i = 0; i < length; i++) {
            list[i] = (int) drawingPoints[i].getX() + X_CORR;
        }

        return list;
    }

    /**
     * Returns all the y values from polygons points in an array.
     *
     * @return Y values.
     */
    private int[] getDrawingyValues() {
        int[] list = new int[length];

        for (int i = 0; i < length; i++) {
            list[i] = (int) drawingPoints[i].getY() + Y_CORR;
        }

        return list;
    }

    /**
     * Moves the polygon to desired direction.
     *
     * @param vector Direction and speed of the movement.
     */
    public void move(Vector3D vector) {
        for (Point3D p : points) {
            p.addVectorToPoint(vector);
        }
    }

    /**
     * Compares polygons distance so they can be drawn in correct order.
     *
     * @param poly Polygon to be compared with.
     * @return Which polygon is closer.
     */
    @Override
    public int compareTo(Polygon3D poly) {
        return poly.distance - distance;
    }
}
