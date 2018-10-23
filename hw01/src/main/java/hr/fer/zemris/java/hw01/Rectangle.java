package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author KarloFrühwirth
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Method called on start of the program
	 * 
	 * @param args
	 *            arguments from the command line
	 */
	public static void main(String[] args) {
		int numberOfArguments = args.length;

		if (numberOfArguments == 2) {
			try {
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);
				print(width, height);
			} catch (IllegalArgumentException e) {
				System.out.println("Pogrešni argumenti, lijep pozdrav");
				System.exit(1);
			}
		} else if (numberOfArguments == 0) {
			double width = input("sirinu");
			double height = input("visinu");
			print(width, height);
		} else {
			System.out.println("Pogrešni argumenti, lijep pozdrav");
			System.exit(1);
		}

	}

	/**
	 * Method for user input of the value for the variable until correct
	 * 
	 * @param string
	 *            specifies which variable we are working with
	 * @return valid number input for the variable
	 */
	static double input(String string) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		double number;

		while (true) {
			System.out.print("Unesite " + string + " > ");
			String input = sc.next();
			try {
				number = Double.parseDouble(input);
				if (number < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else if (number == 0) {
					System.out.println("Unijeli ste nulu.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "'  se ne može protumačiti kao broj.");
			}
		}
		return number;
	}

	/**
	 * Method for printing the data regarding the given rectangle in a specified
	 * form
	 * 
	 * @param width
	 *            rectangle width
	 * @param height
	 *            rectangle height
	 */
	static void print(double width, double height) {

		if (width < 1 || height < 1) {
			throw new IllegalArgumentException();
		}
		System.out.printf("Pravokutnik širine " + "%.1f" + " i visine " + "%.1f" + " ima površinu " + "%.1f"
				+ " te opseg " + "%.1f", width, height, surface(width, height), perimeter(width, height));
	}

	/**
	 * Method for calculating the perimeter of the given rectangle
	 * 
	 * @param width
	 *            rectangle width
	 * @param height
	 *            rectangle height
	 * @return perimeter of the rectangle
	 */
	static double perimeter(double width, double height) {
		return 2 * width + 2 * height;
	}

	/**
	 * Method for calculating the surface of the given rectangle
	 * 
	 * @param width
	 *            rectangle width
	 * @param height
	 *            rectangle height
	 * @return surface of the rectangle
	 */
	static double surface(double width, double height) {
		return width * height;
	}

}