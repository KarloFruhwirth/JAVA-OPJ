package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * Status bar used to display the fgColor and bgColor in {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class JVDStatusBar extends JPanel implements ColorChangeListener {

	/**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Color fg
	 */
	private Color fgColor;
	/**
	 * Color bg
	 */
	private Color bgColor;
	/**
	 * IColorProvider for fg
	 */
	private IColorProvider fgProvider;
	/**
	 * IColorProvider for bg
	 */
	private IColorProvider bgProvider;

	/**
	 * Foreground & background color
	 */
	private JLabel colors;

	/**
	 * Constructor
	 * 
	 * @param fg
	 *            JColorArea
	 * @param bg
	 *            JColorArea
	 */
	public JVDStatusBar(JColorArea fg, JColorArea bg) {
		fgProvider = fg;
		bgProvider = bg;
		fg.addColorChangeListener(this);
		bg.addColorChangeListener(this);
		fgColor = fg.getCurrentColor();
		bgColor = bg.getCurrentColor();
		String text = setText();
		colors = new JLabel(text);
		this.add(colors);
	}

	/**
	 * Text setter
	 * 
	 * @return text
	 */
	private String setText() {
		return String.format("Foreground color: (%d, %d, %d) , background color: (%d, %d, %d).", fgColor.getRed(),
				fgColor.getGreen(), fgColor.getBlue(), bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());

	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source.equals(fgProvider)) {
			fgColor = newColor;
		} else if (source.equals(bgProvider)) {
			bgColor = newColor;
		}
		colors.setText(setText());

	}
}
