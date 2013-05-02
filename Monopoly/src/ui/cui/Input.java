package ui.cui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//JAVADOC
class Input {
    private BufferedReader bReader;
    private String input;

    Input() {
        bReader = new BufferedReader(new InputStreamReader(System.in));
    }

    //JAVADOC
    String readString() {
        try {
            input = bReader.readLine();
            if (input.equals("")) {
                System.out.print("Sie haben nichts eingegeben, probier es nochmal.\n> ");
                return readString();
            } else {
                return input;
            }
        } catch (Exception e) {
            System.err.println("There was an error while reading from the console. "
                    + "The possibility for that was under 0.1% congratulations!\n"
                    + "The return value will be an empty String...");
            return "";
        }
    }

    //JAVADOC
    char readChar() {
        input = readString();
        if (input.length() == 1) {
            return input.charAt(0);
        } else if (input.length() == 0) {
            return '\0';
        } else {
            System.out.print("Sie haben zuviele Zeichen eingegeben, probier es nochmal.\n> ");
            return readChar();
        }
    }

    //JAVADOC
    int readInt() {
        try {
            input = readString();
            return Integer.parseInt(input);
        } catch (Exception e) {
            System.out.print("Sie haben keine Zahl eingegeben, probier es nochmal.\n> ");
            return readInt();
        }
    }

    //JAVADOC
    boolean isCorrectChar(char input, char[] options) {
        for (char o : options) {
            if (o == input) {
                return true;
            }
        }
        return false;
    }

    //JAVADOC
    boolean userBoolean(char yes, char no, String errorString) {
        char input = readChar();
        while (!isCorrectChar(input, new char[]{yes, no})) {
            System.out.println(errorString);
            System.out.print("Probier es nochmal.\n" + "> ");
            input = readChar();
        }
        return (input == yes);
    }

    //JAVADOC
    char userChar(char[] options, String errorString) {
        char input = readChar();
        while (!isCorrectChar(input, options)) {
            System.out.println(errorString);
            System.out.print("Probier es nochmal.\n" + "> ");
            input = readChar();
        }
        return input;
    }

    //JAVADOC
    int userInt(int start, int end) {
        int input = readInt();
        if (input < start) {
            System.out.print("Die Zahl war kleiner als " + start + "! Probier es nochmal.\n" + "> ");
            return userInt(start, end);
        } else if (input > end) {
            System.out.print("Die Zahl war groeßer als" + end + "! Probier es nochmal.\n" + "> ");
            return userInt(start, end);
        }
        return input;
    }

    //JAVADOC
    int userInt(int[] options, String errorString) {
        int input = readInt();
        while (!isCorrectInt(input, options)) {
            System.out.println(errorString);
            System.out.print("Probier es nochmal.\n" + "> ");
            input = readInt();
        }
        return input;
    }

    //JAVADOC
    boolean isCorrectInt(int input, int[] options) {
        for (int o : options) {
            if (o == input) {
                return true;
            }
        }
        return false;
    }
}
