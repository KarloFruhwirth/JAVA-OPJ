package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.editor.FilledPoligonEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

public class FilledPoligon extends GeometricalObject {

	private List<Point> list;
	/**
	 * color1
	 */
	private Color color1;
	/**
	 * color2
	 */
	private Color color2;

	public FilledPoligon(Color color1, Color color2, List<Point> list) {
		this.color1 = color1;
		this.color2 = color2;
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Filled poligon (");
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).getX() + " " + list.get(i).getY() + " ");
		}
		sb.append(String.format("#%02X%02X%02X", color2.getRed(), color2.getGreen(), color2.getBlue()));
		return sb.toString();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledPoligonEditor(this);
	}

	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();
		sb.append("FPOLY ");
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).getX() + " " + list.get(i).getY() + " ");
		}
		sb.append(color1.getRed() + " " + color1.getGreen() + " " + color1.getBlue() + " " + color2.getRed() + " "
				+ color2.getGreen() + " " + color2.getBlue());
		return sb.toString();
	}

	public List<Point> getPoints() {
		return list;
	}

	public void setPoints(List<Point> list) {
		this.list = list;
	}

	public Color getColor2() {
		return color2;
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color) {
		this.color1 = color;
	}

	public void setColor2(Color color) {
		this.color2 = color;
	}

}
