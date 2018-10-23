package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * @author KarloFrühwirth Class that implements Command it overrides the execute
 *         method to to calculate the location where the turtle must move and
 *         stores the position in the currentstate and draw a line
 */
public class DrawCommand implements Command {
	/**
	 * Step for the line
	 */
	private double step;

	/**
	 * Constructor for the DrawCommand sets the step to step
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		double x0 = ctx.getCurrentState().getPosition().getX();
		double y0 = ctx.getCurrentState().getPosition().getY();

		// Vector2D original = new Vector2D(x0, y0);
		Vector2D offset = ctx.getCurrentState().getOrientation().normalization()
				.scaled(step * ctx.getCurrentState().getUnitLength());
		// original.translate(offset);
		ctx.getCurrentState().getPosition().translate(offset);

		double x1 = ctx.getCurrentState().getPosition().getX();
		double y1 = ctx.getCurrentState().getPosition().getY();

		painter.drawLine(x0, y0, x1, y1, ctx.getCurrentState().getColor(), 1);

	}
}
