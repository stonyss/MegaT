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

	// The list of signatures
	private List<Signature> signaturesList = new ArrayList<Signature>();

	/**
	 * The constructor takes a file oject
	 * 
	 * @param file
	 */
	public PrepsTemplate(File file) {

		List<String> strList = readFile(file);
		getSignatures(strList);
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
	 * The method creates the list of strings for each signature found in the
	 * list of template's strings
	 * 
	 * @param source
	 */
	private void getSignatures(List<String> strList) {

		int signStart = 0;

		signStart = Utility.getLineIndex(strList, "%SSiSignature:", 0);
		if (signStart != -1) {
			// looking for a next signature
			int nextSignStart = Utility.getLineIndex(strList, "%SSiSignature:",
					signStart + 1);
			// if the next signature has found
			if (nextSignStart != -1) {

				Signature sign = new Signature(strList.subList(signStart,
						nextSignStart));
				signaturesList.add(sign);
				getSignatures(strList.subList(nextSignStart, strList.size()));

			} else {

				Signature sign = new Signature(strList.subList(signStart,
						strList.size()));
				signaturesList.add(sign);
			}
		}
	}


	/**
	 * Returns signature by an index
	 * 
	 * @param index
	 * @return signature
	 */
	public Signature getSignature(int index) {

		return signaturesList.get(index);
	}

	/**
	 * Returns the list of signatures
	 * 
	 * @return
	 */
	public List<Signature> getSignaturesList() {

		return signaturesList;

	}

	/**
	 * Returns the count of all signatures
	 * 
	 * @return
	 */
	public int getSignaturesCount() {

		return signaturesList.size();
	}

}
