package com.nikoengine.geometry.shapes;

import com.nikoengine.geometry.*;
import java.awt.Color;

/**
 * Class for creating three dimensional colorable and straight walls.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1127
 * @since 1.8
 */
public class Wall {

    /**
     * Holds the polygons of the wall.
     */
    Polygon3D[] polygons;

    /**
     * Holds the corner points of the wall.
     */
    Point3D[][] points;

    /**
     * Determines walls first color.
     */
    Color c1;

    /**
     * Determines walls second color.
     */
    Color c2;

    /**
     * Creates a 3D wall.
     *
     * @param start Starting point.
     * @param end Ending point.
     * @param height Wall´s height.
     * @param polX How many polygons in a row.
     * @param polY How many polygons in a column.
     * @param c1 Wall´s starting color.
     * @param c2 Wall´s ending color.
     */
    public Wall(Point3D start, Point3D end, double height, int polX, int polY,
            Color c1, Color c2) {

        polygons = new Polygon3D[polX * polY];
        points = new Point3D[polY + 1][polX + 1];

        this.c1 = c1;
        this.c2 = c2;

        createPoints(start, end, height, polX, polY);
    }

    /**
     * Generates walls points, polygons and colors.
     *
     * @param start Starting point.
     * @param end Ending point.
     * @param height Wall´s height.
     * @param polX How many polygons in a row.
     * @param polY How many polygons in a column.
     */
    private void createPoints(Point3D start, Point3D end, double height,
            int polX, int polY) {

        Vector3D wallDirection = end.getVectorFromPoint(start);
        double blockWidth = wallDirection.getMagnitude() / polX;
        wallDirection.normalize();
        wallDirection.setScale(blockWidth);
        double oneBlockHeight = height / polY;

        double origRed = c1.getRed();
        double origBlue = c1.getBlue();
        double origGreen = c1.getGreen();
        double redAdd = (c1.getRed() - c2.getRed()) / (double) (polX + 1);
        double greenAdd = (c1.getGreen() - c2.getGreen()) / (double) (polX + 1);
        double blueAdd = (c1.getBlue() - c2.getBlue()) / (double) (polX + 1);

        int polygonIndex = 0;

        for (int i = 0; i <= polX; i++) {

            origRed -= redAdd;
            origBlue -= blueAdd;
            origGreen -= greenAdd;
            Color c = new Color((int) origRed, (int) origGreen, (int) origBlue);

            for (int j = 0; j <= polY; j++) {

                points[j][i] = new Point3D(start.getX(),
                        start.getY() + oneBlockHeight * j,
                        start.getZ());

                if (i > 0 && j > 0) {

                    polygons[polygonIndex] = new Polygon3D(points[j][i - 1],
                            points[j][i],
                            points[j - 1][i],
                            points[j - 1][i - 1]);

                    polygons[polygonIndex].setColor(c);
                    polygonIndex++;
                }
            }

            start.addVectorToPoint(wallDirection);
        }
    }
}
