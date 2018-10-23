package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * {@link JComponent} used for change of colors in {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Color
	 */
	private Color selectedColor;
	/**
	 * List of ColorChangeListener
	 */
	private List<ColorChangeListener> list = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param selectedColor
	 *            Color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Color color = JColorChooser.showDialog(JColorArea.this, "Select a color", selectedColor);
				if (color != null) {
					setSelectedColor(color);
				}
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	public void setSelectedColor(Color color) {
		for (ColorChangeListener l : list) {
			l.newColorSelected(this, selectedColor, color);
		}
		selectedColor = color;
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		list.add(l);

	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		list.remove(l);
	}

}
