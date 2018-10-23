package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author KarloFrühwirth
 *
 *Tests for the class ComplexNumber
 *each methods purpose is given by its name
 */
public class ComplexNumberTest {

	@Test(expected = IllegalAccessError.class)
	public void notComplexNumber() {
		ComplexNumber.parse("tekst");
	}
	
	@Test
	public void checkReal() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertEquals(2, c1.getReal(),0.001);
	}
	
	@Test
	public void checkImaginary() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertEquals(3, c1.getImaginary(),0.001);
	}
	
	@Test
	public void checkMagnitude() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		Assert.assertEquals(5, c1.getMagnitude(),0.001);
	}
	
	@Test
	public void checkAngle() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertEquals(0.9827, c1.getAngle(),0.001);
	}
	
	@Test
	public void checkOperations() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(3, 2);
		Assert.assertEquals(5, c1.add(c2).getReal(),0.001);
		Assert.assertEquals(5, c2.add(c1).getImaginary(),0.001);
		Assert.assertEquals(-1, c1.sub(c2).getReal(),0.001);
		Assert.assertEquals(-1, c2.sub(c1).getImaginary(),0.001);
		Assert.assertEquals(0.9230769230769231, c1.div(c2).getReal(),0.001);
		Assert.assertEquals(-0.38461538461538464, c2.div(c1).getImaginary(),0.001);
		Assert.assertEquals(13.0, c2.mul(c1).getImaginary(),0.001);
	}
	
	@Test
	public void checkToString() {
		ComplexNumber c1 = new ComplexNumber(2, -3);
		Assert.assertEquals("2.0-3.0i", c1.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativePower() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		c1.power(-2);
	}
	
	@Test public void checkPower() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertEquals(-4.999999999999999, c1.power(2).getReal(),0.001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeRoot() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		c1.root(-2);
	}
	@Test public void checkRoot() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		Assert.assertEquals(-1.6741492280355403, c1.root(2)[1].getReal(),0.001);
	}
	
	
}
