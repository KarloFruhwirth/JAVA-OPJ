package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * @author KarloFrühwirth
 *
 *Interface for commands that defines a method execute
 */
public interface Command {
	
	/**
	 * Method that depending on the command instance executes the defined task 
	 * @param ctx Contex
	 * @param painter Painter
	 */
	void execute(Context ctx, Painter painter);

}
