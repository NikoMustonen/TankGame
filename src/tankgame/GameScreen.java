package tankgame;

import com.nikoengine.screen.*;
import com.nikoengine.geometry.*;
import com.nikoengine.camera.*;
import com.nikoengine.geometry.shapes.Cylinder;
import com.nikoengine.geometry.shapes.Wall;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Class for creating a tank battle arena.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1215
 * @since 1.8
 */
public class GameScreen extends Screen {

    /**
     * Holds all the walls in the test area.
     */
    Wall[] walls = new Wall[14];

    /**
     * Controls cameras origin point.
     */
    Point3D p = new Point3D(0, -4, 10);

    /**
     * Controls cameras direction.
     */
    Vector3D v = new Vector3D(0, 0, 1);

    /**
     * Holds camera to explore test area.
     */
    Camera3D cam = new Camera3D(p, v);

    /**
     * Determines color one in the room.
     */
    Color color1 = Color.RED;

    /**
     * Determines color two in the room.
     */
    Color color2 = Color.BLUE;

    /**
     * Holds rotating cylinder.
     */
    Cylinder c = new Cylinder(20, 0, 20, -5, 2, 16);

    /**
     * Holds players tank.
     */
    Tank tank;

    /**
     * Holds bullet.
     */
    Bullet bullet = new Bullet(0, 0, 0);

    /**
     * Creates new game arena for tank game.
     */
    public GameScreen() {

        tank = new Tank();

        walls[0] = new Wall(new Point3D(-40, 0, -40), new Point3D(-40, 0, 40),
                -12, 40, 6, color1, color2);

        walls[1] = new Wall(new Point3D(-40, 0, 40), new Point3D(40, 0, 40),
                -12, 40, 6, color2, color2);

        walls[2] = new Wall(new Point3D(40, 0, 40), new Point3D(40, 0, -40),
                -12, 40, 6, color2, color1);

        walls[3] = new Wall(new Point3D(40, 0, -40), new Point3D(10, 0, -40),
                -12, 15, 6, color1, color2);

        walls[4] = new Wall(new Point3D(-40, 0, -40), new Point3D(-10, 0, -40),
                -12, 15, 6, color1, color2);

        walls[5] = new Wall(new Point3D(-10, -6, -40), new Point3D(10, -6, -40),
                -6, 10, 3, color2, color2);

        walls[6] = new Wall(new Point3D(-10, -6, -40), new Point3D(-5, -6, -80),
                6, 20, 3, color1, color2);

        walls[7] = new Wall(new Point3D(10, -6, -40), new Point3D(5, -6, -80),
                6, 20, 3, color1, color2);

        walls[8] = new Wall(new Point3D(40, 0, -80), new Point3D(5, 0, -80),
                -12, 15, 6, color2, color1);

        walls[9] = new Wall(new Point3D(-40, 0, -80), new Point3D(-5, 0, -80),
                -12, 15, 6, color2, color1);

        walls[10] = new Wall(new Point3D(-5, -6, -80), new Point3D(5, -6, -80),
                -6, 10, 3, color1, color1);

        walls[11] = new Wall(new Point3D(-40, 0, -160),
                new Point3D(-40, 0, -80), -12, 40, 6, color1, color2);

        walls[12] = new Wall(new Point3D(-40, 0, -160),
                new Point3D(40, 0, -160), -12, 40, 6, color1, color2);

        walls[13] = new Wall(new Point3D(40, 0, -80), new Point3D(40, 0, -160),
                -12, 40, 6, color1, color2);
    }

    /**
     * Draws game arena.
     *
     * @param g Graphics element for drawing.
     */
    @Override
    public void draw(Graphics2D g) {

        cam.renderCameraView(g);

        g.setColor(Color.red);
        g.drawString("Move tank with arrow keys", 30, 30);
        g.drawString("Turn turret: \"A\" and \"D\" buttons", 30, 50);
        g.drawString("Toggle wire frame: \"R\"", 30, 70);
        g.drawString("Shoot: SPACE-button", 30, 90);
        g.drawString("Move Camera: \"Q\" and \"E\"", 30, 110);
    }

    /**
     * Updates game screen.
     *
     * @param delta Delta time.
     */
    @Override
    public void update(float delta) {
        tank.update();
        cam.update();
        c.rotate();
        cam.setDirection(tank.getOrigin());
        bullet.update();

        Vector3D distance = tank.getOrigin().getVectorFromPoint(
                cam.getPosition());

        if (distance.getDotProduct() > 400) {
            double moveFactor = (distance.getDotProduct() - 400) / 50000;
            distance.setScale(moveFactor);
            distance.setY(0);
            cam.move(distance);
        }
    }

    /**
     * Handles users input and moves the tank and the camera.
     *
     * @param e User input.
     */
    @Override
    public void keyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                cam.backWards();
                break;
            case KeyEvent.VK_S:
                cam.forward();
                break;
            case KeyEvent.VK_A:
                tank.turnTurret(-1);
                break;
            case KeyEvent.VK_D:
                tank.turnTurret(1);
                break;
            case KeyEvent.VK_RIGHT:
                tank.turnTank(1);
                break;
            case KeyEvent.VK_LEFT:
                tank.turnTank(-1);
                break;
            case KeyEvent.VK_UP:
                tank.move(true);
                break;
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_SPACE:
                bullet.shoot(tank.getTurretOrigin(), tank.getTurretDirection());
                break;
            case KeyEvent.VK_Q:
                cam.strafeRight();
                break;
            case KeyEvent.VK_E:
                cam.strafeLeft();
                break;
            case KeyEvent.VK_R:
                cam.toggleWireFrame();
                break;
        }
    }

    /**
     * Handles users key releases and stops corresponding items.
     *
     * @param e Released key..
     */
    @Override
    public void keyRelease(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                cam.stopMove();
                break;
            case KeyEvent.VK_S:
                cam.stopMove();
                break;
            case KeyEvent.VK_A:
                tank.turnTurret(0);
                break;
            case KeyEvent.VK_D:
                tank.turnTurret(0);
                break;
            case KeyEvent.VK_LEFT:
                tank.turnTank(0);
                break;
            case KeyEvent.VK_RIGHT:
                tank.turnTank(0);
                break;
            case KeyEvent.VK_UP:
                tank.move(false);
                break;
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_Q:
                cam.stopStrafe();
                break;
            case KeyEvent.VK_E:
                cam.stopStrafe();
                break;
        }
    }

    /**
     * Handles mouse key press.
     *
     * <p>
     * Not used in this screen.
     *
     * @param e Not used in this screen.
     */
    @Override
    public void mousePress(MouseEvent e) {

    }

    /**
     * Handles mouse key release.
     *
     * <p>
     * Not used in this screen.
     *
     * @param e Not used in this screen.
     */
    @Override
    public void mouseRelease(MouseEvent e) {

    }
}
