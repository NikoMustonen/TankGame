package com.nikoengine.geometry;

import java.awt.*;

/**
 * Class for creating and handling two dimensional polygons.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1123
 * @since 1.8
 */
public class Polygon2D {

    /**
     * Holds polygons corner points.
     */
    protected Point2D[] points;

    /**
     * Holds polygons origin point.
     */
    private Point2D origin;

    /**
     * Creates a new 2d polygon and sets all the corner points to it.
     *
     * @param points Corner points to polygon.
     */
    public Polygon2D(Point2D... points) {
        this.points = points;
        this.origin = new Point2D(0, 0);
    }

    /**
     * Draws polygons out lines.
     *
     * @param g Graphic element for drawing.
     */
    public void drawPolygon(Graphics2D g) {

        for (int i = 0; i < points.length - 1; i++) {

            g.drawLine((int) points[i].getX(), (int) points[i].getY(),
                    (int) points[i + 1].getX(), (int) points[i + 1].getY());
        }

        g.drawLine((int) points[points.length - 1].getX(),
                (int) points[points.length - 1].getY(),
                (int) points[0].getX(),
                (int) points[0].getY());

        g.fillOval((int) origin.getX(),
                (int) origin.getY(), 10, 10);
    }

    /**
     * Moves the polygon to desired direction.
     *
     * @param directionAndSpeed Direction and speed of the movement.
     */
    public void move(Vector2D directionAndSpeed) {
        origin.addVectorToPoint(directionAndSpeed);

        for (Point2D p : points) {

            p.addVectorToPoint(directionAndSpeed);
        }
    }

    /**
     * Moves the polygon to desired direction.
     *
     * @param speedX Direction and speed on the x-plane.
     * @param speedY Direction and speed on the y-plane.
     */
    public void move(double speedX, double speedY) {
        origin.moveX(speedX);
        origin.moveY(speedY);

        for (Point2D p : points) {

            p.moveX(speedX);
            p.moveY(speedY);
        }
    }

    /**
     * Sets the origin point for the polygon.
     *
     * <p>
     * Polygons rotation works around this point.
     *
     * @param point New origin point.
     */
    public void setOrigin(Point2D point) {
        this.origin = point;
    }

    /**
     * Sets the origin point for the polygon.
     *
     * @param x Desired position on the x-plane.
     * @param y Desired position on the y-plane.
     */
    public void setOrigin(double x, double y) {

        if (this.origin != null) {
            this.origin.setPosition(x, y);
        } else {
            setOrigin(new Point2D());
        }
    }

    /**
     * Returns the origin point of the polygon.
     *
     * @return Origin point of the polygon.
     */
    public Point2D getOrigin() {
        return this.origin;
    }

    /**
     * Holds the static rotation vector for all the polygons in the game.
     */
    private static final Vector2D ROT_VEC = new Vector2D(1, 0);

    /**
     * Rotates the polygon by given speed.
     *
     * @param speed Speed value in degrees.
     */
    public void rotate(int speed) {

        for (Point2D point : points) {

            ROT_VEC.setVectorFromPointToPoint(origin, point);
            ROT_VEC.rotate(speed);

            point.setPosition(origin.getX() + ROT_VEC.getX(),
                    origin.getY() + ROT_VEC.getY());
        }
    }

    /**
     * Returns all the corner points from the polygon.
     *
     * @return All the corner points.
     */
    public Point2D[] getPoints() {
        return points;
    }

    /**
     * Checks collision between two polygons.
     *
     * @param polygon polygon to be compared with.
     * @return If collision happened.
     */
    public boolean intersects(Polygon2D polygon) {
        double minX1 = points[0].getX();
        double minY1 = points[0].getY();
        double maxX1 = points[0].getX();
        double maxY1 = points[0].getY();

        double minX2 = polygon.getPoints()[0].getX();
        double minY2 = polygon.getPoints()[0].getY();
        double maxX2 = polygon.getPoints()[0].getX();
        double maxY2 = polygon.getPoints()[0].getY();

        boolean gapX = false;
        boolean gapY = false;

        for (int i = 1; i < 4; i++) {

            minX1 = Math.min(minX1, points[i].getX());
            minY1 = Math.min(minY1, points[i].getY());

            maxX1 = Math.max(maxX1, points[i].getX());
            maxY1 = Math.max(maxY1, points[i].getY());

            minX2 = Math.min(minX2, polygon.getPoints()[i].getX());
            minY2 = Math.min(minY2, polygon.getPoints()[i].getY());

            maxX2 = Math.max(maxX2, polygon.getPoints()[i].getX());
            maxY2 = Math.max(maxY2, polygon.getPoints()[i].getY());
        }

        double diffX = Math.max(maxX1, maxX2) - Math.min(minX1, minX2);
        gapX = diffX - ((maxX1 - minX1) + (maxX2 - minX2)) > 0;

        if (!gapX) {

            double diffY = Math.max(maxY1, maxY2) - Math.min(minY1, minY2);
            gapY = diffY - ((maxY1 - minY1) + (maxY2 - minY2)) > 0;
        }

        return !gapX && !gapY;
    }

    /**
     * Helps calculation position setting.
     */
    private static final Vector2D POSITION_VECTOR = new Vector2D(0, 0);

    /**
     * Sets a new position for the polygon.
     *
     * @param x Position on the x-plane.
     * @param y Position on the y-plane.
     */
    public void setPosition(double x, double y) {

        POSITION_VECTOR.setDirection(x - origin.getX(), y - origin.getY());
        origin.addVectorToPoint(POSITION_VECTOR);

        for (Point2D p : points) {

            p.addVectorToPoint(POSITION_VECTOR);
        }
    }
}
