package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface providing methods for {@link DefaultSingleDocumentModel}
 * 
 * @author KarloFrühwirth
 *
 */
public interface SingleDocumentModel {
	/**
	 * Gets JTextArea
	 * 
	 * @return JTextArea
	 */
	JTextArea getTextComponent();

	/**
	 * Gets file path
	 * 
	 * @return Path
	 */
	Path getFilePath();

	/**
	 * Sets file path
	 * 
	 * @param path
	 *            Path
	 */
	void setFilePath(Path path);

	/**
	 * Returns if modified is true
	 * 
	 * @return boolean
	 */
	boolean isModified();

	/**
	 * sets modified
	 * 
	 * @param modified
	 *            boolean
	 */
	void setModified(boolean modified);

	/**
	 * Adds SingleDocumentListener
	 * 
	 * @param l
	 *            SingleDocumentListener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	void removeSingleDocumentListener(SingleDocumentListener l);
}
