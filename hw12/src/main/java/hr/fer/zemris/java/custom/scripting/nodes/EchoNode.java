package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * @author KarloFrühwirth A node representing a command which generates some
 *         textual output dynamically. It inherits from Node class.
 */
public class EchoNode extends Node {
	private Element[] elements;

	/**
	 * Getter for the elements array
	 * 
	 * @return
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Constructor that creates an instance of EchoNode and sets the elements to
	 * elements
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		String text = "";
		text += "{$=";
		int i = 0;
		while (i < elements.length) {
			if (elements[i] instanceof ElementString || elements[i] instanceof ElementVariable
					|| elements[i] instanceof ElementConstantDouble || elements[i] instanceof ElementConstantInteger
					|| elements[i] instanceof ElementFunction || elements[i] instanceof ElementOperator) {
				text += elements[i].asText() + " ";
			}
			i++;
		}
		text += "$}";

		return text;
	}

	@Override
	public void accept(INodeVisitor nodeVisitor) {
		nodeVisitor.visitEchoNode(this);
	}

}
