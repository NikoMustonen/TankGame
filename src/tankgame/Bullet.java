package tankgame;

import com.nikoengine.geometry.*;
import java.awt.Color;

/**
 * Is used for shooting.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1217
 * @since 1.8
 */
public class Bullet {
    
    /**
     * Bullets object in three dee space.
     */
    private GameObject3D bullet;
    
    /**
     * Creates new bullet.
     * 
     * @param x Bullets x position.
     * @param z Bullets y position.
     * @param y Bullets z position.
     */
    public Bullet(double x, double z, double y) {

        Point3D origin = new Point3D(x, y, z);
        Vector3D direction = new Vector3D(0, 0, 1);
        
        Point3D[] pts = new Point3D[6];
        
        pts[0] = new Point3D(x, y, z - .5);
        pts[1] = new Point3D(x -.5, y, z);
        pts[2] = new Point3D(x, y, z + .5);
        pts[3] = new Point3D(x + .5, y, z);
        
        pts[4] = new Point3D(x, y + .5, z);
        pts[5] = new Point3D(x, y - .5, z);
        
        bullet = new GameObject3D(origin, direction, pts);
        
        Polygon3D p1 = new Polygon3D(pts[0], pts[1], pts[4]);
        p1.setColor(Color.yellow);
        
        Polygon3D p2 = new Polygon3D(pts[0], pts[1], pts[5]);
        p2.setColor(Color.orange);
        
        Polygon3D p3 = new Polygon3D(pts[1], pts[2], pts[4]);
        p3.setColor(Color.orange);
        
        Polygon3D p4 = new Polygon3D(pts[1], pts[2], pts[5]);
        p4.setColor(Color.yellow);
        
        Polygon3D p5 = new Polygon3D(pts[2], pts[3], pts[4]);
        p5.setColor(Color.yellow);
        
        Polygon3D p6 = new Polygon3D(pts[2], pts[3], pts[5]);
        p6.setColor(Color.orange);
        
        Polygon3D p7 = new Polygon3D(pts[3], pts[0], pts[4]);
        p7.setColor(Color.orange);
        
        Polygon3D p8 = new Polygon3D(pts[3], pts[0], pts[5]);
        p8.setColor(Color.yellow);
    }
    
    /**
     * Makes bullet start shooting.
     * 
     * @param position Bullets starting position.
     * @param direction Bullets direction.
     */
    public void shoot(Point3D position, Vector3D direction) {
        bullet.setPosition(position.getX(), -2, position.getZ());
        bullet.setDirection(direction, 0.8);
        bullet.move(true);
    }
    
    /**
     * Updates bullet.
     */
    public void update() {
        bullet.update();
    }
}
