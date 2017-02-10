package tankgame;

import com.nikoengine.application.*;
import java.awt.Graphics2D;

/**
 * Starting point for tank game.
 *
 * <p>
 * Starts new window and game loop and instantiates new game screen.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1215
 * @since 1.8
 */
public class TankGame extends Application {

    /**
     * Holds current game screen.
     */
    GameScreen game;

    /**
     * Starts the program.
     *
     * @param args Not used.
     */
    public static void main(String... args) {

        new TankGame().start();
    }

    /**
     * Initializes game screen and input listening.
     */
    @Override
    public void initialize() {

        game = new GameScreen();
        startInputListener();
        setScreen(game);
    }

    /**
     * Draws current screen.
     *
     * @param g Graphics element for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        game.draw(g);
    }

    /**
     * Updates current screen.
     *
     * @param delta Delta time.
     */
    @Override
    public void update(float delta) {
        game.update(delta);
    }
}
