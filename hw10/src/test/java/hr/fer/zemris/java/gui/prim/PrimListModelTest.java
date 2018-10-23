package hr.fer.zemris.java.gui.prim;


import org.junit.Assert;
import org.junit.Test;


public class PrimListModelTest {
	private PrimListModel listModel = new PrimListModel();
	
	@Test
	public void checkInitialSizeAndElement() {
		Assert.assertEquals(1, listModel.getSize());
		Assert.assertEquals(1, listModel.getElementAt(0).intValue());
	}
	
	@Test
	public void checkNext() {
		listModel.next();
		listModel.next();
		listModel.next();
		Assert.assertEquals(1, listModel.getElementAt(0).intValue());
		Assert.assertEquals(2, listModel.getElementAt(1).intValue());
		Assert.assertEquals(3, listModel.getElementAt(2).intValue());
		Assert.assertEquals(5, listModel.getElementAt(3).intValue());
	}
	
	@Test
	public void checkSize() {
		listModel.next();
		listModel.next();
		listModel.next();
		listModel.next();
		listModel.next();
		listModel.next();
		Assert.assertEquals(7, listModel.getSize());
	}
}
