package hr.fer.zemris.java.custom.scripting.elems;

/**
 * @author KarloFrühwirth Inherits Element and has a single read-only property
 *         Also it overrides the asText method from the Element
 * 
 */
public class ElementOperator extends Element {
	private String symbol;

	/**
	 * Constructor which sets the value of symbol to value
	 * 
	 * @param value
	 * 
	 * @throws IllegalArgumentException
	 *             if operator is null
	 */
	public ElementOperator(String value) {
		if (value == null)
			throw new IllegalArgumentException("Symbol of the operator mustn't be null.");
		this.symbol = value;
	}

	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Getter that returns symbol
	 * 
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}
}
