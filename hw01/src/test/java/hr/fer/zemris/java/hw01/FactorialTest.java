package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Factorial
 * 
 * @author KarloFrühwirth
 * @version 1.0
 */
public class FactorialTest {

	/**
	 * Test for negative number input
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeNumber() {
		Factorial.calculate(-4);
	}

	/**
	 * Test for too large number input
	 */
	@Test(expected = IllegalArgumentException.class)
	public void numberTooLarge() {
		Factorial.calculate(25);
	}

	/**
	 * Test for input of number zero
	 */
	@Test(expected = IllegalArgumentException.class)
	public void zero() {
		Factorial.calculate(0);
	}

	/**
	 * Test for input of number 3
	 */
	@Test
	public void three() {
		Assert.assertEquals(6, Factorial.calculate(3));
	}

	/**
	 * Test for input of number 6
	 */
	@Test
	public void six() {
		Assert.assertEquals(720, Factorial.calculate(6));
	}

	/**
	 * Test for input of number 20
	 */
	@Test
	public void twenty() {
		long twenty = 2432902008176640000L;
		Assert.assertEquals(twenty, Factorial.calculate(20));
	}
}
