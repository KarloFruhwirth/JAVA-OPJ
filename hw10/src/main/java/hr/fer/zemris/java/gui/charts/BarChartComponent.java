package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * @author KarloFrühwirth
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Left gap
	 */
	private static final int LEFT_GAP = 50;
	/**
	 * Bottom gap
	 */
	private static final int BOTTOM_GAP = 50;

	/**
	 * BarChart
	 */
	private BarChart bc;

	/**
	 * Constructor for BarChartComponent
	 * 
	 * @param bc
	 */
	public BarChartComponent(BarChart bc) {
		this.bc = bc;
	}

	@Override
	protected void paintComponent(Graphics g) {
		int height = this.getHeight();
		int width = this.getWidth();
		int yMax = bc.getyMax();
		int yMin = bc.getyMin();
		int y = yMax - yMin;
		int step = bc.getStep();
		int numOfXY = bc.getValues().size();
		String xAxis = bc.getxDescription();
		String yAxis = bc.getyDescription();

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		FontMetrics fm = g.getFontMetrics();
		int charLenght = fm.stringWidth("a");
		int yLenght = fm.stringWidth(yAxis);
		g2d.drawString(yAxis, -height / 2 - yLenght, LEFT_GAP);
		at.rotate(Math.PI / 2);
		g2d.setTransform(at);

		int xLenght = fm.stringWidth(xAxis);
		g.drawString(xAxis, width / 2 - xLenght / 2, height - BOTTOM_GAP);

		int xGridSpace = (width - LEFT_GAP - 30) / numOfXY;
		int yGridSpace = (height - BOTTOM_GAP - 30) / y;
		g.drawLine(LEFT_GAP + 30, 5, LEFT_GAP + 30, height - BOTTOM_GAP - 25);
		int temp = yMin;
		for (int i = 0; i <= y; i += step) {
			String yVal = String.format("%2s", Integer.toString(temp));
			temp += step;
			g.drawString(yVal, LEFT_GAP + 10, height - BOTTOM_GAP - 30 - i * yGridSpace);
			g.drawLine(LEFT_GAP + 25, height - BOTTOM_GAP - 35 - i * yGridSpace, width,
					height - BOTTOM_GAP - 35 - i * yGridSpace);
		}

		for (int i = 1; i <= numOfXY; i++) {
			String xVal = Integer.toString(i);
			g.drawString(xVal, (i - 1) * xGridSpace + xGridSpace / 2 + LEFT_GAP + 30 - charLenght,
					height - BOTTOM_GAP - 20);
			g.drawLine(i * xGridSpace + LEFT_GAP + 30, 5, i * xGridSpace + LEFT_GAP + 30, height - BOTTOM_GAP - 25);
			g.setColor(Color.decode("#fc8353"));
			g.fillRect((i - 1) * xGridSpace + LEFT_GAP + 30,
					height - BOTTOM_GAP - 35 - (bc.getValues().get(i - 1).getY()) * yGridSpace, xGridSpace,
					(bc.getValues().get(i - 1).getY()) * yGridSpace);
			g.setColor(Color.decode("#7f6e67"));
			g.drawRect((i - 1) * xGridSpace + LEFT_GAP + 30,
					height - BOTTOM_GAP - 35 - (bc.getValues().get(i - 1).getY()) * yGridSpace, xGridSpace,
					(bc.getValues().get(i - 1).getY()) * yGridSpace);
			g.setColor(getForeground());
		}

		g.fillPolygon(new int[] { LEFT_GAP + 25, LEFT_GAP + 30, LEFT_GAP + 35 }, new int[] { 5, 0, 5 }, 3);
		g.fillPolygon(new int[] { width - 5, width, width - 5 },
				new int[] { height - BOTTOM_GAP - 40, height - BOTTOM_GAP - 35, height - BOTTOM_GAP - 30 }, 3);
	}

}
