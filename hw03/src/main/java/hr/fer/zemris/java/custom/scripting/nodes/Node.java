package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * @author KarloFrühwirth A base class for all graph nodes.
 */
public class Node {
	private ArrayIndexedCollection arrayCollection;

	/**
	 * Method that adds a node to the arrayCollection
	 * 
	 * @param child
	 *            added node
	 */
	public void addChildNode(Node child) {
		if (arrayCollection == null) {
			arrayCollection = new ArrayIndexedCollection();
		}
		arrayCollection.add(child);
	}

	/**
	 * Returns the number of children of a certain node
	 * 
	 * @return number of children
	 */
	public int numberOfChildren() {
		if (arrayCollection == null)
			return 0;
		return arrayCollection.size();
	}

	/**
	 * Getter for child node
	 * 
	 * @param index
	 *            which child to return
	 * @return Node
	 * 
	 * @throws IllegalArgumentException
	 *             if index is out of bounds
	 */
	public Node getChild(int index) {
		if (index < 0 || index > numberOfChildren())
			throw new IllegalArgumentException("Invalid input for index.");
		return (Node) arrayCollection.get(index);
	}
	
	public void accept(INodeVisitor nodeVisitor) {}
}
