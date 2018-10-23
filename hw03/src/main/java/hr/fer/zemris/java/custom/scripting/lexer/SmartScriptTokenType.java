package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * @author KarloFrühwirth enum that defines token types
 */
public enum SmartScriptTokenType {
	/**
	 * start of tag {$
	 */
	TAGSTART,
	/**
	 * end of tag $}
	 */
	TAGEND,
	/**
	 * variable name
	 */
	VARIABLE,
	/**
	 * function name
	 */
	FUNCION,
	/**
	 * end of file
	 */
	EOF,
	/**
	 * operator +,-,*,/,^
	 */
	OPERATOR,
	/**
	 * END tag
	 */
	END,
	/**
	 * FOR tag
	 */
	FOR,
	/**
	 * =
	 */
	EQUALS,
	/**
	* text in tag
	*/
	TEXT,
	/**
	 * integer token
	 */
	INTEGER,
	/**
	 * double token
	 */
	DOUBLE,
	/**
	 * space,\t,\r,\n
	 */
	SPACE,
	/**
	 * text representation
	 */
	STRING
}
