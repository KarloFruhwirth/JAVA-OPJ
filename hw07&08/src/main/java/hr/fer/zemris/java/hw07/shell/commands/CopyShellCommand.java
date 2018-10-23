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
 * Implementation of ShellCommand used to copy a certain file. It expects 2
 * arguments. If destination file exists, ask the user is it allowed to
 * overwrite it.
 * 
 * @author KarloFrühwirth
 *
 */
public class CopyShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "copy";

	/**
	 * {@inheritDoc} CopyShellCommand <br>
	 * Copy a certain file based on the rules defined in the class
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() == 0) {
			env.writeln("Command copy must recive two argument.");
		} else {
			Path src = env.getCurrentDirectory().resolve(args[0]).normalize();
			File source = src.toFile();
			Path dest = env.getCurrentDirectory().resolve(args[1]).normalize();
			File destination = dest.toFile();
			if (!source.exists() || !destination.exists()) {
				env.writeln("Copy command cant be performed with these arguments");
			} else {
				if (destination.isDirectory()) {
					args[1] = args[1] + "/" + source.getName();
					dest = env.getCurrentDirectory().resolve(args[1]).normalize();
					destination = dest.toFile();
				}
				InputStream is = null;
				OutputStream os = null;
				try {
					boolean exists = destination.createNewFile();
					if (exists == false) {
						env.writeln("Do you wish to overwrite the existing file y/n:");
						String input = env.readLine();
						if (input.equals("y")) {
							copy(is, os, source, destination, env);
						} else if (input.equals("n")) {
							args[1] = args[1].replace(destination.getName(), "copy" + destination.getName());
							dest = env.getCurrentDirectory().resolve(args[1]).normalize();
							destination = dest.toFile();
							copy(is, os, source, destination, env);
						} else {
							env.writeln("Input was not y/n, copy wont be performed.");
						}
					} else {
						copy(is, os, source, destination, env);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies a file
	 * @param is InputStream
	 * @param os OutputStream
	 * @param source File source
	 * @param destination File destination
	 * @param env Environment
	 */
	private void copy(InputStream is, OutputStream os, File source, File destination, Environment env) {
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(destination);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
			env.writeln("Copy complete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command expects two arguments:source file name and destination file name");
		description.add("If destination file exists, you should ask user is it allowed to overwrite it");
		description.add(
				"If the second argument is directory, copy the original file into that directory using the original file name.");
		return description;
	}

}
