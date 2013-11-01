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

// Klase aptarnaujanti Preps sablona
public class PrepsTemplate {

	// viso sablono eilute
	String template;

	// masyvas su sablono eilutemis
	List<String> templArray;

	// masyvas su pirmutinemis sablono eilutemis
	List<String> header = new ArrayList<String>();

	// masyvas talpinantis signaturu masyvus
	List<List<String>> signatures = new ArrayList<List<String>>();

	// Konstruktorius priemantis sablono faila
	public PrepsTemplate(File file) {

		readFile(file);
		// getHeader();
		getSignatures(templArray);

	}

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

	// Skaitome sablono faila
	private void readFile(File file) {

		FileInputStream inFile = null;
		BufferedReader reader = null;

		try {

			inFile = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inFile, "UTF8"));
			String line;
			templArray = new ArrayList<String>();

			while ((line = reader.readLine()) != null) {

				templArray.add(line);
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
	}

	// ieskome duotame masyve sablono eilutes, pasikartojancio n-taji karta
	private static String getLineAt(List<String> source, String tag, int n) {

		int occur = 0;
		for (int i = 0; i < source.size(); i++) {

			String line = source.get(i);

			if (line.startsWith(tag)) {
				occur++;
				if (occur == n) {
					return line;
				}
			}
		}
		return null;
	}

	// ieskome duotame masyve sablono eilutes indekso nuo nurodytos vietos
	private static int getLineIndexAt(List<String> source, String tag, int from) {

		for (int i = from; i < source.size(); i++) {

			String line = source.get(i);

			if (line.startsWith(tag)) {
				return i;
			}
		}
		return -1;
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

	private List<String> getSubList(List<String> source, int from, int to) {

		return source.subList(from, to + 1);
	}

	// atskiriame sablono titulines eilutes i atskira masyva
	private void getHeader() {

		int startHdr = 0;
		int endHdr = 7;
		header.addAll(templArray.subList(startHdr, endHdr));

	}

	// atskiriame visas signaturas i atskirus masyvus
	private void getSignatures(List<String> source) {

		int start = 0;

		start = getLineIndexAt(source, "%SSiSignature:", 0);
		if (start != -1) {
			// ieskome sekancios signaturos uz pries tai surastos
			int nextSignStart = getLineIndexAt(source, "%SSiSignature:",
					start + 1);
			// jei sekanti signatura yra
			if (nextSignStart != -1) { 

				List<String> sign = new ArrayList<String>();
				sign.addAll(source.subList(start, nextSignStart));
				signatures.add(sign);
				getSignatures(source.subList(nextSignStart, source.size()));

			} else {
				List<String> sign = new ArrayList<String>();
				sign.addAll(source.subList(start, source.size()));
				signatures.add(sign);
			}
		}
	}

	// graziname signaturu lista
	public List<List<String>> getSignaturesList() {

		return signatures;

	}

	// graziname signatura pagal numeri
	public List<String> getSignature(int index) {

		return signatures.get(index);
	}

	// graziname siganturu skaiciu
	public int getSignaturesCount() {

		return signatures.size();
	}

	// graziname siganturos pavadinima
	public static String getSignatureName(List<String> signature) {

		String tag = signature.get(0);
		String signName = tag.split("\\|")[1];
		return signName;
	}

	// graziname signaturos ploti
	public static float getSignatureWidth(List<String> signature) {

		String tag = getLineAt(signature, "%SSiPressSheet:", 1);
		if (tag != null) {
			String value = getValueAt(tag, 1);
			return Float.valueOf(value);
		}

		return -1;
	}

	// graziname signaturos auksti
	public static float getSignatureHeight(List<String> signature) {

		String tag = getLineAt(signature, "%SSiPressSheet:", 1);
		if (tag != null) {
			String value = getValueAt(tag, 2);
			return Float.valueOf(value);
		}
		return -1;
	}

	// graziname reiksme eiluteje nurodytu indeksu
	private static String getValueAt(String str, int index) {

		String[] strArr = str.split(" ");
		return strArr[index];
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
