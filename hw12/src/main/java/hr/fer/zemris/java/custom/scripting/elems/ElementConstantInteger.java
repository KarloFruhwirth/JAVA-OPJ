package hr.fer.zemris.java.custom.scripting.elems;

/**
 * @author KarloFrühwirth 
 * Inherits Element and has a single read-only property
 * Also it overrides the asText method from the Element
 */
public class ElementConstantInteger extends Element {
	private int value;

	/**
	 * Constructor which sets the value of variable value
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	/**
	 * Getter that returns value
	 * @return value
	 */
	public int getValue() {
		return value;
	}
}
