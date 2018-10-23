package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * SingleDocument listener which calls appropriate methods when a change occurs
 * 
 * @author KarloFrühwirth
 *
 */
public interface SingleDocumentListener {
	/**
	 * Document under SingleDocumentModel is modified
	 * 
	 * @param model
	 *            SingleDocumentModel
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Update file path of SingleDocumentModel
	 * 
	 * @param model
	 *            SingleDocumentModel
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
