package hr.fer.zemris.java.custom.scripting.elems;

/**
 * @author KarloFrühwirth
 *
 *         Inherits Element and has a single read-only property Also it
 *         overrides the asText method from the Element
 *
 */
public class ElementVariable extends Element {
	private String name;

	/**
	 * Constructor which sets the value of variable name to value
	 * 
	 * @param value 
	 * @throws IllegalArgumentException
	 *             if variable is null
	 */
	public ElementVariable(String value) {
		if (value == null)
			throw new IllegalArgumentException("Variable name mustn't be null.");
		this.name = value;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Getter that returns variable name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

}
