package com.nikoengine.image;

/**
 * Class for creating animations from sprite sheet images.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1123
 * @since 1.8
 */
public class Animation extends Drawable {

    /**
     * Determines whether back and forth repeat is set on.
     */
    private boolean isBackAndForthRepeat = false;

    /**
     * Holds the original amount of the frames.
     *
     * <p>
     * Is used for enabling and disabling back and forth repeat.
     */
    private final int ORIGINAL_FRAMES_AMOUNT;

    /**
     * Holds all the images in the animation.
     */
    Image[] imgs;

    /**
     * Determines frames per second speed.
     */
    private int fps = 12;

    /**
     * Determines how long will frame stay on the screen.
     */
    private int frameDurationMillis;

    /**
     * Determines one frames elapsed time so it can be changed.
     */
    float elapsedTime = 0;

    /**
     * Holds an index value of the current frame from the list of frames.
     */
    int currentFrameIndex = 0;

    /**
     * Creates new animation from sprite sheet image.
     *
     * @param spriteSheet Sprite sheet image.
     * @param cols Amount of the image columns.
     * @param rows Amount of the image rows.
     * @param n How many images there will be in the animation.
     */
    public Animation(Image spriteSheet, int cols, int rows, int n) {
        ORIGINAL_FRAMES_AMOUNT = n;
        imgs = new Image[ORIGINAL_FRAMES_AMOUNT];
        int index = 0;
        setWidth(spriteSheet.getWidth() / cols);
        setHeight(spriteSheet.getHeight() / rows);
        frameDurationMillis = 1000 / fps;

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                if (index < n) {
                    imgs[index] = spriteSheet.getSubImage(
                            j * getWidth(),
                            i * getHeight(),
                            getWidth(),
                            getHeight());
                }

                index++;
            }
        }

        img = imgs[0].img;
    }

    @Override
    public void update(float delta) {

        if (isVisible) {

            radians = Math.toRadians(rotation);
            elapsedTime += delta;

            if (elapsedTime > frameDurationMillis) {
                img = imgs[currentFrameIndex].img;
                currentFrameIndex++;
                elapsedTime = 0;

                if (currentFrameIndex == imgs.length) {
                    currentFrameIndex = 0;
                    
                    if (!isLoop) {
                        isVisible = false;
                    }
                }
            }
        }
    }

    /**
     * Enables back and forth repeat to animation.
     *
     * <p>
     * Creates new frames to the end of the animation so animation will loop
     * back and forth.
     */
    public void enableBackAndForthRepeat() {

        if (!isBackAndForthRepeat && imgs.length > 2) {

            int tmpFrameAmount = imgs.length - 2;
            Image[] tmpFrames = new Image[imgs.length + tmpFrameAmount];
            System.arraycopy(imgs, 0, tmpFrames, 0, imgs.length);

            int index = imgs.length;

            for (int i = tmpFrameAmount; i > 0; i--) {

                tmpFrames[index] = imgs[i];
                index++;
            }

            imgs = tmpFrames;
            isBackAndForthRepeat = true;
        }
    }

    /**
     * Disables back and forth repeat of the animation.
     *
     * <p>
     * Removes end half of the frames from the frame list and sets the animation
     * length to be original length.
     */
    public void disableBackAndForthRepeat() {

        if (isBackAndForthRepeat && imgs.length > 2) {

            Image[] tmpFrames = new Image[ORIGINAL_FRAMES_AMOUNT];
            System.arraycopy(imgs, 0, tmpFrames, 0, ORIGINAL_FRAMES_AMOUNT);

            imgs = tmpFrames;

            if (currentFrameIndex > ORIGINAL_FRAMES_AMOUNT) {

                currentFrameIndex = 0;
            }

            isBackAndForthRepeat = false;
        }
    }

    @Override
    public final int getWidth() {

        return this.width;
    }

    @Override
    public final int getHeight() {

        return this.height;
    }

    /**
     * Sets fps for the animation.
     * 
     * @param fps Desired frames per second.
     */
    public void setFPS(int fps) {
        this.fps = fps;
        frameDurationMillis = 1000 / fps;
    }

    /**
     * Defines whether animation is looping or not.
     */
    private boolean isLoop = true;

    /**
     * Sets images looping sequence.
     * 
     * @param isLoop Whether animation is looping.
     */
    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        img = imgs[currentFrameIndex].img;
    }
}
