package ui.gui.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The iNtegerDocument is a Document that just allows only integers as input
 */
class IntegerDocument extends PlainDocument {
	private final int maxLength;

	/**
	 * The default constructor allows only 9 digits to be put in
	 */
	public IntegerDocument() {
		maxLength = 9;
	}

	/**
	 * @param maxLength The value determines the the max length of the document
	 */
	public IntegerDocument(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @param offs The value determines the offset at which the String shall be put in at
	 * @param str  The value determines the String to be put in
	 * @param a    The value determines the Attribute set that shall be used
	 * @throws BadLocationException if the offset is out of range
	 */
	//TODO don't cap at 9 digits but at the max intager value
	@Override public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if(str == null) {
			return;
		} else if(str.length() + offs > maxLength) {
			return;
		}

		char[] letters = str.toCharArray();
		boolean numbers = true;

		for(char c : letters) {
			if(!Character.isDigit(c)) {
				numbers = false;
				break;
			}
		}

		if(numbers) {
			super.insertString(offs, new String(letters), a);
		}
	}
}
