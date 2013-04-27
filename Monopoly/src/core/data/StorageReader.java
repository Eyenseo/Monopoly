package core.data;

import objects.exceptions.data.EndOfBlockException;
import objects.exceptions.data.EndOfFileException;
import objects.exceptions.data.StorageReaderException;

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
	private BufferedReader file;
	String path = "./Monopoly/src/storage/";

	/**
	 * @param file The value determines which file will be loaded.
	 */
	StorageReader(String file) {
		try {
			//TODO Path may be different if run as package.
			this.file = new BufferedReader(new FileReader(path + file));
			this.path += file;
		} catch(FileNotFoundException e) {
			System.out.println("The requested file was not found:\n\t" + path);
			e.printStackTrace();
		}
	}

	/**
	 * @return The return value is the next valid line as int.
	 *
	 * @throws StorageReaderException The Exception holds in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 * @throws EndOfFileException     The Exception holds the path to the file ended unexpected.
	 * @throws EndOfBlockException    The Exception holds the path to the file were the end of block statement was
	 *                                missing.
	 */
	int nextInt() throws StorageReaderException {
		String line = nextString();
		try {
			return Integer.parseInt(line);
		} catch(NumberFormatException e) {
			throw new StorageReaderException("The String was ment to be casted to a Integer:\n\t" + line);
		}
	}

	/**
	 * @return The return value is the next valid line as String.
	 *
	 * @throws StorageReaderException The Exception holds in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 * @throws EndOfFileException     The Exception holds the path to the file ended unexpected.
	 * @throws EndOfBlockException    The Exception holds the path to the file were the end of block statement was
	 *                                missing.
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
			if(isEndOfBlock(line)) {
				throw new EndOfBlockException(path);
			}
			return line;
		} catch(IOException e) {
			throw new StorageReaderException(e);
		}
	}

	//JAVADOC
	String nextControllWord() throws StorageReaderException {
		try {
			String line = file.readLine();
			while(line != null && (isCommentString(line) || !isControlWord(line))) {
				line = file.readLine();
			}
			if(isEndOfBlock(line)) {
				throw new EndOfBlockException(path);
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
	 * @return The return value is true if the String is a comment.
	 */
	boolean isCommentString(String line) {
		if(line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) == '#')) {
			return true;
		}
		return false;
	}

	/**
	 * @return The return value is the next valid line as String.
	 *
	 * @throws StorageReaderException The Exception holds in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 * @throws EndOfFileException     The Exception holds the path to the file ended unexpected.
	 */
	boolean isEndOfBlock() throws StorageReaderException {
		try {
			String line = file.readLine();
			while(isCommentString(line)) {
				line = file.readLine();
			}
			if(line == null) {
				throw new EndOfFileException(path);
			}
			return isEndOfBlock(line);
		} catch(IOException e) {
			throw new StorageReaderException(e);
		}
	}

	/**
	 * The Method checks if the next line is the end statement of a data block.
	 *
	 * @param line The value determines the String to be checked
	 * @return The return value is true if the String represents the end statement of a data block.
	 */
	boolean isEndOfBlock(String line) {
		if(line != null && (line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) == '!'))) {
			return true;
		}
		return false;
	}

	/**
	 * The Method checks if the the String is a control word.
	 *
	 * @param line The value determines the String to be checked
	 * @return The return value is true if the String is a control word.
	 */
	boolean isControlWord(String line) {
		if(line != null && (line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) != ' '))) {
			return true;
		}
		return false;
	}
}
