package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

	
	@Test
	public void checkReal() {
		Complex c1 = new Complex(2, 3);
		Assert.assertEquals(2, c1.getReal(),0.001);
	}
	
	@Test
	public void checkImaginary() {
		Complex c1 = new Complex(2, 3);
		Assert.assertEquals(3, c1.getImaginary(),0.001);
	}

	@Test
	public void checkModule() {
		Complex c1 = new Complex(3, 4);
		Assert.assertEquals(5, c1.module(),0.001);
	}
	
	@Test
	public void checkNegate() {
		Complex c1 = new Complex(3, 4);
		Assert.assertEquals(c1.negate(), new Complex(-3, -4));
	}

	@Test
	public void checkConjugate() {
		Complex c1 = new Complex(3, 4);
		Complex c2 = new Complex();
		Assert.assertEquals(c2.conjugate(c1), new Complex(3, -4));
	}
	
	@Test
	public void checkOperations() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(3, 2);
		Assert.assertEquals(5, c1.add(c2).getReal(),0.001);
		Assert.assertEquals(5, c2.add(c1).getImaginary(),0.001);
		Assert.assertEquals(-1, c1.sub(c2).getReal(),0.001);
		Assert.assertEquals(-1, c2.sub(c1).getImaginary(),0.001);
		Assert.assertEquals(0.9230769230769231, c1.divide(c2).getReal(),0.001);
		Assert.assertEquals(-0.38461538461538464, c2.divide(c1).getImaginary(),0.001);
		Assert.assertEquals(13.0, c2.multiply(c1).getImaginary(),0.001);
	}
	
	@Test
	public void checkToString() {
		Complex c1 = new Complex(2, -3);
		Assert.assertEquals("2.0-3.0i", c1.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativePower() {
		Complex c1 = new Complex(2, 3);
		c1.power(-2);
	}
	
	@Test public void checkPower() {
		Complex c1 = new Complex(2, 3);
		Assert.assertEquals(-4.999999999999999, c1.power(2).getReal(),0.001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeRoot() {
		Complex c1 = new Complex(2, 3);
		c1.root(-2);
	}
	@Test public void checkRoot() {
		Complex c1 = new Complex(2, 3);
		Assert.assertEquals(-1.6741492280355403, c1.root(2).get(1).getReal(),0.001);
	}

}
