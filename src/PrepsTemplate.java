import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author sauliuss The class maintains a Preps template file
 */
public class PrepsTemplate {

	// Preps template strings array
	// List<String> templArray;

	// masyvas su pirmutinemis sablono eilutemis
	List<String> header = new ArrayList<String>();

	// The list of signatures
	List<Signature> signaturesList = new ArrayList<Signature>();

	/**
	 * The constructor takes a file oject
	 * 
	 * @param file
	 */
	public PrepsTemplate(File file) {

		List<String> strArray = readFile(file);
		getSignatures(strArray);
	}

	/**
	 * The method reads a template file into the list of strings
	 * 
	 * @param file
	 */
	private List<String> readFile(File file) {

		FileInputStream inFile = null;
		BufferedReader reader = null;
		List<String> strList = null;

		try {

			inFile = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inFile, "UTF8"));
			String line;
			strList = new ArrayList<String>();

			while ((line = reader.readLine()) != null) {

				strList.add(line);
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (inFile != null) {
				try {
					reader.close();
					inFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return strList;
	}

	/**
	 * The method creates the list of strings for each signature found the list
	 * of template
	 * 
	 * @param source
	 */
	private void getSignatures(List<String> source) {

		int signStart = 0;
		List<String> strList = new ArrayList<String>();

		signStart = getLineIndex(source, "%SSiSignature:", 0);
		if (signStart != -1) {
			// looking for a next signature
			int nextSignStart = getLineIndex(source, "%SSiSignature:",
					signStart + 1);
			// if the next signature has found
			if (nextSignStart != -1) {

				Signature sign = new Signature(source.subList(signStart,
						nextSignStart));
				signaturesList.add(sign);
				getSignatures(source.subList(nextSignStart, source.size()));

			} else {

				Signature sign = new Signature(source.subList(signStart,
						source.size()));
				signaturesList.add(sign);
			}
		}
	}

	/**
	 * The method looks for the line which begins with a given tag and returns
	 * an index of the line
	 * 
	 * @param source
	 *            - list of strings
	 * @param tag
	 *            which starts the line
	 * @param from
	 *            which line the searching begins
	 * @return index of the found line
	 */
	private int getLineIndex(List<String> source, String tag, int from) {

		for (int i = from; i < source.size(); i++) {

			String line = source.get(i);

			if (line.startsWith(tag)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns signature by an index
	 * @param index
	 * @return signature
	 */
	public Signature getSignature(int index) {

		return signaturesList.get(index);
	}

	/**
	 * Returns the list of signatures
	 * @return
	 */
	public List<Signature> getSignaturesList() {

		return signaturesList;

	}

	/**
	 * Returns the count of all signatures
	 * @return
	 */
	public int getSignaturesCount() {

		return signaturesList.size();
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

	// graziname uzduoto tago pasikartojimo skaiciu masyve
	private static int getTagCount(List<String> source, String tag) {

		int i = 0;
		for (String line : source) {

			if (line.startsWith(tag)) {
				++i;
			}
		}
		return i;
	}

	// graziname masyva tarp dvieju tagu
	private static List<String> getSubList(List<String> source, String fromTag,
			String toTag) {

		int from = getLineIndexAt(source, fromTag, 0);
		int to = getLineIndexAt(source, toTag, 0);

		if (from != -1 && to != -1) {

			return source.subList(from, to);
		} else {

			return null;
		}

	}

	// graziname masyva nuo duoto tago iki masyvo galo
	private static List<String> getSubList(List<String> source, String fromTag) {

		int from = getLineIndexAt(source, fromTag, 0);
		int to = getLineIndexAt(source, toTag, 0);

		if (from != -1 && to != -1) {

			return source.subList(from, to);
		} else {

			return null;
		}

	}

	// atskiriame sablono titulines eilutes i atskira masyva
	private void getHeader() {

		int startHdr = 0;
		int endHdr = 7;
		header.addAll(strList.subList(startHdr, endHdr));

	}







	// graziname impozicijos masyva
	private static List<String> getImposition(List<String> signature) {

		List<String> impos = getSubList(signature, "%SSiPrshMatrix: 1",
				"%SSiPrshMatrix: 7");

		if (impos != null) {
			return impos;
		} else {
			return null;
		}
	}

	// graziname puslapio impozicijoje auksti (imame is pirmo %SSiPrshPage tago,
	// nes seni templatai gali buti irasyti nekorektiskai)
	public static Float getPageHeight(List<String> signature) {

		List<String> impos = getImposition(signature);
		String tag = getLineAt(impos, "%SSiPrshPage:", 1);
		if (tag != null) {
			String value = getValueAt(tag, 4);
			return Float.valueOf(value);
		}

		return -1f;
	}

	// graziname puslapio impozicijoje ploti (imame is pirmo %SSiPrshPage tago,
	// nes seni templatai gali buti irasyti nekorektiskai)
	public static Float getPageWidth(List<String> signature) {

		List<String> impos = getImposition(signature);
		String tag = getLineAt(impos, "%SSiPrshPage:", 1);
		if (tag != null) {
			String value = getValueAt(tag, 3);
			return Float.valueOf(value);
		}

		return -1f;
	}

	// graziname vertikaliu tarpu masyva
	private static List<String> getVerticalGaps(List<String> signature) {

		List<String> gaps = null;
		List<String> impos = getImposition(signature);
		if (impos != null) {

			int from = 0;
			int to = 0;

			for (int i = 0; i < impos.size(); i++) {

				String line = impos.get(i);
				if (line.startsWith("%SSiPrshMatrix: 14")
						|| line.startsWith("%SSiPrshMatrix: 2")) {
					from = i;
				} else if (line.startsWith("%SSiPrshMatrix: 13")
						|| line.startsWith("%SSiPrshMatrix: 3")) {
					to = i;
				}
			}

			gaps = impos.subList(from, to);
			if (gaps.size() != 0) {

				return gaps;
			}
		}
		return null;
	}

	// graziname horizontaliu tarpu masyva
	private static List<String> getHorizontalGaps(List<String> signature) {

		List<String> gaps = null;
		List<String> impos = getImposition(signature);
		if (impos != null) {

			int from = 0;
			int to = 0;

			for (int i = 0; i < impos.size(); i++) {

				String line = impos.get(i);
				if (line.startsWith("%SSiPrshMatrix: 13")
						|| line.startsWith("%SSiPrshMatrix: 3")) {
					from = i;
				} else if (line.startsWith("%SSiPrshPage:")) {
					to = i;
				}
			}

			gaps = impos.subList(from, to);
			if (gaps.size() != 0) {

				return gaps;
			}
		}
		return null;
	}

	// graziname puslapiu skaiciu pagal horizontalia arba vertikalia ashis
	public static int getColumnsCount(List<String> signature) {

		int pCount;
		List<String> gaps = getVerticalGaps(signature);
		if (gaps != null) {
			pCount = getTagCount(gaps, "%SSiPrshMatrix: 5");
			return pCount;
		}

		return -1;
	}

	// graziname puslapiu skaiciu pagal horizontalia ashi
	public static int getRowsCount(List<String> signature) {

		int pCount;
		List<String> gaps = getHorizontalGaps(signature);
		if (gaps != null) {
			pCount = getTagCount(gaps, "%SSiPrshMatrix: 5");
			return pCount;
		}

		return -1;
	}

	// gaziname workstyle koda
	public static int getWorkstyle(List<String> signature) {

		String tag = getLineAt(signature, "%SSiPressSheet:", 1);
		if (tag != null) {
			String value = getValueAt(tag, 5);
			return Integer.valueOf(value);
		}
		return -1;
	}

	// graziname impozicijos puslapius (%SSiPrshPage tagus)
	private static List<String> getImposPages(List<String> signature) {

		List<String> imposPages;

		List<String> impos = getImposition(signature);
		if (impos != null) {

			imposPages = getSubList(impos, "%SSiPrshPage:", "%SSiPrshMatrix: 7");

			if (imposPages != null) {

				return imposPages;
			}
		}
		return null;
	}

	public static int[] getRotations(List<String> signature) {

		String page;
		int orientation;
		List<String> imposPages = getImposPages(signature);
		if (imposPages != null) {

			int[] rotations = new int[imposPages.size()];
			for (int i = 0; i < imposPages.size(); i++) {

				page = imposPages.get(i);
				orientation = Integer.valueOf(getValueAt(page, 5));

				switch (orientation) {

				case 3:
					rotations[i] = 270;
					break;
				case 4:
					rotations[i] = 180;
					break;
				case 5:
					rotations[i] = 90;
					break;
				case 6:
					rotations[i] = 0;
					break;
				default:
					rotations[i] = 0;
					break;
				}

			}

			return rotations;
		}

		return null;
	}

	public static int[] getPageNumbers(List<String> signature) {

		String page;
		int number;
		List<String> imposPages = getImposPages(signature);
		if (imposPages != null) {

			int[] pageNumbers = new int[imposPages.size()];
			for (int i = 0; i < imposPages.size(); i++) {

				page = imposPages.get(i);
				number = Integer.valueOf(getValueAt(page, 6));
				pageNumbers[i] = number;
			}

			return pageNumbers;
		}

		return null;
	}

}
