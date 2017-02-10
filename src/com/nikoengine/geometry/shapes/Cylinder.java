package com.nikoengine.geometry.shapes;

import com.nikoengine.geometry.*;
import java.awt.Color;

/**
 * Class for creating a three dimensional, resizable and placeable red cylinder.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1127
 * @since 1.8
 */
public class Cylinder {

    /**
     * Holds reference to polygons for the cylinder movement and rotation.
     */
    Polygon3D[] polygons;

    /**
     * Holds cylinders upper points.
     */
    Point3D[] upperPoints;

    /**
     * Holds cylinders lower points.
     */
    Point3D[] lowerPoints;

    /**
     * Holds cylinders origin point.
     */
    public Point3D origin;

    /**
     * Creates a new Cylinder.
     *
     * @param x Cylinders position on the x-plane.
     * @param y Cylinders position on the y-plane.
     * @param z Cylinders position on the z-plane.
     * @param height Cylinders height.
     * @param radius Cylinders radius.
     * @param polygonAmount How many polygons there will be in the cylinder.
     */
    public Cylinder(double x, double y, double z, double height, double radius,
            int polygonAmount) {

        origin = new Point3D(x, y, z);
        polygons = new Polygon3D[polygonAmount];
        createPoints(polygonAmount, radius, height);
    }

    /**
     * Creates points and polygons for the cylinder.
     *
     * @param polygonAmount How many polygons there will be in the cylinder.
     * @param radius Cylinders radius.
     * @param height Cylinders height.
     */
    private void createPoints(int polygonAmount, double radius, double height) {
        upperPoints = new Point3D[polygonAmount];
        lowerPoints = new Point3D[polygonAmount];

        Vector2D rotationVector = new Vector2D(radius, 0);
        int oneRotation = 360 / polygonAmount;
        int redAmount = 10;
        int greenAmount = 0;
        int redAddition = (220 - redAmount) / (polygonAmount / 2);

        for (int i = 0; i < polygonAmount; i++) {

            double x = rotationVector.getX();
            double z = rotationVector.getY();

            lowerPoints[i] = new Point3D(origin.getX() + x,
                    origin.getY(),
                    z + origin.getZ());

            upperPoints[i] = new Point3D(origin.getX() + x,
                    height,
                    z + origin.getZ());

            if (i != 0) {
                polygons[i - 1] = new Polygon3D(upperPoints[i - 1],
                        upperPoints[i],
                        lowerPoints[i],
                        lowerPoints[i - 1]);

                polygons[i - 1].setOrigin(origin);
                polygons[i - 1].setColor(new Color(redAmount += redAddition,
                        greenAmount += (redAddition / 3), 0));

                if (i == polygonAmount / 2) {
                    redAddition = -redAddition;
                }
            }

            rotationVector.rotate(-oneRotation);
        }

        int i = polygonAmount - 1;
        polygons[i] = new Polygon3D(upperPoints[i],
                upperPoints[0],
                lowerPoints[0],
                lowerPoints[i]);

        polygons[i].setOrigin(origin);
        polygons[i].setColor(new Color(redAmount += redAddition, 0, 0));
    }

    /**
     * Moves the cylinder.
     *
     * @param vec Moving speed and direction.
     */
    public void move(Vector3D vec) {
        origin.addVectorToPoint(vec);

        for (int i = 0; i < upperPoints.length; i++) {
            upperPoints[i].addVectorToPoint(vec);
            lowerPoints[i].addVectorToPoint(vec);
        }
    }
    
    /**
     * Rotation vector for upper points.
     */
    Vector3D rotVec = new Vector3D(0, 0, 0);
    
    /**
     * Rotation vector for lower points.
     */
    Vector3D rotVec2 = new Vector3D(0, 0, 0);
    
    /**
     * Rotates cylinder on all the possible axis.
     */
    public void rotate() {
        
        for (int i = 0; i < upperPoints.length; i++) {
            
            rotVec.setDirection(origin, upperPoints[i]);
            rotVec2.setDirection(origin, lowerPoints[i]);
            
            rotVec.rotateXY(1);
            rotVec.rotateXZ(1);
            rotVec.rotateYZ(1);
            
            rotVec2.rotateXY(1);
            rotVec2.rotateXZ(1);
            rotVec2.rotateYZ(1);
            
            upperPoints[i].setPosition(origin.getX() + rotVec.getX(), 
                    origin.getY() + rotVec.getY(),
                    origin.getZ() + rotVec.getZ());
            
            lowerPoints[i].setPosition(origin.getX() + rotVec2.getX(), 
                    origin.getY() + rotVec2.getY(),
                    origin.getZ() + rotVec2.getZ());
        }
    }
}
