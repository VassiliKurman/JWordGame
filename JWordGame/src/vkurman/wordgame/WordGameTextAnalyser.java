package vkurman.wordgame;

/**
 * <code>WordGameTextAnalyser</code>
 *
 * <p>
 * Date created: 14 Jan 2018
 * Date modified: 14 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public interface WordGameTextAnalyser {
	
	/**
	 * Hidden chars will be replaced by this char.
	 */
	public static final char REPLACEMENT_CHAR = 'x';
	/**
	 * Empty space char.
	 */
	public static final char EMPTY_SPACE_CHAR = ' ';
	/**
	 * End of sentence chars.
	 */
	public static final char[] END_OF_SENTENCE__CHARS = {'.', '!', '?'};
	
	/**
	 * Analyses and splits provided text into workable fragments.
	 * 
	 * @param prefs
	 * @param text
	 * @return String
	 */
	public String analyse(WordGamePreferences prefs, String text);
	
	/**
	 * Sets <code>WordGameAnalyserListener</code>
	 * 
	 * @param listener
	 */
	public void setWordGameAnalyserListener(WordGameAnalyserListener listener);
	
}