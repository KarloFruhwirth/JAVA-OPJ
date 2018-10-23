package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Rectangle 
 * @author KarloFrühwirth
 * @version 1.0
 */
public class RectangleTest {

	/**
	 * Test for cases of negative number input for the methode print
	 */
	@Test(expected  = IllegalArgumentException.class)
	public void negativeNumber() {
		Rectangle.print(-4,2);
	}
	

	/**
	 * Test for method perimeter
	 */
	@Test
	public void perimeter() {
		Assert.assertEquals(12.0, Rectangle.perimeter(3,3),0.001);
	}
	
	/**
	 * Test for method surface
	 */
	@Test
	public void surfice() {
		Assert.assertEquals(9.0, Rectangle.surface(3,3),0.001);
	}
}

