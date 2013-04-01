package core;

import objects.exceptions.EndOfFileException;
import objects.exceptions.StorageReaderException;

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
	String path = null;

	/**
	 * @param path The value determines which file will be loaded.
	 */
	public StorageReader(String path) {
		try {
			this.file = new BufferedReader(new FileReader(path));
			this.path = path;
		} catch(FileNotFoundException e) {
			System.out.println("The requested file (" + path + ") was not found! The Program must be corrupted since" +
			                   " " +
			                   "the file is stored package local.");
			e.printStackTrace();
		}
	}

	/**
	 * @return The return value is the next valid line as Int.
	 *
	 * @throws StorageReaderException The Exception hold in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 */
	int nextInt() throws StorageReaderException {
		return Integer.parseInt(nextString());
	}

	/**
	 * @return The return value is the next valid line as String.
	 *
	 * @throws StorageReaderException The Exception hold in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 */
	String nextString() throws StorageReaderException {
		try {
			String line = file.readLine();
			while(isCommentString(line)) {
				line = file.readLine();
			}
			if(line == null) {
				throw new EndOfFileException(path);
			}
			return line;
		} catch(IOException e) {
			throw new StorageReaderException(e);
		}
	}

	/**
	 * The Method checks if the the String is a comment.
	 *
	 * @param line The value determines the String to be checked
	 *
	 * @return The return value is true if the String is a comment.
	 */
	boolean isCommentString(String line) {
		if(line != null && (line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) == '#'))) {
			return true;
		}
		return false;
	}

	/**
	 * The Method checks if the the String is a control word.
	 *
	 * @param line The value determines the String to be checked
	 *
	 * @return The return value is true if the String is a control word.
	 */
	boolean isControlWord(String line) {
		if(line != null && (line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) != ' '))) {
			return true;
		}
		return false;
	}
}
