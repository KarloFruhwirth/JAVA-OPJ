package hr.fer.zemris.java.custom.scripting.elems;

/**
 * @author KarloFrühwirth Inherits Element and has a single read-only property
 *         Also it overrides the asText method from the Element
 */
public class ElementFunction extends Element {
	private String name;

	/**
	 * Constructor which sets the value of variable value
	 * 
	 * @param value
	 * 
	 * @throws IllegalArgumentException
	 *             if the name of the function is null
	 */
	public ElementFunction(String value) {
		if (value == null)
			throw new IllegalArgumentException("Name of the function mustn't be null.");
		this.name = value;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

	/**
	 * Getter that returns value
	 * 
	 * @return value
	 */
	public String getValue() {
		return name;
	}
}
