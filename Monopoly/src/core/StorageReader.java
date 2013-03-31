package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The StorageReader is a input class that reads the files in the storage package.
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
		//TODO Handle Exception
		try {
			this.file = new BufferedReader(new FileReader(path));
		} catch(FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	/**
	 * @return The return value is the next valid line as Int.
	 *
	 * @throws IOException
	 */
	//TODO check that Methods handle null return at file end
	int nextInt() throws IOException {
		//TODO Handle Exception
		String line = file.readLine();
		while(!isValidString(line)) {
			line = file.readLine();
		}
		System.out.println(line);
		return Integer.parseInt(line);
	}

	/**
	 * The Method checks if the the String is a comment, set end or with information.
	 *
	 * @param line The value determines the String to be checked
	 *
	 * @return The return value is true if the String is valid.
	 */
	boolean isValidString(String line) {
		if(line == null) {
			return true;
		}
		if(line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) == '#')) {
			return false;
		}
		if(line.length() == 1 && (line.charAt(0) == '#' || line.charAt(0) == '!')) {
			return false;
		}
		return !line.isEmpty();
	}

	/**
	 * @return The return value is the next valid line as String.
	 *
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
