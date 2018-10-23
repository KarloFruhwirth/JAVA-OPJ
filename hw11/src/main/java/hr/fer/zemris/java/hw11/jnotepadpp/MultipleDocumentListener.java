package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener for MultipleDocument
 * 
 * @author KarloFrühwirth
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Called when current document has been changed
	 * 
	 * @param previousModel
	 *            SingleDocumentModel
	 * @param currentModel
	 *            SingleDocumentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when current document has been added
	 * 
	 * @param model
	 *            SingleDocumentModel
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when current document has been removed
	 * 
	 * @param model
	 *            SingleDocumentModel
	 */
	void documentRemoved(SingleDocumentModel model);
}
