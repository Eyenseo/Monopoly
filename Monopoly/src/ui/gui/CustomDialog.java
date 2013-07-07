package ui.gui;

import objects.value.MessageData;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Custom Dialog is a class to be used to display card text
 */
//TODO replace with a panel on the MapPanel
class CustomDialog extends JFrame {
	/**
	 * @param model       The value determines the Model to obtain information from
	 * @param messageData The value determines the messageDate that contains all information that is needed to display the
	 *                    CustomDialog
	 */
	public CustomDialog(final Model model, final MessageData messageData) {
		super(model.getPlayerHashMap().get(messageData.getUserId()).getName() + ": " + messageData.getTyp());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JTextPane textPane = null;

		try {

			//TODO why doesn't this work here if it works in the StatusBar @ModelEventListener positionUpdate = ...
			//Apparently it is some weird bug in Java - all attributes are used but the alignment is ignored.
			//          textPane = new JTextPane();
			//			StyledDocument doc = textPane.getStyledDocument();
			//			Style style = textPane.addStyle("Style", null);
			//			StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
			//			StyleConstants.setForeground(style, new Color(0, 0, 0));
			//			doc.insertString(0, "\n\n" + messageData.getText(), doc.getStyle("Style"));

			StyledDocument document = new DefaultStyledDocument();
			Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
			StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);
			document.insertString(0, "\n\n" + messageData.getText(), null);
			textPane = new JTextPane(document);
			textPane.setEditable(false);
		} catch(BadLocationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		panel.add(textPane);

		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.getMessageDataArrayList().remove(messageData);

						setVisible(false);
						dispose();
					}
				});
			}
		});
		button.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(button);

		panel.setPreferredSize(new Dimension(300, 150));

		setLocationRelativeTo(getRootPane());
		getContentPane().add(panel);
		pack();
		setVisible(true);
	}
}
