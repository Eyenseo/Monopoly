package core.data;

import objects.exceptions.data.EndOfBlockException;
import objects.exceptions.data.EndOfFileException;
import objects.exceptions.data.StorageReaderException;

import java.io.*;

/**
 * The StorageReader is a input class that reads the files in the storage package.
 *
 * @author Eyenseo
 * @version 1
 */
abstract class StorageReader {
	private BufferedReader file;
	String fileName;

	/**
	 * @param fileName The value determines which file will be loaded.
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be
	 *                                read out with getMessageStack.
	 */
	StorageReader(String fileName) throws StorageReaderException {
		try {
			this.file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			this.fileName = fileName;
		} catch(FileNotFoundException e) {
			throw new StorageReaderException("The requested file was not found:\n\t" + this.fileName, e);
		}
	}

	/**
	 * @return The return value is the next valid line as int.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be
	 *                                read out with getMessageStack.
	 */
	int nextInt() throws StorageReaderException {
		String line = nextString();
		try {
			return Integer.parseInt(line);
		} catch(NumberFormatException e) {
			throw new StorageReaderException("The string was meant to be casted to an integer:\n\t" + line);
		}
	}

	/**
	 * @return The return value is the next valid line as String.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be
	 *                                read out with getMessageStack.
	 */
	String nextString() throws StorageReaderException {
		try {
			String line = file.readLine();
			while(isCommentString(line)) {
				line = file.readLine();
			}
			if(line == null) {
				throw new EndOfFileException(fileName);
			}
			if(isEndOfBlock(line)) {
				throw new EndOfBlockException(fileName);
			}
			String tempOne = "\\n";
			String tempTwo = "\n";
			line = line.replace(tempOne.subSequence(0, tempOne.length()), tempTwo.subSequence(0, tempTwo.length()));
			return line;
		} catch(IOException e) {
			throw new StorageReaderException(e);
		}
	}

	/**
	 * @return The return value is the next valid control word as String.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	String nextControlWord() throws StorageReaderException {
		try {
			String line = file.readLine();
			while(line != null && (isCommentString(line) || !isControlWord(line))) {
				line = file.readLine();
			}
			if(isEndOfBlock(line)) {
				throw new EndOfBlockException(fileName);
			}
			return line;
		} catch(IOException e) {
			throw new StorageReaderException(e);
		}
	}

	/**
	 * @param line The value determines the String to be checked
	 * @return The return value is true if the String is a comment.
	 */
	boolean isCommentString(String line) {
		return line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) == '#');
	}

	/**
	 * @return The return value is true if the next line is the end of a data block.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be
	 *                                read out with getMessageStack.
	 */
	boolean isEndOfBlock() throws StorageReaderException {
		try {
			String line = file.readLine();
			while(isCommentString(line)) {
				line = file.readLine();
			}
			if(line == null) {
				throw new EndOfFileException(fileName);
			}
			return isEndOfBlock(line);
		} catch(IOException e) {
			throw new StorageReaderException(e);
		}
	}

	/**
	 * @param line The value determines the String to be checked
	 * @return The return value is true if the next line is the end of a data block.
	 */
	boolean isEndOfBlock(String line) {
		return line != null && (line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) == '!'));
	}

	/**
	 * @param line The value determines the String to be checked
	 * @return The return value is true if the String is a control word.
	 */
	boolean isControlWord(String line) {
		return line != null && (line.length() >= 2 && (line.charAt(0) == '#' && line.charAt(1) != ' '));
	}
}
