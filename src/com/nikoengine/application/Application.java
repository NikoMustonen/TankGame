package com.nikoengine.application;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

import com.nikoengine.util.InputListener;
import com.nikoengine.screen.Screen;

/**
 * Main class for NikoEngine game development.
 *
 * <p>
 * This class is the main starting point for Niko Engine game development. It
 * sets up a window for visual presentation and it also creates and starts a
 * game loop for updating your game. This class basically holds three things:
 * window, game loop and player input handling.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1127
 * @since 1.8
 */
public abstract class Application {

    /**
     * Holds game screen width.
     */
    public static int screenWidth = 1240;

    /**
     * Holds game screen height.
     */
    public static int screenHeight = 720;

    /**
     * Holds desired frames per second value.
     */
    private int targetFPS;

    /**
     * Holds one frames duration in milliseconds.
     */
    private int oneFrameMillis;

    /**
     * Tells if the game loop is running or not.
     */
    private boolean isRunning;

    /**
     * Runs the game loop.
     */
    private final Thread gameThread;

    /**
     * Draws the screen.
     */
    private final GamePanel gamePanel;

    /**
     * Shows a window to hold all the visuals.
     */
    private final JFrame window;

    /**
     * Handles input listening from the player.
     */
    private InputListener inputListener;

    /**
     * Creates the Application instance which runs and shows the game.
     */
    public Application() {
        gamePanel = new GamePanel();
        window = new JFrame();
        gameThread = new Thread(new GameLoop());
        setDesiredFramesPerSecond(60);
    }

    /**
     * Class for drawing the screen.
     *
     * <p>
     * This is a private encapsulated JPanel class which handles the game screen
     * drawing.
     *
     * @author Niko Mustonen mustonen.niko@gmail.com
     * @version 2016.1123
     * @since 1.8
     */
    private class GamePanel extends JPanel {

        /**
         * Sets image to be drawn on the screen.
         */
        Image gameView;

        /**
         * Renders image to the screen.
         */
        public void renderGameImage() {

            if (gameView != null) {
                getGraphics().drawImage(gameView, 0, 0, null);
            }
            
            getGraphics().dispose();
        }

        /**
         * Prepares the screen for drawing new image.
         */
        public void setImage() {
            
            if (gameView == null) {
                gameView = createImage(getWidth(), getHeight());
            }
            
            Graphics g = gameView.getGraphics();
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Class for updating the game.
     *
     * <p>
     * This is a private encapsulated game loop class and it holds the game loop
     * which updates the games current screen. Instance of this class also
     * calculates the updating time so the game can try to keep the desired
     * frames per second value.
     *
     * @author Niko Mustonen mustonen.niko@gmail.com
     * @version 2016.1123
     * @since 1.8
     */
    private class GameLoop implements Runnable {

        @Override
        public void run() {

            initialize();
            long updateDur = 0;
            long sleepDuration = 0;

            while (isRunning) {

                long timeBeforeUpdate = System.nanoTime();
                long delta = updateDur + sleepDuration;

                update(delta);
                gamePanel.setImage();
                draw((Graphics2D) gamePanel.gameView.getGraphics());
                gamePanel.renderGameImage();

                updateDur = (System.nanoTime() - timeBeforeUpdate) / 1000000L;
                sleepDuration = Math.max(1, oneFrameMillis - updateDur);

                sleep(sleepDuration);
            }
        }
    }

    /**
     * Runs before the game updating and drawing starts.
     */
    public abstract void initialize();

    /**
     * Draws the game.
     *
     * @param g Graphics2D-element for drawing the game.
     */
    public abstract void draw(Graphics2D g);

    /**
     * Updates the game.
     *
     * @param delta Delta value for timing control.
     */
    public abstract void update(float delta);

    /**
     * Starts the game and initializes a window for visual presentation.
     */
    protected final void start() {

        createWindow(screenWidth, screenHeight);
        isRunning = true;
        gameThread.start();
    }

    /**
     * Pauses the game for to maintain the desired frames per second value.
     *
     * @param milliSeconds Pausing time in milliseconds.
     */
    private void sleep(long milliSeconds) {

        // System.out.println(milliSeconds);
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a game window.
     *
     * @param screenWidth Desired width for the window.
     * @param screenHeight Desired height for the window.
     */
    private void createWindow(int screenWidth, int screenHeight) {

        window.setBackground(Color.DARK_GRAY);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gamePanel);

        setScreenSize(screenWidth, screenHeight);

        window.setVisible(true);
    }

    /**
     * Returns game panels graphic element.
     *
     * @return game panels Graphics2D-element.
     */
    public final Graphics2D getGraphics() {

        return (Graphics2D) gamePanel.getGraphics();
    }

    /**
     * Sets the frames per second value for the game loop.
     *
     * @param desiredFPS Frames per second value.
     */
    public final void setDesiredFramesPerSecond(int desiredFPS) {

        this.targetFPS = desiredFPS;
        oneFrameMillis = (int) ((1f / (float) targetFPS) * 1000);
    }

    /**
     * Sets screens size.
     *
     * @param width Width of the screen.
     * @param height Height of the screen.
     */
    public final void setScreenSize(int width, int height) {

        gamePanel.setPreferredSize(new Dimension(width, height));
        window.pack();
        screenWidth = width;
        screenHeight = height;
    }

    /**
     * Starts a input listening device for the game.
     */
    protected final void startInputListener() {

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        inputListener = new InputListener(gamePanel);
    }

    /**
     * Sets a new screen element to be listened for the input listener.
     *
     * @param newScreen New scree to be listened.
     */
    protected final void setScreen(Screen newScreen) {

        this.inputListener.setCurrentScreen(newScreen);
    }

    /**
     * Returns the height of the screen.
     *
     * @return Height of the screen.
     */
    public static final int getScreenHeight() {

        return screenHeight;
    }

    /**
     * Returns the width of the screen.
     *
     * @return Width of the screen.
     */
    public static final int getScreenWidth() {

        return screenWidth;
    }
}
