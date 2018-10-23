package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValueWrapperTest {
	
	private class Illegal{
	}

	@Test
	public void TestNullValueWrapper() {
		ValueWrapper v1 = new ValueWrapper(null);
		assertEquals(v1.getValue(), null);
	}
	
	@Test
	public void TestStringValueWrapper() {
		ValueWrapper v1 = new ValueWrapper("Wrapper");
		assertEquals(v1.getValue(), "Wrapper");
	}
	
	@Test
	public void TestIntegerValueWrapper() {
		ValueWrapper v1 = new ValueWrapper(4);
		assertEquals(v1.getValue(), 4);
	}
	
	@Test
	public void TestDoubleValueWrapper() {
		ValueWrapper v1 = new ValueWrapper(4.2);
		assertEquals(v1.getValue(), 4.2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestIllegalValueWrapper() {
		ValueWrapper v1 = new ValueWrapper(new Illegal());
		assertEquals(v1.getValue(), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestIllegalArgumentValueWrapper() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
		assertEquals(v7.getValue(), 1);
	}
	
	@Test
	public void TestAddValueWrapper1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestAddValueWrapper2() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(v1.getValue(), 12);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestAddValueWrapper3() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.add(v2.getValue());
		assertEquals(v1.getValue(), 3);
		assertEquals(v2.getValue(), 2);
	}
	
	@Test
	public void TestSubtractValueWrapper1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestSubtractValueWrapper2() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		assertEquals(v1.getValue(), 12);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestSubtractValueWrapper3() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2.1);
		v1.subtract(v2.getValue());
		assertEquals(v1.getValue(), -1.1);
		assertEquals(v2.getValue(), 2.1);
	}	
	
	@Test
	public void TestMultiplyValueWrapper1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestMultiplyValueWrapper2() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestMultiplyValueWrapper3() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2.0);
		v1.multiply(v2.getValue());
		assertEquals(v1.getValue(), 2.0);
		assertEquals(v2.getValue(), 2.0);
	}
	
	@Test(expected=ArithmeticException.class)
	public void TestDivideValueWrapper1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void TestDivideValueWrapper2() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(3);
		v1.divide(v2.getValue());
		assertEquals(v1.getValue(), 4);
		assertEquals(v2.getValue(), 3);
	}
	
	@Test
	public void TestDivideValueWrapper3() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.divide(v2.getValue());
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), 2);
	}
	
	@Test
	public void TestnumCompareValueWrapper1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		assertEquals(v1.numCompare(v2.getValue()),0);
	}
	
	@Test
	public void TestnumCompareValueWrapper2() {
		ValueWrapper v1 = new ValueWrapper(12);
		ValueWrapper v2 = new ValueWrapper(3);
		boolean bigger = false;
		if(v1.numCompare(v2.getValue())>0) bigger = true;
		assertEquals(bigger,true);
	}
	
	@Test
	public void TestnumCompareValueWrapper3() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2);
		boolean less = false;
		if(v1.numCompare(v2.getValue())<0) less = true;
		assertEquals(less,true);
	}
}
