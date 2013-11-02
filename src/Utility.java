import java.util.List;

/**
 * @author sauliuss The utility class for common jobs such as measurement
 *         conversion
 */
public class Utility {

	// Measurement conversion from millimeters to points.
	public static final float millimetersToPoints(final float value) {
		return inchesToPoints(millimetersToInches(value));
	}

	// Measurement conversion from millimeters to inches.
	public static final float millimetersToInches(final float value) {
		return value / 25.4f;
	}

	// Measurement conversion from points to millimeters.
	public static final float pointsToMillimeters(final float value) {
		return inchesToMillimeters(pointsToInches(value));
	}

	// Measurement conversion from points to inches.
	public static final float pointsToInches(final float value) {
		return value / 72f;
	}

	// Measurement conversion from inches to millimeters.
	public static final float inchesToMillimeters(final float value) {
		return value * 25.4f;
	}

	// Measurement conversion from inches to points.
	public static final float inchesToPoints(final float value) {
		return value * 72f;
	}

	/**
	 * Looks for the line with a given tag
	 * 
	 * @param source
	 * @param tag
	 * @param n
	 *            - number of occurrence
	 * @return - string
	 */
	public static String getLine(List<String> strList, String tag, int n) {

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
	 * Returns an index of the line which begins with a given tag
	 * 
	 * @param strList
	 * @param tag
	 * @param n
	 *            - number of occurrence
	 * @return - integer index
	 */
	public static int getLineIndex(List<String> strList, String tag, int n) {

		int occur = 0;
		for (int i = 0; i < strList.size(); i++) {

			String line = strList.get(i);

			if (line.startsWith(tag)) {
				occur++;
				if (occur == n) {
					return i;
				}
			}
		}
		return -1;

	}

	/**
	 * Returns the value at the given position (index) of a string
	 * 
	 * @param str
	 *            - string
	 * @param index
	 *            - a position in the string
	 * @return - value as string
	 */
	public static String getValueAt(String str, int index) {

		String[] strList = str.split(" ");
		return strList[index];
	}

	/**
	 * Returns a sublist between two tags
	 * 
	 * @param srtList
	 *            - a list of strings
	 * @param fromTag
	 *            - a staring tag
	 * @param toTag
	 *            - an ending tag
	 * @return - sublist
	 */
	public static List<String> getSubList(List<String> strList, String fromTag,
			String toTag, boolean incl) {

		int from = getLineIndex(strList, fromTag, 0);
		int to = getLineIndex(strList, toTag, 0);

		if (from != -1 && to != -1) {

			// if 'to' tag is the last return all remaining lines
			if (to == strList.size() - 1) {

				return strList.subList(from, strList.size());

			} else {

				// if inclusively
				if (incl) {

					return strList.subList(from, to + 1);

					// else if exclusively
				} else {

					return strList.subList(from, to);
				}
			}

		} else {

			return null;
		}
	}

	/**
	 * Returns the total count of lines with a given tag
	 * 
	 * @param strList
	 * @param tag
	 * @return - the total count as integer
	 */
	public static int getTagCount(List<String> strList, String tag) {

		int i = 0;
		for (String line : strList) {

			if (line.startsWith(tag)) {
				++i;
			}
		}
		return i;
	}
	
	// ieskome paskutini tago indeksa
	private int getLastIndex(List<String> source, int from, String tag) {

		String line = "";
		String nextLine = "";

		for (int i = from; i < source.size(); i++) {

			line = source.get(i);

			if (i + 1 < source.size()) {
				nextLine = source.get(i + 1);
			}

			if (line.startsWith(tag)) {
				if (!nextLine.startsWith(tag)) {
					return i;
				}
			}
		}
		return -1;
	}

}
