package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to inform the user of the commands and
 * its purposes
 * 
 * @author KarloFrühwirth
 *
 */
public class HelpShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "help";

	/**
	 * {@inheritDoc} HelpShellCommand <br>
	 * Command lists supported commands if no args are provided If arg is command
	 * returns detailed description of the command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, ShellCommand> list = env.commands();
		if (arguments.length() == 0) {
			env.writeln("Supported comands are:");
			list.forEach((k, v) -> env.writeln(k));
		} else {
			arguments.trim();
			List<String> description = new ArrayList<>();
			try {
				description = list.get(arguments).getCommandDescription();
				description.forEach((k) -> env.writeln(k));
			} catch (Exception e) {
				env.writeln("This command is not supported.");
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
		description.add("Command takes a single argument or no arguments.");
		description.add("If started with single argument, it must print name and the description of selected command.");
		description.add("If started with no arguments, it must list names of all supported commands.");
		return description;
	}

}
