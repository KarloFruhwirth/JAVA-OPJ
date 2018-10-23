package hr.fer.zemris.java.hw16.jvdraw.visitor;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPoligon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Visitor implements GeometricalObjectVisitor
 * 
 * @author KarloFrühwirth
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Point
	 */
	private Point min = new Point(10000, 10000);
	/**
	 * Point
	 */
	private Point end = new Point(-10000, -10000);

	@Override
	public void visit(Line line) {
		if (line.getStartX() < min.getX())
			min.x = line.getStartX();
		if (line.getEndX() < min.getX())
			min.x = line.getEndX();
		if (line.getStartY() < min.getY())
			min.y = line.getStartY();
		if (line.getEndY() < min.getY())
			min.y = line.getEndY();

		if (line.getStartX() > end.getX())
			end.x = line.getStartX();
		if (line.getEndX() > end.getX())
			end.x = line.getEndX();
		if (line.getStartY() > end.getY())
			end.y = line.getStartY();
		if (line.getEndY() > end.getY())
			end.y = line.getEndY();
	}

	@Override
	public void visit(Circle circle) {
		int r = circle.getRadius();
		Point c = new Point(circle.getStartX(), circle.getStartY());
		if (c.getX() - r < min.getX())
			min.x = c.x - r;
		if (c.getY() - r < min.getY())
			min.y = c.y - r;
		if (c.getX() + r > end.getX())
			end.x = c.x + r;
		if (c.getY() + r > end.getY())
			end.y = c.y + r;
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int r = filledCircle.getRadius();
		Point c = new Point(filledCircle.getStartX(), filledCircle.getStartY());
		if (c.getX() - r < min.getX())
			min.x = c.x - r;
		if (c.getY() - r < min.getY())
			min.y = c.y - r;
		if (c.getX() + r > end.getX())
			end.x = c.x + r;
		if (c.getY() + r > end.getY())
			end.y = c.y + r;

	}

	@Override
	public void visit(FilledPoligon filledPoligon) {
		List<Point> list = filledPoligon.getPoints();
		for (Point p : list) {
			if (p.x < min.getX())
				min.x = p.x;
			if (p.x > end.getX())
				end.x = p.x;
			if (p.y < min.getY())
				min.y = p.y;
			if (p.y > end.getY())
				end.y = p.y;
		}
	}

	/**
	 * @return Rectangle with set bounds
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(min.x, min.y, end.x - min.x, end.y - min.y);
	}

}
