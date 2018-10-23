package hr.fer.zemris.java.hw06.demo2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class PrimesCollectionTest {
	
	private int[] primeNumbersArray = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,97 };
	
	@Test
	public void TestPrimeNumbers() {
		PrimesCollection primesCollection = new PrimesCollection(10);
		int i =0;
		for (Integer prime : primesCollection) {
			assertEquals(prime.intValue(), primeNumbersArray[i]);
			i++;
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestWrongInput() {
		PrimesCollection primesCollection = new PrimesCollection(-2);
		for (Integer prime : primesCollection) {
			System.out.println(prime);
		}
	}
	
	@Test
	public void TestHasNext() {
		PrimesCollection primesCollection = new PrimesCollection(1);
		assertEquals(primesCollection.iterator().hasNext(), true);
	}

}
