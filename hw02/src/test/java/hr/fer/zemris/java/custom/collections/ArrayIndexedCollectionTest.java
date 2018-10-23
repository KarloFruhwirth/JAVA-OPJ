package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author KarloFrühwirth
 *Tests for the class ArrayIndexedCollection
 *each methods purpose is given by its name
 */
public class ArrayIndexedCollectionTest {

	@Test
	public void checkDefaultSize() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		Assert.assertEquals(0, a1.size());
	}
	
	@Test
	public void checkAdd() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		Assert.assertEquals(1, a1.size());
	}
	
	@Test
	public void checkEmpty() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		Assert.assertEquals(true, a1.isEmpty());
		a1.add("Prvi");
		Assert.assertEquals(false, a1.isEmpty());
	}
	
	@Test
	public void checkContains() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		a1.add("Drugi");
		a1.add("Treci");
		Assert.assertEquals(false, a1.contains("cetvrti"));
		Assert.assertEquals(true, a1.contains("Drugi"));
		Assert.assertEquals(false, a1.contains("2."));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getWrongIndex() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		a1.add("Drugi");
		a1.add("Treci");
		a1.get(-5);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void insertWrongIndex() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		a1.add("Drugi");
		a1.add("Treci");
		a1.insert("cetvrti",-5);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeWrongIndex() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		a1.add("Drugi");
		a1.add("Treci");
		a1.remove(-5);
	}
	
	@Test
	public void checkInsertAddRemove() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		a1.add("Drugi");
		a1.add("Treci");
		Assert.assertEquals(3, a1.size());
		a1.remove(0);
		Assert.assertEquals(2, a1.size());
		a1.remove("Drugi");
		Assert.assertEquals(1, a1.size());
		a1.insert("TreciDva", 0);
		Assert.assertEquals(2, a1.size());
	}
	
	@Test
	public void checkIndexOfNull() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		Assert.assertEquals(-1, a1.indexOf(null));
	}
	
	@Test
	public void checkIndexOf() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Prvi");
		a1.add("Drugi");
		a1.add("Treci");
		Assert.assertEquals(0, a1.indexOf("Prvi"));
	}
	
}
