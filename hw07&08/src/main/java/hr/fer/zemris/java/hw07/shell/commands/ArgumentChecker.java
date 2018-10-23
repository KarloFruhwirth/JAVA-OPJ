package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.ShellCommand;

/**
 * Class used to parse the arguments provided to {@link ShellCommand}s After the
 * ending double-quote, either no more characters must be present or at least
 * one space character must be present <br>
 * "C:\fi le".txt is an invalid example
 * 
 * @author KarloFrühwirth
 *
 */
public class ArgumentChecker {

	private char[] input;
	int currentIndex;

	/**
	 * Returns an array of elements from the arguments.
	 * 
	 * @param arguments
	 *            input for the command
	 * @return String[]
	 */
	public String[] getElements(String arguments) {
		input = arguments.toCharArray();
		String[] elements = new String[5];
		Arrays.fill(elements, "");
		for (int i = 0; i < 5; i++) {
			elements[i] = nextInput();
		}
		return elements;
	}

	private String nextInput() {
		if (input.length <= currentIndex) {
			return "";
		} else {
			if (input[currentIndex] == ' ') {
				wightspaces();
			}
			if (input[currentIndex] == '\"') {
				currentIndex++;
				return quoted();
			} else {
				return word();
			}
		}
	}

	/**
	 * Method used to skip spaces
	 */
	private void wightspaces() {
		while (true) {
			if (input[currentIndex] != ' ')
				break;
			currentIndex++;
		}
	}

	/**
	 * Method used to return a word until the first space or EOL
	 * 
	 * @return string
	 */
	private String word() {
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (currentIndex >= input.length || input[currentIndex] == ' ')
				break;
			sb.append(input[currentIndex]);
			currentIndex++;
		}
		return sb.toString();
	}

	/**
	 * Method used to return a string of a quoted string.<br>
	 * Character escaping is enabled
	 * 
	 * @return string
	 */
	private String quoted() {
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (input[currentIndex] == '\"') {
				currentIndex++;
				if (currentIndex == input.length || input[currentIndex] == ' ') {
					break;
				}
			} else if (input[currentIndex] == '\\') {
				currentIndex++;
				if (currentIndex <= input.length) {
					if (input[currentIndex] == '\\' || input[currentIndex] == '\"') {
						sb.append(input[currentIndex]);
					} else {
						sb.append("\\" + input[currentIndex]);
					}
					currentIndex++;
				} else {
					sb.append("\"");
				}
			} else {
				sb.append(input[currentIndex]);
				currentIndex++;
			}
		}
		return sb.toString();
	}

}
