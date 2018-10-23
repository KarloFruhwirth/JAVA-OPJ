package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Tool implementation used to define a {@link Line}
 * 
 * @author KarloFrühwirth
 *
 */
public class LineTool implements Tool {

	/**
	 * Start point
	 */
	private Point start;
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
	public LineTool(DrawingModel model, JColorArea fgColorArea) {
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
			start = e.getPoint();
			set = true;
		} else {
			end = e.getPoint();
			set = false;
			dm.add(new Line(start.x, start.y, end.x, end.y, jca.getCurrentColor()));
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
			Line line = new Line((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY(),
					jca.getCurrentColor());
			g2d.setColor(line.getColor());
			g2d.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
		}

	}

}
