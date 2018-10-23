package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Demo class which show the functionality of the {@link ObjectMultistack}.
 * The expected output for this test is as follows:
 * 
 * Current value for year: 2000
 * Current value for price: 200.51
 * Current value for year: 1900 
 * Current value for year: 1950
 * Current value for year: 2000
 * Current value for year: 2005
 * Current value for year: 2010
 * Current value for year: 2015.0
 * 
 * 
 * @author KarloFrühwirth
 *
 */
public class ObjectMultistackDemo {
	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
		System.out.println("Current value for price: " + multistack.peek("price").getValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
		multistack.peek("year").setValue(((Integer) multistack.peek("year").getValue()).intValue() + 50);
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
		multistack.pop("year");
		//by removing the comments from the two lines below the program throws
		// multistack.pop("year");
		// multistack.pop("price");
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
		multistack.peek("year").add("5");
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
		multistack.peek("year").add(5);
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
		multistack.peek("year").add(5.0);
		System.out.println("Current value for year: " + multistack.peek("year").getValue());
	}
}