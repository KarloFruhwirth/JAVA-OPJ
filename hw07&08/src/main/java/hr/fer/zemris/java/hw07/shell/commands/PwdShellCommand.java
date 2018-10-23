package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to print current working directory
 * 
 * @author KarloFrühwirth
 *
 */
public class PwdShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "pwd";

	/**
	 * {@inheritDoc} PwdShellCommand <br>
	 * Writes out the current directory
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Pwd command takes no arguments.");
		} else {
			env.writeln(env.getCurrentDirectory().toString());
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
		description.add("Takes no arguments and prints the current directory path.");
		description.add("It prints an absolute path of the current directory.");
		return description;
	}

}
