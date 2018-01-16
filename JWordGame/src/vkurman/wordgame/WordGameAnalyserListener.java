package vkurman.wordgame;

/**
 * <code>WordGameAnalyserListener</code> listener for numbers in text.
 *
 * <p>
 * Date created: 15 Jan 2018
 * Date modified: 15 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public interface WordGameAnalyserListener {
	
	public static final int SENTENCES_IN_TEXT_ID = 201;
	public static final int SENTENCES_WITH_MINIMUM_WORDS_ID = 202;
	public static final int WORDS_IN_TEXT_ID = 203;
	public static final int WORDS_WITH_MINIMUM_LETTERS_ID = 204;
	public static final int WORDS_WITH_PERCENTAGE_APPLIED_ID = 205;
	public static final int WORDS_WITH_CONCECUTIVE_FILTER_APPLIED_ID = 206;
	public static final int WORDS_TOTAL_CAN_HIDE_ID = 207;
	
	/**
	 * Id can be either ELIGIBLE_WORDS_ID or FILTERED_WORDS_ID.
	 * 
	 * @param id
	 * @param value
	 */
	public void changeCalculated(int id, int value);
	
}