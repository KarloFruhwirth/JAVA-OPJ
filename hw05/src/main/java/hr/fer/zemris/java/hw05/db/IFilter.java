package hr.fer.zemris.java.hw05.db;

/**
 * Interface that provides a single method accepts Implementation is provided in
 * {@link QueryFilter}
 * 
 * @author KarloFrühwirth
 *
 */
public interface IFilter {
	/**
	 * Returns true or false if a StudentRecord is accepted
	 * 
	 * @param record
	 *            StudentRecord
	 * @return true || false
	 */
	public boolean accepts(StudentRecord record);
}
