package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implements {@link MultipleDocumentModel} and extends {@link JTabbedPane}<br>
 * It implements the methods essential for the usage of {@link JNotepadPP}. <br>
 * Contains SingleDocumentModel which are shown in tabs.
 * 
 * @author KarloFrühwirth
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * index of new file
	 */
	private int index = 1;
	/**
	 * list of SingleDocumentModel
	 */
	private List<SingleDocumentModel> listSingleDocuments = new ArrayList<>();
	/**
	 * list of MultipleDocumentListener
	 */
	private List<MultipleDocumentListener> listMultipleDocuments = new ArrayList<>();
	/**
	 * index of active SingleDocumentModel
	 */
	private int active = 0;
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for DefaultMultipleDocumentModel, adds ChangeListener
	 */
	public DefaultMultipleDocumentModel() {
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (listSingleDocuments.size() > 0) {

					active = getSelectedIndex();
					if (active >= 0)
						listMultipleDocuments
								.forEach(a -> a.currentDocumentChanged(null, listSingleDocuments.get(active)));
				} else {
					return;
				}
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return listSingleDocuments.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel sd = new DefaultSingleDocumentModel(null, null);
		this.addTab(String.format("New%d", index), getImage("icons/not-changed.png"),
				new JScrollPane(sd.getTextComponent()), String.format("New%d", index));
		index++;
		listSingleDocuments.add(sd);
		listMultipleDocuments.forEach(a -> a.documentAdded(sd));
		listMultipleDocuments.forEach(a -> a.currentDocumentChanged(null, sd));
		sd.addSingleDocumentListener(new MySingleDocumentListener());
		this.setSelectedIndex(listSingleDocuments.indexOf(sd));
		return sd;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		if (active >= 0) {
			SingleDocumentModel sd = listSingleDocuments.get(active);
			this.setSelectedIndex(listSingleDocuments.indexOf(sd));
			return sd;
		} else {
			active = 0;
			SingleDocumentModel sd = listSingleDocuments.get(active);
			this.setSelectedIndex(listSingleDocuments.indexOf(sd));
			return sd;
		}
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel sd = new DefaultSingleDocumentModel(path, text);
		this.addTab(path.getFileName().toString(), getImage("icons/not-changed.png"),
				new JScrollPane(sd.getTextComponent()), path.toAbsolutePath().toString());

		listSingleDocuments.add(sd);
		this.setSelectedIndex(listSingleDocuments.indexOf(sd));
		listMultipleDocuments.forEach(a -> {
			a.documentAdded(sd);
			a.currentDocumentChanged(null, sd);
		});
		sd.addSingleDocumentListener(new MySingleDocumentListener());
		return sd;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
			model.setModified(false);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		listMultipleDocuments.forEach(a -> a.currentDocumentChanged(null, model));
		int index = listSingleDocuments.indexOf(model);
		this.setTitleAt(index, newPath.getFileName().toString());
		this.setToolTipTextAt(index, newPath.toAbsolutePath().toString());
		this.setIconAt(index, getImage("icons/not-changed.png"));
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = listSingleDocuments.indexOf(model);
		this.removeTabAt(index);
		if (index > 0) {
			this.setSelectedIndex(index - 1);
			listSingleDocuments.remove(model);
		} else {
			if (this.getTabCount() == 0) {
				listSingleDocuments.remove(model);
				listMultipleDocuments.forEach(l -> {
					l.documentRemoved(model);
					l.currentDocumentChanged(null, null);
				});
				return;
			}
			this.setSelectedIndex(listSingleDocuments.indexOf(model));
			listSingleDocuments.remove(model);
		}
		listMultipleDocuments.forEach(l -> {
			l.documentRemoved(model);
			l.currentDocumentChanged(null, model);
		});

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listMultipleDocuments.add(l);

	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listMultipleDocuments.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return listSingleDocuments.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return listSingleDocuments.get(index);
	}

	/**
	 * Returns image based on the string inputed
	 * 
	 * @param string
	 *            path of image
	 * @return ImageIcon
	 * @throws IllegalArgumentException
	 *             if InputStream is null
	 */
	private ImageIcon getImage(String string) {
		InputStream is = this.getClass().getResourceAsStream(string);
		if (is == null)
			throw new IllegalArgumentException();
		try {
			byte[] bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method used to set title of {@link JNotepadPP}
	 * 
	 * @return title
	 */
	public String title() {
		return getTitleAt(listSingleDocuments.indexOf(this.getCurrentDocument()));
	}

	/**
	 * Implementation of SingleDocumentListener<br>
	 * used to update icon/title/toolTip
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	class MySingleDocumentListener implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = listSingleDocuments.indexOf(model);
			setIconAt(index, getImage("icons/changed.png"));
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = listSingleDocuments.indexOf(model);
			setTitleAt(index, listSingleDocuments.get(index).getFilePath().getFileName().toString());
			setToolTipTextAt(index, listSingleDocuments.get(index).getFilePath().toAbsolutePath().toString());
		}

	}

}
