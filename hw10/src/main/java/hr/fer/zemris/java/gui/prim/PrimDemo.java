package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class representing an implementation of a panel which has two columns which
 * on the press of JButton adds next prime number<br>
 * <br>
 * <br>
 * Created referring to Prozor7b.
 * 
 * @author KarloFrühwirth
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * PrimListModel
	 */
	private PrimListModel listModel = new PrimListModel();
	/**
	 * default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for PrimDemo
	 */
	public PrimDemo() {
		setMinimumSize(new Dimension(400, 250));
		setTitle("Prime numbers");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Method used to initialize the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(1, 0));
		JList<Integer> firstList = new JList<>(listModel);
		JList<Integer> secondList = new JList<>(listModel);
		panel.add(new JScrollPane(firstList));
		panel.add(new JScrollPane(secondList));
		JButton button = new JButton("Next prime");
		button.addActionListener(e -> listModel.next());
		cp.add(panel, BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setVisible(true);
		});
	}

}
