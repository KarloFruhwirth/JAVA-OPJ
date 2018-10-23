package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class which contains the columns/elements of the barChart
 * 
 * @author KarloFrühwirth
 *
 */
public class BarChart {
	/**
	 * List of XYValue
	 */
	private List<XYValue> values;
	/**
	 * Description of xAxis
	 */
	String xDescription;
	/**
	 * Description of yAxis
	 */
	String yDescription;
	/**
	 * Minimum y
	 */
	int yMin;
	/**
	 * Maximum y
	 */
	int yMax;
	/**
	 * Step
	 */
	int step;

	/**
	 * Constructor for BarChart
	 * 
	 * @param values
	 *            List<XYValue>
	 * @param xDescription
	 *            xDescription
	 * @param yDescription
	 *            yDescription
	 * @param yMin
	 *            yMin
	 * @param yMax
	 *            yMax
	 * @param step
	 *            step
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int step) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		if (step <= 0) {
			throw new IllegalArgumentException("Step must be greather than 0.");
		}
		if ((yMax - yMin) % step != 0) {
			this.step = (int) Math.floor((double) (yMax - yMin) / (double) step);
		} else {
			this.step = step;
		}
	}

	/**
	 * Getter for List<XYValue>
	 * 
	 * @return List<XYValue>
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for xDescription
	 * 
	 * @return xDescription
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter for yDescription
	 * 
	 * @return yDescription
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter for yMin
	 * 
	 * @return yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter for yMax
	 * 
	 * @return yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter for step
	 * 
	 * @return step
	 */
	public int getStep() {
		return step;
	}
}
