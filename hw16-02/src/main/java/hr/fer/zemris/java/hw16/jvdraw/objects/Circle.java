package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.editor.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * Circle used in {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * x0
	 */
	private int startX;
	/**
	 * y0
	 */
	private int startY;
	/**
	 * radius
	 */
	private int radius;
	/**
	 * Color
	 */
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            x0
	 * @param startY
	 *            y0
	 * @param radius
	 *            r
	 * @param r
	 *            R
	 * @param g
	 *            G
	 * @param b
	 *            B
	 */
	public Circle(int startX, int startY, int radius, int r, int g, int b) {
		this.startX = startX;
		this.startY = startY;
		this.radius = radius;
		this.color = new Color(r, g, b);
	}

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            x0
	 * @param startY
	 *            y0
	 * @param radius
	 *            r
	 * @param color
	 *            Color
	 */
	public Circle(int startX, int startY, int radius, Color color) {
		this.startX = startX;
		this.startY = startY;
		this.radius = radius;
		this.color = color;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Circle (" + startX + "," + startY + "), " + radius;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	// CIRCLE 40 40 18 0 0 255
	@Override
	public String getText() {
		return String.format("CIRCLE %d %d %d %d %d %d", startX, startY, radius, color.getRed(), color.getGreen(),
				color.getBlue());
	}

}
