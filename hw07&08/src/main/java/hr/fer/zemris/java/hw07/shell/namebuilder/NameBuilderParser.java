package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for the NameBuilder. For the EXPRESION parses it and creates
 * NameBuilder objects. There are 3 different implementations of
 * {@link NameBuilder}:<br>
 * {@link NameBuilderGroup}, {@link NameBuilderList}, {@link NameBuilderString}
 * 
 * @author KarloFrühwirth
 *
 */
public class NameBuilderParser {

	/**
	 * list of NameBuilder
	 */
	private List<NameBuilder> list = new ArrayList<>();
	/**
	 * char array
	 */
	char[] array;
	/**
	 * index counter
	 */
	int currentIndex = 0;
	/**
	 * size of the array
	 */
	int size;

	/**
	 * Constructor for NameBuilderParser
	 * 
	 * @param expression
	 *            String EXPRESION
	 */
	public NameBuilderParser(String expression) {
		array = expression.toCharArray();
		size = array.length;
	}

	/**
	 * returns a NameBuilderList which is made of NameBuilderGroups and
	 * NameBuilderStrings
	 * 
	 * @return NameBuilderList
	 */
	public NameBuilder getNameBuilder() {
		while (true) {
			if (currentIndex >= size) {
				break;
			} else {
				if (array[currentIndex] == '$') {
					String group = checkGroups();
					NameBuilderGroup g = new NameBuilderGroup(group);
					list.add(g);
				} else if (array[currentIndex] == '}') {
					currentIndex++;
				} else {
					String constant = checkString();
					NameBuilderString g = new NameBuilderString(constant);
					list.add(g);
				}
			}
		}
		return new NameBuilderList(list);
	}

	/**
	 * Method used to return the string content of constant strings outside of the
	 * group definition ${}
	 * 
	 * @return String
	 */
	private String checkString() {
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (currentIndex >= size || array[currentIndex] == '$')
				break;
			sb.append(array[currentIndex]);
			currentIndex++;
		}
		return sb.toString();

	}

	/**
	 * Method used to return the string of the group ${}
	 * 
	 * @return String
	 */
	private String checkGroups() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		if (array[currentIndex] != '{')
			return "";
		else {
			currentIndex++;
			while (true) {
				if (currentIndex >= size || array[currentIndex] == '}')
					break;
				else if (array[currentIndex] == ' ') {
					currentIndex++;
				} else {
					sb.append(array[currentIndex]);
					currentIndex++;
				}
			}
			return sb.toString();
		}
	}
}
