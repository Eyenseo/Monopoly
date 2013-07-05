package ui.gui;

import objects.value.MassageData;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//JAVADOC
public class CustomDialog extends JFrame {
	public CustomDialog(final Model model, final MassageData massageData) {
		super(massageData.getUserId() + ": " + massageData.getTyp());
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
			//			doc.insertString(0, "\n\n" + massageData.getText(), doc.getStyle("Style"));

			StyledDocument document = new DefaultStyledDocument();
			Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
			StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);
			document.insertString(0, "\n\n" + massageData.getText(), null);
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
						model.getMassageDataArrayList().remove(massageData);

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
