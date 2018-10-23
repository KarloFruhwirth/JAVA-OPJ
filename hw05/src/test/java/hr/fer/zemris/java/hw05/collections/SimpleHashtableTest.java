package hr.fer.zemris.java.hw05.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SimpleHashtableTest {
	
	public SimpleHashtable<String, Integer> initializeSimpleHashtable() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 		
		return examMarks;
	}

	@Test
	public void TestDefaultHashTableSize() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		assertEquals(table.numberOfSlots(), 16);
	}
	
	@Test
	public void TestHashTableSize() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		assertEquals(table.numberOfSlots(), 2);
	}
	
	@Test(expected=NullPointerException.class)
	public void TestGetNull() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.get(null);
	}
	
	@Test
	public void TestGet() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		int kristinaGrade = table.get("Kristina");
		assertEquals(kristinaGrade, 5);
	}
	
	@Test
	public void TestGetForNonExcisting() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		Integer grade = table.get("Anamarija");
		assertEquals(grade, null);
	}
	
	@Test
	public void TestPut() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("prvi", 1);
		assertEquals(table.size(), 1);
		table.put("drugi", 2);
		assertEquals(table.size(), 2);
		table.put("treci", 3);
		assertEquals(table.size(), 3);
		
	}
	
	@Test
	public void TestPutSameKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("prvi", 1);
		assertEquals(table.size(), 1);
		table.put("drugi", 2);
		assertEquals(table.size(), 2);
		table.put("prvi", 3);
		assertEquals(table.size(), 2);
		
	}
	
	@Test
	public void TestContainsKey() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		boolean contains = table.containsKey("Kristina");
		assertEquals(contains, true);
	}
	
	@Test
	public void TestDoesntContainKey() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		boolean contains = table.containsKey("AAAAA");
		assertEquals(contains, false);
	}
	
	@Test
	public void TestContainsValue() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		boolean contains = table.containsValue(2);
		assertEquals(contains, true);
	}
	
	@Test
	public void TestDoesntContainValue() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		boolean contains = table.containsValue(null);
		assertEquals(contains, false);
	}
	
	@Test
	public void TestRemove() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		table.remove("Kristina");
		assertEquals(table.size(), 3);
	}
	
	@Test
	public void TestIsEmpty() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		boolean empty = table.isEmpty();
		assertEquals(empty, false);
	}
	
	@Test
	public void TestClear() {
		SimpleHashtable<String, Integer> table = initializeSimpleHashtable();
		table.clear();
		assertEquals( table.size(),0);
	}
}
