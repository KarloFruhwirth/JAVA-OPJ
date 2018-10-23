package hr.fer.zemris.java.gui.charts;

/**
 * Defines the dimensions of columns used in {@link BarChartDemo}.
 * 
 * @author KarloFrühwirth
 *
 */
public class XYValue {

	/**
	 * Horizontal component
	 */
	private int x;
	/**
	 * Vertical component
	 */
	private int y;

	/**
	 * Constructor for XYValue
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
