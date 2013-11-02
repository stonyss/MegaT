import java.util.List;

/**
 * The class of a signature
 * 
 * @author sauliuss
 * 
 */
public class Signature {
	
	
	// the list of all lines in a signature
	private List<String> signatureList;

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

	// the array of pages rotations
	private int[] pagesRotations;
	
	// the array of pages numbers
	private int[] pagesNumbers;

	/**
	 * Constructor
	 * 
	 * @param strList
	 *            - the list of signature's strings
	 */
	public Signature(List<String> strList) {

		signatureList= strList;
		readName(signatureList);
		readWorkstyle(signatureList);
		readSignatureDimension(signatureList);
		readImposition(signatureList);
		readPageDimension(impositionList);
		readVerticalGaps(impositionList);
		readHorizontalGaps(impositionList);
		columns = Utility.getTagCount(verticalGaps, "%SSiPrshMatrix: 5");
		rows = Utility.getTagCount(horizontalGaps, "%SSiPrshMatrix: 5");
		readImpositionPages(impositionList);
		readRotations(impositionPagesList);
		readPagesNumbers(impositionPagesList);

	}

	/**
	 * Reads the name of a signature
	 * 
	 * @param signature
	 */
	private void readName(List<String> strList) {

		String line = strList.get(0);
		sigName = line.split("\\|")[1];
	}

	/**
	 * @return the sigName
	 */
	public String getSigName() {
		return sigName;
	}

	/**
	 * @return the signWidth
	 */
	public float getSignWidth() {
		return signWidth;
	}

	/**
	 * @return the signHeight
	 */
	public float getSignHeight() {
		return signHeight;
	}

	/**
	 * @return the impositionList
	 */
	public List<String> getImpositionList() {
		return impositionList;
	}

	/**
	 * @return the pageHeight
	 */
	public float getPageHeight() {
		return pageHeight;
	}

	/**
	 * @return the pageWidth
	 */
	public float getPageWidth() {
		return pageWidth;
	}

	/**
	 * @return the verticalGaps
	 */
	public List<String> getVerticalGaps() {
		return verticalGaps;
	}

	/**
	 * @return the horizontalGaps
	 */
	public List<String> getHorizontalGaps() {
		return horizontalGaps;
	}

	/**
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return the workStyle
	 */
	public int getWorkStyle() {
		return workStyle;
	}

	/**
	 * @return the impositionPagesList
	 */
	public List<String> getImpositionPagesList() {
		return impositionPagesList;
	}

	/**
	 * @return the pagesRotations
	 */
	public int[] getPagesRotations() {
		return pagesRotations;
	}

	/**
	 * @return the pagesNumbers
	 */
	public int[] getPagesNumbers() {
		return pagesNumbers;
	}
	
	/**
	 * Returns the first line of signature for identification
	 * @param strList
	 * @return
	 */
	public String getFirstLine() {
		
		return signatureList.get(0);
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
				"%SSiPrshMatrix: 7", true);
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

	/**
	 * Reads all lines with %SSiPrshPage tag in the imposition
	 * 
	 * @param strList
	 */
	private void readImpositionPages(List<String> strList) {

		if (strList != null) {

			impositionPagesList = Utility.getSubList(strList, "%SSiPrshPage:",
					"%SSiPrshMatrix: 7", false);
		}
	}

	/**
	 * Reads orientations of pages and puts them into array
	 * @param strList
	 */
	private void readRotations(List<String> strList) {

		String page;
		int orientation;
		if (strList != null) {

			pagesRotations = new int[strList.size()];
			for (int i = 0; i < strList.size(); i++) {

				page = strList.get(i);
				orientation = Integer.valueOf(Utility.getValueAt(page, 5));

				switch (orientation) {

				case 3:
					pagesRotations[i] = 270;
					break;
				case 4:
					pagesRotations[i] = 180;
					break;
				case 5:
					pagesRotations[i] = 90;
					break;
				case 6:
					pagesRotations[i] = 0;
					break;
				default:
					pagesRotations[i] = 0;
					break;
				}
			}

		}
	}
	
	/**
	 * Reads page number and puts it into array
	 * @param strList
	 */
	private void readPagesNumbers(List<String> strList) {

		String page;
		int number;
		if (strList != null) {

			pagesNumbers = new int[strList.size()];
			for (int i = 0; i < strList.size(); i++) {

				page = strList.get(i);
				number = Integer.valueOf(Utility.getValueAt(page, 6));
				pagesNumbers[i] = number;
			}
		}
	}

}
