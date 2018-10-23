package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a complex number z=a+bi Standard operations for working
 * with complex numbers are provided:<br>
 * multiply,divide,add,sub,module,negate,power,root.
 * 
 * @author KarloFrühwirth
 *
 */
public class Complex {
	/**
	 * Real and imaginary parts of the complex number
	 */
	private double real, imaginary;

	/**
	 * Complex number representing 0+0i
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Complex number representing 1+0i
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Complex number representing -1+0i
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Complex number representing 0+i
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex number representing 0-i
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor for complex number returns 0+0i
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor for complex number, sets real to re and imaginary to im
	 * 
	 * @param re
	 *            real
	 * @param im
	 *            imaginary
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}

	/**
	 * @return module of complex number
	 */
	public double module() {
		double rez = Math.sqrt(real * real + imaginary * imaginary);
		return rez;
	}

	/**
	 * Multiplication of two complex numbers
	 * 
	 * @param c
	 * @return this*c
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Division of complex numbers
	 * 
	 * @param c
	 *            Complex
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		double divider = c.real * c.real + c.imaginary * c.imaginary;
		c = conjugate(c);
		Complex multiply = this.multiply(c);

		return new Complex(multiply.real / divider, multiply.imaginary / divider);
	}

	/**
	 * Conjugates the Complex number a+ib -> a-ib
	 * 
	 * @param complex
	 *            Complex
	 * @return connjugate complex
	 */
	public Complex conjugate(Complex complex) {
		return new Complex(complex.real, -complex.imaginary);
	}

	/**
	 * Addition of complex numbers
	 * 
	 * @param c
	 *            Complex
	 * @return this + c
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	// returns this-c
	/**
	 * Subtraction of complex numbers
	 * 
	 * @param c
	 *            Complex
	 * @return this -c
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Negate complex number
	 * 
	 * @return -this
	 */
	public Complex negate() {
		return this.multiply(ONE_NEG);
	}

	/**
	 * Calculates the power value of complex number
	 * 
	 * @param n
	 *            power
	 * @return this^n, n is non-negative integer
	 * @throws IllegalArgumentException
	 *             if n is <0
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Pow must have a positiv exponent, you have inputed" + n);
		}
		double module = this.module();
		double angle = Math.atan2(imaginary, real);
		return new Complex(Math.pow(module, n) * Math.cos(n * angle), Math.pow(module, n) * Math.sin(n * angle));
	}

	/**
	 * Calculates the root value of complex number
	 * 
	 * @param n
	 *            root
	 * @return returns n-th root of this, n is positive integer
	 * @throws IllegalArgumentException
	 *             if n is <0
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root must be greater than 0, you have inputed" + n);
		}
		double angle = Math.atan2(imaginary, real);
		double module = this.module();
		List<Complex> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(new Complex(Math.pow(module, 1.0 / n) * Math.cos((angle + 2 * Math.PI * i) / n),
					Math.pow(module, 1.0 / n) * Math.sin((angle + 2 * Math.PI * i) / n)));
		}
		return list;
	}

	/**
	 * Getter for real part of the complex number
	 * 
	 * @return real
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter for imaginary part of the complex number
	 * 
	 * @return imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.real != 0) {
			sb.append(String.valueOf(this.real));
		}
		if (this.imaginary != 0) {
			if (this.imaginary > 0 && this.real != 0) {
				sb.append("+" + String.valueOf(this.imaginary) + "i");
			} else if (this.imaginary > 0 && this.real == 0) {
				sb.append(String.valueOf(this.imaginary) + "i");
			} else {
				sb.append("-" + String.valueOf(this.imaginary * -1) + "i");
			}
		}
		if (sb.length() == 0)
			sb.append("0");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
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
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

}
