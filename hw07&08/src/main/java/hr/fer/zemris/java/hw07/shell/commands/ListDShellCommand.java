package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to list all paths stored on the stack
 * 
 * @author KarloFrühwirth
 *
 */
public class ListDShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "listd";

	/**
	 * {@inheritDoc} ListDShellCommand <br>
	 * It list all paths stored on the stack
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Command listd is executed without arguments");
		} else {
			if (env.getSharedData("cdstack") == null) {
				env.writeln("Map SharedData doesnt have this key(cdstack).");
			} else {
				@SuppressWarnings("unchecked")
				Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
				int size = stack.size();
				if (size == 0) {
					env.writeln("There are no stored directories.");
				} else {
					while (size != 0) {
						Path top = stack.get(size - 1);
						env.writeln(top.toString());
						size--;
					}
				}
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
		description.add("Takes no arguments.");
		description.add("Checks the stack and writes out the paths starting from the top one.");
		description.add("If the stack is empty the command writes -> There are no stored directories");
		return description;
	}

}
