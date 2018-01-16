package vkurman.wordgame;

import java.awt.EventQueue;

/**
 * <code>WordGame</code> is the application which can randomly hide words in a
 * text. This purpose of the application is for kids to learn English by forcing
 * them to brainstorm the words.
 * <ol>
 * <li>For example,
 * "Our woefully inadequate efforts in recycling make a strong case for tackling the problem at the source."
 * After put in the application, it goes like this
 * "Our xxxxx inadequate efforts in xxxxx make a strong xxxxx for tackling the problem at the source."
 * </li>
 * <li>In the application, it is possible to adjust the percentage of the words
 * that can be hide like 80% , 90% etc.</li>
 * <li>Easy words like " the " and "or" etc... will not be hidden. It is
 * possible to choose to hide words which has more than 5 letters depending on
 * choice.</li>
 * <li>It is possible to choose to hide 2-3 consecutive words to make the kids
 * to memorise the phrases. For example
 * "Our woefully inadequate efforts in recycling make a strong case for tackling the problem at the source."
 * After put in the application, it goes like this
 * "Our xxxxx xxxxx xxxxx in xxxxx make a xxxxx xxxxx for tackling the problem at the source."
 * </li>
 * <li>Sentences that have only one word will not be hidden, like "Run".</li>
 * <li>It is possible to adjust to hide words that are in a sentences with more
 * than 3 words.</li>
 * </ol>
 *
 * <p>
 * Date created: 6 Jan 2018
 * Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 *
 */
public class WordGame implements WordGameTextInputListener, WordGamePreferenceChangeListener {
	
	private WordGameUI ui;
	private WordGamePreferences prefs;
	private WordGameProcessor textProcessor;
	private WordGameTextAnalyser analyser;
	
	/**
	 * Constructor.
	 */
	private WordGame() {
		prefs = new WordGamePreferences();
		analyser = new WordGameEnglishTextAnalyser();
		
		textProcessor = new WordGameProcessor(analyser);
	}
	
	/**
	 * Shows UI.
	 */
	private void showUI() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ui = new WordGameUI();
				ui.setWordGameTextInputListener(WordGame.this);
				ui.setWordGamePreferenceChangeListener(WordGame.this);
				
				// Setting ui as listener to analyser
				analyser.setWordGameAnalyserListener(ui);
			}
		});
	}
	
	@Override
	public void textChanged(String text) {
		if(text != null && !text.isEmpty() && textProcessor != null && prefs != null) {
			String t = textProcessor.process(prefs, text);
			ui.showText(t);
		}
	}
	
	@Override
	public void preferenceChanged(int preferenceId, int oldValue, int newValue) {
		switch(preferenceId) {
			case WordGamePreferences.HIDE_PERCENTAGE_ID:
				prefs.setHidePercentage(newValue);
				break;
			case WordGamePreferences.MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_ID:
				prefs.setMaxConsecutiveWordsThreshold(newValue);
				break;
			case WordGamePreferences.MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_ID:
				prefs.setMinLettersInWordThreshold(newValue);
				break;
			case WordGamePreferences.MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_ID:
				prefs.setMinWordsInSentenceThreshold(newValue);
				break;
		}
		
		String t = textProcessor.reprocess(prefs);
		ui.showText(t);
	}
	
	/**
	 * Entry point
	 */
	public static void main(String[] args) {
		WordGame wg = new WordGame();
		wg.showUI();
	}
}