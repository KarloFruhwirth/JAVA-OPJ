package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Polynomial f(z) = z^n*zn+z^n-1*zn-1+...+z^2*z2+z^1*z+z is created based on
 * the factors provided in the constructor. Polynomial is of the n-th order
 * 
 * @author KarloFrühwirth
 *
 */
public class ComplexPolynomial {
	/**
	 * List of complex numbers
	 */
	private List<Complex> list = new ArrayList<>();

	/**
	 * Constructor for ComplexPolynomial adds factors to list
	 * 
	 * @param factors
	 *            variable number of complex numbers
	 * @throws IllegalArgumentException
	 *             if no complex numbers are provided
	 * 
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors.length == 0)
			throw new IllegalArgumentException("ComplexPolynomial must recive one Complex number");
		for (Complex c : factors) {
			list.add(c);
		}
	}

	/**
	 * @return order of this polynom
	 */
	public short order() {
		return (short) (list.size() - 1);
	}

	// computes a new polynomial this*p
	/**
	 * Multiplication of ComplexPolynomials
	 * 
	 * @param p
	 *            ComplexPolynomial
	 * @return new polynomial this*p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[list.size() + p.getList().size() - 1];
		Arrays.fill(result, Complex.ZERO);
		for (int i = 0, size = list.size(); i < size; i++) {
			for (int j = 0, size2 = p.getList().size(); j < size2; j++) {
				result[i + j] = result[i + j].add(list.get(i).multiply(p.getList().get(j)));
			}
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * Derives the ComplexPolynomial.<br>
	 * example : (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[list.size() - 1];
		Arrays.fill(result, Complex.ZERO);
		for (int i = 0, size = list.size() - 1; i < size; i++) {
			result[i] = list.get(i).multiply(new Complex(size - i, 0));
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z
	 *            Complex
	 * @return Complex result
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		int exponent = list.size() - 1;
		for (Complex c : list) {
			result = result.add(c.multiply(z.power(exponent)));
			exponent--;
		}
		return result;
	}

	/**
	 * Getter for list of complex numbers
	 * 
	 * @return list
	 */
	public List<Complex> getList() {
		return list;
	}

	@Override
	public String toString() {
		int exponent = this.order();
		StringBuilder sb = new StringBuilder();
		for (Complex c : list) {
			if (exponent == 0) {
				sb.append("(" + c + ")");
			} else if (exponent == 1) {
				if (c.getImaginary() == 0) {
					sb.append("(" + c + ")z+");
				} else {
					sb.append("(" + c + ")z+");
				}
			} else {
				if (c.getImaginary() == 0) {
					sb.append("(" + c + ")z^" + exponent + "+");
				} else {
					sb.append(("(" + c + ")z^" + exponent + "+"));
				}
			}
			exponent--;
		}
		return sb.toString();
	}
}
