package vkurman.wordgame;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * <code>WordGameFileFilter</code> is a text<code>FileFilter</code>.
 *
 * <p>
 * Date created: 6 Jan 2018
 * Date modified: 6 Jan 2018
 *
 * @author Vassili Kurman
 * @version 1.0
 */
public class WordGameFileFilter extends FileFilter {
	
	@Override
	public String getDescription() {
		return "Text file - *.txt";
	}
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		
		String extension = WordGameUtils.getExtension(f);
		if(extension != null) {
			if(extension.equals(WordGameUtils.EXTENSION_TXT)) {
				return true;
			}
		}
		
		return false;
	}
}