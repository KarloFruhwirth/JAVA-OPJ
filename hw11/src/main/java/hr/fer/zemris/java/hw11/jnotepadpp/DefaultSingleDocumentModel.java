package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implements {@link SingleDocumentModel} and implements DocumentListener used
 * to notify about modifications
 * 
 * @author KarloFrühwirth
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * path of file
	 */
	private Path filePath;
	/**
	 * text of file
	 */
	private String text;
	/**
	 * JTextArea textArea
	 */
	private JTextArea textArea;
	/**
	 * is modified
	 */
	private boolean isModified = false;
	/**
	 * list of SingleDocumentListener
	 */
	private List<SingleDocumentListener> list = new ArrayList<>();

	/**
	 * Constructor for DefaultSingleDocumentModel
	 * 
	 * @param filePath
	 *            Path
	 * @param text
	 *            String
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath;
		this.text = text;
		textArea = new JTextArea(this.text);
		textArea.getDocument().addDocumentListener(new MyDocumentListener());
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath = path;
		list.forEach(a -> a.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.isModified != modified) {
			this.isModified = modified;
			list.forEach(a -> a.documentModifyStatusUpdated(this));
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		list.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		list.remove(l);
	}

	/**
	 * 
	 * Implementation of DocumentListener<br>
	 * If anything changes setModified(true)
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	class MyDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			setModified(true);
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			setModified(true);
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			setModified(true);
		}

	}

}
