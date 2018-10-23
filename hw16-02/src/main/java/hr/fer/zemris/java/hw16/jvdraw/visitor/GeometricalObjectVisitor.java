package hr.fer.zemris.java.hw16.jvdraw.visitor;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPoligon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Visitor used as described in Visitor format
 * 
 * @author KarloFrühwirth
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Method performed on Line
	 * 
	 * @param line
	 *            Line
	 */
	public abstract void visit(Line line);

	/**
	 * Method performed on Circle
	 * 
	 * @param line
	 *            Line
	 */
	public abstract void visit(Circle circle);

	/**
	 * Method performed on FilledCircle
	 * 
	 * @param line
	 *            Line
	 */
	public abstract void visit(FilledCircle filledCircle);
	
	public abstract void visit(FilledPoligon filledPoligon);
}