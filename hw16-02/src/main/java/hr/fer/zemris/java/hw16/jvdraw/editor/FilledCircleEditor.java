package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

/**
 * Editor for {@link FilledCircle} object
 * 
 * @author KarloFrühwirth
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * FilledCircle
	 */
	private FilledCircle circle;
	/**
	 * JTextArea x0
	 */
	private JTextArea startX = new JTextArea();
	/**
	 * JTextArea y0
	 */
	private JTextArea startY = new JTextArea();
	/**
	 * JTextArea radius
	 */
	private JTextArea radius = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea r1RGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea g1RGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea b1RGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea r2RGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea g2RGB = new JTextArea();
	/**
	 * JTextArea RGB
	 */
	private JTextArea b2RGB = new JTextArea();

	/**
	 * Constructor
	 * 
	 * @param circle FilledCircle
	 */
	public FilledCircleEditor(FilledCircle circle) {
		this.circle = circle;
		JPanel panel = new JPanel(new GridLayout(1, 18));
		panel.add(new JLabel("x0"));
		startX.setText(String.valueOf(circle.getStartX()));
		panel.add(startX);
		panel.add(new JLabel("y0"));
		startY.setText(String.valueOf(circle.getStartY()));
		panel.add(startY);
		panel.add(new JLabel("radius"));
		radius.setText(String.valueOf(circle.getRadius()));
		panel.add(radius);
		panel.add(new JLabel("R"));
		r1RGB.setText(String.valueOf(circle.getColor1().getRed()));
		panel.add(r1RGB);
		panel.add(new JLabel("G"));
		g1RGB.setText(String.valueOf(circle.getColor1().getGreen()));
		panel.add(g1RGB);
		panel.add(new JLabel("B"));
		b1RGB.setText(String.valueOf(circle.getColor1().getBlue()));
		panel.add(b1RGB);
		panel.add(new JLabel("R"));
		r2RGB.setText(String.valueOf(circle.getColor2().getRed()));
		panel.add(r2RGB);
		panel.add(new JLabel("G"));
		g2RGB.setText(String.valueOf(circle.getColor2().getGreen()));
		panel.add(g2RGB);
		panel.add(new JLabel("B"));
		b2RGB.setText(String.valueOf(circle.getColor2().getBlue()));
		panel.add(b2RGB);
		add(panel);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(startX.getText());
			Integer.parseInt(startY.getText());
			if (Integer.parseInt(radius.getText()) < 0) {
				throw new IllegalArgumentException("Radius must be positive");
			}
			if (Integer.parseInt(r1RGB.getText()) < 0 || Integer.parseInt(r1RGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(g1RGB.getText()) < 0 || Integer.parseInt(g1RGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(b1RGB.getText()) < 0 || Integer.parseInt(b1RGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(r2RGB.getText()) < 0 || Integer.parseInt(r2RGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(g2RGB.getText()) < 0 || Integer.parseInt(g2RGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
			if (Integer.parseInt(b2RGB.getText()) < 0 || Integer.parseInt(b2RGB.getText()) > 255) {
				throw new IllegalArgumentException("RGB component can be set to a value from the interval [0,255]");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Only numbers can be inputed...");
		}

	}

	@Override
	public void acceptEditing() {
		circle.setStartX(Integer.parseInt(startX.getText()));
		circle.setStartY(Integer.parseInt(startY.getText()));
		circle.setRadius(Integer.parseInt(radius.getText()));
		circle.setColor1(new Color(Integer.parseInt(r1RGB.getText()), Integer.parseInt(g1RGB.getText()),
				Integer.parseInt(b1RGB.getText())));
		circle.setColor2(new Color(Integer.parseInt(r2RGB.getText()), Integer.parseInt(g2RGB.getText()),
				Integer.parseInt(b2RGB.getText())));
		for (GeometricalObjectListener go : circle.list) {
			go.geometricalObjectChanged(circle);
		}
		;
	}
}
