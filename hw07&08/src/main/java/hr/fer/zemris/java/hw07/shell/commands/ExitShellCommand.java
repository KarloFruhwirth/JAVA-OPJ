package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to exit/terminate the shell
 * 
 * @author KarloFrühwirth
 *
 */
public class ExitShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "exit";

	/**
	 * {@inheritDoc} ExitShellCommand <br>
	 * It terminates the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command that accepts no arguments");
		description.add("Used to terminate shell");
		return description;
	}

}
