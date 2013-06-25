package ui.gui.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//JAVADOC
public class IntegerDocument extends PlainDocument {
	//JAVADOC
	@Override public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if(str == null) {
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
