package com.nikoengine.util;

import java.awt.event.*;
import javax.swing.*;

import com.nikoengine.screen.*;

/**
 * Simplified input handling class.
 *
 * <p>
 * This class encapsulates Java´s KeyListener and MouseListener classes so that
 * the developer can only insert an instance of this class to Application. This
 * class works as follows. Developer inserts a instance of a Screen class to
 * this InputListener. This InputListener then calls that Screen classes input
 * handling methods.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1123
 * @since 1.8
 */
public class InputListener {

    /**
     * Holds currently drawn and updated screen.
     */
    private Screen currentScreen;

    /**
     * Creates new InputListener.
     *
     * @param panel Panel where this input listener is set.
     */
    public InputListener(JPanel panel) {
        panel.addMouseListener(new MouseInput());
        panel.addKeyListener(new KeyInput());
    }

    /**
     * Encapsulated MouseListener.
     *
     * <p>
     * Calls current screen´s input methods.
     *
     * @author Niko Mustonen mustonen.niko@gmail.com
     * @version 2016.1123
     * @since 1.8
     */
    private class MouseInput implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            currentScreen.mousePress(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            currentScreen.mouseRelease(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Encapsulated KeyListener.
     *
     * <p>
     * Calls current screen´s input methods.
     *
     * @author Niko Mustonen mustonen.niko@gmail.com
     * @version 2016.1123
     * @since 1.8
     */
    private class KeyInput implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            currentScreen.keyPress(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            currentScreen.keyRelease(e);
        }
    }

    /**
     * Sets the InputListener to desired JPanel.
     *
     * @param panel JPanel to be setted on.
     */
    public final void setThisInputListenerToJPanel(JPanel panel) {
        panel.addMouseListener(new MouseInput());
        panel.addKeyListener(new KeyInput());
    }

    /**
     * Sets current screen to be handled.
     *
     * @param newScreen New Screen element.
     */
    public final void setCurrentScreen(Screen newScreen) {
        this.currentScreen = newScreen;
    }
}
