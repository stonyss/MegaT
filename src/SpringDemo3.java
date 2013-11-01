import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;

public class SpringDemo3 {
    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
	// Create and set up the window.
	JFrame frame = new JFrame("SpringDemo3");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Set up the content pane.
	Container contentPane = frame.getContentPane();
	SpringLayout layout = new SpringLayout();
	contentPane.setLayout(layout);

	contentPane.setBackground(Color.WHITE);

	// Create and add the components.
	for (int i = 0; i < 8; i++) {

	    JLabel label = new JLabel(getPageImage(i, 180));
	    label.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
	    contentPane.add(label);

	}

	SpringUtilities.makeCompactGrid(contentPane, // parent
		2, 4, 3, 3, // initX, initY
		3, 3); // xPad, yPad

	// JTextField textField = new JTextField("Text field", 15);

	// contentPane.add(textField);

	// Display the window.
	frame.pack();
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	// Schedule a job for the event-dispatching thread:
	// creating and showing this application's GUI.
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		createAndShowGUI();
	    }
	});
    }

    private static ImageIcon getPageImage(int pgNumber, int rotate) {

	int imgWidth = 200; // page icon width
	int imgHeight = 200; // page icon height
	ImageIcon lblIcon;

	// page icon color
	Color colorBcgr = new Color(248, 248, 221);

	// page icon frame color
	Color colorFrame = new Color(102, 153, 102);

	BufferedImage imgPage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = imgPage.createGraphics();

	// setting a color to the background
	g2.setColor(colorBcgr);
	g2.fillRect(0, 0, imgWidth, imgHeight);

	// draw the page number
	Font fnt = new Font("SansSerif", Font.BOLD, 60);
	int bline = fnt.getBaselineFor('J');
	FontRenderContext frc = new FontRenderContext(null, false, false);
	Rectangle2D strRec = fnt.getStringBounds(String.valueOf(pgNumber), frc);
	g2.setFont(fnt);
	g2.setColor(Color.BLACK);
	g2.drawString(String.valueOf(pgNumber), (int) (imgWidth - strRec.getWidth()) / 2,
		(int) ((imgHeight - bline) + 40) / 2);

	// applying transformation of the image (rotation)
	AffineTransform tx = new AffineTransform();
	tx.rotate(Math.toRadians(rotate), imgWidth / 2, imgHeight / 2);
	g2.setTransform(tx);

	// drawing the page border
	int[] xPointsBorder = { 2, 170, 198, 198, 2 };
	int[] yPointsBorder = { 2, 2, 30, 198, 198 };
	g2.setColor(colorFrame);
	g2.drawPolygon(xPointsBorder, yPointsBorder, 5);

	// drawing of the head mark of a page
	g2.setColor(colorFrame);
	g2.fillRect((imgWidth - 140) / 2, 10, 120, 10);

	// drawing of page turn triangle
	int[] xPointsTriangle = { 170, 170, 198 };
	int[] yPointsTriangle = { 2, 30, 30 };
	g2.drawPolygon(xPointsTriangle, yPointsTriangle, 3);

	lblIcon = new ImageIcon(imgPage);

	return lblIcon;
    }
}