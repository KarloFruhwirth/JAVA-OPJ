package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to push to the stack a newly provided
 * path
 * 
 * @author KarloFrühwirth
 *
 */
public class PushDShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "pushd";
	/**
	 * Stack
	 */
	private Stack<Path> stack = new Stack<>();

	/**
	 * {@inheritDoc} PushDShellCommand <br>
	 * Pushes to the top of the stack a new path
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() != 0) {
			env.writeln("Please provide a single argument(path) for the command to be executed.");
		} else {
			File dir = new File(args[0]);
			if (!dir.isDirectory()) {
				env.writeln("The provided path doesn't represent an existing directory");
			} else {
				if (env.getSharedData("cdstack") == null) {
					stack.push(dir.toPath());
					env.setSharedData("cdstack", stack);
					env.setCurrentDirectory(dir.toPath());
				} else {
					stack = (Stack<Path>) env.getSharedData("cdstack");
					stack.push(dir.toPath());
					env.setSharedData("cdstack", stack);
					env.setCurrentDirectory(dir.toPath());
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
		description.add("Takes one argument, a path.");
		description.add("If the given path exists pushes the current directory to the stack under the cdstack key.");
		description.add(
				"If the given path doesn't exists the command is not executed and an appropriate message is written");
		return description;
	}

}
