package ui.gui.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//JAVADOC
public class IntegerDocument extends PlainDocument {
	private int maxLength;

	//JAVADOC
	public IntegerDocument() {
		maxLength = 9;
	}

	//JAVADOC
	public IntegerDocument(int maxLength) {
		this.maxLength = maxLength;
	}

	//JAVADOC
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
