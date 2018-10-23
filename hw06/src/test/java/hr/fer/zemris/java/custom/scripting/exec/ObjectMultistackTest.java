package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;

import org.junit.Test;


public class ObjectMultistackTest {

	@Test
	public void TestIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertEquals(multistack.isEmpty(null),true);
	}
	
	@Test
	public void TestIsEmpty2() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(multistack.isEmpty("year"),false);
	}
	
	@Test
	public void TestIsPushAndPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("year", new ValueWrapper(Integer.valueOf(2000)));
		assertEquals(multistack.peek("year").getValue(),2000);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(multistack.peek("year").getValue(),1900);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1800)));
		assertEquals(multistack.peek("year").getValue(),1800);
		multistack.push("age", new ValueWrapper(Integer.valueOf(21)));
		assertEquals(multistack.peek("age").getValue(),21);
	}
	
	@Test(expected=EmptyStackException.class)
	public void TestEmptyPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.pop("key");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestNoKeyPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("year", new ValueWrapper(Integer.valueOf(2000)));
		multistack.pop("key");
	}
	
	@Test(expected=EmptyStackException.class)
	public void TestEmptyPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.peek("key");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestNoKeyPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("year", new ValueWrapper(Integer.valueOf(2000)));
		multistack.peek("key");
	}
	
	@Test
	public void TestPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("year", new ValueWrapper(Integer.valueOf(2000)));
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		multistack.push("year", new ValueWrapper(Integer.valueOf(1800)));
		multistack.push("age", new ValueWrapper(Integer.valueOf(21)));
		assertEquals(multistack.pop("age").getValue(),21);
		assertEquals(multistack.pop("year").getValue(),1800);
		assertEquals(multistack.pop("year").getValue(),1900);
		assertEquals(multistack.pop("year").getValue(),2000);
	}
}
