package hr.fer.zemris.java.hw06.demo2;

/**
 * Second demo class which show the functionality of the
 * {@link PrimesCollection} in a way that it prints out the Cartesian product
 * with 2 nested for loops
 * 
 * @author KarloFrühwirth
 *
 */
public class PrimesDemo2 {
	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
