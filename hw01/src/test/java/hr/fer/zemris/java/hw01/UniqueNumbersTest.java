package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

/**
 * Tests for Rectangle 
 * @author KarloFrühwirth
 * @version 1.0
 */
public class UniqueNumbersTest {

	/**
	 * Test that checks the size of an empty tree
	 */
	@Test
	public void emptyTree() {
		Assert.assertEquals(0,UniqueNumbers.treeSize(null));
	}

	/**
	 * Test that checks if the tree contains a node with a value 5
	 */
	@Test
	public void notSoEmptyTree() {
		TreeNode glava = new TreeNode(5);
		Assert.assertEquals(true,UniqueNumbers.containsValue(glava, 5));
	}
}
