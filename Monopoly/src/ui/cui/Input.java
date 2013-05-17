package ui.cui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The Input class is responsible for the interaction with the Human.
 *
 * @author Eyenseo
 * @version 1
 */
class Input {
	private BufferedReader bReader;
	private String         input;

	public Input() {
		bReader = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * @return The return value is the next line from the console.
	 */
	String readString() {
		try {
			input = bReader.readLine();
			if(input.equals("")) {
				System.out.print("Sie haben nichts eingegeben, probier es nochmal.\n> ");
				return readString();
			} else {
				return input;
			}
		} catch(Exception e) {
			System.err.println("There was an error while reading from the console. " +
			                   "The possibility for that was under 0.1% congratulations!\n" +
			                   "The return value will be an empty String...");
			return "";
		}
	}

	/**
	 * @return The return value is the next char from the console.
	 */
	private char readChar() {
		input = readString();
		if(input.length() == 1) {
			return input.charAt(0);
		} else if(input.length() == 0) {
			return '\0';
		} else {
			System.out.print("Sie haben zuviele Zeichen eingegeben, probier es nochmal.\n> ");
			return readChar();
		}
	}

	/**
	 * @return The return value is the next int from the console.
	 */
	int readInt() {
		try {
			input = readString();
			return Integer.parseInt(input);
		} catch(Exception e) {
			System.out.print("Sie haben keine Zahl eingegeben, probier es nochmal.\n> ");
			return readInt();
		}
	}

	/**
	 * @param input   The value determines the char that will e checked.
	 * @param options The value determines the chars to check against.
	 * @return The return value is true if the input exists in options.
	 */
	private boolean isCorrectChar(char input, char[] options) {
		for(char o : options) {
			if(o == input) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param yes         The value determines which char will be true.
	 * @param no          The value determines which char will be false.
	 * @param errorString The value determines the text to be presented if the user types a bad char.
	 * @return The return value is true if the Human enters the yes char.
	 */
	boolean userBoolean(char yes, char no, String errorString) {
		char input = readChar();
		while(!isCorrectChar(input, new char[]{yes, no})) {
			System.out.println(errorString);
			System.out.print("Probier es nochmal.\n" + "> ");
			input = readChar();
		}
		return (input == yes);
	}

	/**
	 * @param options     The value determines the chars to check against.
	 * @param errorString The value determines the text to be presented if the user types a bad char.
	 * @return The return value is a selected char out of options.
	 */
	char userChar(char[] options, String errorString) {
		char input = readChar();
		while(!isCorrectChar(input, options)) {
			System.out.println(errorString);
			System.out.print("Probier es nochmal.\n" + "> ");
			input = readChar();
		}
		return input;
	}

	/**
	 * @param start       The value determines the start of the numbers that are allowed.
	 * @param end         The value determines the end of the numbers that are allowed.
	 * @param errorString The value determines the text to be presented if the user types a bad char.
	 * @return The return value is a selected int out of options.
	 */
	int userInt(int start, int end, String errorString) {
		int input = readInt();
		if(input < start || input > end) {
			System.out.println(errorString);
			System.out.print("Probier es nochmal.\n" + "> ");
			return userInt(start, end, errorString);
		}
		return input;
	}

	/**
	 * @param options     The value determines the ints to check against.
	 * @param errorString The value determines the text to be presented if the user types a bad char.
	 * @return The return value is a selected int out of options.
	 */
	int userInt(int[] options, String errorString) {
		int input = readInt();
		while(!isCorrectInt(input, options)) {
			System.out.println(errorString);
			System.out.print("Probier es nochmal.\n" + "> ");
			input = readInt();
		}
		return input;
	}

	/**
	 * @param input   The value determines the char that will e checked.
	 * @param options The value determines the ints to check against.
	 * @return The return value is tur if the int was in the array.
	 */
	private boolean isCorrectInt(int input, int[] options) {
		for(int o : options) {
			if(o == input) {
				return true;
			}
		}
		return false;
	}
}
