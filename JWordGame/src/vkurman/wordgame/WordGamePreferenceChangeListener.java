package vkurman.wordgame;

/**
 * <code>WordGamePreferenceChangeListener</code>
 *
 * <p>
 * Date created: 6 Jan 2018
 * Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public interface WordGamePreferenceChangeListener {
	
	/**
	 * Preference change notifier.
	 * 
	 * @param preferenceId
	 * @param oldValue
	 * @param newValue
	 */
	public void preferenceChanged(int preferenceId, int oldValue, int newValue);
	
}