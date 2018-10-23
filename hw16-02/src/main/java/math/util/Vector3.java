package math.util;

/**
 * Class representing a vector in 3D. Its components are x,y,z. Usual operations
 * are provided, such as : <br>
 * norm,normalized,add,sub,dot,cross,scale,angle
 * 
 * @author KarloFrühwirth
 *
 */
public class Vector3 {
	/**
	 * Double values for x,y,z
	 */
	private double x, y, z;

	/**
	 * Constructor for Vector3
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param z
	 *            z
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return norm of the Vector3
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalized vector A/|A|
	 * 
	 * @return normalized Vector3
	 */
	public Vector3 normalized() {
		double coefficient = Math.sqrt((x * x + y * y + z * z));
		Vector3 result = new Vector3(x / coefficient, y / coefficient, z / coefficient);
		return result;
	}

	/**
	 * Addition of Vector3s
	 * 
	 * @param other
	 *            Vector3
	 * @return Vector3
	 */
	public Vector3 add(Vector3 other) {
		Vector3 result = new Vector3(x + other.x, y + other.y, z + other.z);
		return result;
	}

	/**
	 * Subtraction of Vector3s
	 * 
	 * @param other
	 *            Vector3
	 * @return Vector3
	 */
	public Vector3 sub(Vector3 other) {
		Vector3 result = new Vector3(x - other.x, y - other.y, z - other.z);
		return result;
	}

	/**
	 * Scalar of vector1 * vector2
	 * 
	 * @param other
	 *            Vector3
	 * @return Vector3
	 */
	public double dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Cross product of vectors: vector1 x vector2
	 * 
	 * @param other
	 *            Vector3
	 * @return Vector3
	 */
	public Vector3 cross(Vector3 other) {
		Vector3 rez = new Vector3(y * other.z - other.y * z, -x * other.z + other.x * z, x * other.y - other.x * y);
		return rez;
	}

	/**
	 * Scaling of the Vector3
	 * 
	 * @param s
	 *            scale
	 * @return Vector3
	 */
	public Vector3 scale(double s) {
		Vector3 result = new Vector3(x * s, y * s, z * s);
		return result;
	}

	/**
	 * Calculates angle between Vector3s
	 * 
	 * @param other
	 *            Vector3
	 * @return cos angle
	 */
	public double cosAngle(Vector3 other) {
		double angle = dot(other) / (norm() * other.norm());
		return angle;
	}

	/**
	 * Getter for x
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter for z
	 * 
	 * @return
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @return array representation of x,y,z
	 */
	public double[] toArray() {
		double[] array = { x, y, z };
		return array;
	}

	public String toString() {
		String text = String.format("(%.6f, %.6f, %.6f)", x, y, z);
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}
