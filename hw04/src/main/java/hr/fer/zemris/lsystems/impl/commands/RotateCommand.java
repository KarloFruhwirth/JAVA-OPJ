package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author KarloFrühwirth Class that implements Command it overrides the execute
 *         method to rotate the orientation of the turtle
 */
public class RotateCommand implements Command {
	/**
	 * angle
	 */
	private double angle;

	/**
	 * Constructor for the RotateCommand sets the angle to angle
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.getOrientation().rotate(angle);
	}
}
