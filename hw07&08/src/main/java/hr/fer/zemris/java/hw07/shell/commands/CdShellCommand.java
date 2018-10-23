package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to change current directory
 * 
 * @author KarloFrühwirth
 *
 */
public class CdShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "cd";

	/**
	 * {@inheritDoc} CdShellCommand <br>
	 * It changes the current directory to the newly provided if such directory
	 * exists
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() != 0) {
			env.writeln("Command cd must recive one argument.");
		} else {
			Path other = env.getCurrentDirectory().resolve(args[0]);
			File file = other.toFile();
			if (file.exists() && file.isDirectory()) {
				env.setCurrentDirectory(other);
			} else {
				throw new ShellIOException("The provided path cant be set as the new currentDirectory.");
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
		description.add("Takes one argument, a path.");
		description.add("If the given path exists changes the current directory to the new path.");
		description.add("If the given path doesn't exists it throws an exception");
		return description;
	}

}
