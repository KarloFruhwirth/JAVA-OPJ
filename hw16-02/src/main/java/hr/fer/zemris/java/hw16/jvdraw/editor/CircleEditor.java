package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

/**
 * Editor for {@link Circle} object
 * 
 * @author KarloFrühwirth
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Circle
	 */
	private Circle circle;
	/**
	 * JTextArea X0
	 */
	private JTextArea startX = new JTextArea();
	/**
	 * JTextArea Y0
	 */
	private JTextArea startY = new JTextArea();
	/**
	 * JTextArea radius
	 */
	private JTextArea radius = new JTextArea();
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
	 * @param circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		JPanel panel = new JPanel(new GridLayout(1, 12));
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
		rRGB.setText(String.valueOf(circle.getColor().getRed()));
		panel.add(rRGB);
		panel.add(new JLabel("G"));
		gRGB.setText(String.valueOf(circle.getColor().getGreen()));
		panel.add(gRGB);
		panel.add(new JLabel("B"));
		bRGB.setText(String.valueOf(circle.getColor().getBlue()));
		panel.add(bRGB);
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
		circle.setStartX(Integer.parseInt(startX.getText()));
		circle.setStartY(Integer.parseInt(startY.getText()));
		circle.setRadius(Integer.parseInt(radius.getText()));
		circle.setColor(new Color(Integer.parseInt(rRGB.getText()), Integer.parseInt(gRGB.getText()),
				Integer.parseInt(bRGB.getText())));
		for (GeometricalObjectListener go : circle.list) {
			go.geometricalObjectChanged(circle);
		}
		;
	}

}
