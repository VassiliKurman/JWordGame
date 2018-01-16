package vkurman.wordgame;

/**
 * <code>WordGameProcessor</code>
 *
 * <p>
 * Date created: 6 Jan 2018
 * Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGameProcessor {
	
	/**
	 * Text analyser.
	 */
	private WordGameTextAnalyser analyser;
	/**
	 * Original text
	 */
	private String originalText;
	
	/**
	 * Constructor.
	 * 
	 * @param preferences
	 */
	public WordGameProcessor(WordGameTextAnalyser analyser) {
		this.analyser = analyser;
	}

	/**
	 * Return text that has been passed to processor.
	 * 
	 * @return String
	 */
	public String getOriginalText() {
		return originalText;
	}
	
	/**
	 * Text processor.
	 * 
	 * @param text
	 * @return String
	 */
	public String process(WordGamePreferences preferences, String text) {
		if(text == null || text.isEmpty()) {
			return null;
		}
		
		originalText = text;
		
		return analyser.analyse(preferences, text);
	}
	
	/**
	 * Text processor.
	 * 
	 * @param text
	 * @return String
	 */
	public String reprocess(WordGamePreferences preferences) {
		return process(preferences, originalText);
	}
}