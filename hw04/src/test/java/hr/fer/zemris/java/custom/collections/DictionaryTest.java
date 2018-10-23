package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class DictionaryTest {

	@Test (expected=NullPointerException.class)
	public void TestNullEntry() {
		Dictionary d = new Dictionary();
		d.put(null, null);
	}
	
	@Test
	public void TestEntryAndSize() {
		Dictionary d = new Dictionary();
		d.put("prvi", 1);
		int size = d.size();
		assertEquals(size, 1);
		d.put("drugi", 2);
		d.put("treci", 3);
		size = d.size();
		assertEquals(size, 3);
		d.clear();
		size = d.size();
		assertEquals(size, 0);
	}
	
	@Test
	public void TestIsEmpty() {
		Dictionary d = new Dictionary();
		boolean b = d.isEmpty();
		assertEquals(b, true);
	}
	
	@Test
	public void TestSameKeyEntry() {
		Dictionary d = new Dictionary();
		d.put("prvi", 1);
		int size = d.size();
		assertEquals(size, 1);
		d.put("prvi", 2);
		d.put("treci", 3);
		size = d.size();
		assertEquals(size, 2);
	}
	
	@Test
	public void TestGetNull() {
		Dictionary d = new Dictionary();
		d.put("prvi", 1);
		d.put("DRUGI", 2);
		d.put("treci", 3);
		Object rez = d.get(null);
		assertEquals(rez, null);
	}
	
	@Test
	public void TestGet() {
		Dictionary d = new Dictionary();
		d.put("prvi", 1);
		d.put("drugi", 2);
		d.put("treci", 3);
		Object rez = d.get("drugi");
		assertEquals(rez, 2);
	}
	
}
