package com.nikoengine.resources;

import com.nikoengine.camera.Camera2D;
import com.nikoengine.image.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Helper class for resource loading.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1201
 * @since 1.8
 */
public class TiledMap {

    /**
     * Controls all the data for drawing the tile map.
     */
    private final int[][][] layers;

    /**
     * Array of tile images which is separated from the tile set image.
     */
    private BufferedImage[] tileSet;
    
    /**
     * Holds one tile width.
     */
    private final int tileWidth;
    
    /**
     * Holds one tile height.
     */
    private final int tileHeight;

    /**
     * Generates a TiledMap instance for viewing tile map.
     *
     * @param layersAndTiles Three dimensional int array of tiles and layers.
     * @param tileWidth One tile width;
     * @param tileHeight One tile height.
     * @param tileSet Tile set image.
     */
    public TiledMap(int[][][] layersAndTiles, int tileWidth, int tileHeight,
            Image tileSet) {

        this.layers = layersAndTiles;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        setTileSet(tileSet);
    }

    /**
     * Draws the whole tile map to the screen.
     *
     * @param g Graphics element for drawing.
     */
    public void draw(Graphics2D g) {

        for (int[][] layer : layers) {

            for (int j = 0; j < layer.length; j++) {

                for (int k = 0; k < layer[j].length; k++) {
                    int imageIndex = layer[j][k] - 1;
                    
                    if (imageIndex > -1) {
                        g.drawImage(tileSet[imageIndex],
                                k * tileWidth, j * tileHeight, null);
                    }
                }
            }
        }
    }

    /**
     * Draws only the portion of the tiled map which is visible to camera.
     *
     * @param cam Camera to view from.
     * @param g Graphics2D drawing element.
     */
    public void renderMap(Camera2D cam, Graphics2D g) {

        startBlockX = cam.getTopX() / tileWidth;
        
        if (startBlockX < 0) {
            startBlockX = 0;
        }

        startBlockY = cam.getTopY() / tileHeight;
        
        if (startBlockY < 0) {
            startBlockY = 0;
        }

        endBlockX = cam.getBottomX() / tileWidth + 1;
        
        if (endBlockX >= layers[0][0].length) {
            endBlockX = layers[0][0].length;
        }

        endBlockY = cam.getBottomY() / tileHeight + 1;
        
        if (endBlockY >= layers[0].length) {
            endBlockY = layers[0].length;
        }

        for (int[][] layer : layers) {

            for (int j = startBlockY; j < endBlockY; j++) {

                for (int k = startBlockX; k < endBlockX; k++) {
                    int imageIndex = layer[j][k] - 1;

                    if (imageIndex > -1) {
                        g.drawImage(tileSet[imageIndex],
                                k * tileWidth - cam.getX(),
                                j * tileHeight - cam.getY(), null);
                    }
                }
            }
        }
    }

    /**
     * Controls rendering starting tile x when viewed through camera.
     */
    int startBlockX;

    /**
     * Controls rendering starting tile y when viewed through camera.
     */
    int startBlockY;

    /**
     * Controls rendering ending tile x when viewed through camera.
     */
    int endBlockX;

    /**
     * Controls rendering ending tile y when viewed through camera.
     */
    int endBlockY;

    /**
     * Separates tiles from tile set image.
     *
     * @param img Tile set image.
     */
    private void setTileSet(Image img) {

        int xBlocks = img.getWidth() / tileWidth;
        int yBlocks = img.getHeight() / tileHeight;
        tileSet = new BufferedImage[xBlocks * yBlocks];

        int index = 0;

        for (int i = 0; i < yBlocks; i++) {

            for (int j = 0; j < xBlocks; j++) {

                tileSet[index] = img.getSubImage(j * tileWidth, i * tileHeight,
                        tileWidth, tileHeight).img;
                index++;
            }
        }
    }

    /**
     * Returns corresponding layer to layer index attribute.
     *
     * <p>
     * Can be used for collision checking for example.
     *
     * @param layerIndex Index of layer.
     * @return Layer element.
     */
    public int[][] getLayer(int layerIndex) {

        return layers[layerIndex];
    }
}
