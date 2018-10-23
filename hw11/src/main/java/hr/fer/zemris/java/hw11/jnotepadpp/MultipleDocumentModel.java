package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface which defines the methods used by
 * {@link DefaultMultipleDocumentModel}
 * 
 * @author KarloFrühwirth
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new document
	 * 
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Gets current document
	 * 
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document
	 * 
	 * @param path
	 *            Path
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves document
	 * 
	 * @param model
	 *            SingleDocumentModel
	 * @param newPath
	 *            Path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes document
	 * 
	 * @param model
	 *            SingleDocumentModel
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds MultipleDocumentListener
	 * 
	 * @param l
	 *            MultipleDocumentListener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes MultipleDocumentListener
	 * 
	 * @param l
	 *            MultipleDocumentListener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Gets number of documents
	 * 
	 * @return number of documents opend
	 */
	int getNumberOfDocuments();

	/**
	 * Gets document at index
	 * 
	 * @param index
	 *            int
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel getDocument(int index);
}
