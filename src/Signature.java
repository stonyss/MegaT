import java.util.List;

/**
 * The class of a signature
 * 
 * @author sauliuss
 * 
 */
public class Signature {

	// the name of the signature
	private String sigName;

	// width of the signature
	private float signWidth;

	// height of the signature
	private float signHeight;

	// an imposition of the signature
	private List<String> impositionList = null;

	// a height of the page
	private float pageHeight;

	// a width of the page
	private float pageWidth;

	// a list of vertical gaps of the imposition
	private List<String> verticalGaps;

	// a list of horizontal gaps of the imposition
	private List<String> horizontalGaps;

	// the total count of columns
	private int columns;

	// the total count of rows
	private int rows;

	// workstyle of the imposition
	private int workStyle;

	// the list of imposition's pages
	private List<String> impositionPagesList;

	/**
	 * Constructor
	 * 
	 * @param strList
	 *            - the list of signature's strings
	 */
	public Signature(List<String> strList) {

		readName(strList);
		readWorkstyle(strList);
		readSignatureDimension(strList);
		readImposition(strList);
		readPageDimension(impositionList);
		readVerticalGaps(impositionList);
		readHorizontalGaps(impositionList);
		columns = Utility.getTagCount(verticalGaps, "%SSiPrshMatrix: 5");
		rows = Utility.getTagCount(horizontalGaps, "%SSiPrshMatrix: 5");
		readImpositionPages(impositionList);

	}

	/**
	 * Returns the name of a signature
	 * 
	 * @param signature
	 */
	private void readName(List<String> strList) {

		String line = strList.get(0);
		sigName = line.split("\\|")[1];
	}

	/**
	 * Gets width and height of the signature
	 * 
	 * @param string
	 *            list
	 */
	private void readSignatureDimension(List<String> strList) {

		String value;

		String line = Utility.getLine(strList, "%SSiPressSheet:", 1);
		if (line != null) {
			value = Utility.getValueAt(line, 1);
			signWidth = Float.valueOf(value);
			value = Utility.getValueAt(line, 2);
			signHeight = Float.valueOf(value);
		}
	}

	/**
	 * Reads all lines about an imposition
	 * 
	 * @param strList
	 *            - the list of signature's lines
	 */
	private void readImposition(List<String> strList) {

		impositionList = Utility.getSubList(strList, "%SSiPrshMatrix: 1",
				"%SSiPrshMatrix: 7");
	}

	/**
	 * Reads width and height of a page
	 * 
	 * @param strList
	 */
	private void readPageDimension(List<String> strList) {

		String value = null;

		if (strList != null) {
			String line = Utility.getLine(strList, "%SSiPrshPage:", 1);

			if (line != null) {
				value = Utility.getValueAt(line, 4);
				pageHeight = Float.valueOf(value);
				value = Utility.getValueAt(line, 3);
				pageWidth = Float.valueOf(value);
			}
		}
	}

	/**
	 * Reads vertical gaps of the imposition
	 * 
	 * @param strList
	 */
	private void readVerticalGaps(List<String> strList) {

		if (strList != null) {

			int from = 0;
			int to = 0;

			for (int i = 0; i < strList.size(); i++) {

				String line = strList.get(i);
				if (line.startsWith("%SSiPrshMatrix: 14")
						|| line.startsWith("%SSiPrshMatrix: 2")) {
					from = i;
				} else if (line.startsWith("%SSiPrshMatrix: 13")
						|| line.startsWith("%SSiPrshMatrix: 3")) {
					to = i;
				}
			}

			verticalGaps = strList.subList(from, to);

		}
	}

	/**
	 * Reads horizontal gaps of the imposition
	 * 
	 * @param strList
	 */
	private void readHorizontalGaps(List<String> strList) {

		if (strList != null) {

			int from = 0;
			int to = 0;

			for (int i = 0; i < strList.size(); i++) {

				String line = strList.get(i);
				if (line.startsWith("%SSiPrshMatrix: 13")
						|| line.startsWith("%SSiPrshMatrix: 3")) {
					from = i;
				} else if (line.startsWith("%SSiPrshPage:")) {
					to = i;
				}
			}

			horizontalGaps = strList.subList(from, to);
		}
	}

	/**
	 * Reads a work style code of the imposition
	 * 
	 * @param strList
	 */
	private void readWorkstyle(List<String> strList) {

		String line = Utility.getLine(strList, "%SSiPressSheet:", 1);
		if (line != null) {
			String value = Utility.getValueAt(line, 5);
			workStyle = Integer.valueOf(value);
		}
	}

	// graziname impozicijos puslapius (%SSiPrshPage tagus)
	private void readImpositionPages(List<String> strList) {

		if (strList != null) {

			impositionPagesList = Utility.getSubList(strList, "%SSiPrshPage:",
					"%SSiPrshMatrix: 7");
		}
	}

}
