package hr.fer.zemris.java.hw05.db;

/**
 * Class that presents us with different strategies for
 * {@link IFieldValueGetter} interface
 * 
 * @author KarloFrühwirth
 *
 */
public class FieldValueGetters {

	/**
	 * Strategy implementation that returns the first name of a
	 * {@link StudentRecord} record
	 * 
	 * @throws NullPointerException
	 *             if record is null
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		if (record == null)
			throw new NullPointerException("record cant be null");
		return record.getFirstName();
	};

	/**
	 * Strategy implementation that returns the last name of a 
	 * {@link StudentRecord} record
	 * 
	 * @throws NullPointerException
	 *             if record is null
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		if (record == null)
			throw new NullPointerException("record cant be null");
		return record.getLastName();
	};

	/**
	 * Strategy implementation that returns the jmbag of a {@link StudentRecord}
	 * record
	 * 
	 * @throws NullPointerException
	 *             if record is null
	 */
	public static final IFieldValueGetter JMBAG = (record) -> {
		if (record == null)
			throw new NullPointerException("record cant be null");
		return record.getJmbag();
	};

}
