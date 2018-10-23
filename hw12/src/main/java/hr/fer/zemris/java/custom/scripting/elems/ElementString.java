package hr.fer.zemris.java.custom.scripting.elems;

/**
 * @author KarloFrühwirth Inherits Element and has a single read-only property
 *         Also it overrides the asText method from the Element
 */
public class ElementString extends Element {
	private String text;

	/**
	 * Constructor which sets the value of text to value
	 * 
	 * @param value
	 * 
	 * @throws IllegalArgumentException
	 *             if string is null
	 */
	public ElementString(String value) {
		if (value == null)
			throw new IllegalArgumentException("String mustn't be null.");
		this.text = value;
	}

	@Override
	public String asText() {
		return text;
	}

	/**
	 * Getter for the text variable
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}
}
