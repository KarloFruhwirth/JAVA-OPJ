package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author KarloFrühwirth
 * @version 1.0
 */
public class Factorial {

	/**
	 * Method called on start of the program
	 * 
	 * @param args
	 *            arguments from the command line
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			if (input.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}
			try {
				long number = Long.parseLong(input);
				long result = calculate(number);
				System.out.println(number + "! = " + result);
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' nije cijeli broj");
			} catch (IllegalArgumentException a) {
				System.out.println("'" + input + "' nije u dozvoljenom rasponu.");
			}

		}
		sc.close();
	}

	/**
	 * Method for calculating a factorial for a certain number
	 * 
	 * @param number
	 *            inputed number
	 * @return calculated factorial
	 */
	static long calculate(long number) {
		if (number < 1 || number > 20) {
			throw new IllegalArgumentException();
		}
		long fact = 1;
		for (int i = 1; i <= number; i++) {
			fact *= i;
		}
		return fact;
	}
}
