
package DesplieguedeimagenesSVG;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DespliegueSVG extends JComponent {

    private Document doc;
    private Element root;
    private int svgW;
    private int svgH;

    private Properties webColors;

    public DespliegueSVG(Document svgDoc) {
        super();

        doc = svgDoc;

        root = doc.getDocumentElement();


        svgW = Integer.parseInt(root.getAttribute("width"));
        svgH = Integer.parseInt(root.getAttribute("height"));


        this.setBackground(Color.white);

        loadColors();

    }

    private void loadColors() {
        try {

            String userDir = System.getProperty("user.dir");
            FileReader reader = new FileReader(userDir + "/colors.properties");
            
            webColors = new Properties();
            webColors.load(reader);
            

            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(APISVG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(APISVG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {


        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        NodeList list = null;


        list = root.getChildNodes();

        int n = list.getLength();
        Element element = null;
        for (int i = 0; i < n; i++) {
            Node nodo = list.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) nodo;


                String name = element.getTagName();

                if (name.equals("line")) {

                    drawLine(element, g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(svgW, svgH);
    }

    private void drawLine(Element line, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        System.out.println(line.getAttribute("id"));

        if (line.hasAttribute("stroke-width")) {

            float sw = Float.parseFloat(line.getAttribute("stroke-width"));
            g2d.setStroke(new BasicStroke(sw));
        } else {

            g2d.setStroke(new BasicStroke(1));
        }

        if (line.hasAttribute("stroke")) {

            String colorCode = line.getAttribute("stroke");

            g2d.setColor( webColor(colorCode, 1) );

        } else {

            g2d.setColor(Color.BLACK);
        }


        int x1 = (int) Double.parseDouble(line.getAttribute("x1"));
        int y1 = (int) Double.parseDouble(line.getAttribute("y1"));

        int x2 = (int) Double.parseDouble(line.getAttribute("x2"));
        int y2 = (int) Double.parseDouble(line.getAttribute("y2"));


        g2d.draw(new Line2D.Double(x1, y1, x2, y2));

    }

    private Color webColor(String colorString, float opacity) {
        String colorCode = colorString.toLowerCase();
        Color newColor = null;



        if (colorCode.startsWith("#")) {

            colorCode = colorCode.substring(1);
        } else if (colorCode.startsWith("0x")) {
            colorCode = colorCode.substring(2);
        } else if (colorCode.startsWith("rgb")) {

            if (colorCode.startsWith("(", 3)) {
                return Color.BLACK;
            } else if (colorCode.startsWith("a(", 3)) {
                return Color.BLACK;
            }
        } else {


            colorCode = webColors.getProperty(colorCode).substring(1).trim();


        }

        try {
            int r;
            int g;
            int b;
            int a;
            int len = colorCode.length();

            if (len == 6) {
                System.out.println(colorCode);
                r = Integer.parseInt(colorCode.substring(0, 2), 16);
                g = Integer.parseInt(colorCode.substring(2, 4), 16);
                b = Integer.parseInt(colorCode.substring(4, 6), 16);

                newColor = new Color(r, g, b);
            }

        } catch (NumberFormatException nfe) {
            Logger.getLogger(DespliegueSVG.class.getName()).log(Level.SEVERE, null, nfe);
        }

        return newColor;
    }

    private Color getWebColor(String colorString, float opacity) {
        String colorCode = colorString.toLowerCase();
        Color newColor = null;

        if (colorCode.startsWith("#")) {

            colorCode = colorCode.substring(1);
        } else if (colorCode.startsWith("0x")) {
            colorCode = colorCode.substring(2);
        } else if (colorCode.startsWith("rgb")) {

            if (colorCode.startsWith("(", 3)) {
                return Color.BLACK;
            } else if (colorCode.startsWith("a(", 3)) {
                return Color.BLACK;
            }
        } else {

            Color namedColor = Color.getColor(colorString);

            colorString = System.getProperty(colorCode);
            System.out.println(colorString);


            if (namedColor != null) {
                if (opacity == 1.0) {
                    newColor = namedColor;
                } else {
                    newColor = new Color(
                            namedColor.getRed(),
                            namedColor.getGreen(),
                            namedColor.getBlue(),
                            opacity);
                }
            } else {
                System.out.println("Invalid color name.");
                newColor = Color.BLACK;
            }
        }

        try {
            int r;
            int g;
            int b;
            int a;
            int len = colorCode.length();

            if (len == 6) {

                r = Integer.parseInt(colorCode.substring(0, 2), 16);
                g = Integer.parseInt(colorCode.substring(2, 4), 16);
                b = Integer.parseInt(colorCode.substring(4, 6), 16);

                newColor = new Color(r, g, b);
            }

        } catch (NumberFormatException nfe) {
            Logger.getLogger(DespliegueSVG.class.getName()).log(Level.SEVERE, null, nfe);
        }

        return newColor;
    }

}
