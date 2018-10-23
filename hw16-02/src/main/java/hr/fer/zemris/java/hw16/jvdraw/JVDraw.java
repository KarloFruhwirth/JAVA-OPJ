package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPoligon;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledPoligonTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectPainter;

/**
 * Main class used to display our magnificent canvas
 * 
 * @author KarloFrühwirth
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * JVDStatusBar
	 */
	private JVDStatusBar statusBar;
	/**
	 * JColorArea fg
	 */
	private JColorArea fgColorArea;
	/**
	 * JColorArea bg
	 */
	private JColorArea bgColorArea;
	/**
	 * JDrawingCanvas
	 */
	private JDrawingCanvas canvas;
	/**
	 * DrawingModel
	 */
	private DrawingModel model = new DocumentModel();
	/**
	 * Tool
	 */
	private Tool source;
	/**
	 * Path
	 */
	private Path filePath;
	/**
	 * Flag
	 */
	private boolean modified = false;
	/**
	 * Exit action
	 */
	private Action exitFileAction = createExitAction();
	/**
	 * JList
	 */
	private JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(model));

	/**
	 * Constructor for JVDraw
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setMinimumSize(new Dimension(500, 400));
		setTitle("JVDraw");
		initGUI();
	}

	/**
	 * Method used to create exit action
	 * 
	 * @return Action
	 */
	private Action createExitAction() {
		Action a = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!modified) {
					dispose();
				} else {
					int n = JOptionPane.showConfirmDialog(JVDraw.this, "Save before exit", "Save",
							JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						if (filePath == null) {
							JFileChooser fc = new JFileChooser();
							FileNameExtensionFilter filter = new FileNameExtensionFilter(".jvd", "jvd", "jvd");
							fc.setFileFilter(filter);
							if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
								return;
							}
							fc.setDialogTitle("Save document");
							File fileName = fc.getSelectedFile();
							String name = fileName.toString() + filter.getDescription();
							fileName = new File(name);
							filePath = fileName.toPath();
						}
						saveDocument(filePath, model);
						dispose();
					} else {
						dispose();
					}

				}
			}
		};
		return a;
	}

	/**
	 * Initializes the GUI for JVDraw
	 */
	private void initGUI() {
		JPanel panel = new JPanel(new BorderLayout());
		this.getContentPane().setLayout(new BorderLayout());
		createMenu();
		fgColorArea = new JColorArea(Color.RED);
		bgColorArea = new JColorArea(Color.WHITE);
		fgColorArea.setMaximumSize(new Dimension(16, 16));
		bgColorArea.setMaximumSize(new Dimension(16, 16));
		createToolbar();
		statusBar = new JVDStatusBar(fgColorArea, bgColorArea);
		canvas = new JDrawingCanvas(model, this);
		source = new LineTool(model, fgColorArea);
		canvas.setSource(source);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				source.mouseClicked(arg0);
			}
		});
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				source.mouseMoved(arg0);
				canvas.repaint();
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					GeometricalObject clicked = list.getSelectedValue();
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit component",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
					}

				}
			}
		});
		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE) {
					GeometricalObject clicked = list.getSelectedValue();
					if (clicked != null) {
						model.remove(clicked);
					}
				}
				if (arg0.getKeyCode() == KeyEvent.VK_ADD || arg0.getKeyCode() == KeyEvent.VK_PLUS) {
					GeometricalObject clicked = list.getSelectedValue();
					if (clicked != null) {
						model.changeOrder(clicked, 1);
					}
				}
				if (arg0.getKeyCode() == KeyEvent.VK_SUBTRACT || arg0.getKeyCode() == KeyEvent.VK_MINUS) {
					GeometricalObject clicked = list.getSelectedValue();
					if (clicked != null) {
						model.changeOrder(clicked, -1);
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		panel.add(new JScrollPane(list), BorderLayout.LINE_END);
		panel.add(statusBar, BorderLayout.PAGE_END);
		panel.add((Component) canvas, BorderLayout.CENTER);
		this.getContentPane().add(panel, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exitFileAction.actionPerformed(null);
			}
		});
	}

	/**
	 * Creates toolbar
	 */
	private void createToolbar() {
		JToolBar toolbar = new JToolBar("ToolBar");
		toolbar.setFloatable(true);
		toolbar.add(fgColorArea);
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		JButton line = new JButton("Line");
		line.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				source = new LineTool(model, fgColorArea);
				canvas.setSource(source);
			}
		});
		toolbar.add(line);
		JButton circle = new JButton("Circle");
		circle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				source = new CircleTool(model, fgColorArea);
				canvas.setSource(source);
			}
		});
		toolbar.add(circle);
		JButton filledCircle = new JButton("Filled circle");
		filledCircle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				source = new FilledCircleTool(model, fgColorArea, bgColorArea);
				canvas.setSource(source);
			}
		});
		toolbar.add(filledCircle);
		JButton filledPoligon = new JButton("Filled poligon");
		filledPoligon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				source = new FilledPoligonTool(model, fgColorArea, bgColorArea);
				canvas.setSource(source);
			}
		});
		toolbar.add(filledPoligon);
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates menus
	 */
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".jvd", "jvd", "jvd");
				fc.setFileFilter(filter);
				if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				fc.setDialogTitle("Open file");
				File fileName = fc.getSelectedFile();
				filePath = fileName.toPath();
				if (!fileName.toString().endsWith(filter.getDescription())) {
					JOptionPane.showMessageDialog(JVDraw.this, "This isnt a JVD file", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(JVDraw.this, "This file doesnt exsist", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				loadDocument(filePath, model);
				setModified(false);
			}
		});
		fileMenu.add(open);
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (filePath == null) {
					JFileChooser fc = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".jvd", "jvd", "jvd");
					fc.setFileFilter(filter);
					if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
						return;
					}
					fc.setDialogTitle("Save document");
					File fileName = fc.getSelectedFile();
					String name = fileName.toString() + filter.getDescription();
					fileName = new File(name);
					filePath = fileName.toPath();
					if (Files.exists(filePath)) {
						int n = JOptionPane.showConfirmDialog(JVDraw.this, "File with this name exsists",
								"Do you wish to overide", JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.YES_OPTION) {
							saveDocument(filePath, model);
						} else {
							filePath = null;
							return;
						}
					}
				}
				saveDocument(filePath, model);
				setModified(false);
			}
		});
		fileMenu.add(save);
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".jvd", "jvd", "jvd");
				fc.setFileFilter(filter);
				if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				fc.setDialogTitle("Save document as");
				File fileName = fc.getSelectedFile();
				filePath = fileName.toPath();
				if (Files.exists(filePath)) {
					int n = JOptionPane.showConfirmDialog(JVDraw.this, "File with this name exsists",
							"Do you wish to overide", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						saveDocument(filePath, model);
					}
				} else {
					String name = fileName.toString() + filter.getDescription();
					fileName = new File(name);
					filePath = fileName.toPath();
					saveDocument(filePath, model);
					setModified(false);
				}
			}
		});
		fileMenu.add(saveAs);
		JMenuItem export = new JMenuItem("Export");
		export.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
				for (int i = 0; i < model.getSize(); i++) {
					model.getObject(i).accept(bbcalc);
				}
				Rectangle box = bbcalc.getBoundingBox();
				BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g = image.createGraphics();
				AffineTransform a = new AffineTransform();
				a.translate(-box.x, -box.y);
				g.transform(a);
				GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
				for (int i = 0; i < model.getSize(); i++) {
					model.getObject(i).accept(painter);
				}
				g.dispose();
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter1 = new FileNameExtensionFilter(".jpg", "jpg");
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter(".gif", "gif");
				FileNameExtensionFilter filter3 = new FileNameExtensionFilter(".png", "png");
				fc.addChoosableFileFilter(filter1);
				fc.addChoosableFileFilter(filter2);
				fc.addChoosableFileFilter(filter3);
				if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				String name = fileName.toString();
				if (fc.getFileFilter().getDescription().equals("All Files")) {
					if (fileName.toString().lastIndexOf(".") == -1) {
						name += ".jpg";
					} else {
						String ext = name.substring(name.lastIndexOf("."), name.length());
						if (!ext.equals(".jpg") && !ext.equals(".png") && !ext.equals(".gif")) {
							name += ".jpg";
						}
					}
				} else {
					name = fileName.toString() + fc.getFileFilter().getDescription();
				}
				fileName = new File(name);
				fc.setDialogTitle("Save document as");
				filePath = fileName.toPath();
				String extension = filePath.toString().substring(filePath.toString().lastIndexOf("."),
						filePath.toString().length());
				try {
					ImageIO.write(image, extension.substring(1), fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				setModified(false);

			}
		});
		fileMenu.add(export);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this.exitFileAction);
		fileMenu.add(exit);
		this.setJMenuBar(menuBar);

	}

	/**
	 * Method used to save jvd file
	 * 
	 * @param filePath
	 *            Path
	 * @param model
	 *            DrawingModel
	 */
	protected void saveDocument(Path filePath, DrawingModel model) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < model.getSize(); i++) {
			sb.append(model.getObject(i).getText());
			sb.append("\n");
		}
		byte[] data = sb.toString().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(filePath, data);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method used to open jvd file
	 * 
	 * @param filePath
	 *            Path
	 * @param model
	 *            DrawingModel
	 */
	protected void loadDocument(Path filePath, DrawingModel model) {
		for (int i = 0; i < model.getSize();) {
			model.remove(model.getObject(0));
		}
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(filePath);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		String text = new String(bytes, StandardCharsets.UTF_8);
		String[] array = text.split("\\n");
		for (String s : array) {
			if (s.trim().startsWith("LINE")) {
				String[] elements = s.split("\\s+");
				if (elements.length != 8)
					throw new IllegalArgumentException("Invalid number of arguments");
				Line line = new Line(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]),
						Integer.parseInt(elements[3]), Integer.parseInt(elements[4]),
						new Color(Integer.parseInt(elements[5]), Integer.parseInt(elements[6]),
								Integer.parseInt(elements[7])));
				model.add(line);
			} else if (s.trim().startsWith("CIRCLE")) {
				String[] elements = s.split("\\s+");
				if (elements.length != 7)
					throw new IllegalArgumentException("Invalid number of arguments");
				Circle circle = new Circle(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]),
						Integer.parseInt(elements[3]), new Color(Integer.parseInt(elements[4]),
								Integer.parseInt(elements[5]), Integer.parseInt(elements[6])));
				model.add(circle);
			} else if (s.trim().startsWith("FCIRCLE")) {
				String[] elements = s.split("\\s+");
				if (elements.length != 10)
					throw new IllegalArgumentException("Invalid number of arguments");
				FilledCircle circle = new FilledCircle(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]),
						Integer.parseInt(elements[3]),
						new Color(Integer.parseInt(elements[4]), Integer.parseInt(elements[5]),
								Integer.parseInt(elements[6])),
						new Color(Integer.parseInt(elements[7]), Integer.parseInt(elements[8]),
								Integer.parseInt(elements[9])));
				model.add(circle);
			} else if (s.trim().startsWith("FPOLY")) {
				String[] elements = s.split("\\s+");
				if (elements.length < 13)
					throw new IllegalArgumentException("Invalid number of arguments");
				int indexOfColors = elements.length - 6;
				System.out.println(indexOfColors);
				Color c1 = new Color(Integer.parseInt(elements[indexOfColors]),
						Integer.parseInt(elements[indexOfColors + 1]), Integer.parseInt(elements[indexOfColors + 2]));
				Color c2 = new Color(Integer.parseInt(elements[indexOfColors + 3]),
						Integer.parseInt(elements[indexOfColors + 4]), Integer.parseInt(elements[indexOfColors + 5]));
				int numOfPoints = (elements.length - indexOfColors) / 2;
				List<Point> list = new ArrayList<>();
				int index = 0;
				for (int i = 0; i < numOfPoints; i++) {
					list.add(new Point(Integer.parseInt(elements[index]), Integer.parseInt(elements[index + 1])));
					index += 2;
				}
				FilledPoligon fPoly = new FilledPoligon(c1, c2, list);
				model.add(fPoly);
			}
		}
	}

	/**
	 * @return isModified
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * Setter for modified flag
	 * 
	 * @param modified
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            not used for this task
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JVDraw();
			frame.setVisible(true);
		});
	}

}
