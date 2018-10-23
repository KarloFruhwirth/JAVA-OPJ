package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * @author KarloFrühwirth Class that remembers the current possition,
 *         orientation, color and the unitLenght of the Turtle It also defines a
 *         method copy which returns a new object with the copy of the current
 *         state
 */
public class TurtleState {
	/**
	 * Turtles current position
	 */
	private Vector2D position;
	/**
	 * Turtles current orientation
	 */
	private Vector2D orientation;
	/**
	 * Color of the line that the turtle draws
	 */
	private Color color;
	/**
	 * initial length of the line drawn
	 */
	private double unitLength;

	/**
	 * Constructor for the TurtleState
	 * 
	 * @param position
	 *            position
	 * @param orientation
	 *            orientation
	 * @param color
	 *            color
	 * @param unitLength
	 *            unitLength
	 */
	public TurtleState(Vector2D position, Vector2D orientation, Color color, double unitLength) {
		this.position = position;
		this.orientation = orientation;
		this.color = color;
		this.unitLength = unitLength;
	}

	/**
	 * Method which returns a new object with the copy of the current state
	 * 
	 * @return new TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), orientation.copy(), color, unitLength);
	}

	/**
	 * Getter for position
	 * 
	 * @return position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Getter for orientation
	 * 
	 * @return orientation
	 */
	public Vector2D getOrientation() {
		return orientation;
	}

	/**
	 * Getter for color
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color
	 * 
	 * @param color
	 *            color
	 */
	public void setColor(String color) {
		this.color = Color.decode("#" + color);
	}

	/**
	 * Getter for unitLength
	 * 
	 * @return unitLength
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * Setter for unitLength
	 * 
	 * @param scale
	 *            which scales the unitLength
	 */
	public void setUnitLength(double scale) {
		this.unitLength *= scale;
	}

}
