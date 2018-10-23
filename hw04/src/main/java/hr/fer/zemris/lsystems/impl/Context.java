package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * @author KarloFrühwirth Class that enables the execution of the fraktal it
 *         also offers an ObjectStack on which we can push and pop the state of
 *         the TurtleState
 */
public class Context {
	/**
	 * Representation of the ObjectStack
	 */
	private ObjectStack stack = new ObjectStack();

	/**
	 * Method that returns the top of the ObjectStack
	 * 
	 * @return peek
	 */
	public TurtleState getCurrentState() {
		TurtleState state = (TurtleState) stack.peek();
		return state;
	}

	/**
	 * Method that pushes the TurtleState to the ObjectStack
	 * 
	 * @param state
	 *            TurtleState
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Method that removes the top TurtleState from the ObjectStack
	 */
	public void popState() {
		stack.pop();
	}
}
