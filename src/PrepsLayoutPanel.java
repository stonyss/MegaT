import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JSeparator;

public class PrepsLayoutPanel extends JPanel {

	static JPanel panImpos;
	JTextField marginTop;
	JTextField marginBottom;
	JTextField marginLeft;
	JTextField marginRight;
	static final String INITEXT = "17.25";

	// set initial properties of the panel
	public PrepsLayoutPanel() {

		createComponents();
		// makeImpositionGrid(2, 1);
	}

	private void createComponents() {

		// margins area
		JPanel panMargins = new JPanel(new BorderLayout());

		// imposition area
		panImpos = new JPanel(new GridBagLayout());
		panImpos.setBackground(Color.WHITE);

		marginTop = new JTextField(INITEXT, 5);
		marginTop.setHorizontalAlignment(SwingConstants.CENTER);
		marginTop.setOpaque(false);
		marginTop.setBorder(null);
		marginTop.setPreferredSize(new Dimension(50, 30));
		marginBottom = new JTextField(INITEXT, 5);
		marginBottom.setHorizontalAlignment(SwingConstants.CENTER);
		marginBottom.setOpaque(false);
		marginBottom.setBorder(null);
		marginBottom.setPreferredSize(new Dimension(50, 30));
		marginLeft = new JTextField(INITEXT, 3);
		marginLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		marginLeft.setOpaque(false);
		marginLeft.setBorder(null);
		marginRight = new JTextField(INITEXT, 3);
		marginRight.setHorizontalAlignment(SwingConstants.LEFT);
		marginRight.setOpaque(false);
		marginRight.setBorder(null);

		panMargins.add(marginTop, BorderLayout.NORTH);
		panMargins.add(marginBottom, BorderLayout.SOUTH);
		panMargins.add(marginLeft, BorderLayout.WEST);
		panMargins.add(marginRight, BorderLayout.EAST);
		panMargins.add(panImpos, BorderLayout.CENTER);
		panMargins.setPreferredSize(new Dimension(1700 / 2, 800 / 2));
		panMargins.setBorder(null);

		add(panMargins);
		setBorder(null);

	}

