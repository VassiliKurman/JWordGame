package vkurman.wordgame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * <code>WordGameNewTextDialog</code> dialog to enter new text.
 *
 * <p>
 * Date created: 12 Jan 2018
 * Date modified: 12 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGameNewTextDialog extends JDialog implements ActionListener {

	public static final String TITLE = "Text input dialog";
	/**
	 * 
	 */
	private static final long serialVersionUID = 5806805897470185064L;
	
	private JTextArea textArea;
	private JButton btnSave;
	private JButton btnOpen;
	private JButton btnClose;
	
	private boolean openPressed;
	private String userTextInput;
	
	WordGameNewTextDialog(JFrame parent) {
		super(parent, TITLE, true);
		
		JPanel pnlContent = new JPanel();
		pnlContent.setLayout(new BorderLayout(10, 10));
		pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		openPressed = false;
		
		textArea = new JTextArea(30, 75);
		
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(this);
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		
		pnlButtons.add(btnSave);
		pnlButtons.add(btnOpen);
		pnlButtons.add(btnClose);
		
		pnlContent.add(textArea, BorderLayout.CENTER);
		pnlContent.add(pnlButtons, BorderLayout.SOUTH);
		
		setContentPane(pnlContent);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	/**
	 * Indicator that button pressed to open content.
	 * 
	 * @return boolean
	 */
	public boolean isOpenPressed() {
		return openPressed;
	}
	
	/**
	 * Returns user input text
	 * 
	 * @return String
	 */
	public String getUserTextInput() {
		return userTextInput;
	}
	
	/**
	 * Allows to open input text by parent.
	 */
	private void openInputText() {
		if(textArea.getText() != null && !textArea.getText().isEmpty()) {
			userTextInput = textArea.getText();
			openPressed = true;
			dispose();
		}
	}
	
	/**
	 * Saves text to file.
	 */
	private boolean saveToFile() {
		if(textArea.getText() != null && !textArea.getText().isEmpty()) {
			JFileChooser fc = new JFileChooser();
			// Allowing to chose only files
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// Disabling all files showing in JFileChooser
			fc.setAcceptAllFileFilterUsed(false);
			// Setting current working directory
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			// Setting FileFilter
			fc.addChoosableFileFilter(new WordGameFileFilter());
			
			int returnVal = fc.showSaveDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				// Opening file and reading text
				String pathString = fc.getSelectedFile().getPath();
				if(!pathString.endsWith("." + WordGameUtils.EXTENSION_TXT)) {
					pathString = pathString + "." + WordGameUtils.EXTENSION_TXT;
				}
				Path path = Paths.get(pathString);
				
				try (OutputStream out = Files.newOutputStream(path);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
					textArea.write(writer);
				} catch (IOException exc) {
					System.err.println("Error writing to file: " + path);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOpen) {
			if(textArea.getText() != null && !textArea.getText().isEmpty()) {
				openInputText();
			} else {
				JOptionPane.showMessageDialog(this, "No text found in the input area!", "No text error!", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == btnSave) {
			if(saveToFile()) {
				JOptionPane.showMessageDialog(this, "File saved successuly!", "Confirmation message!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "File not saved!", "Save file error!", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == btnClose) {
			dispose();
		}
	}
}