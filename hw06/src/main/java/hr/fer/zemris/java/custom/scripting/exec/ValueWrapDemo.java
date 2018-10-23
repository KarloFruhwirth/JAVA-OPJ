package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Demo class which show the functionality of the {@link ValueWrapper}. Outputs
 * are added to see what is stored in which ValueWrapper more easily
 * 
 * @author KarloFrühwirth
 *
 */
public class ValueWrapDemo {
	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		System.out.println(v1.getValue());
		System.out.println(v2.getValue());

		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		System.out.println(v3.getValue());
		System.out.println(v4.getValue());

		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		System.out.println(v5.getValue());
		System.out.println(v6.getValue());

		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue()); // throws RuntimeException

	}

}
