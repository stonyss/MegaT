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

	/**
	 * Constructor
	 * 
	 * @param strList - the list of signature's strings
	 */
	public Signature(List<String> strList) {

		getName(strList);
		getSignatureDimension(strList);

	}

	/**
	 * Returns the name of signature
	 * 
	 * @param signature
	 * @return the name of signature
	 */
	private void getName(List<String> strList) {

		String line = strList.get(0);
		sigName = line.split("\\|")[1];
	}

	/**
	 * Looks for the line with a given tag
	 * 
	 * @param source
	 * @param tag
	 * @param n
	 *            - number of occurrence
	 * @return
	 */
	private String getLineAt(List<String> strList, String tag, int n) {

		int occur = 0;
		for (int i = 0; i < strList.size(); i++) {

			String line = strList.get(i);

			if (line.startsWith(tag)) {
				occur++;
				if (occur == n) {
					return line;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the value at the given position (index) of the string
	 * 
	 * @param str
	 * @param index
	 * @return
	 */
	private String getValueAt(String str, int index) {

		String[] strList = str.split(" ");
		return strList[index];
	}

	/**
	 * Gets width and height of the signature
	 * 
	 * @param string
	 *            list
	 * @return
	 */
	private void getSignatureDimension(List<String> strList) {

		String value;

		String line = getLineAt(strList, "%SSiPressSheet:", 1);
		if (line != null) {
			value = getValueAt(line, 1);
			signWidth = Float.valueOf(value);
			value = getValueAt(line, 2);
			signHeight = Float.valueOf(value);
		}
	}

}