	// make the imposition grid
	public static void makeImpositionGrid(Signature sign) {

		int rows = sign.getRows();
		int cols = sign.getColumns();
		float width = sign.getPageWidth();
		float height = sign.getPageHeight();
		int[] rotations = sign.getPagesRotations();
		int[] numbers = sign.getPagesNumbers();

		int gridRows = rows * 2; // total count of rows in the grid
		int gridCols = cols * 2; // total count of columns in the
									// grid
		boolean alignRight = true;
		boolean alignBottom = true;
		int compIndex = 0;

		// the first page in Preps is the lover left page
		int rowIndex = 0;
		int pageIndex = 0;
		int startIndex = cols * (rows - 1);

		// the last column and row with text labels
		int lastColumn = gridCols - 1;
		int lastRow = gridRows - 1;

		// calculating of the scaling factor
		Dimension imposDim = getImposDimension(rows, cols, width, height,
				rotations[0]);
		Dimension panelDim = new Dimension(panImpos.getWidth(),
				panImpos.getHeight());
		float scaling = getScalingFactor(panelDim, imposDim);

		// removing all initial components
		panImpos.removeAll();

		for (int r = 0; r < gridRows; r++) {

			for (int c = 0; c < gridCols; c++) {

				// put horizontal separator if the row is odd
				if (r % 2 != 0 && r < lastRow) {
					JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
					GridBagConstraints constr = new GridBagConstraints();
					constr.gridx = 0;
					constr.gridy = r;
					constr.gridwidth = gridCols;
					constr.fill = GridBagConstraints.HORIZONTAL;
					panImpos.add(sep, constr);

					// put vertical separator if the column is odd
				} else if (c % 2 != 0 && c < lastColumn) {
					JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
					GridBagConstraints constr = new GridBagConstraints();
					constr.gridx = c;
					constr.gridy = 0;
					constr.gridheight = gridRows;
					constr.fill = GridBagConstraints.VERTICAL;
					panImpos.add(sep, constr);

					// if the row and the column is even, put a page label
				} else if (c % 2 == 0 && r % 2 == 0) {

					int arrayIndex = pageIndex
							+ (startIndex - (startIndex * rowIndex));
					int rotation = rotations[arrayIndex];
					String pageNumber = String.valueOf(numbers[arrayIndex]);
					pageIndex++;

					JLabel label = getPageLabel((int) width, (int) height,
							pageNumber, rotation, scaling);
					GridBagConstraints constr = new GridBagConstraints();
					constr.gridx = c;
					constr.gridy = r;
					panImpos.add(label, constr);

					// if it is the last column, put a text label
				} else if (c == lastColumn && r < lastRow && rows > 1) {

					// if there are pages in the middle, put two text labels
					if (r > 0 && r < lastRow - 2) {
						JPanel panel = new JPanel(new GridLayout(2, 1));
						panel.setBackground(Color.WHITE);
						JLabel lblTop = new JLabel(INITEXT);
						lblTop.setVerticalAlignment(SwingConstants.TOP);
						JLabel lblBottom = new JLabel(INITEXT);
						lblBottom.setVerticalAlignment(SwingConstants.BOTTOM);
						panel.add(lblTop);
						panel.add(lblBottom);
						GridBagConstraints constr = new GridBagConstraints();
						constr.gridx = lastColumn;
						constr.gridy = r;
						constr.fill = GridBagConstraints.VERTICAL;
						panImpos.add(panel, constr);

					} else {

						GridBagConstraints constr = new GridBagConstraints();
						constr.gridx = lastColumn;
						constr.gridy = r;
						if (alignBottom) {
							constr.anchor = GridBagConstraints.LAST_LINE_START;
							alignBottom = !alignBottom;
						} else {
							constr.anchor = GridBagConstraints.FIRST_LINE_START;
							alignBottom = !alignBottom;
						}

						panImpos.add(new JLabel(INITEXT), constr);
						compIndex++;
					}

					// put a text label if it's the last row
				} else if (r == lastRow && c < lastColumn && cols > 1) {

					// if there are pages in the middle, put two text labels
					if (c > 0 && c < lastColumn - 2) {
						JPanel panel = new JPanel(new GridLayout(1, 2));
						panel.setBackground(Color.WHITE);
						JLabel lblLeft = new JLabel(INITEXT);
						lblLeft.setHorizontalAlignment(SwingConstants.LEFT);
						JLabel lblRight = new JLabel(INITEXT);
						lblRight.setHorizontalAlignment(SwingConstants.RIGHT);
						panel.add(lblLeft);
						panel.add(lblRight);
						GridBagConstraints constr = new GridBagConstraints();
						constr.gridx = c;
						constr.gridy = lastRow;
						constr.fill = GridBagConstraints.HORIZONTAL;
						panImpos.add(panel, constr);

					} else {

						GridBagConstraints constr = new GridBagConstraints();
						constr.gridx = c;
						constr.gridy = lastRow;
						if (alignRight) {
							constr.anchor = GridBagConstraints.FIRST_LINE_END;
							alignRight = !alignRight;
						} else {
							constr.anchor = GridBagConstraints.FIRST_LINE_START;
							alignRight = !alignRight;
						}
						panImpos.add(new JLabel(INITEXT), constr);
						compIndex++;
					}
				}
			}
			rowIndex++;
		}

		panImpos.revalidate();
		panImpos.repaint();

	}

	private static ImageIcon getPageIcon() {
		int imgWidth = 45; // page icon width
		int imgHeight = 45; // page icon height
		ImageIcon lblIcon;

		// page icon color
		Color colorBcgr = Color.BLACK;

		BufferedImage imgPage = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = imgPage.createGraphics();

		// setting a color to the background
		g2.setColor(colorBcgr);
		g2.fillRect(0, 0, imgWidth, imgHeight);

		lblIcon = new ImageIcon(imgPage);

		return lblIcon;

	}

