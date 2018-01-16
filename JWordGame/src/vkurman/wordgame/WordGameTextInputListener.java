package vkurman.wordgame;

/**
 * <code>WordGameTextInputListener</code> listener for new text.
 *
 * <p>
 * Date created: 6 Jan 2018
 * Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public interface WordGameTextInputListener {
	
	/**
	 * Text has been changed.
	 * 
	 * @param text
	 */
	public void textChanged(String text);
	
}