package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * @author KarloFrühwirth Class that implements LSystemBuilder It uses two sets
 *         of Dictionarys to remember the productions and commands Also it holds
 *         a nested class which implements LSystem and is responsible for
 *         drawing and generating the productions for the LSystem used in method
 *         build()
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * Dictionary for productions
	 */
	private Dictionary productions = new Dictionary();
	/**
	 * Dictionary for commands
	 */
	private Dictionary commands = new Dictionary();
	/**
	 * Default value unitLength
	 */
	private double unitLength = 0.1;
	/**
	 * Default value unitLengthDegreeScaler
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Default value for origin
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Default value for angle
	 */
	private double angle = 0;
	/**
	 * Default value for axiom
	 */
	private String axiom = "";

	/**
	 * Method that uses the nested class to create the builder for the Lsystem
	 */
	@Override
	public LSystem build() {
		return new Builder();
	}

	/**
	 * Method that parses the String array used to define the parameters
	 * 
	 * @throws IllegalArgumentException
	 *             when ever a parameter is deffined invalid
	 */
	@Override
	public LSystemBuilder configureFromText(String[] array) {
		for (String s : array) {
			if (s.equals("")) {
				continue;
			} else {
				String[] parts = s.split("\\s+");
				switch (parts[0]) {
				case ("origin"):
					if (parts.length != 3)
						throw new IllegalArgumentException("Origin must have only x and y deffined");
					else if (!isDouble(parts[1]) || !isDouble(parts[2]))
						throw new IllegalArgumentException("x and y must be double");
					else {
						double x = Double.parseDouble(parts[1]);
						double y = Double.parseDouble(parts[2]);
						setOrigin(x, y);
					}
					break;
				case ("angle"):
					if (parts.length != 2)
						throw new IllegalArgumentException("Angle must be deffined with only one value! ");
					else if (!isDouble(parts[1]))
						throw new IllegalArgumentException("angle must be double");
					else {
						double angle = Double.parseDouble(parts[1]);
						setAngle(angle);
					}
					break;
				case ("unitLength"):
					if (parts.length != 2)
						throw new IllegalArgumentException("unitLength must be deffined with only one value! ");
					else if (!isDouble(parts[1]))
						throw new IllegalArgumentException("unitLength must be double");
					else {
						double unitLength = Double.parseDouble(parts[1]);
						setUnitLength(unitLength);
					}
					break;
				case ("unitLengthDegreeScaler"):
					if (parts.length < 2 || parts.length > 4)
						throw new IllegalArgumentException("unitLengthDegreeScaler must be deffined correctly ");
					else if (parts.length == 2) {
						if (isDouble(parts[1])) {
							double unitLengthDegreeScaler = Double.parseDouble(parts[1]);
							setUnitLengthDegreeScaler(unitLengthDegreeScaler);
						} else {
							String[] division = parts[1].split("/");
							if (division.length != 2)
								throw new IllegalArgumentException("Division has only one /...");
							double unitLengthDegreeScaler = Double.parseDouble(division[1])
									/ Double.parseDouble(division[1]);
							setUnitLengthDegreeScaler(unitLengthDegreeScaler);
						}
					} else {
						String division = "";
						for (int i = 1, size = parts.length; i < size; i++) {
							division += parts[i];
						}
						String[] divisions = division.split("/");
						if (divisions.length != 2)
							throw new IllegalArgumentException("Division has only one /...");
						double unitLengthDegreeScaler = Double.parseDouble(divisions[0])
								/ Double.parseDouble(divisions[1]);
						setUnitLengthDegreeScaler(unitLengthDegreeScaler);
					}
					break;
				case ("command"):
					if (parts.length < 3 || parts[1].length() != 1)
						throw new IllegalArgumentException("command mis not properly deffined ");
					else {
						char symbol = parts[1].charAt(0);
						String command = "";
						for (int i = 2, size = parts.length; i < size; i++) {
							command += parts[i] + " ";
						}
						registerCommand(symbol, command.trim());
					}
					break;
				case ("axiom"):
					if (parts.length != 2)
						throw new IllegalArgumentException("axiom must be deffined with only one value! ");
					setAxiom(parts[1]);
					break;
				case ("production"):
					if (parts.length != 3 || parts[1].length() != 1)
						throw new IllegalArgumentException(
								"production must be deffined with a char and a string value! ");
					else {
						char symbol = parts[1].charAt(0);
						String production = parts[2];
						registerProduction(symbol, production);
					}
					break;
				default:
					throw new IllegalArgumentException(parts[0] + " is not a supported command");
				}
			}
		}
		return this;
	}

	/**
	 * Method that checks if a string is a representation of a double
	 * 
	 * @param string
	 *            String beeing checked
	 * @return true|false
	 */
	private boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method that adds commands for a certain symbol to the commands Dictionary
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String command) {
		Command registerCommand = getCommand(command);
		commands.put(symbol, registerCommand);
		return this;
	}

	/**
	 * Method used to determain what command is used and returns it if an exceprion
	 * ocures
	 * 
	 * @throws IllegalArgumentException
	 * @param command
	 *            String that is being checked if its a command
	 * @return representation of a command
	 */
	private Command getCommand(String command) {
		command = command.replaceAll("\\s+", " ");
		String[] commandParts = command.split(" ");
		switch (commandParts[0]) {
		case ("draw"):
			if (commandParts.length != 2)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			double parametr = Double.parseDouble(commandParts[1]);
			return new DrawCommand(parametr);
		case ("skip"):
			if (commandParts.length != 2)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			parametr = Double.parseDouble(commandParts[1]);
			return new SkipCommand(parametr);
		case ("scale"):
			if (commandParts.length != 2)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			parametr = Double.parseDouble(commandParts[1]);
			return new ScaleCommand(parametr);
		case ("rotate"):
			if (commandParts.length != 2)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			parametr = Double.parseDouble(commandParts[1]);
			return new RotateCommand(parametr);
		case ("push"):
			if (commandParts.length != 1)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			return new PushCommand();
		case ("pop"):
			if (commandParts.length != 1)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			return new PopCommand();
		case ("color"):
			if (commandParts.length != 2)
				throw new IllegalArgumentException("Command has illegal number of arguments");
			return new ColorCommand(commandParts[1]);
		default:
			throw new IllegalArgumentException("Command " + commandParts[0] + " is not supported");
		}

	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Setter for angle
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Setter for axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Setter for origin
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Setter for unitLength
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLenght) {
		this.unitLength = unitLenght;
		return this;
	}

	/**
	 * Setter for unitLengthDegreeScaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * @author KarloFrühwirth Nested class used for the method build() it implements
	 *         the LSystem and overrides its methods draw and generate because of
	 *         the these two methods we get a drawing of the LSystem
	 */
	private class Builder implements LSystem {
		/**
		 * Method which creates a new Context and creates a new TurtleState which it
		 * pushes on the Context then based on the generated string that represents the
		 * productions the method checks char by char the string and if there is a
		 * command it executes it othervise it skips that char and continues
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			TurtleState state = new TurtleState(origin.copy(), new Vector2D(1, 0).rotated(angle), Color.black,
					unitLength * Math.pow(unitLengthDegreeScaler, level));
			ctx.pushState(state);
			String generated = generate(level);
			System.out.println(generated);
			char[] array = generated.toCharArray();
			for (int i = 0, size = array.length; i < size; i++) {
				if (commands.get(array[i]) != null) {
					Command command = (Command) commands.get(array[i]);
					command.execute(ctx, painter);
				} else {
					continue;
				}
			}

		}

		/**
		 * Recursive function that generates a string representation of the production
		 * based on the level on which the Lsystem is on
		 * 
		 * @return String generated productions
		 */
		@Override
		public String generate(int level) {
			if (level < 0)
				throw new IllegalArgumentException("Level must be greater then zero!");
			else if (level == 0) {
				return axiom;
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(generate(level - 1));
				String text = sb.toString();
				char[] array = text.toCharArray();
				StringBuilder sb2 = new StringBuilder();
				for (int i = 0, size = array.length; i < size; i++) {
					if (productions.get(array[i]) == null) {
						sb2.append(array[i]);
					} else {
						sb2.append(productions.get(array[i]));
					}
				}
				return sb2.toString();
			}
		}
	}
}
