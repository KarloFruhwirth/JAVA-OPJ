package hr.fer.zemris.java.custom.scripting.elems;

/**
 * @author KarloFrühwirth
 * Inherits Element and has a single read-only property
 * Also it overrides the asText method from the Element
 */
public class ElementConstantDouble extends Element{
	private double value;
	
	/**
	 * Constructor which sets the value of variable value
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	/**
	 * Getter for the read only value
	 * @return value
	 */
	public double getValue() {
		return value;
	}
	
}
