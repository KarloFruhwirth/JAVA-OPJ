package hr.fer.zemris.java.custom.collections;

/**
 * @author KarloFrühwirth
 * @version 1.0
 * 
 * Demo example of the functions for the ComplexNumber class
 */
public class ComplexDemo {

	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c4 = ComplexNumber.fromMagnitudeAndAngle(2, 1.57);
		ComplexNumber c5 = c1.add(c4).div(c2);
		ComplexNumber c6 = c5.power(3);
		ComplexNumber c3 = c6.root(2)[1];
		System.out.println(c3);
		ComplexNumber c7 = ComplexNumber.parse("2-3i");
		System.out.println(c7);
	}

}
