import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Insets;
import java.awt.color.CMMException;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class StaticContentPane extends JPanel implements MegaTConstants {

    private JTextField txf_jobNumber;
    private JTextField txf_page1Width;
    private JTextField txf_page2Width;
    private JTextField txf_page3Width;
    private JTextField txf_page4Width;
    private JTextField txf_pageHeight;
    private JTextField txf_pressSheet;
    private JTextField txf_head;
    private JTextField txf_foot;
    private JTextField txf_right;
    private JComboBox cbx_WS;
    private Border border;
    private PrepsLayoutPanel prepsLayout;

    final int TOP = 3;
    final int TOP_TOP = 20;
    final int BOTTOM = 3;
    final int BOTTOM_BOTTOM = 20;
    final int RIGHT = 0;
    final int RIGHT_RIGHT = 20;
    final int LEFT = 5;
    final int LEFT_LEFT = 20;

    /**
     * Create the panel.
     */
    public StaticContentPane() {

	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[] { 5, 5, 5, 5, 5 };
	gridBagLayout.rowHeights = new int[] { 5, 5, 5, 5 };
	gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0 };
	gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0 };
	setLayout(gridBagLayout);
	border = new CustomBorder(1);

	// Job info fields area
	JPanel paneJobInfo = new JPanel(new GridLayout(2, 2, 10, 10));
	JLabel lblJobNumber = new JLabel("Job number:", SwingConstants.RIGHT);
	txf_jobNumber = new JTextField();
	JLabel lblTemplName = new JLabel("Template name:", SwingConstants.RIGHT);
	JComboBox cbx_templName = new JComboBox(new String[] { "Virselis", "Aplankas", "Lakas" });
	paneJobInfo.add(lblJobNumber);
	paneJobInfo.add(txf_jobNumber);
	paneJobInfo.add(lblTemplName);
	paneJobInfo.add(cbx_templName);
	// paneJobInfo.setBorder(BorderFactory.createLoweredBevelBorder());
	paneJobInfo.setBorder(border);
	GridBagConstraints gbc_paneJobInfo = new GridBagConstraints();
	gbc_paneJobInfo.insets = new Insets(TOP_TOP, LEFT_LEFT, BOTTOM, RIGHT);
	gbc_paneJobInfo.anchor = GridBagConstraints.FIRST_LINE_START;
	gbc_paneJobInfo.gridx = 0;
	gbc_paneJobInfo.gridy = 0;
	add(paneJobInfo, gbc_paneJobInfo);

	// Widths area
	TitledBorder titledBorder = new TitledBorder(border, " Pages widths ");
	JPanel pageWidthPanel = new JPanel(new GridLayout(2, 4, 10, 10));
	pageWidthPanel.setBorder(titledBorder);

	JLabel lblWidth1 = new JLabel("Page 1:", SwingConstants.RIGHT);
	pageWidthPanel.add(lblWidth1);

	txf_page1Width = new JTextField();
	txf_page1Width.setColumns(5);
	pageWidthPanel.add(txf_page1Width);

	JLabel lblWidth2 = new JLabel("Page 2:", SwingConstants.RIGHT);
	pageWidthPanel.add(lblWidth2);

	txf_page2Width = new JTextField();
	txf_page2Width.setColumns(5);
	pageWidthPanel.add(txf_page2Width);

	JLabel lblWidth3 = new JLabel("Page 3:", SwingConstants.RIGHT);
	pageWidthPanel.add(lblWidth3);

	txf_page3Width = new JTextField();
	txf_page3Width.setColumns(5);
	pageWidthPanel.add(txf_page3Width);

	JLabel lblWidth4 = new JLabel("Page 4:", SwingConstants.RIGHT);
	pageWidthPanel.add(lblWidth4);

	txf_page4Width = new JTextField();
	txf_page4Width.setColumns(5);
	pageWidthPanel.add(txf_page4Width);

	GridBagConstraints gbc_pageWidthPanel = new GridBagConstraints();
	gbc_pageWidthPanel.insets = new Insets(TOP, LEFT_LEFT, BOTTOM, RIGHT);
	gbc_pageWidthPanel.anchor = GridBagConstraints.FIRST_LINE_START;
	gbc_pageWidthPanel.gridx = 0;
	gbc_pageWidthPanel.gridy = 1;
	add(pageWidthPanel, gbc_pageWidthPanel);

	// Template info area
	JPanel paneTemplInfo = new JPanel(new GridLayout(3, 2, 5, 10));
	paneTemplInfo.setBorder(border);
	JLabel lblPageHeight = new JLabel("Page height:", SwingConstants.RIGHT);
	txf_pageHeight = new JTextField(4);
	JLabel lblPressSheet = new JLabel("Press sheet size:", SwingConstants.RIGHT);
	txf_pressSheet = new JTextField(4);
	JLabel lblWS = new JLabel("Work style:", SwingConstants.RIGHT);
	cbx_WS = new JComboBox(new String[] { "Sheetwise", "Work and Turn", "Work and Tumble", "Single-Sided",
		"Perfector" });
	cbx_WS.setPreferredSize(new Dimension(135, 20));
	cbx_WS.setMaximumSize(new Dimension(135, 20));

	JLabel lblHead = new JLabel("Head:", SwingConstants.RIGHT);
	txf_head = new JTextField(4);
	JLabel lblFoot = new JLabel("Foot:", SwingConstants.RIGHT);
	txf_foot = new JTextField(4);
	JLabel lblRight = new JLabel("Right:", SwingConstants.RIGHT);
	txf_right = new JTextField(4);
	paneTemplInfo.add(lblPageHeight);
	paneTemplInfo.add(txf_pageHeight);
	paneTemplInfo.add(lblHead);
	paneTemplInfo.add(txf_head);
	paneTemplInfo.add(lblPressSheet);
	paneTemplInfo.add(txf_pressSheet);
	paneTemplInfo.add(lblFoot);
	paneTemplInfo.add(txf_foot);
	paneTemplInfo.add(lblWS);
	paneTemplInfo.add(cbx_WS);
	paneTemplInfo.add(lblRight);
	paneTemplInfo.add(txf_right);
	GridBagConstraints gbc_paneTemplInfo = new GridBagConstraints();
	gbc_paneTemplInfo.insets = new Insets(TOP_TOP, LEFT, BOTTOM, RIGHT);
	gbc_paneTemplInfo.anchor = GridBagConstraints.FIRST_LINE_START;
	gbc_paneTemplInfo.gridx = 1;
	gbc_paneTemplInfo.gridy = 0;
	gbc_paneTemplInfo.gridheight = GridBagConstraints.REMAINDER;
	add(paneTemplInfo, gbc_paneTemplInfo);

	// Templates tree area
	JPanel paneTemplTree = new JPanel(new GridLayout(1,1, 10, 10));
	//TitledBorder tBorder = new TitledBorder(border, " Templates ");
	//paneTemplTree.setBorder(tBorder);
	TemplatesTree tree = new TemplatesTree(this);
	paneTemplTree.add(tree);
	GridBagConstraints gbc_paneTemplTree = new GridBagConstraints();
	gbc_paneTemplTree.insets = new Insets(TOP_TOP, LEFT, BOTTOM_BOTTOM, RIGHT_RIGHT);
	gbc_paneTemplTree.anchor = GridBagConstraints.FIRST_LINE_START;
	gbc_paneTemplTree.gridwidth = GridBagConstraints.REMAINDER;
	gbc_paneTemplTree.gridheight = GridBagConstraints.REMAINDER;
	gbc_paneTemplTree.fill = GridBagConstraints.BOTH;
	gbc_paneTemplTree.gridx = 2;
	gbc_paneTemplTree.gridy = 0;
	add(paneTemplTree, gbc_paneTemplTree);

	// Preps layout area
	JPanel paneTemplLayout = new JPanel(new GridLayout(1,1));
	TitledBorder tBorder2 = new TitledBorder(border, " Imposition ");
	paneTemplLayout.setBorder(tBorder2);
	
	prepsLayout = new PrepsLayoutPanel();
	prepsLayout.setBackground(Color.WHITE);
	paneTemplLayout.add(prepsLayout);
	GridBagConstraints gbc_paneTemplLayout = new GridBagConstraints();
	gbc_paneTemplLayout.insets = new Insets(TOP, LEFT_LEFT, BOTTOM_BOTTOM, RIGHT);
	gbc_paneTemplLayout.anchor = GridBagConstraints.FIRST_LINE_START;
	gbc_paneTemplLayout.gridwidth = 2;
	gbc_paneTemplLayout.gridheight = GridBagConstraints.REMAINDER;
	gbc_paneTemplLayout.fill = GridBagConstraints.BOTH;
	gbc_paneTemplLayout.gridx = 0;
	gbc_paneTemplLayout.gridy = 2;
	add(paneTemplLayout, gbc_paneTemplLayout);
	//paneTemplLayout.setPreferredSize(new Dimension(1280/2, 800/2));
    }

    public PrepsLayoutPanel getPrepLayoutPanel() {

	return prepsLayout;
    }

    public void setWorkstyle(int code) {

	cbx_WS.setSelectedIndex(code);
    }

    public void setPressSheetSize(float width, float height) {

	String w = convertToString(width);
	String h = convertToString(height);
	String fieldValue;

	if (HEIGHT_FIRST.equals("true")) {

	    fieldValue = h + "x" + w;
	} else {

	    fieldValue = w + "x" + h;
	}

	txf_pressSheet.setText(fieldValue);
    }

    public void setPageHeight(float value) {

	String fieldValue = convertToString(value);
	txf_pageHeight.setText(fieldValue);
    }

    // konvertuoja is float i String atsizvelgiant i matavimo sistema
    private static String convertToString(float value) {

	if (MEASURMENT.equals("mm")) {

	    value = PrepsTemplate.pointsToMillimeters(value);

	} else if (MEASURMENT.equals("in")) {

	    value = PrepsTemplate.pointsToInches(value);

	} else if (MEASURMENT.equals("cm")) {

	    value = PrepsTemplate.pointsToMillimeters(value) / 10;
	}

	float f = value % 1;

	if ((f > 0.99) || (f < 0.01)) {

	    return String.format("%d", Math.round(value));
	} else {

	    return String.format("%.2f", value);
	}
    }

    // teh custom border
    private class CustomBorder extends SoftBevelBorder {

	public CustomBorder(int bevelType) {
	    super(bevelType);
	}

	public Insets getBorderInsets(Component c) {

	    return new Insets(5, 10, 10, 10);
	}

	public Insets getBorderInsets(Component c, Insets insets) {

	    return insets;
	}

    }

}
