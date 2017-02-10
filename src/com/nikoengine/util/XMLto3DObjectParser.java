package com.nikoengine.util;

import com.nikoengine.geometry.GameObject3D;
import com.nikoengine.geometry.Point3D;
import com.nikoengine.geometry.Polygon3D;
import com.nikoengine.geometry.Vector3D;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for parsing XML files to 3d objects.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version 2016.1220
 * @since 1.8
 */
public class XMLto3DObjectParser {

    /**
     * Stores node name for origin point.
     */
    public static final String NODE_ORIGIN = "origin";

    /**
     * Stores node name for point map in XML file.
     */
    public static final String NODE_POINT_MAP = "point-map";

    /**
     * Stores node name for polygon map in XML file.
     */
    public static final String NODE_POLYGON_MAP = "polygon-map";

    /**
     * Objects desired scale.
     */
    private double scale = 1.0;

    /**
     * Parses a new GameObject3D from XML file.
     *
     * @param file XML file.
     * @param scale Desired scale for the object.
     * @return Three dimensional game object.
     */
    public GameObject3D newGameObject(String file, double scale) {

        this.scale = scale;
        GameObject3D object = null;
        Point3D origin;
        Point3D[] points;

        try {
            File f = new File(file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            NodeList n = doc.getChildNodes().item(0).getChildNodes();

            origin = getPoint(doc.getElementsByTagName(NODE_ORIGIN).item(0));
            points = getPoints(doc.getElementsByTagName(NODE_POINT_MAP)
                    .item(0).getChildNodes());

            createPolygons(doc.getElementsByTagName(
                    NODE_POLYGON_MAP).item(0).getChildNodes(), points);
            object = new GameObject3D(origin, new Vector3D(0, 0, 1), points);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * Returns a new point parsed from XML node.
     *
     * @param n Node to be parsed.
     * @return New 3D point.
     */
    private Point3D getPoint(Node n) {

        Element e = (Element) n;
        double x = scale * Double.parseDouble(e.getAttribute("x"));
        double y = scale * Double.parseDouble(e.getAttribute("y"));
        double z = scale * Double.parseDouble(e.getAttribute("z"));

        return new Point3D(x, y, z);
    }

    /**
     * Parses all the 3D points from node list.
     *
     * @param n NodeList to be parsed.
     * @return Array of points.
     */
    private Point3D[] getPoints(NodeList n) {
        Point3D[] points = new Point3D[n.getLength() / 2];
        int index = 0;

        for (int i = 0; i < n.getLength(); i++) {
            Node node = n.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                points[index] = getPoint(node);
                index++;
            }
        }

        return points;
    }

    /**
     * Parses polygons from the XML NodeList.
     *
     * @param n NodeList to be parsed.
     * @param points Array of points.
     */
    private void createPolygons(NodeList n, Point3D[] points) {

        for (int i = 0; i < n.getLength(); i++) {
            Node node = n.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element e = (Element) node;
                int red = Integer.parseInt(e.getAttribute("red"));
                int green = Integer.parseInt(e.getAttribute("green"));
                int blue = Integer.parseInt(e.getAttribute("blue"));

                String s = node.getChildNodes().item(0).getNodeValue();
                String[] pointsAsString = s.split(" ");
                Point3D[] tmpPoints = new Point3D[pointsAsString.length];

                for (int j = 0; j < tmpPoints.length; j++) {
                    tmpPoints[j] = points[Integer.parseInt(pointsAsString[j])];
                }

                Polygon3D polygon = new Polygon3D(tmpPoints);
                polygon.setColor(new Color(red, green, blue));
            }
        }
    }
}
