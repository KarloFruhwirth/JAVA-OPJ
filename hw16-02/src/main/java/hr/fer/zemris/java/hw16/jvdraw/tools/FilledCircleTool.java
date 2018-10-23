package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

/**
 * Tool implementation used to define a {@link FilledCircle}
 * 
 * @author KarloFrühwirth
 *
 */
public class FilledCircleTool implements Tool {

	/**
	 * center of circle
	 */
	private Point center;
	/**
	 * End point
	 */
	private Point end;
	/**
	 * DrawingModel
	 */
	private DrawingModel dm;
	/**
	 * Click counter
	 */
	private int clicked = 0;
	/**
	 * Flag
	 */
	private boolean set = false;
	/**
	 * JColorArea
	 */
	private JColorArea jca1;
	/**
	 * JColorArea
	 */
	private JColorArea jca2;

	/**
	 * Constructor
	 * 
	 * @param model
	 *            DrawingModel
	 * @param fgColorArea
	 *            JColorArea
	 * @param bgColorArea
	 *            JColorArea
	 */
	public FilledCircleTool(DrawingModel model, JColorArea fgColorArea, JColorArea bgColorArea) {
		this.dm = model;
		this.jca1 = fgColorArea;
		this.jca2 = bgColorArea;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked++;
		if (!set) {
			center = e.getPoint();
			set = true;
		} else {
			end = e.getPoint();
			double distance = Math.hypot(center.x - end.x, center.y - end.y);
			set = false;
			dm.add(new FilledCircle(center.x, center.y, (int) distance, jca1.getCurrentColor(),
					jca2.getCurrentColor()));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (set) {
			end = e.getPoint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (clicked % 2 == 1) {
			double distance = Math.hypot(center.x - end.x, center.y - end.y);
			FilledCircle filledCircle = new FilledCircle(center.x, center.y, (int) distance, jca1.getCurrentColor(),
					jca2.getCurrentColor());
			g2d.setColor(filledCircle.getColor2());
			g2d.fillOval(filledCircle.getStartX() - filledCircle.getRadius(),
					filledCircle.getStartY() - filledCircle.getRadius(), filledCircle.getRadius() * 2,
					filledCircle.getRadius() * 2);
			g2d.setColor(filledCircle.getColor1());
			g2d.drawOval(filledCircle.getStartX() - filledCircle.getRadius(),
					filledCircle.getStartY() - filledCircle.getRadius(), filledCircle.getRadius() * 2,
					filledCircle.getRadius() * 2);
		}

	}

}
