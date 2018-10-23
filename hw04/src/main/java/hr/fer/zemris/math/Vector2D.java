package hr.fer.zemris.math;

/**
 * @author KarloFrühwirth Class that represents a 2D vector it is defined by its
 *         x and y
 */
public class Vector2D {
	/**
	 * X coordinate
	 */
	private double x;
	/**
	 * Y coordinate
	 */
	private double y;

	/**
	 * Constructor for the Vector2D
	 * 
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates the current Vector2D depending on the offset
	 * 
	 * @param offset
	 */
	public void translate(Vector2D offset) {
		x = x + offset.getX();
		y = y + offset.getY();
	}

	/**
	 * Returns a new Vector2D that is the same as a translated current Vector2D
	 * depending on the offset
	 * 
	 * @param offset
	 * @return
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D translated = new Vector2D(x + offset.getX(), y + offset.getY());
		return translated;
	}

	/**
	 * Rotates the Vector2D for the given angle
	 * 
	 * @param angle
	 */
	public void rotate(double angle) {
		while (angle < 0) {
			angle += 360;
		}
		double c = Math.sqrt(x * x + y * y);
		double rotatex = c * Math.cos(Math.atan2(y, x) + Math.toRadians(angle % 360));
		double rotatey = c * Math.sin(Math.atan2(y, x) + Math.toRadians(angle % 360));
		x = rotatex;
		y = rotatey;
	}

	/**
	 * Returns a new Vector2D that is a rotated Vector2D for the given angle
	 * 
	 * @param angle
	 * @return
	 */
	public Vector2D rotated(double angle) {
		while (angle < 0) {
			angle += 360;
		}
		double c = Math.sqrt(x * x + y * y);
		double rotatex = c * Math.cos(Math.atan2(y, x) + Math.toRadians(angle % 360));
		double rotatey = c * Math.sin(Math.atan2(y, x) + Math.toRadians(angle % 360));
		Vector2D rotated = new Vector2D(rotatex, rotatey);
		return rotated;
	}

	/**
	 * Scales the Vector2D
	 * 
	 * @param scaler
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Creates a new Vector2D that is the same as the original Vector2D scaled by
	 * scaler
	 * 
	 * @param scaler
	 * @return new Vector2D
	 */
	public Vector2D scaled(double scaler) {
		Vector2D scaled = new Vector2D(x * scaler, y * scaler);
		return scaled;
	}

	/**
	 * Copy of a Vector2D
	 * 
	 * @return copy
	 */
	public Vector2D copy() {
		Vector2D copy = new Vector2D(this.x, this.y);
		return copy;
	}

	/**
	 * Method that normalizates
	 * 
	 * @return normalized Vector2D
	 */
	public Vector2D normalization() {
		double lenght = Math.sqrt(x * x + y * y);
		Vector2D nomalized = new Vector2D(x / lenght, y / lenght);
		return nomalized;
	}

}
