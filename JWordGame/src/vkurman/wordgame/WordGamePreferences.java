package vkurman.wordgame;

/**
 * <code>WordGamePreferences</code> are preferences for <code>WordGame</code>
 * application.
 *
 * <p>
 * Date created: 6 Jan 2018 Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGamePreferences {

	/**
	 * Id for percentage of words that will be hidden.
	 */
	public final static int HIDE_PERCENTAGE_ID = 101;
	public final static int HIDE_PERCENTAGE_MINIMUM = 0;
	public final static int HIDE_PERCENTAGE_MAXIMUM = 100;
	public final static int HIDE_PERCENTAGE_DEFAULT = 20;
	public static final String HIDE_PERCENTAGE_LABEL = "Hide percentage threshold: ";
	/**
	 * Id for maximum consecutive words hidden threshold.
	 */
	public final static int MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_ID = 102;
	public final static int MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MINIMUM = 1;
	public final static int MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MAXIMUM = 5;
	public final static int MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_DEFAULT = 1;
	public static final String MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_LABEL = "Max consecutive words threshold: ";
	/**
	 * Id for minimum words in sentence threshold when hiding is applied.
	 */
	public final static int MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_ID = 103;
	public final static int MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MINIMUM = 1;
	public final static int MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MAXIMUM = 11;
	public final static int MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_DEFAULT = 3;
	public static final String MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_LABEL = "Min words in sentence threshold: ";
	/**
	 * Id for minimum letters in the word threshold when hiding is applied.
	 */
	public final static int MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_ID = 104;
	public final static int MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MINIMUM = 1;
	public final static int MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MAXIMUM = 9;
	public final static int MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_DEFAULT = 3;
	public static final String MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_LABEL = "Minimum letters in word threshold: ";
	
	/**
	 * Percentage of words that will be hidden.
	 */
	private int hidePercentage = HIDE_PERCENTAGE_DEFAULT;
	/**
	 * Maximum consecutive words hidden threshold.
	 */
	private int maxConsecutiveWordsHiddenThreshold = MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_DEFAULT;
	/**
	 * Minimum words in sentence threshold when hiding is applied.
	 */
	private int minWordsInSentenceHidingThreshold = MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_DEFAULT;
	/**
	 * Minimum letters in the word threshold when hiding is applied.
	 */
	private int minLettersInWordHidingThreshold = MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_DEFAULT;

	public int getHidePercentage() {
		return hidePercentage;
	}

	public void setHidePercentage(int hidePercentage) {
		this.hidePercentage = hidePercentage > HIDE_PERCENTAGE_MAXIMUM ? HIDE_PERCENTAGE_MAXIMUM
				: hidePercentage < HIDE_PERCENTAGE_MINIMUM ? HIDE_PERCENTAGE_MINIMUM
						: hidePercentage;
	}

	public int getMaxConsecutiveWordsThreshold() {
		return maxConsecutiveWordsHiddenThreshold;
	}

	public void setMaxConsecutiveWordsThreshold(
			int maxConsecutiveWordsHiddenThreshold) {
		this.maxConsecutiveWordsHiddenThreshold = maxConsecutiveWordsHiddenThreshold < MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MINIMUM ? MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MINIMUM
				: maxConsecutiveWordsHiddenThreshold > MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MAXIMUM ? MAX_CONSECUTIVE_WORDS_HIDDEN_THRESHOLD_MAXIMUM
						: maxConsecutiveWordsHiddenThreshold;
	}

	public int getMinWordsInSentenceThreshold() {
		return minWordsInSentenceHidingThreshold;
	}

	public void setMinWordsInSentenceThreshold(
			int minWordsInSentenceHidingThreshold) {
		this.minWordsInSentenceHidingThreshold = minWordsInSentenceHidingThreshold < MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MINIMUM ? MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MINIMUM
				: minWordsInSentenceHidingThreshold > MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MAXIMUM ? MIN_WORDS_IN_SENTENCE_HIDING_THRESHOLD_MAXIMUM
						: minWordsInSentenceHidingThreshold;
	}

	public int getMinLettersInWordThreshold() {
		return minLettersInWordHidingThreshold;
	}

	public void setMinLettersInWordThreshold(
			int minLettersInWordHidingThreshold) {
		this.minLettersInWordHidingThreshold = minLettersInWordHidingThreshold < MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MINIMUM ? MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MINIMUM
				: minLettersInWordHidingThreshold > MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MAXIMUM ? MIN_LETTERS_IN_WORD_HIDING_THRESHOLD_MAXIMUM
						: minLettersInWordHidingThreshold;
	}
}