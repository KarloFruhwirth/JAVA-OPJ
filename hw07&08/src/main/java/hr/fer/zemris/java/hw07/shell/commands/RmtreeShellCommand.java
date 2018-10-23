package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to remove an entire tree for the provided
 * directory. proceed with caution when using this method.
 * 
 * @author KarloFrühwirth
 *
 */
public class RmtreeShellCommand implements ShellCommand {

	/**
	 * Name of the command
	 */
	private String commandName = "rmtree";

	/**
	 * {@inheritDoc} RmtreeShellCommand <br>
	 * It removes an entire tree
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() != 0) {
			env.writeln("Command tree must recive one argument.");
		} else {
			Path other = env.getCurrentDirectory().resolve(args[0]).normalize();
			File dir = other.toFile();
			System.out.println(other.toString() + " brisemo,lets hope for the best...");
			// FileUtils.deleteDirectory(dir);
			removeTree(dir);
			dir.delete();
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Recursive function for removing a tree
	 * 
	 * @param dir
	 */
	private void removeTree(File dir) {
		String[] entries = dir.list();
		for (String s : entries) {
			File currentFile = new File(dir.getPath(), s);
			if (currentFile.isDirectory()) {
				removeTree(currentFile);
			}
			currentFile.delete();
		}
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description
				.add("Command expects one argument a path to a certain directory, be very cearfull with this command.");
		description.add("If the directory excists it delets its content and the content of its children.");
		return description;
	}

}
