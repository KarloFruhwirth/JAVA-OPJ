package hr.fer.zemris.java.hw05.db;

/**
 * Enum that defines the states in which a {@link QueryLexer} can be in
 * 
 * @author KarloFrühwirth
 *
 */
public enum QueryLexerState {
	/**
	 * State of the lexer if the query is an instance of jmbag="xxx"
	 */
	DIRECT,
	/**
	 * State of the lexer if the query is anything other than jmbag="xxx"
	 */
	DEFAULT
}
