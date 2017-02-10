package com.nikoengine.resources;

import com.nikoengine.image.Image;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import org.w3c.dom.*;

/**
 * Helper class for resource loading.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1201
 * @since 1.8
 */
public class TiledMapReader {

    /**
     * Defines xml attribute name for layer.
     */
    public static final String NODE_LAYER = "layer";

    /**
     * Defines xml attribute name for tile set.
     */
    public static final String NODE_TILESET = "tileset";

    /**
     * Defines xml attribute name for map data.
     */
    public static final String NODE_MAP_DATA = "data";

    /**
     * Defines xml attribute name for map width in tiles.
     */
    public static final String NODE_MAP_WIDTH = "width";

    /**
     * Defines xml attribute name for map height in tiles.
     */
    public static final String NODE_MAP_HEIGHT = "height";

    /**
     * Defines xml attribute name for one block width.
     */
    public static final String NODE_TILE_WIDTH = "tilewidth";

    /**
     * Defines xml attribute name for one block height.
     */
    public static final String NODE_TILE_HEIGHT = "tileheight";

    /**
     * Controls the layers and tiles of the TiledMAp instance.
     *
     * <p>
     * First array holds all layers, second all the tile rows. Last array holds
     * the tiles in corresponding row.
     */
    int[][][] layersAndBlocks;

    /**
     * Holds a width of one tile.
     */
    int tileWidth;

    /**
     * Holds a height of one tile.
     */
    int tileHeight;

    /**
     * Holds the tile set image used in tile map.
     */
    Image img;

    /**
     * Reads and converts tiled map file.
     *
     * @param tiledFile File reference to tiled map file.
     * @return TiledMap instance which can be used to render tile map.
     */
    public TiledMap newTiledMap(File tiledFile) {

        TiledMap tiledMap = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(tiledFile);

            NodeList n = doc.getChildNodes().item(0).getChildNodes(); //Map

            layersAndBlocks = new int[getLayerAmount(n)][1][1];
            setTileDimension(n);
            findAndSetLayers(n);

            img = getImage(n, tiledFile);

            tiledMap = new TiledMap(layersAndBlocks, tileWidth,
                    tileHeight, img);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return tiledMap;
    }

    /**
     * Converts tiled map file to Niko Engine format.
     *
     * @param n Node element list from the XML map file.
     */
    private void findAndSetLayers(NodeList n) {

        int width = 0;
        int height = 0;
        int layerIndex = 0;

        for (int i = 0; i < n.getLength(); i++) {

            String nodeName = n.item(i).getNodeName();

            if (nodeName.equals(NODE_LAYER)) {
                NamedNodeMap layer = n.item(i).getAttributes();

                for (int j = 0; j < layer.getLength(); j++) {

                    switch (layer.item(j).getNodeName()) {
                        case NODE_MAP_WIDTH:

                            width = Integer.parseInt(layer.item(j).
                                    getNodeValue());

                            break;
                        case NODE_MAP_HEIGHT:

                            height = Integer.parseInt(layer.item(j).
                                    getNodeValue());
                            break;
                    }
                }

                String s = n.item(i).getChildNodes().item(1).getFirstChild().
                        getNodeValue();

                String[] data = s.split(",");
                int[][] map = convertDataToMap(data, width, height);
                layersAndBlocks[layerIndex] = map;
                layerIndex++;
            }
        }
    }

    /**
     * Calculates the amount of layers in the tiled map file.
     *
     * @param n Node element list from the XML map file.
     * @return Amount of layers.
     */
    private int getLayerAmount(NodeList n) {

        int layers = 0;

        for (int i = 0; i < n.getLength(); i++) {
            String nodeName = n.item(i).getNodeName();

            if (nodeName.equals(NODE_LAYER)) {
                layers++;
            }
        }

        return layers;
    }

    /**
     * Converts tiled map file's tile data to two dimensional array.
     *
     * <p>
     * This two dimensional array can be used for creating TiledMap instances.
     *
     * @param data Tiled map file's tile map data.
     * @param width Horizontal blocks amount.
     * @param height Vertical blocks amount.
     * @return Converted two dimensional array.
     */
    private int[][] convertDataToMap(String[] data, int width, int height) {

        int[][] map = new int[height][width];
        int index = 0;

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                int v = (int) Integer.parseInt(data[index].trim());
                map[i][j] = v;
                index++;
            }
        }

        return map;
    }

    /**
     * Reads image file from the tiled map file and returns corresponding image.
     *
     * @param n Node element list from the XML map file.
     * @param tiledFile File reference to tiled map file.
     * @return Tile set image that is used when tiled map file was created.
     */
    private Image getImage(NodeList n, File tiledFile) {

        for (int i = 0; i < n.getLength(); i++) {

            if (n.item(i).getNodeName().equals(NODE_TILESET)) {

                for (int j = 0; j < n.item(i).getChildNodes().getLength();
                        j++) {

                    String path = n.item(1).getChildNodes().item(1).
                            getAttributes().item(1).getNodeValue();

                    if (path != null) {
                        return new Image(tiledFile.getParent()
                                + "/" + path, 0, 0);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Sets tile dimension for the TiledMap instance.
     *
     * @param n Node element list from the XML map file.
     */
    private void setTileDimension(NodeList n) {

        for (int i = 0; i < n.getLength(); i++) {

            if (n.item(i).getNodeName().equals(NODE_TILESET)) {

                for (int j = 0; j < n.item(i).getAttributes().getLength();
                        j++) {

                    Node attribute = n.item(i).getAttributes().item(j);

                    if (attribute.getNodeName().equals(NODE_TILE_WIDTH)) {
                        tileWidth = Integer.parseInt(attribute.getNodeValue());
                    }

                    if (attribute.getNodeName().equals(NODE_TILE_HEIGHT)) {
                        tileHeight = Integer.parseInt(attribute.getNodeValue());
                    }
                }
            }
        }
    }
}
