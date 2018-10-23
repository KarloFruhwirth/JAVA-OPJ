package hr.fer.zemris.java.custom.collections;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that supports work with complex numbers and represents an unmodifiable complex number
 * It also provides us with methods useful for working with complex numbers
 * Every complex number has its real and imaginary part
 * @author KarloFrühwirth
 * @version 1.0
 */
public class ComplexNumber {
	private double real;
	private double imaginary;

	/** 
	 * Constructor that sets the real and imaginary to the given params
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Method that returns a complex number only consisted of the real part which is set to param
	 * @param real
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Method that returns a complex number only consisted of the imaginary part which is set to param
	 * @param imaginary
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Method that returns a complex number based on its magnitude 
	 * and angle which is set to param
	 * @param magnitude
	 * @param angle
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = Math.cos(angle) * magnitude;
		double imaginary = Math.sin(angle) * magnitude;
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method that returns a complex number based on a String which is then matched to the regex 
	 * and returns a complex number or throws a IllegalAccessError exception
	 * @param s
	 * @return ComplexNumber
	 */
	public static ComplexNumber parse(String s) {
		String pattern1 = "(-?[0-9]+\\.?[0-9]*)([+|-]?[0-9]+\\.?[0-9]*i?)";
		String pattern2 = "([-]?[0-9]+\\.?[0-9]*)[i$]";
		String pattern3 = "(-?[0-9]+\\\\.?[0-9]*)";
		String pattern4 = "-?i";
		Pattern r1 = Pattern.compile(pattern1);
		Pattern r2 = Pattern.compile(pattern2);
		Pattern r3 = Pattern.compile(pattern3);
		Pattern r4 = Pattern.compile(pattern4);
		Matcher m1 = r1.matcher(s);
		Matcher m2 = r2.matcher(s);
		Matcher m3 = r3.matcher(s);
		Matcher m4 = r4.matcher(s);
		if (m1.find()) {
			double real = Double.parseDouble(m1.group(1));
			double imaginary = Double.parseDouble(m1.group(2).substring(0,m1.group(2).length()-1));
			return new ComplexNumber(real, imaginary);
		} else if (m2.find()) {
			double imaginary = Double.parseDouble(m2.group(1));
			return new ComplexNumber(0, imaginary);
		}else if(m3.find()){
			double real = Double.parseDouble(m3.group(1));
			return new ComplexNumber(real, 0);
		}else if (m4.find()){
			if (m4.group(0) == "-i") {
				return new ComplexNumber(0, -1);
			} else {
				return new ComplexNumber(0, 1);
			}
		}
		else {
			throw new IllegalAccessError("This string does not represent a complex number" + s);
		}
		
	}

	/**
	 * Returns the real part of complex number
	 * @return real
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Returns the imaginary part of complex number
	 * @return imaginary
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Returns the magnitude of a complex number
	 * @return magnitude
	 */
	public double getMagnitude() {
		double rez = Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
		return rez;
	}

	/**
	 * Returns the angle of a complex number
	 * @return angle
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary,real );
		return angle;
	}

	/**
	 * Returns a new complex number as a result of addition 
	 * @param c 
	 * @return  ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Returns a new complex number as a result of subtraction
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Returns a new complex number as a result of multiplication
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Returns a new complex number as a result of division
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		double divider = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		return new ComplexNumber((this.real * c.real + this.imaginary * c.imaginary) / divider,
				(this.imaginary * c.real - this.real * c.imaginary ) / divider);
	}

	/**
	 * Returns a new complex number as a result of power function
	 * Exponent must be positive or the method throws an IllegalArgumentException
	 * @param n
	 * @return ComplexNumber
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Pow must have a positiv exponent, you have inputed" + n);
		}
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		return new ComplexNumber(Math.pow(magnitude, n) * Math.cos(n * angle),
				Math.pow(magnitude, n) * Math.sin(n * angle));
	}
	
	/**
	 * Returns an array of size n of  complex number as a result of root function
	 * Root must be positive or the method throws an IllegalArgumentException
	 * @param n size of an array
	 * @return ComplexNumber
	 */
	public ComplexNumber[] root(int n){
		if (n <= 0) {
			throw new IllegalArgumentException("Root must be greater than 0, you have inputed" + n);
		}
		double angle = this.getAngle();
		double magnitude = this.getMagnitude();
		ComplexNumber[] rez = new ComplexNumber[n];
		for(int i = 0;i<n;i++) {
			rez[i]=new ComplexNumber(Math.pow(magnitude, 1.0/n)*Math.cos((angle+2*Math.PI*i)/n),
					Math.pow(magnitude, 1.0/n)*Math.sin((angle+2*Math.PI*i)/n));
		}
		return rez;
	}
	
	@Override
	public String toString() {
		String text = "";
		if(this.real!=0) {
			text+=String.valueOf(this.real);
		}if(this.imaginary!=0) {
			if(this.imaginary>0) {
				text+="+"+String.valueOf(this.imaginary);
				text+="i";
			}else {
				text+=String.valueOf(this.imaginary);
				text+="i";
			}
		}
		return text;
	}

}
