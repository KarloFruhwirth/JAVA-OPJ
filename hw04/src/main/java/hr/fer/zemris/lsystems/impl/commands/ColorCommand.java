package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * @author KarloFrühwirth Class that implements Command it overrides the execute
 *         method to set the color of the line
 */
public class ColorCommand implements Command {
	/**
	 * String value of the color
	 */
	private String color;

	/**
	 * Constructor for the ColorCommand sets the color to color
	 * 
	 * @param color
	 */
	public ColorCommand(String color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setColor(color);
	}
}
