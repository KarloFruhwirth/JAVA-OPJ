package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPoligon;

public class FilledPoligonEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FilledPoligon filledPoligon;

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

	public FilledPoligonEditor(FilledPoligon filledPoligon) {
		this.filledPoligon = filledPoligon;
		int size = (filledPoligon.getPoints().size() * 2) + 6;
		JPanel panel = new JPanel(new GridLayout(1, size));
		for (int i = 0; i < filledPoligon.getPoints().size(); i++) {
			panel.add(new JLabel("x" + i));
			panel.add(new JTextArea(String.valueOf(filledPoligon.getPoints().get(i).x)));
			panel.add(new JLabel("y" + i));
			panel.add(new JTextArea(String.valueOf(filledPoligon.getPoints().get(i).y)));
		}
		panel.add(new JLabel("R"));
		r1RGB.setText(String.valueOf(filledPoligon.getColor1().getRed()));
		panel.add(r1RGB);
		panel.add(new JLabel("G"));
		g1RGB.setText(String.valueOf(filledPoligon.getColor1().getGreen()));
		panel.add(g1RGB);
		panel.add(new JLabel("B"));
		b1RGB.setText(String.valueOf(filledPoligon.getColor1().getBlue()));
		panel.add(b1RGB);
		panel.add(new JLabel("R"));
		r2RGB.setText(String.valueOf(filledPoligon.getColor2().getRed()));
		panel.add(r2RGB);
		panel.add(new JLabel("G"));
		g2RGB.setText(String.valueOf(filledPoligon.getColor2().getGreen()));
		panel.add(g2RGB);
		panel.add(new JLabel("B"));
		b2RGB.setText(String.valueOf(filledPoligon.getColor2().getBlue()));
		panel.add(b2RGB);
		add(panel);
	}

	@Override
	public void checkEditing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptEditing() {
		// TODO Auto-generated method stub

	}

}