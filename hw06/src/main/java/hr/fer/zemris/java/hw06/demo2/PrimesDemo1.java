package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo class which show the functionality of the {@link PrimesCollection} in a
 * way that it prints out the first n prime numbers with 2 nested for loops
 * 
 * @author KarloFrühwirth
 *
 */
public class PrimesDemo1 {
	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
