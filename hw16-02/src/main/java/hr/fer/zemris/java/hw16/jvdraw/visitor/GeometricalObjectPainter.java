package hr.fer.zemris.java.hw16.jvdraw.visitor;

import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPoligon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Visitor implements GeometricalObjectVisitor used to draw objects
 * 
 * @author KarloFrühwirth
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics2D
	 */
	private Graphics2D graphics;

	/**
	 * Constructor
	 * 
	 * @param graphics
	 *            Graphics2D
	 */
	public GeometricalObjectPainter(Graphics2D graphics) {
		this.graphics = graphics;
	}

	@Override
	public void visit(Line line) {
		graphics.setColor(line.getColor());
		graphics.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	}

	@Override
	public void visit(Circle circle) {
		graphics.setColor(circle.getColor());
		graphics.drawOval(circle.getStartX() - circle.getRadius(), circle.getStartY() - circle.getRadius(),
				circle.getRadius() * 2, circle.getRadius() * 2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		graphics.setColor(filledCircle.getColor2());
		graphics.fillOval(filledCircle.getStartX() - filledCircle.getRadius(),
				filledCircle.getStartY() - filledCircle.getRadius(), filledCircle.getRadius() * 2,
				filledCircle.getRadius() * 2);
		graphics.setColor(filledCircle.getColor1());
		graphics.drawOval(filledCircle.getStartX() - filledCircle.getRadius(),
				filledCircle.getStartY() - filledCircle.getRadius(), filledCircle.getRadius() * 2,
				filledCircle.getRadius() * 2);

	}

	@Override
	public void visit(FilledPoligon filledPoligon) {
		int[] xPoints = new int[filledPoligon.getPoints().size()];
		int[] yPoints = new int[filledPoligon.getPoints().size()];
		int i = 0;
		for (Point p : filledPoligon.getPoints()) {
			xPoints[i] = p.x;
			yPoints[i] = p.y;
			i++;
		}
		graphics.setColor(filledPoligon.getColor2());
		graphics.fillPolygon(xPoints, yPoints, i);
		graphics.setColor(filledPoligon.getColor1());
		graphics.drawPolygon(xPoints, yPoints, i);
	}

}
