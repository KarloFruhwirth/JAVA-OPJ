package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to change copy an entire tree to another
 * directory.
 * 
 * @author KarloFrühwirth
 *
 */
public class CptreeShellCommand implements ShellCommand {

	/**
	 * Name of the command
	 */
	private String commandName = "cptree";

	/**
	 * {@inheritDoc} CptreeShellCommand <br>
	 * It copies an entire tree to another directory exists
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() == 0) {
			env.writeln("Command cptree must recive two argument.");
		} else {
			Path src = env.getCurrentDirectory().resolve(args[0]).normalize();
			File source = src.toFile();
			Path dest = env.getCurrentDirectory().resolve(args[1]).normalize();
			File destination = dest.toFile();
			if (!source.exists() || !dest.getParent().toFile().exists()) {
				env.writeln("Invalid arguments.");
			} else {
				// FileUtils.copyDirectory(source,destination);
				if (destination.exists()) {
					dest = dest.resolve(source.getName());
					destination = dest.toFile();
					copyTree(source, destination);
				} else {
					destination.mkdir();
					copyTree(source, destination);
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Recursive function for copying a File tree
	 * 
	 * @param source
	 *            File
	 * @param destination
	 *            File
	 */
	private void copyTree(File source, File destination) {
		if (source.isDirectory()) {
			copyDirectory(source, destination);
		} else {
			copyFile(source, destination);
		}
	}

	/**
	 * Method used to copy a file
	 * 
	 * @param source
	 *            File
	 * @param destination
	 *            File
	 */
	private void copyFile(File source, File destination) {
		try {
			InputStream is = new FileInputStream(source);
			OutputStream os = new FileOutputStream(destination);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * When copying a directory you need to create it
	 * 
	 * @param source
	 *            File
	 * @param destination
	 *            File
	 */
	private void copyDirectory(File source, File destination) {
		if (!destination.exists()) {
			destination.mkdir();
		}
		for (String f : source.list()) {
			copyTree(new File(source, f), new File(destination, f));
		}
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add(
				"Command expects two arguments, a path to a certain dir from which we copy and a path where we copy the dir tree.");
		description.add("If any of the paths is invalid a message is provided.");
		description.add("Example");
		description.add("Path1 is given as folows : something/name1 and it contains dat.txt.");
		description.add("Path2 is somethingelse/name2/name3.");
		description.add("If path2 excists the result is somethingelse/name2/name3/name1/dat.txt.");
		description.add(
				"If somethingelse/name2/name3 doesnt excist, but somethingelse/name2 does the result is somethingelse/name2/name3/dat.txt");
		return description;
	}

}
