package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The StorageReader is a input class that reads the data files.
 *
 * @author Eyenseo
 * @version 1
 */
abstract class StorageReader {
	BufferedReader file;

	/**
	 * @param path The value determines which file will be loaded.
	 */
	public StorageReader(String path) {
		try {
			this.file = new BufferedReader(new FileReader(path));
		} catch(FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	/**
	 * @return The return value is the Integer of the next line.
	 * @throws IOException
	 */
	//TODO check that Methods handle null return at file end
	int nextInt() throws IOException {
		//TODO Handle Exeption
		String line = file.readLine();
		while(!isValidString(line)) {
			line = file.readLine();
		}
		System.out.println(line);
		return Integer.parseInt(line);
	}

	/**
	 * The Method checks if the the String is a comment or a line without information.
	 *
	 * @param l The value determines the String to be checked
	 * @return
	 */
	boolean isValidString(String l) {
		if(l == null) {
			return true;
		}
		if(l.length() >= 2 && (l.charAt(0) == '#' && l.charAt(1) == '#')) {
			return false;
		}
		if(l.length() == 1 && (l.charAt(0) == '#' || l.charAt(0) == '!' || l.charAt(0) == ' ' || l.charAt(0) ==
		                                                                                         '\t')) {
			return false;
		}
		return !l.isEmpty();
	}

	/**
	 * @return The return value is the String of the next line.
	 * @throws IOException
	 */
	//TODO check that Methods handle null return at file end
	String nextString() throws IOException {
		//TODO Handle Exeption
		String line = file.readLine();
		while(!isValidString(line)) {
			line = file.readLine();
		}
		System.out.println(line);
		return line;
	}
}
