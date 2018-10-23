package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * FilledCircle used in {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class FilledCircle extends GeometricalObject {

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
	 * color1
	 */
	private Color color1;
	/**
	 * color2
	 */
	private Color color2;

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            x0
	 * @param startY
	 *            y0
	 * @param radius
	 *            r
	 * @param r1
	 *            R1
	 * @param g1
	 *            G1
	 * @param b1
	 *            B1
	 * @param r2
	 *            R2
	 * @param g2
	 *            G2
	 * @param b2
	 *            B2
	 */
	public FilledCircle(int startX, int startY, int radius, int r1, int g1, int b1, int r2, int g2, int b2) {
		this.startX = startX;
		this.startY = startY;
		this.radius = radius;
		this.color1 = new Color(r1, g1, b1);
		this.color2 = new Color(r2, g2, b2);
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
	 * @param color1
	 *            Color
	 * @param color2
	 *            Color
	 */
	public FilledCircle(int startX, int startY, int radius, Color color1, Color color2) {
		this.startX = startX;
		this.startY = startY;
		this.radius = radius;
		this.color1 = color1;
		this.color2 = color2;
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

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color) {
		this.color1 = color;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color) {
		this.color2 = color;
	}

	@Override
	public String toString() {
		return "Filled circle (" + startX + "," + startY + "), " + radius + ","
				+ String.format("#%02X%02X%02X", color2.getRed(), color2.getGreen(), color2.getBlue());
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	// FCIRCLE 40 40 18 0 0 255 255 0 0
	@Override
	public String getText() {
		return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", startX, startY, radius, color1.getRed(),
				color1.getGreen(), color1.getBlue(), color2.getRed(), color2.getGreen(), color2.getBlue());
	}
}
