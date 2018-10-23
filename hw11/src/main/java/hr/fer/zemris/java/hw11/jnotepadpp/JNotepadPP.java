package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * Class representing our implementation of JNotepad++
 * 
 * @author KarloFrühwirth
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * MultipleDocumentModel tabbedPane
	 */
	private MultipleDocumentModel tabbedPane;
	/**
	 * Clipboard clipboard
	 */
	private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	/**
	 * StringSelection cut_copy
	 */
	private StringSelection cut_copy;
	/**
	 * name of tab
	 */
	private String name = "";
	/**
	 * JStatusBar JStatusBar
	 */
	private JStatusBar statusBar;
	/**
	 * FormLocalizationProvider
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/**
	 * language
	 */
	private String language;

	/**
	 * Constructor for JNotepadPP
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepad++");
		initGUI();
	}

	/**
	 * Initializes the GUI for JNotepadPP
	 */
	private void initGUI() {

		tabbedPane = new DefaultMultipleDocumentModel();
		JPanel panel = new JPanel(new BorderLayout());
		this.getContentPane().setLayout(new BorderLayout());

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exitFileAction.actionPerformed(null);
			}
		});

		createActions();
		createMenues();
		createToolbars();

		statusBar = new JStatusBar(new GridLayout(1, 3));
		panel.add(statusBar, BorderLayout.PAGE_END);
		panel.add((Component) tabbedPane, BorderLayout.CENTER);

		this.getContentPane().add(panel, BorderLayout.CENTER);
		tabbedPane.addMultipleDocumentListener(new MyMultipleDocumentListener());

		setActionsState(false);
		setSelectedActionsState(false);

	}

	/**
	 * Sets actions to b
	 * 
	 * @param b
	 *            boolean
	 */
	private void setActionsState(boolean b) {
		saveDocumentAction.setEnabled(b);
		saveAsDocumentAction.setEnabled(b);
		cutSelectedPartAction.setEnabled(b);
		copySelectedPartAction.setEnabled(b);
		pasteSelectedPartAction.setEnabled(b);
		closeDocumentAction.setEnabled(b);
		statsSelectedPartAction.setEnabled(b);
	}

	/**
	 * Sets actions to b
	 * 
	 * @param b
	 *            boolean
	 */
	private void setSelectedActionsState(boolean b) {
		uppercase.setEnabled(b);
		lowercase.setEnabled(b);
		changecase.setEnabled(b);
		ascendingSort.setEnabled(b);
		descendingSort.setEnabled(b);
		uniqueAction.setEnabled(b);
	}

	/**
	 * implementation of MultipleDocumentListener when currentDocumentChanges sets
	 * the status bar and title dynamically
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	class MyMultipleDocumentListener implements MultipleDocumentListener {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if (currentModel == null) {
				setActionsState(false);
				setTitle("JNotepad++");
				updateStatus(1, 0, null);
			} else if (currentModel.getFilePath() == null) {
				name = ((DefaultMultipleDocumentModel) tabbedPane).title();
				setTitle(name + " - JNotepad++");
			} else {
				setTitle(currentModel.getFilePath().toString() + " - JNotepad++");
			}
			if (currentModel != null) {
				setActionsState(true);
				JTextArea text = currentModel.getTextComponent();
				try {
					updateStatus(text.getLineOfOffset(text.getCaretPosition()) + 1, text.getCaretPosition()
							- text.getLineStartOffset(text.getLineOfOffset(text.getCaretPosition())), text);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				currentModel.getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent arg0) {
						checkSelected(currentModel);
						JTextArea textArea = (JTextArea) arg0.getSource();
						int line = 1;
						int column = 1;
						try {
							int position = textArea.getCaretPosition();
							line = textArea.getLineOfOffset(position);
							column = position - textArea.getLineStartOffset(line);
							line += 1;
						} catch (Exception e) {
						}
						updateStatus(line, column, textArea);
					}

				});
				currentModel.getTextComponent().addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseMoved(MouseEvent arg0) {
					}

					@Override
					public void mouseDragged(MouseEvent arg0) {
						checkSelected(currentModel);
						statusBar.setSelected(" Sel: " + Math.abs(currentModel.getTextComponent().getCaret().getDot()
								- currentModel.getTextComponent().getCaret().getMark()));
					}
				});
			}

		}

		/**
		 * Method used to check if there is any selected text
		 * 
		 * @param currentModel
		 *            SingleDocumentModel
		 */
		protected void checkSelected(SingleDocumentModel currentModel) {
			if (Math.abs(currentModel.getTextComponent().getCaret().getDot()
					- currentModel.getTextComponent().getCaret().getMark()) != 0) {
				setSelectedActionsState(true);
			} else {
				setSelectedActionsState(false);
			}

		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
		}

	}

	/**
	 * Update info of status bar when currentDocumentChanged
	 * 
	 * @param line
	 *            line
	 * @param column
	 *            column
	 * @param textArea
	 *            textArea
	 */
	private void updateStatus(int line, int column, JTextArea textArea) {

		statusBar.setLenght(flp.getString("length") + (textArea == null ? "0" : textArea.getText().length()));
		statusBar.setLine("Ln: " + line);
		statusBar.setColumn(" Col: " + column);
		statusBar.setSelected(" Sel: "
				+ (textArea == null ? "0" : Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark())));
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
	 * Sets language to lang
	 * 
	 * @param lang
	 *            String
	 */
	private void setLanguage(String lang) {
		this.language = lang;
		LocalizationProvider.getInstance().setLanguage(language);
	}

	/**
	 * Sets icons for actions
	 */
	private void createActions() {
		createDocumendAction.putValue(Action.SMALL_ICON, getImage("icons/new-file.png"));
		openDocumendAction.putValue(Action.SMALL_ICON, getImage("icons/open.png"));
		saveDocumentAction.putValue(Action.SMALL_ICON, getImage("icons/save.png"));
		saveAsDocumentAction.putValue(Action.SMALL_ICON, getImage("icons/save-as.png"));
		closeDocumentAction.putValue(Action.SMALL_ICON, getImage("icons/close.png"));
		cutSelectedPartAction.putValue(Action.SMALL_ICON, getImage("icons/cut.png"));
		copySelectedPartAction.putValue(Action.SMALL_ICON, getImage("icons/copy.png"));
		pasteSelectedPartAction.putValue(Action.SMALL_ICON, getImage("icons/paste.png"));
		statsSelectedPartAction.putValue(Action.SMALL_ICON, getImage("icons/stats.png"));
		exitFileAction.putValue(Action.SMALL_ICON, getImage("icons/exit.png"));
		englishLanguage.putValue(Action.SMALL_ICON, getImage("icons/eng.png"));
		germainLanguage.putValue(Action.SMALL_ICON, getImage("icons/ger.png"));
		croatianLanguage.putValue(Action.SMALL_ICON, getImage("icons/cro.png"));
		spanishLanguage.putValue(Action.SMALL_ICON, getImage("icons/esp.png"));
	}

	/**
	 * Creates toolbar
	 */
	private void createToolbars() {
		JToolBar toolbar = new JToolBar("ToolBar");
		toolbar.setFloatable(true);

		JButton addNew = new JButton(createDocumendAction);
		addNew.setIcon((Icon) createDocumendAction.getValue(Action.SMALL_ICON));
		addNew.setHideActionText(true);
		toolbar.add(addNew);

		JButton open = new JButton(openDocumendAction);
		open.setIcon((Icon) openDocumendAction.getValue(Action.SMALL_ICON));
		open.setHideActionText(true);
		toolbar.add(open);

		JButton save = new JButton(saveDocumentAction);
		save.setIcon((Icon) saveDocumentAction.getValue(Action.SMALL_ICON));
		save.setHideActionText(true);
		toolbar.add(save);

		JButton saveAs = new JButton(saveAsDocumentAction);
		saveAs.setIcon((Icon) saveAsDocumentAction.getValue(Action.SMALL_ICON));
		saveAs.setHideActionText(true);
		toolbar.add(saveAs);

		JButton close = new JButton(closeDocumentAction);
		close.setIcon((Icon) closeDocumentAction.getValue(Action.SMALL_ICON));
		close.setHideActionText(true);
		toolbar.add(close);

		toolbar.addSeparator();

		JButton cut = new JButton(cutSelectedPartAction);
		cut.setIcon((Icon) cutSelectedPartAction.getValue(Action.SMALL_ICON));
		cut.setHideActionText(true);
		toolbar.add(cut);

		JButton copy = new JButton(copySelectedPartAction);
		copy.setIcon((Icon) copySelectedPartAction.getValue(Action.SMALL_ICON));
		copy.setHideActionText(true);
		toolbar.add(copy);

		JButton paste = new JButton(pasteSelectedPartAction);
		paste.setIcon((Icon) pasteSelectedPartAction.getValue(Action.SMALL_ICON));
		paste.setHideActionText(true);
		toolbar.add(paste);

		toolbar.addSeparator();

		JButton stats = new JButton(statsSelectedPartAction);
		stats.setIcon((Icon) statsSelectedPartAction.getValue(Action.SMALL_ICON));
		stats.setHideActionText(true);
		toolbar.add(stats);

		toolbar.addSeparator();

		JButton exit = new JButton(exitFileAction);
		exit.setIcon((Icon) exitFileAction.getValue(Action.SMALL_ICON));
		exit.setHideActionText(true);
		toolbar.add(exit);

		toolbar.addSeparator();

		JButton eng = new JButton(englishLanguage);
		eng.setIcon((Icon) englishLanguage.getValue(Action.SMALL_ICON));
		eng.setHideActionText(true);
		toolbar.add(eng);

		JButton ger = new JButton(germainLanguage);
		ger.setIcon((Icon) germainLanguage.getValue(Action.SMALL_ICON));
		ger.setHideActionText(true);
		toolbar.add(ger);

		JButton cro = new JButton(croatianLanguage);
		cro.setIcon((Icon) croatianLanguage.getValue(Action.SMALL_ICON));
		cro.setHideActionText(true);
		toolbar.add(cro);

		JButton esp = new JButton(spanishLanguage);
		esp.setIcon((Icon) spanishLanguage.getValue(Action.SMALL_ICON));
		esp.setHideActionText(true);
		toolbar.add(esp);

		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);

	}

	/**
	 * Creates menu
	 */
	private void createMenues() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(fileMenu);

		JMenuItem addNew = new JMenuItem(createDocumendAction);
		addNew.setIcon(null);
		fileMenu.add(addNew);

		JMenuItem open = new JMenuItem(openDocumendAction);
		open.setIcon(null);
		fileMenu.add(open);

		JMenuItem save = new JMenuItem(saveDocumentAction);
		save.setIcon(null);
		fileMenu.add(save);

		JMenuItem saveAs = new JMenuItem(saveAsDocumentAction);
		saveAs.setIcon(null);
		fileMenu.add(saveAs);

		JMenuItem close = new JMenuItem(closeDocumentAction);
		close.setIcon(null);
		fileMenu.add(close);

		JMenuItem exit = new JMenuItem(exitFileAction);
		exit.setIcon(null);
		fileMenu.add(exit);

		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(editMenu);

		JMenuItem cut = new JMenuItem(cutSelectedPartAction);
		cut.setIcon(null);
		editMenu.add(cut);

		JMenuItem copy = new JMenuItem(copySelectedPartAction);
		copy.setIcon(null);
		editMenu.add(copy);

		JMenuItem paste = new JMenuItem(pasteSelectedPartAction);
		paste.setIcon(null);
		editMenu.add(paste);

		JMenu statsMenu = new JMenu(statsSelectedPartAction);
		statsMenu.setIcon(null);
		menuBar.add(statsMenu);

		JMenuItem stats = new JMenuItem(statsSelectedPartAction);
		stats.setIcon(null);
		statsMenu.add(stats);

		JMenu languageMenu = new JMenu(new LocalizableAction("languages", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(languageMenu);

		JMenuItem eng = new JMenuItem(englishLanguage);
		eng.setIcon(null);
		languageMenu.add(eng);

		JMenuItem ger = new JMenuItem(germainLanguage);
		ger.setIcon(null);
		languageMenu.add(ger);

		JMenuItem cro = new JMenuItem(croatianLanguage);
		cro.setIcon(null);
		languageMenu.add(cro);

		JMenuItem esp = new JMenuItem(spanishLanguage);
		esp.setIcon(null);
		languageMenu.add(esp);

		JMenu tools = new JMenu(new LocalizableAction("tools", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(tools);

		JMenu caseChange = new JMenu(changecase);
		caseChange.setIcon(null);
		tools.add(caseChange);

		JMenuItem up = new JMenuItem(uppercase);
		caseChange.add(up);

		JMenuItem low = new JMenuItem(lowercase);
		caseChange.add(low);

		JMenuItem change = new JMenuItem(changecase);
		caseChange.add(change);

		JMenu sort = new JMenu(new LocalizableAction("sort", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		tools.add(sort);

		JMenuItem asc = new JMenuItem(ascendingSort);
		sort.add(asc);

		JMenuItem desc = new JMenuItem(descendingSort);
		sort.add(desc);

		JMenuItem unique = new JMenuItem(uniqueAction);
		tools.add(unique);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates new LocalizableAction for englishLanguage
	 */
	private Action englishLanguage = new LocalizableAction("english", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setLanguage("en");
		}

	};

	/**
	 * Creates new LocalizableAction for germainLanguage
	 */
	private Action germainLanguage = new LocalizableAction("german", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setLanguage("de");
		}

	};

	/**
	 * Creates new LocalizableAction for croatianLanguage
	 */
	private Action croatianLanguage = new LocalizableAction("croatian", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setLanguage("hr");
		}

	};

	/**
	 * Creates new LocalizableAction for spanishLanguage
	 */
	private Action spanishLanguage = new LocalizableAction("spanish", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setLanguage("es");
		}

	};

	/**
	 * Creates new LocalizableAction for uniqueAction
	 */
	private Action uniqueAction = new LocalizableAction("uniqueAction", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			JTextArea area = sd.getTextComponent();
			Document doc = area.getDocument();
			int len = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
			try {
				offset = area.getLineStartOffset(area.getLineOfOffset(offset));
				len = area.getLineEndOffset(area.getLineOfOffset(len + offset));
				String text = doc.getText(offset, len - offset);
				Set<String> unique = new LinkedHashSet<>(Arrays.asList(text.split("\\n")));
				doc.remove(offset, len - offset);
				for (String string : unique) {
					doc.insertString(offset, string + "\n", null);
					offset += string.length() + 1;
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	};

	/**
	 * Creates new LocalizableAction for descendingSort
	 */
	private Action descendingSort = new LocalizableAction("descendingSort", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			JTextArea area = sd.getTextComponent();
			Document doc = area.getDocument();
			int len = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
			try {
				offset = area.getLineStartOffset(area.getLineOfOffset(offset));
				len = area.getLineEndOffset(area.getLineOfOffset(len + offset));
				String text = doc.getText(offset, len - offset);
				List<String> sort = Arrays.asList(text.split("\\n"));
				Collator collator = Collator
						.getInstance(new Locale(LocalizationProvider.getInstance().getCurrentLanguage()));
				Collections.sort(sort, (x, y) -> collator.compare(y, x));
				doc.remove(offset, len - offset);
				for (String string : sort) {
					doc.insertString(offset, string + "\n", null);
					offset += string.length() + 1;
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	};

	/**
	 * Creates new LocalizableAction for ascendingSort
	 */
	private Action ascendingSort = new LocalizableAction("ascendingSort", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			JTextArea area = sd.getTextComponent();
			Document doc = area.getDocument();
			int len = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
			try {
				offset = area.getLineStartOffset(area.getLineOfOffset(offset));
				len = area.getLineEndOffset(area.getLineOfOffset(len + offset));
				String text = doc.getText(offset, len - offset);
				List<String> sort = Arrays.asList(text.split("\\n"));
				Collator collator = Collator
						.getInstance(new Locale(LocalizationProvider.getInstance().getCurrentLanguage()));
				Collections.sort(sort, (x, y) -> collator.compare(x, y));
				doc.remove(offset, len - offset);
				for (String string : sort) {
					doc.insertString(offset, string + "\n", null);
					offset += string.length() + 1;
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	};

	/**
	 * Creates new LocalizableAction for uppercase
	 */
	private Action uppercase = new LocalizableAction("uppercase", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			Document doc = sd.getTextComponent().getDocument();
			int len = Math.abs(sd.getTextComponent().getCaret().getDot() - sd.getTextComponent().getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(sd.getTextComponent().getCaret().getDot(),
					sd.getTextComponent().getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = text.toUpperCase();
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (Exception e) {
			}

		}
	};

	/**
	 * Creates new LocalizableAction for lowercase
	 */
	private Action lowercase = new LocalizableAction("lowercase", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			Document doc = sd.getTextComponent().getDocument();
			int len = Math.abs(sd.getTextComponent().getCaret().getDot() - sd.getTextComponent().getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(sd.getTextComponent().getCaret().getDot(),
					sd.getTextComponent().getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = text.toLowerCase();
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (Exception e) {
			}

		}
	};

	/**
	 * Creates new LocalizableAction for changecase
	 */
	private Action changecase = new LocalizableAction("changecase", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			Document doc = sd.getTextComponent().getDocument();
			int len = Math.abs(sd.getTextComponent().getCaret().getDot() - sd.getTextComponent().getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(sd.getTextComponent().getCaret().getDot(),
					sd.getTextComponent().getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (Exception e) {
			}

		}

		/**
		 * Method used to change case of letters in a string
		 * 
		 * @param text
		 *            string
		 * @return string
		 */
		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}

	};

	/**
	 * Creates new LocalizableAction for exitFileAction
	 */
	private Action exitFileAction = new LocalizableAction("exit", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Iterator<SingleDocumentModel> iter = tabbedPane.iterator();
			int i = 1;
			while (iter.hasNext()) {
				SingleDocumentModel sd = iter.next();
				if (sd.isModified()) {
					System.out.println();
					String name = sd.getFilePath() == null ? String.format("New%d", i++)
							: sd.getFilePath().getFileName().toString();
					int n = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("confirmSave") + name,
							flp.getString("confirmSaveTitle"), JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						save(sd);
						if (sd.getFilePath() != null) {
							tabbedPane.saveDocument(sd, sd.getFilePath());
							iter.remove();
						}
					} else {
						iter.remove();
					}
				} else {
					if (sd.getFilePath() == null)
						i++;
					iter.remove();
				}
			}
			statusBar.setStopRequested(true);
			dispose();
		}
	};

	/**
	 * Creates new LocalizableAction for cutSelectedPartAction
	 */
	private Action cutSelectedPartAction = new LocalizableAction("cut", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			Document doc = sd.getTextComponent().getDocument();
			int len = Math.abs(sd.getTextComponent().getCaret().getDot() - sd.getTextComponent().getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(sd.getTextComponent().getCaret().getDot(),
					sd.getTextComponent().getCaret().getMark());
			try {
				cut_copy = new StringSelection(doc.getText(offset, len));
				clipboard.setContents(cut_copy, cut_copy);
				doc.remove(offset, len);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * Creates new LocalizableAction for statsSelectedPartAction
	 */
	private Action statsSelectedPartAction = new LocalizableAction("stats", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			int total = sd.getTextComponent().getText().length();
			int chars = sd.getTextComponent().getText().replaceAll("\\s+", "").length();
			int lines = sd.getTextComponent().getLineCount();
			JOptionPane.showMessageDialog(JNotepadPP.this,
					String.format(flp.getString("statsPanel"), total, chars, lines), "Stats",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};

	/**
	 * Creates new LocalizableAction for pasteSelectedPartAction
	 */
	private Action pasteSelectedPartAction = new LocalizableAction("paste", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			Document doc = sd.getTextComponent().getDocument();
			int len = Math.abs(sd.getTextComponent().getCaret().getDot() - sd.getTextComponent().getCaret().getMark());
			try {
				if (len == 0) {
					doc.insertString(sd.getTextComponent().getCaret().getDot(),
							(String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor), null);
				} else {
					int offset = Math.min(sd.getTextComponent().getCaret().getDot(),
							sd.getTextComponent().getCaret().getMark());
					doc.insertString(offset,
							(String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor), null);
				}
			} catch (BadLocationException | UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * Creates new LocalizableAction for copySelectedPartAction
	 */
	private Action copySelectedPartAction = new LocalizableAction("copy", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			Document doc = sd.getTextComponent().getDocument();
			int len = Math.abs(sd.getTextComponent().getCaret().getDot() - sd.getTextComponent().getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(sd.getTextComponent().getCaret().getDot(),
					sd.getTextComponent().getCaret().getMark());
			try {
				cut_copy = new StringSelection(doc.getText(offset, len));
				clipboard.setContents(cut_copy, cut_copy);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * Creates new LocalizableAction for createDocumendAction
	 */
	private final Action createDocumendAction = new LocalizableAction("new", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			tabbedPane.createNewDocument();
		}
	};

	/**
	 * Creates new LocalizableAction for openDocumendAction
	 */
	private final Action openDocumendAction = new LocalizableAction("open", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						String.format(flp.getString("doesntExist"), fileName.getAbsolutePath()), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int counter = 0;
			Iterator<SingleDocumentModel> iter = tabbedPane.iterator();
			while (iter.hasNext()) {
				SingleDocumentModel sd = iter.next();
				if (sd.getFilePath() == null) {
					counter++;
					continue;
				} else if (sd.getFilePath().equals(filePath)) {
					((JTabbedPane) tabbedPane).setSelectedIndex(counter);
					return;
				}
			}
			tabbedPane.loadDocument(filePath);
		}
	};

	/**
	 * Creates new LocalizableAction for saveDocumentAction
	 */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			if (sd.getFilePath() == null) {
				if (save(sd)) {
					tabbedPane.saveDocument(sd, sd.getFilePath());
				}
			} else {
				tabbedPane.saveDocument(sd, sd.getFilePath());
			}
		}
	};

	/**
	 * Creates new LocalizableAction for saveAsDocumentAction
	 */
	private Action saveAsDocumentAction = new LocalizableAction("saveAs", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			if (save(sd)) {
				if (sd.getFilePath() != null) {
					tabbedPane.saveDocument(sd, sd.getFilePath());
				}
			}
		}
	};

	/**
	 * Creates new LocalizableAction for closeDocumentAction
	 */
	private Action closeDocumentAction = new LocalizableAction("close", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SingleDocumentModel sd = tabbedPane.getCurrentDocument();
			if (sd.isModified()) {
				int n = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("saveCurrent"),
						flp.getString("saveCurrent.title"), JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					save(sd);
					if (sd.getFilePath() != null) {
						tabbedPane.saveDocument(sd, sd.getFilePath());
					}
				}
			}
			tabbedPane.closeDocument(sd);
		}
	};

	/**
	 * Main method
	 * 
	 * @param args
	 *            not used for this task
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JNotepadPP();
			frame.setVisible(true);
		});
	}
	/**
	 * Helper method for save and saveAs actions
	 * 
	 * @param sd
	 *            SingleDocumentModel
	 * @return boolean
	 */
	protected boolean save(SingleDocumentModel sd) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("save.error"), "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		File file = jfc.getSelectedFile();
		Path filePath = file.toPath();
		if (Files.exists(filePath)) {
			int counter = 0;
			Iterator<SingleDocumentModel> iter = tabbedPane.iterator();
			while (iter.hasNext()) {
				SingleDocumentModel sdd = iter.next();
				if (sdd.getFilePath() == null) {
					counter++;
					continue;
				} else if (sdd.getFilePath().equals(filePath)) {
					((JTabbedPane) tabbedPane).setSelectedIndex(counter);
					return false;
				}
			}
			int n = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("save.exists"),
					flp.getString("save.exists.title"), JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				sd.setFilePath(jfc.getSelectedFile().toPath());
				return true;
			} else {
				return false;
			}
		}
		sd.setFilePath(jfc.getSelectedFile().toPath());
		return true;
	}
}
