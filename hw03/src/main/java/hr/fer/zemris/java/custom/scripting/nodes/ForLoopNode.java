package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * @author KarloFrühwirth A node representing a single for-loop construct. It
 *         inherits from Node class.
 */
public class ForLoopNode extends Node {
	/**
	 * 
	 */
	private ElementVariable variable;
	/**
	 * 
	 */
	private Element startExpression;
	/**
	 * 
	 */
	private Element endExpression;
	/**
	 * 
	 */
	private Element stepExpression;

	/**
	 * Constructor for ForLoopNode with 4 elements
	 * 
	 * @param variable
	 *            ElementVariable
	 * @param startExpression
	 *            Element
	 * @param endExpression
	 *            Element
	 * @param stepExpression
	 *            Element
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = (ElementVariable) variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Constructor for ForLoopNode with 3 elements
	 * 
	 * @param variable
	 *            ElementVariable
	 * @param startExpression
	 *            Element
	 * @param endExpression
	 *            Element
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this.variable = (ElementVariable) variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
	}

	/**
	 * Getter for variable
	 * 
	 * @return
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for startExpression
	 * 
	 * @return
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for endExpression
	 * 
	 * @return
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for stepExpression
	 * 
	 * @return
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String text = "";
		text += "{$ FOR " + variable.asText() + " ";
		if (startExpression instanceof ElementString || startExpression instanceof ElementVariable
				|| startExpression instanceof ElementConstantDouble
				|| startExpression instanceof ElementConstantInteger) {
			text += startExpression.asText() + " ";
		}
		if (endExpression instanceof ElementString || endExpression instanceof ElementVariable
				|| endExpression instanceof ElementConstantDouble || endExpression instanceof ElementConstantInteger) {
			text += endExpression.asText() + " ";
		}
		if (stepExpression != null) {
			if (stepExpression instanceof ElementString || stepExpression instanceof ElementVariable
					|| stepExpression instanceof ElementConstantDouble
					|| stepExpression instanceof ElementConstantInteger) {
				text += stepExpression.asText() + " ";
			}
		}
		text += "$}";

		return text;
	}
	
	@Override
	public void accept(INodeVisitor nodeVisitor) {
		nodeVisitor.visitForLoopNode(this);
	}
}
