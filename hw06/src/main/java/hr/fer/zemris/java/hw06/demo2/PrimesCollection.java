package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * Class {@link PrimesCollection} implements Iterable since it must go trough
 * the first n prime numbers and return them without the usage of any multiple
 * element storages. It holds an integer value which is used to determine how
 * many prime numbers do we seek to get.
 * 
 * @author KarloFrühwirth
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Determines the number of primes
	 */
	private int numberOfPrimes;

	/**
	 * Constructor for the PrimesCollection
	 * 
	 * @param numberOfPrimes
	 *            sets the number of primes
	 * @throws IllegalArgumentException
	 *             if numberOfPrimes is null or less than 1
	 */
	public PrimesCollection(int numberOfPrimes) {
		if (numberOfPrimes < 1)
			throw new IllegalArgumentException("numberOfPrimes must be an int greater than 1");
		this.numberOfPrimes = numberOfPrimes;
	}

	/**
	 * Getter for the numberOfPrimes
	 * 
	 * @return numberOfPrimes
	 */
	public int getNumberOfPrimes() {
		return numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImplementation();
	}

	/**
	 * Our own implementation of an iterator used to iterate trough the prime
	 * numbers.
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	private class IteratorImplementation implements Iterator<Integer> {
		/**
		 * Counter for how many prime numbers have already been returned
		 */
		private int counter;
		/**
		 * Prime number, starts of as 2 since that is the first prime number
		 */
		private int number = 2;

		@Override
		public boolean hasNext() {
			return (counter < numberOfPrimes);
		}

		@Override
		public Integer next() {
			while (hasNext()) {
				boolean primeNum = true;
				for (int i = 2; i <= Math.sqrt(number); i++) {
					if (number % i == 0) {
						primeNum = false;
						break;
					}
				}
				if (primeNum) {
					counter++;
					int nextPrime = number;
					number++;
					return nextPrime;
				}
				number++;
			}
			throw new IllegalArgumentException("No next elements");
		}

	}
}
