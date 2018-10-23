package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to create a new directory
 * 
 * @author KarloFrühwirth
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "mkdir";

	/**
	 * {@inheritDoc} MkdirShellCommand <br>
	 * For the provided File creates the appropriate directory structure.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() != 0) {
			env.writeln("Command mkdir must recive one argument.");
		} else {
			try {
				Path other = env.getCurrentDirectory().resolve(args[0]);
				System.out.println(other.toString());
				File file = other.toFile();
				file.mkdirs();
			} catch (Exception e) {
				throw new InvalidPathException(args[0],"char <\"> is illegal tried to create somethig like this");
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add(
				"Command takes a single argument: directory name, and creates the appropriate directory structure.");
		return description;
	}

}
