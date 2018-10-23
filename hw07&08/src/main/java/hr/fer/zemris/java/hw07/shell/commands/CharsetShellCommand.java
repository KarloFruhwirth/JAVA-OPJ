package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to get all of the Charset's which can be
 * used.
 * 
 * @author KarloFrühwirth
 *
 */
public class CharsetShellCommand implements ShellCommand {

	/**
	 * Name of the command
	 */
	private String commandName = "charsets";

	/**
	 * {@inheritDoc} CharsetShellCommand <br>
	 * Lists all of the Charset's which can be used in this class.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Command charsets must be called with no additional arguments.");
		} else {
			SortedMap<String, Charset> list = Charset.availableCharsets();
			list.forEach((k, v) -> env.writeln(v.toString()));
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
		description.add("Takes no arguments and lists names of supported charsets for your Java platform.");
		description.add("A single charset name is written per line.");
		return description;
	}

}
