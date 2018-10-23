package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * @author KarloFrühwirth Class that implements Command it overrides the execute
 *         method to copy the peek of the stack and to pussh it on the stack
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}
}
