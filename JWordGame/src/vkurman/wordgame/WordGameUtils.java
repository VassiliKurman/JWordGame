package vkurman.wordgame;

import java.io.File;
import java.util.regex.Pattern;

/**
 * <code>WordGameUtils</code>
 *
 * <p>
 * Date created: 6 Jan 2018 Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGameUtils {

	/**
	 * EXTENSION_TXT - text file extension
	 */
	public final static String EXTENSION_TXT = "txt";

	/**
	 * Returns extension of specified file.
	 * 
	 * @param file
	 * @return String
	 */
	public static String getExtension(File file) {
		String extension = null;
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');

		if (index > 0 && index < fileName.length() - 1) {
			extension = fileName.substring(index + 1).toLowerCase();
		}

		return extension;
	}

	/**
	 * Checks if provided character is a letter.
	 * 
	 * @param c
	 * @return boolean
	 */
	public static boolean isLetter(char c) {
		return Pattern.compile("[a-zA-Z]").matcher(Character.toString(c))
				.matches();
	}

	/**
	 * Decomposing text to sentences omitting end of sentence character.
	 * 
	 * @param text
	 * @return String[]
	 */
	public static String[] decomposeToSentences(String text) {
		if (text == null || text.isEmpty()) {
			return null;
		}
		String regex = "[.!?]";
		return text.split(regex);
	}

	/**
	 * Decomposing sentences into words omitting spaces. Characters, like
	 * come(',') are included in the word String.
	 * 
	 * @param text
	 * @return String[]
	 */
	public static String[] decomposeToWords(String sentence) {
		if (sentence == null || sentence.isEmpty()) {
			return null;
		}

		String regex = "\\s";
		return sentence.split(regex);
	}
}