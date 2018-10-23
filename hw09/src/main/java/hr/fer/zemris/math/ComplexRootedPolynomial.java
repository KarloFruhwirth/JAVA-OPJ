package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Polynomial f(z) = (z-z1)*(z-z2)*...*(z-zn) is created based on the roots
 * provided in the constructor. Polynomial is of the n-th order
 * 
 * @author KarloFrühwirth
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * List of complex numbers
	 */
	private List<Complex> list = new ArrayList<>();

	/**
	 * Constructor for ComplexRootedPolynomial adds factors to list
	 * 
	 * @param roots
	 * @throws IllegalArgumentException
	 *             if no complex numbers are provided
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots.length == 0)
			throw new IllegalArgumentException("ComplexRootedPolynomial must recive one Complex number");
		for (Complex c : roots) {
			list.add(c);
		}
	}

	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z
	 *            Complex
	 * @return Complex
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for (Complex c : list) {
			result = result.multiply(z.sub(c));
		}
		return result;
	}

	/**
	 * Converts this representation to ComplexPolynomial type
	 * 
	 * @return ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial complexPolynomial = new ComplexPolynomial(Complex.ONE);
		for (Complex c : list) {
			complexPolynomial = complexPolynomial.multiply(new ComplexPolynomial(Complex.ONE, c.negate()));
		}
		return complexPolynomial;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		list.forEach((k) -> sb.append("(z-" + k + ")"));
		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold<br>
	 * if there is no such root, returns -1
	 * 
	 * @param z
	 *            Complex
	 * @param treshold
	 *            treshold
	 * @return index
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int closest = -1;
		double defaultDistance = Double.MAX_VALUE;
		double distance;
		for (Complex c : list) {
			distance = z.sub(c).module();
			if (distance < defaultDistance && distance < treshold) {
				defaultDistance = distance;
				closest = list.indexOf(c);
			}
		}
		return closest;
	}
}
