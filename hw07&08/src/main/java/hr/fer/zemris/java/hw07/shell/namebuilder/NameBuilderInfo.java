package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * NameBuilderInfo Interface. Used for the {@link NameBuilderParser} provides us
 * with {@link StringBuilder}.
 * 
 * @author KarloFrühwirth
 *
 */
public interface NameBuilderInfo {

	/**
	 * Getter for StringBuilder
	 * 
	 * @return StringBuilder
	 */
	StringBuilder getStringBuilder();

	/**
	 * Returns a string for the provided group(index) if such exists
	 * 
	 * @param index
	 *            index
	 * @return String
	 */
	String getGroup(int index);
}
