package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to pop the top path from the stack if
 * such exists
 * 
 * @author KarloFrühwirth
 *
 */
public class PopDShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "popd";

	/**
	 * {@inheritDoc} PopDShellCommand <br>
	 * Pops the top path if such exists
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Command popd is executed without arguments");
		} else {
			if (env.getSharedData("cdstack") == null) {
				env.writeln("Map SharedData doesnt have this key(cdstack).");
			} else {
				try {
					@SuppressWarnings("unchecked")
					Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
					Path top = stack.pop();
					env.setCurrentDirectory(top);
				} catch (EmptyStackException e) {
					env.writeln("Stack is empty.");
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
		description.add("Takes no arguments, checks in the SharedData if there is a Stack under cdstack key.");
		description
				.add("If the stack is not yet initialized it writes out Map SharedData doesnt have this key(cdstack).");
		description.add("If the stack contains paths, it pops the top one and sets the current directory to it.");
		description.add("If the stack is empty the command writes -> Stack is empty.");
		return description;
	}

}
