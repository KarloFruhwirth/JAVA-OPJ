package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author KarloFrühwirth Class that implements Command it overrides the execute
 *         method to scale the unithlenght
 */
public class ScaleCommand implements Command {
	/**
	 * Double representation of scale
	 */
	private double scale;

	/**
	 * Constructor for the ScaleCommand sets the scale to scale
	 * 
	 * @param scale
	 */
	public ScaleCommand(double scale) {
		this.scale = scale;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setUnitLength(scale);
	}

}
