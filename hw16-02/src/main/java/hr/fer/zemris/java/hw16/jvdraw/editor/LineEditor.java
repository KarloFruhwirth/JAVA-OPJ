package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Editor for {@link Line} object
 * 
 * @author KarloFrühwirth
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Line
	 */
	private Line line;
	/**
	 * JTextArea x0
	 */
	private JTextArea startX = new JTextArea();
	/**
	 * JTextArea y0
	 */
	private JTextArea startY = new JTextArea();
	/**
	 * JTextArea x1
	 */
	private JTextArea endX = new JTextArea();
	/**
	 * JTextArea y1
	 */
	private JTextArea endY = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea rRGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea gRGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea bRGB = new JTextArea();

	/**
	 * Constructor
	 * 
	 * @param line
	 *            Line
	 */
	public LineEditor(Line line) {
		this.line = line;
		JPanel panel = new JPanel(new GridLayout(1, 14));
		panel.add(new JLabel("x0"));
		startX.setText(String.valueOf(line.getStartX()));
		panel.add(startX);
		panel.add(new JLabel("y0"));
		startY.setText(String.valueOf(line.getStartY()));
		panel.add(startY);
		panel.add(new JLabel("x1"));
		endX.setText(String.valueOf(line.getEndX()));
		panel.add(endX);
		panel.add(new JLabel("y1"));
		endY.setText(String.valueOf(line.getEndY()));
		panel.add(endY);
		panel.add(new JLabel("R"));
		rRGB.setText(String.valueOf(line.getColor().getRed()));
		panel.add(rRGB);
		panel.add(new JLabel("G"));
		gRGB.setText(String.valueOf(line.getColor().getGreen()));
		panel.add(gRGB);
		panel.add(new JLabel("B"));
		bRGB.setText(String.valueOf(line.getColor().getBlue()));
		panel.add(bRGB);
		add(panel);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(startX.getText());
			Integer.parseInt(startY.getText());
			Integer.parseInt(endX.getText());
			Integer.parseInt(endY.getText());
			if (Integer.parseInt(rRGB.getText()) < 0 || Integer.parseInt(rRGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(gRGB.getText()) < 0 || Integer.parseInt(gRGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(bRGB.getText()) < 0 || Integer.parseInt(bRGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Only numbers can be inputed...");
		}

	}

	@Override
	public void acceptEditing() {
		line.setStartX(Integer.parseInt(startX.getText()));
		line.setEndX(Integer.parseInt(endX.getText()));
		line.setStartY(Integer.parseInt(startY.getText()));
		line.setEndY(Integer.parseInt(endY.getText()));
		line.setColor(new Color(Integer.parseInt(rRGB.getText()), Integer.parseInt(gRGB.getText()),
				Integer.parseInt(bRGB.getText())));
		for (GeometricalObjectListener go : line.list) {
			go.geometricalObjectChanged(line);
		}
		;
	}

}
