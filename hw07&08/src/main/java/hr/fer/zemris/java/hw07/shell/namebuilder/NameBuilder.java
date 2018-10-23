package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Interface for NameBuilder used in {@link NameBuilderParser} Generates parts
 * of the name by writing them to {@link StringBuilder} which is provided by
 * {@link NameBuilderInfo}
 * 
 * @author KarloFrühwirth
 *
 */
public interface NameBuilder {

	/**
	 * Method which a NameBuilder calls and appends an appropriate string to
	 * {@link StringBuilder}
	 * 
	 * @param info
	 */
	void execute(NameBuilderInfo info);
}
