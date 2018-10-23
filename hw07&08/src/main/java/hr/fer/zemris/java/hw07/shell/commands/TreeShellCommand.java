package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to print out a tree like structure of the
 * elements in the directory provided
 * 
 * @author KarloFrühwirth
 *
 */
public class TreeShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "tree";

	/**
	 * {@inheritDoc} TreeShellCommand <br>
	 * For the provided directory name and prints a tree.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() != 0) {
			env.writeln("Command tree must recive one argument.");
		} else {
			Path other = env.getCurrentDirectory().resolve(args[0]);
			File file = other.toFile();
			if (!file.isDirectory()) {
				env.writeln("Command tree must recive an excisting directory as an argument.");
			} else {
				rekurzija(file, 0, env);
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Recursive function used to list all child elements of a provided File
	 * 
	 * @param dir
	 *            directory
	 * @param indent
	 *            depth
	 * @param env
	 *            Environment
	 */
	private void rekurzija(File dir, int indent, Environment env) {
		final int SIZE = 2;
		if (indent == 0) {
			env.writeln(dir.getPath());
		} else {
			String result = String.format("%" + (SIZE * indent) + "s%s%n", "", dir.getName());
			env.write(result);
		}
		File[] elements = dir.listFiles();
		if (elements != null) {
			for (File child : elements) {
				if (child.isDirectory()) {
					rekurzija(child, indent + 1, env);
				} else {
					String result = String.format("%" + (SIZE * indent + 1) + "s%s%n", "", child.getName());
					env.write(result);
				}
			}
		}

	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command expects a single argument.");
		description.add("For the provided directory name and prints a tree.");
		description.add("each directory level shifts output two charatcers to the right");
		return description;
	}

}
