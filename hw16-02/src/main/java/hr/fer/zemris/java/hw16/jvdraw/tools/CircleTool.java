package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

/**
 * Tool implementation used to define a {@link Circle}
 * 
 * @author KarloFrühwirth
 *
 */
public class CircleTool implements Tool {

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
	private JColorArea jca;

	/**
	 * Constructor
	 * 
	 * @param model
	 *            DrawingModel
	 * @param fgColorArea
	 *            JColorArea
	 */
	public CircleTool(DrawingModel model, JColorArea fgColorArea) {
		this.dm = model;
		this.jca = fgColorArea;
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
			dm.add(new Circle(center.x, center.y, (int) distance, jca.getCurrentColor()));
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
			Circle circle = new Circle(center.x, center.y, (int) distance, jca.getCurrentColor());
			g2d.setColor(circle.getColor());
			g2d.drawOval(center.x - (int) distance, center.y - (int) distance, circle.getRadius() * 2,
					circle.getRadius() * 2);
		}

	}

}
