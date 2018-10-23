package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * My own JPanel used to display stats of {@link JNotepadPP}
 * 
 * @author KarloFrühwirth
 *
 */
public class JStatusBar extends JPanel {

	/**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * stopRequested to kill thread
	 */
	private volatile boolean stopRequested = false;

	/**
	 * length
	 */
	private JLabel length;
	/**
	 * line
	 */
	private JLabel line;
	/**
	 * column
	 */
	private JLabel column;
	/**
	 * selected
	 */
	private JLabel selected;
	/**
	 * clock
	 */
	private JLabel clock;

	/**
	 * Constructor for JStatusBar
	 * 
	 * @param layout
	 *            LayoutManager
	 */
	public JStatusBar(LayoutManager layout) {
		super(layout);
		setup();
	}

	/**
	 * Method used to setup JStatusBar
	 */
	private void setup() {

		length = new JLabel("lenght: 0");
		length.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel info = new JPanel(new GridLayout(1, 3));
		line = new JLabel("Ln: 1");
		column = new JLabel("Col: 0");
		selected = new JLabel("Sel :0");
		info.add(line);
		info.add(column);
		info.add(selected);
		clock = new JLabel("");
		clock.setHorizontalAlignment(SwingConstants.RIGHT);

		this.add(length);
		this.add(info);
		this.add(clock);

		updateClock(clock);
	}

	/**
	 * Method which constantly updates the clock
	 * 
	 * @param clock
	 *            clock
	 */
	private void updateClock(JLabel clock) {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignorable) {
				}
				if (stopRequested)
					break;
				SwingUtilities.invokeLater(
						() -> clock.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())));
			}
		});
		t.start();
	}

	/**
	 * Set stopRequested
	 * 
	 * @param b
	 *            boolean
	 */
	public void setStopRequested(boolean b) {
		stopRequested = b;
	}

	/**
	 * Setter for language
	 * 
	 * @param text
	 *            language
	 */
	public void setLenght(String text) {
		length.setText(text);
	}

	/**
	 * Set line text
	 * 
	 * @param text
	 *            text
	 */
	public void setLine(String text) {
		line.setText(text);
	}

	/**
	 * Set column text
	 * 
	 * @param text
	 *            text
	 */
	public void setColumn(String text) {
		column.setText(text);
	}

	/**
	 * Set selected text
	 * 
	 * @param text
	 *            text
	 */
	public void setSelected(String text) {
		selected.setText(text);
	}
}
