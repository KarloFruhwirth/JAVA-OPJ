package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * 
 * @author KarloFrühwirth
 *
 */
public interface ShellCommand {

	/**
	 * Method used to execute a command
	 * 
	 * @param env
	 *            Environment
	 * @param arguments
	 *            arguments for the command
	 * @return ShellStatus
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the name of the command
	 * 
	 * @return command name
	 */
	String getCommandName(); // returns the name of the command

	/**
	 * Returns a description(usage instructions) for command
	 * 
	 * @return List<String> description
	 */
	List<String> getCommandDescription(); // returns a description (usage instructions)
}
