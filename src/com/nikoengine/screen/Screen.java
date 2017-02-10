package com.nikoengine.screen;

import java.awt.*;
import java.awt.event.*;

/**
 * Base class for all the different states in the game.
 *
 * <p>
 * By extending this class the developer can create a visual updateable screen
 * to be inserted into the Niko Engines core. Niko Engine calls automatically
 * all the methods in this class. All the key listening methods are called
 * automatically if input listening is started in the Application class.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1122
 * @since 1.8
 */
public abstract class Screen {

    /**
     * Draws the screen.
     */
    public abstract void draw(Graphics2D g);

    /**
     * Updates screen.
     */
    public abstract void update(float delta);

    /**
     * Handles key pressing on the screen.
     */
    public abstract void keyPress(KeyEvent e);

    /**
     * Handles key releasing on the screen.
     */
    public abstract void keyRelease(KeyEvent e);

    /**
     * Handles mouse pressing on the screen.
     */
    public abstract void mousePress(MouseEvent e);

    /**
     * Handles mouse releasing on the screen.
     */
    public abstract void mouseRelease(MouseEvent e);
}
