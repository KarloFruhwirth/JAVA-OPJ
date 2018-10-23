package hr.fer.zemris.java.hw05.db;

/**
 * Interface that provides a single method get Different strategies are provided
 * in {@link FieldValueGetters}
 * 
 * @author KarloFrühwirth
 *
 */
public interface IFieldValueGetter {
	/**
	 * Method that returns StudentRecord
	 * 
	 * @param record
	 *            StudentRecord
	 * @return record
	 */
	public String get(StudentRecord record);
}
