package vkurman.wordgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <code>WordGameEnglishTextAnalyser</code>
 *
 * <p>
 * Date created: 14 Jan 2018 Date modified: 14 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGameEnglishTextAnalyser implements WordGameTextAnalyser {

	private WordGamePreferences prefs;
	private StringBuffer processedText;
	private WordGameAnalyserListener wordGameAnalyserListener;
	
	/**
	 * Number of eligible to process sentences for listener
	 */
	private int eligibleSentences = 0;
	/**
	 * Total number of words in text for listener
	 */
	private int totalWords = 0;

	@Override
	public String analyse(WordGamePreferences preferences, String text) {
		this.prefs = preferences;

		this.processedText = new StringBuffer(text);
		
		if (prefs != null) {
			
			eligibleSentences = 0;
			totalWords = 0;
			
			// Getting sentences pointers
			List<SentenceFlag> sentences = retrieveSentences(processedText);
			// If list of pointers for sentences empty return unedited text.
			if (sentences == null || sentences.isEmpty()) {
				return processedText.toString();
			}

			// Creating list of word pointers
			List<WordFlag> words = new ArrayList<>();
			// Creating temporary list of word pointers
			List<WordFlag> tempWords = new ArrayList<>();
			for (SentenceFlag sentence : sentences) {
				tempWords = retrieveEligibleWords(processedText, sentence);
				// Checking if list is not empty
				if (tempWords != null && !tempWords.isEmpty()) {
					eligibleSentences++;
					words.addAll(tempWords);
					// Clearing temporary list for next iteration
					tempWords.clear();
				}
			}
			
			// Notifying listener
			if (wordGameAnalyserListener != null) {
				wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.SENTENCES_WITH_MINIMUM_WORDS_ID,
						eligibleSentences);
			}
			
			// Notifying listener
			if (wordGameAnalyserListener != null) {
				wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.WORDS_IN_TEXT_ID,
						totalWords);
			}
			// Notifying listener
			if (wordGameAnalyserListener != null) {
				wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.WORDS_WITH_MINIMUM_LETTERS_ID,
						words.size());
			}

			// Creating pattern to mark eligible words for hiding
			boolean[] pattern = createPattern(words);
			// Applying pattern to list of eligible words
			for (int i = 0; i < pattern.length; i++) {
				if (pattern[i]) {
					for (int w = words.get(i).wordStart; w <= words.get(i).wordEnd; w++) {
						processedText.setCharAt(w, REPLACEMENT_CHAR);
					}
				}
			}
		}

		this.prefs = null;

		return processedText.toString();
	}

	/**
	 * Retrieving points for ALL sentences in the text.
	 * 
	 * @param text
	 * @return List<SentenceFlag>
	 */
	private List<SentenceFlag> retrieveSentences(StringBuffer text) {
		// input checks
		if (text == null || text.length() == 0) {
			return null;
		}

		List<SentenceFlag> sentences = new ArrayList<>();
		// Sentence start index
		int start = 0;

		for (int i = 0; i < text.length(); i++) {
			// Checking start of text that it is not empty space
			if (i == 0) {
				while (true) {
					if (text.charAt(start) == EMPTY_SPACE_CHAR) {
						// If reaching end of text and can't find any letter
						// than exit method
						if (++start >= text.length()) {
							return null;
						}
					} else {
						break;
					}
				}
			}

			for (char c : END_OF_SENTENCE__CHARS) {
				if (text.charAt(i) == c) {
					// Checking that start before end
					if (start < i) {
						// Adding sentence
						sentences.add(new SentenceFlag(start, i));
						// Incrementing start for next sentence
						start = i + 1;
						// Looking for next sentence start
						while (true) {
							if (start >= text.length()) {
								return sentences;
							} else {
								if (text.charAt(start) == EMPTY_SPACE_CHAR) {
									start++;
								} else {
									// Break loop
									break;
								}
							}
						}
					}
				}
			}
		}

		// Notifying listener
		if (wordGameAnalyserListener != null) {
			wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.SENTENCES_IN_TEXT_ID,
					sentences.size());
		}
		
		return sentences;
	}

	/**
	 * Retrieving points for each word in sentence. Complies with the letters in
	 * word requirement and words in sentence requirements.
	 * 
	 * @param text
	 * @param sentence
	 * @return List<WordFlag>
	 */
	private List<WordFlag> retrieveEligibleWords(StringBuffer text, SentenceFlag sentence) {
		// input checks
		if (text == null || text.length() == 0 || sentence == null) {
			return null;
		}

		List<WordFlag> words = new ArrayList<>();
		// Word start index
		int start = sentence.sentenceStart;

		// Loop through sentence to find first letter
		for (int i = sentence.sentenceStart; i <= sentence.sentenceEnd; i++) {

			// Checking start of sentence that it is not empty space
			if (i == sentence.sentenceStart) {
				// TODO code repetitive bellow
				while (true) {
					// Finding first letter in sentence for first word
					if (!WordGameUtils.isLetter(text.charAt(i))) {
						// If reaching end of sentence and can't find any letter
						// than exit method
						if (++i >= text.length()) {
							return null;
						}
					} else {
						// Marking start of first word
						start = i;
						break;
					}
				}
			}

			// Checking end of word where is char is not a letter [a-zA-Z].
			if (!WordGameUtils.isLetter(text.charAt(i))) {
				// Checking that start before last letter found
				if (start <= i - 1) {
					// Incrementing word counter
					totalWords++;
					// Adding word if meets REQUIREMENT
					if (i - start >= prefs.getMinLettersInWordThreshold()) {
						words.add(new WordFlag(start, i - 1));
					}
					// Incrementing start for next word
					start = i;
					// Looking for next word start
					while (true) {
						// End of sentence, than return from method
						if (start >= text.length()) {
							// Checking REQUIREMENT for words in sentence
							return (words.size() >= prefs.getMinWordsInSentenceThreshold()) ? words : null;
						} else {
							if (!WordGameUtils.isLetter(text.charAt(start))) {
								start++;
							} else {
								// Next letter found. Set index to start and
								// break loop
								i = start;
								break;
							}
						}
					}
//				} else if (start == i - 1) {
//					// Handling single char word here TODO code repetitive above
//					start = i;
//					// Incrementing word counter for single letter
//					totalWords++;
//					while (true) {
//						// Finding first letter in sentence for first word
//						if (!WordGameUtils.isLetter(text.charAt(start))) {
//							// If reaching end of sentence and can't find any
//							// letter
//							// than exit method
//							if (++i >= text.length()) {
//								return null;
//							}
//						} else {
//							// Marking start of first word
//							start = i;
//							break;
//						}
//					}
				}
			}
		} // End of sentence loop

		// Checking REQUIREMENT for words in sentence
		return (words.size() >= prefs.getMinWordsInSentenceThreshold()) ? words : null;
	}

	/**
	 * Creates pattern to hide eligible words.
	 * 
	 * @param words
	 * @return boolean[]
	 */
	private boolean[] createPattern(List<WordFlag> words) {
		int totalWords = words.size();
		int eligibleToHideWords = calculateWordsToHide(totalWords);

		boolean[] pattern = new boolean[totalWords];
		// Index in pattern to be checked
		int indexToHide;

		Random random = new Random();
		for (int i = eligibleToHideWords; i > 0; i--) {
			while (true) {
				indexToHide = random.nextInt(totalWords);
				if (isAllowedToHide(words, pattern, indexToHide)) {
					pattern[indexToHide] = true;
					// Break while loop
					break;
				}
			}
		}

		return pattern;
	}

	/**
	 * Final calculations how many words to hide.
	 * 
	 * @param total - eligible to hide words after min words in sentence and min letters in words applied
	 * @return int
	 */
	private int calculateWordsToHide(int total) {
		int result = 0;

		int maxConsecutiveWordsHide = total / (prefs.getMaxConsecutiveWordsThreshold() + 2)
				+ (total % prefs.getMaxConsecutiveWordsThreshold());
		// Notifying listener
		if (wordGameAnalyserListener != null) {
			wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.WORDS_WITH_CONCECUTIVE_FILTER_APPLIED_ID,
					maxConsecutiveWordsHide);
		}

		// REQUIREMENT Using prefs percentage
		int totalEligibleToHideByPercentage = total * prefs.getHidePercentage() / 100;
		// Notifying listener
		if (wordGameAnalyserListener != null) {
			wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.WORDS_WITH_PERCENTAGE_APPLIED_ID,
					totalEligibleToHideByPercentage);
		}

		result = Math.min(maxConsecutiveWordsHide, totalEligibleToHideByPercentage);
		// Notifying listener
		if (wordGameAnalyserListener != null) {
			wordGameAnalyserListener.changeCalculated(WordGameAnalyserListener.WORDS_TOTAL_CAN_HIDE_ID, result);
		}

		return result;
	}

	/**
	 * Checking if at specified position it is allowed to hide the word.
	 * 
	 * @param words
	 * @param pattern
	 * @param index
	 * @return boolean
	 */
	private boolean isAllowedToHide(List<WordFlag> words, boolean[] pattern, int index) {
		if (pattern[index]) {
			return false;
		}

		int maxConsecutiveWords = prefs.getMaxConsecutiveWordsThreshold();
		// Counter for consecutive words if current index will be marked
		int consecutiveWordsCounter = 1;

		int curIndex = index - 1;
		// Checking previous in pattern
		if (curIndex >= 0) {
			while (true) {
				if (pattern[curIndex] && isNextWord(words.get(curIndex), words.get(curIndex + 1))) {
					consecutiveWordsCounter++;
					curIndex--;
					if (consecutiveWordsCounter > maxConsecutiveWords) {
						return false;
					}
					// Check for index out of bounds
					if (curIndex < 0) {
						break;
					}
				} else {
					break;
				}
			}
		}
		// Checking next in pattern
		curIndex = index + 1;
		if (curIndex < pattern.length) {
			while (true) {
				if (pattern[curIndex] && isNextWord(words.get(curIndex - 1), words.get(curIndex))) {
					consecutiveWordsCounter++;
					curIndex++;
					if (consecutiveWordsCounter > maxConsecutiveWords) {
						return false;
					}
					// Check for index out of bounds
					if (curIndex >= pattern.length) {
						break;
					}
				} else {
					break;
				}
			}
		}

		return true;
	}

	private boolean isNextWord(WordFlag current, WordFlag next) {
		return (next.wordStart - 2 == current.wordEnd) ? true
				: (hasLetterBetween(next.wordStart, current.wordEnd)) ? false : true;
	}

	private boolean hasLetterBetween(int start, int end) {
		for (int i = start + 1; i < end; i++) {
			if (WordGameUtils.isLetter(processedText.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setWordGameAnalyserListener(WordGameAnalyserListener listener) {
		wordGameAnalyserListener = listener;
	}
}

/**
 * <code>SentenceFlag</code> flag where sentence starts and ends in the text.
 * Both start index and last index are included in the sentence.
 *
 * <p>
 * Date created: 14 Jan 2018 Date modified: 14 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
class SentenceFlag {
	/**
	 * Start index included.
	 */
	int sentenceStart;
	/**
	 * End index included
	 */
	int sentenceEnd;

	SentenceFlag(int sentenceStart, int sentenceEnd) {
		this.sentenceStart = sentenceStart;
		this.sentenceEnd = sentenceEnd;
	}
}

/**
 * <code>WordFlag</code> flag where the word starts and ends in the sentence.
 * Both start index and last index are included in the word.
 *
 * <p>
 * Date created: 14 Jan 2018 Date modified: 14 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
class WordFlag {
	/**
	 * Start index included.
	 */
	int wordStart;
	/**
	 * End index included
	 */
	int wordEnd;

	WordFlag(int wordStart, int wordEnd) {
		this.wordStart = wordStart;
		this.wordEnd = wordEnd;
	}
}