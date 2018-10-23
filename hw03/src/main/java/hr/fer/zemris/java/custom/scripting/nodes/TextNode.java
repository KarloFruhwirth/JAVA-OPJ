package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * @author KarloFrühwirth A node representing a piece of textual data. It
 *         inherits from Node class
 */
public class TextNode extends Node {
	private String text;

	/**
	 * Getter for text
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * Constructor that cretes a new instance of TextNode and sets the text to text
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public void accept(INodeVisitor nodeVisitor) {
		nodeVisitor.visitTextNode(this);
	}
}
