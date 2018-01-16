package vkurman.wordgame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <code>WordGameUI</code> is UI for the <code>WordGame</code> application.
 *
 * <p>
 * Date created: 6 Jan 2018 Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGameUI extends JFrame implements ActionListener,
		ChangeListener, WordGameAnalyserListener {

	public static final String TITLE = "Word Game";

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 5774291152402674431L;

	private WordGameTextInputListener textListener;
	private WordGamePreferenceChangeListener preferenceListener;
	private JTextArea textArea;
	private JButton btnOpen;
	private JButton btnNew;
	private JButton btnExit;
	// Preference labels
	private JLabel lblHide;
	private JLabel lblMaxConsecWords;
	private JLabel lblMinWordsInSentense;
	private JLabel lblMinLettersInWord;
	// Preference sliders
	private JSlider sldHide;
	private JSlider sldMaxConsecWords;
	private JSlider sldMinWordsInSentense;
	private JSlider sldMinLettersInWord;
	// Preference values
	private int hideValue;
	private int maxConsecWordsValue;
	private int minWordsInSentenseValue;
	private int minLettersValue;
	// Text and filter information values
	private JLabel lblSentencesInText;
	private JLabel lblSentencesWithMinWords;
	private JLabel lblWordsInText;
	private JLabel lblWordsWithMinLetterst;
	private JLabel lblWordsWithPercentageApplied;
	private JLabel lblWordsWithConcecutiveFilterApplied;
	private JLabel lblWordsTotalToHide;
	// Text for labels
	private String sentencesInText = "Sentences in text: ";
	private String sentencesWithMinWords = "Sentences with min words filter: ";
	private String wordsInText = "Words in text: ";
	private String wordsWithMinLetters = "Words with min letters applied: ";
	private String wordsWithPercentageApplied = "Words with percentage applied: ";
	private String wordsWithConcecutiveFilterApplied = "Words with concecutive filter applied: ";
	private String wordsTotalToHide = "Words total to hide: ";
	
	/**
	 * Constructor
	 */
	public WordGameUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(TITLE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(panel);

		hideValue = WordGamePreferences.HIDE_PERCENTAGE_DEFAULT;
		maxConsecWordsValue = WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_DEFAULT;
		minWordsInSentenseValue = WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_DEFAULT;
		minLettersValue = WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_DEFAULT;

		lblHide = new JLabel(WordGamePreferences.HIDE_PERCENTAGE_LABEL + hideValue);
		lblMaxConsecWords = new JLabel(WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_LABEL + maxConsecWordsValue);
		lblMinWordsInSentense = new JLabel(WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_LABEL
				+ minWordsInSentenseValue);
		lblMinLettersInWord = new JLabel(WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_LABEL + minLettersValue);

		createContent();
		createSidePanel();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Creates content and adds it to UI.
	 */
	private void createContent() {
		textArea = new JTextArea(30, 75);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		btnNew = new JButton("New");
		btnNew.addActionListener(this);
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(this);
		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);

		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		pnlButtons.add(btnNew);
		pnlButtons.add(btnOpen);
		pnlButtons.add(btnExit);

		add(new JScrollPane(textArea), BorderLayout.CENTER);
		add(pnlButtons, BorderLayout.SOUTH);
	}

	/**
	 * Creates a preference and information panel.
	 */
	private void createSidePanel() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);
		
		panel.add(createPreferencePanel());
		panel.add(createInformationPanel());

		add(panel, BorderLayout.EAST);
	}

	/**
	 * Creates and returns panel with preferences and filters.
	 * 
	 * @return JPanel
	 */
	private JPanel createPreferencePanel() {
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		panel.setBorder(BorderFactory.createTitledBorder("Preferences"));
		BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);

		sldHide = new JSlider(JSlider.HORIZONTAL,
				WordGamePreferences.HIDE_PERCENTAGE_MINIMUM,
				WordGamePreferences.HIDE_PERCENTAGE_MAXIMUM, hideValue);
		sldHide.setMajorTickSpacing(20);
		sldHide.setMinorTickSpacing(10);
		sldHide.setPaintTicks(true);
		sldHide.setPaintLabels(true);
		sldHide.addChangeListener(this);

		sldMaxConsecWords = new JSlider(
				JSlider.HORIZONTAL,
				WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MINIMUM,
				WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MAXIMUM,
				maxConsecWordsValue);
		sldMaxConsecWords.setMajorTickSpacing(2);
		sldMaxConsecWords.setMinorTickSpacing(1);
		sldMaxConsecWords.setPaintTicks(true);
		sldMaxConsecWords.setPaintLabels(true);
		sldMaxConsecWords.addChangeListener(this);

		sldMinWordsInSentense = new JSlider(
				JSlider.HORIZONTAL,
				WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MINIMUM,
				WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MAXIMUM,
				minWordsInSentenseValue);
		sldMinWordsInSentense.setMajorTickSpacing(2);
		sldMinWordsInSentense.setMinorTickSpacing(1);
		sldMinWordsInSentense.setPaintTicks(true);
		sldMinWordsInSentense.setPaintLabels(true);
		sldMinWordsInSentense.addChangeListener(this);

		sldMinLettersInWord = new JSlider(
				JSlider.HORIZONTAL,
				WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MINIMUM,
				WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MAXIMUM,
				minLettersValue);
		sldMinLettersInWord.setMajorTickSpacing(2);
		sldMinLettersInWord.setMinorTickSpacing(1);
		sldMinLettersInWord.setPaintTicks(true);
		sldMinLettersInWord.setPaintLabels(true);
		sldMinLettersInWord.addChangeListener(this);

		panel.add(lblHide);
		panel.add(sldHide);
		panel.add(lblMinWordsInSentense);
		panel.add(sldMinWordsInSentense);
		panel.add(lblMinLettersInWord);
		panel.add(sldMinLettersInWord);
		panel.add(lblMaxConsecWords);
		panel.add(sldMaxConsecWords);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with information about text.
	 * 
	 * @return JPanel
	 */
	private JPanel createInformationPanel() {
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		panel.setBorder(BorderFactory.createTitledBorder("Text information"));
		BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);
		
		lblSentencesInText = new JLabel(sentencesInText + 0);
		lblSentencesWithMinWords = new JLabel(sentencesWithMinWords + 0);
		lblWordsInText = new JLabel(wordsInText + 0);
		lblWordsWithMinLetterst = new JLabel(wordsWithMinLetters + 0);
		lblWordsWithPercentageApplied = new JLabel(wordsWithPercentageApplied + 0);
		lblWordsWithConcecutiveFilterApplied = new JLabel(wordsWithConcecutiveFilterApplied + 0);
		lblWordsTotalToHide = new JLabel(wordsTotalToHide + 0);

		panel.add(lblSentencesInText);
		panel.add(lblSentencesWithMinWords);
		panel.add(lblWordsInText);
		panel.add(lblWordsWithMinLetterst);
		panel.add(lblWordsWithPercentageApplied);
		panel.add(lblWordsWithConcecutiveFilterApplied);
		panel.add(lblWordsTotalToHide);
		
		return panel;
	}
	
	/**
	 * Displays specified text to the user.
	 * 
	 * @param text
	 */
	public void showText(String text) {
		textArea.setText(text);
	}

	/**
	 * Sets WordGameTextInputListener.
	 * 
	 * @param textListener
	 */
	public void setWordGameTextInputListener(
			WordGameTextInputListener textListener) {
		this.textListener = textListener;
	}

	/**
	 * Sets WordGamePreferenceChangeListener.
	 * 
	 * @param preferenceListener
	 */
	public void setWordGamePreferenceChangeListener(
			WordGamePreferenceChangeListener preferenceListener) {
		this.preferenceListener = preferenceListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpen) {
			JFileChooser fc = new JFileChooser();
			// Disabling all files showing in JFileChooser
			fc.setAcceptAllFileFilterUsed(false);
			// Setting current working directory
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			// Setting FileFilter
			fc.addChoosableFileFilter(new WordGameFileFilter());

			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				StringBuffer sb = new StringBuffer();

				File file = fc.getSelectedFile();
				// Opening file and reading text
				Path path = Paths.get(file.getPath());
				try (InputStream in = Files.newInputStream(path);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(in))) {
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					// Sending text to text listener for processing
					if (textListener != null) {
						textListener.textChanged(sb.toString());
					}
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(this, "Error reading file: "
							+ file.getPath(), "File reading error!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getSource() == btnNew) {
			WordGameNewTextDialog dialog = new WordGameNewTextDialog(this);
			if (!dialog.isShowing() && dialog.isOpenPressed()) {
				String text = dialog.getUserTextInput();
				if (text != null && !text.isEmpty()) {
					if (textListener != null) {
						textListener.textChanged(text);
					}
				}
			}
		} else if (e.getSource() == btnExit) {
			System.exit(0);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) e.getSource();
			if (slider == sldHide) {
				if (slider.getValueIsAdjusting()) {
					lblHide.setText(WordGamePreferences.HIDE_PERCENTAGE_LABEL + slider.getValue());
				} else {
					if (preferenceListener != null) {
						int oldValue = hideValue;
						hideValue = slider.getValue();
						preferenceListener.preferenceChanged(
								WordGamePreferences.HIDE_PERCENTAGE_ID,
								oldValue, hideValue);
					}
				}
			} else if (slider == sldMaxConsecWords) {
				if (slider.getValueIsAdjusting()) {
					lblMaxConsecWords.setText(WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_LABEL
							+ slider.getValue());
				} else {
					if (preferenceListener != null) {
						int oldValue = maxConsecWordsValue;
						maxConsecWordsValue = slider.getValue();
						preferenceListener
								.preferenceChanged(
										WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_ID,
										oldValue, maxConsecWordsValue);
					}
				}
			} else if (slider == sldMinLettersInWord) {
				if (slider.getValueIsAdjusting()) {
					lblMinLettersInWord
							.setText(WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_LABEL + slider.getValue());
				} else {
					if (preferenceListener != null) {
						int oldValue = minLettersValue;
						minLettersValue = slider.getValue();
						preferenceListener
								.preferenceChanged(
										WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_ID,
										oldValue, minLettersValue);
					}
				}
			} else if (slider == sldMinWordsInSentense) {
				if (slider.getValueIsAdjusting()) {
					lblMinWordsInSentense.setText(WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_LABEL
							+ slider.getValue());
				} else {
					if (preferenceListener != null) {
						int oldValue = minWordsInSentenseValue;
						minWordsInSentenseValue = slider.getValue();
						preferenceListener
								.preferenceChanged(
										WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_ID,
										oldValue, minWordsInSentenseValue);
					}
				}
			}
		}
	}
	
	@Override
	public void changeCalculated(int id, int value) {
		switch(id) {
			case SENTENCES_IN_TEXT_ID :
				lblSentencesInText.setText(sentencesInText + value);
				break;
			case SENTENCES_WITH_MINIMUM_WORDS_ID :
				lblSentencesWithMinWords.setText(sentencesWithMinWords + value);
				break;
			case WORDS_IN_TEXT_ID :
				lblWordsInText.setText(wordsInText + value);
				break;
			case WORDS_WITH_MINIMUM_LETTERS_ID :
				lblWordsWithMinLetterst.setText(wordsWithMinLetters + value);
				break;
			case WORDS_WITH_PERCENTAGE_APPLIED_ID :
				lblWordsWithPercentageApplied.setText(wordsWithPercentageApplied + value);
				break;
			case WORDS_WITH_CONCECUTIVE_FILTER_APPLIED_ID :
				lblWordsWithConcecutiveFilterApplied.setText(wordsWithConcecutiveFilterApplied + value);
				break;
			case WORDS_TOTAL_CAN_HIDE_ID :
				lblWordsTotalToHide.setText(wordsTotalToHide + value);
				break;
		}
	}
}