	private static JLabel getPageLabel(int width, int height, String pgNumber,
			int rotate, float scaling) {

		JLabel label;

		int imgWidth = (int) (width * scaling); // page icon width
		int imgHeight = (int) (height * scaling); // page icon height
		
		// if pages are rotated by 90 or -90 degrees, reverse width and height
		if (rotate == 90 || rotate == 270) {
			
			int w = imgWidth;
			imgWidth = imgHeight;
			imgHeight = w;
		}

		ImageIcon lblIcon;

		// page icon color
		Color colorBcgr = new Color(248, 248, 221);

		// page icon frame color
		Color colorFrame = new Color(102, 153, 102);

		BufferedImage imgPage = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = imgPage.createGraphics();

		// setting a color to the background
		g2.setColor(colorBcgr);
		g2.fillRect(0, 0, imgWidth, imgHeight);

		// draw the page number
		Font fnt = new Font("SansSerif", Font.BOLD, (int) (250 * scaling));
		int bline = fnt.getBaselineFor('J');
		FontRenderContext frc = new FontRenderContext(null, false, false);
		Rectangle2D strRec = fnt.getStringBounds(String.valueOf(pgNumber), frc);
		g2.setFont(fnt);
		g2.setColor(Color.BLACK);
		g2.drawString(pgNumber, (int) (imgWidth - strRec.getWidth()) / 2,
				(int) ((imgHeight - bline) + 40) / 2);

		// applying transformation to the image (rotation)
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(rotate), imgWidth / 2, imgHeight / 2);
		g2.setTransform(tx);

		// drawing the page border
		int xFlip = (int) (imgWidth * 0.15);
		int yFlip = (int) (imgHeight * 0.15);
		int flipCorner = Math.min(xFlip, yFlip);
		int borderWidth = imgWidth - 2;
		int borderHeight = imgHeight - 2;
		int[] xPointsBorder = { 2, borderWidth - flipCorner, borderWidth,
				borderWidth, 2 };
		int[] yPointsBorder = { 2, 2, flipCorner, borderHeight, borderHeight };
		g2.setColor(colorFrame);
		g2.drawPolygon(xPointsBorder, yPointsBorder, 5);

		// draw the head mark of the page
		g2.setColor(colorFrame);
		g2.fillRect(flipCorner, flipCorner / 2, borderWidth - flipCorner * 3,
				flipCorner / 2);

		// drawing of page turn triangle
		int[] xPointsTriangle = { borderWidth - flipCorner,
				borderWidth - flipCorner, borderWidth };
		int[] yPointsTriangle = { 2, flipCorner, flipCorner };
		g2.drawPolygon(xPointsTriangle, yPointsTriangle, 3);

		lblIcon = new ImageIcon(imgPage);

		label = new JLabel(lblIcon);
		label.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		return label;
	}

	// paskaiciuojame visos impozicijos ploti ir auksti
	private static Dimension getImposDimension(int rows, int cols, float width,
			float height, int rotation) {

		// if pages are rotated by 90 or -90 degrees, reverse width and height
		if (rotation == 90 || rotation == 270) {

			float w = width;
			width = height;
			height = w;
		}

		int verticalSeparatorsCount = cols - 1;
		int horizontalSeparatorsCount = rows - 1;
		// Rectangle rv = new Rectangle();
		float imposWidth = 0;
		float imposHeight = 0;
		int txfCountHorz = cols + 1;
		int txfCountVert = rows + 1;

		// testiniai komponentai ju dydziu paskaiciavimui
		// skaiciuojame text fieldu plocius
		for (int i = 0; i < txfCountHorz; i++) {

			imposWidth += 100;
		}

		// skaiciuojame text fieldu aukscius
		for (int i = 0; i < txfCountVert; i++) {

			imposHeight += 50;
		}

		// skaiciuojame separatoriu plocius
		for (int i = 0; i < verticalSeparatorsCount; i++) {

			// imposWidth += rv.width;
			imposWidth += 20;
		}

		for (int i = 0; i < horizontalSeparatorsCount; i++) {

			// imposHeight += rv.height;
			imposHeight += 20;
		}

		// pridedame puslapio ismatavimus
		for (int i = 0; i < cols; i++) {

			imposWidth += width;
		}

		for (int i = 0; i < rows; i++) {

			imposHeight += height;
		}

		return new Dimension((int) imposWidth, (int) imposHeight);
	}

	private static float getScalingFactor(Dimension destination,
			Dimension source) {

		float scale;
		float scaleX, scaleY;

		scaleX = (float) (destination.getWidth() / source.getWidth());
		scaleY = (float) (destination.getHeight() / source.getHeight());
		scale = Math.min(scaleX, scaleY);

		return scale;
	}
}
