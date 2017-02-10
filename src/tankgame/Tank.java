package tankgame;

import com.nikoengine.geometry.*;
import com.nikoengine.util.XMLto3DObjectParser;

/**
 * Tank for playing tank game.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1217
 * @since 1.8
 */
public class Tank {

    /**
     * Tanks lower part object.
     */
    GameObject3D tank;

    /**
     * Tanks turret object.
     */
    GameObject3D turret;

    /**
     * Defines whether tank is turning.
     */
    boolean isTurretTurning = false;

    /**
     * Defines whether tank is turning left or right.
     */
    boolean isTurretLeft = false;

    /**
     * Creates a new tank object.
     *
     * <p>
     * Parses tanks points and polygons from XML file.
     */
    public Tank() {

        XMLto3DObjectParser parser = new XMLto3DObjectParser();
        tank = parser.newGameObject("./res/tank_model.xml", .5);
        turret = parser.newGameObject("./res/turret_model.xml", .5);
    }

    /**
     * Defines whether tank is moving or not.
     */
    boolean isMoving = false;

    /**
     * Updates tanks position.
     */
    public void update() {

        tank.update();
        turret.update();

        if (isMoving) {
            turret.setPosition(tank.origin);
        }

        if (isTurretTurning) {
            if (isTurretLeft) {
                turret.rotateXZ(-4);
            } else {
                turret.rotateXZ(4);
            }
        }
    }

    /**
     * Toggles tanks movement.
     *
     * @param b Whether is moving or not.
     */
    public void move(boolean b) {
        tank.move(b);
        isMoving = b;
    }

    /**
     * Turns turret.
     *
     * <p>
     * Negative value is left, positive right and zero is stop turning.
     *
     * @param direction Turning direction.
     */
    public void turnTurret(int direction) {

        if (direction < 0) {
            isTurretTurning = true;
            isTurretLeft = true;
        } else if (direction > 0) {
            isTurretTurning = true;
            isTurretLeft = false;
        } else {
            isTurretTurning = false;
        }
    }

    /**
     * Turns tank.
     *
     * <p>
     * Negative value is left, positive right and zero is stop turning.
     *
     * @param direction Turning direction.
     */
    public void turnTank(int direction) {
        tank.turn(direction);
        turret.turn(direction);
    }

    /**
     * Returns tank´s origin point.
     *
     * @return Origin point of the tank.
     */
    public Point3D getOrigin() {
        return tank.origin;
    }

    /**
     * Returns tank´s direction.
     *
     * @return Direction of the tank.
     */
    public Vector3D getDirection() {
        return tank.getDirection();
    }

    /**
     * Returns turrets direction.
     *
     * @return Direction of the turret.
     */
    public Point3D getTurretOrigin() {
        Point3D point = new Point3D(
                turret.origin.getX(),
                turret.origin.getY(),
                turret.origin.getZ());

        point.addVectorToPoint(turret.rotVec);

        return point;
    }

    /**
     * Returns turrets position.
     *
     * @return Position of the turret.
     */
    public Vector3D getTurretDirection() {
        return new Vector3D(turret.rotVec.getX(), 0, turret.rotVec.getZ());
    }
}
