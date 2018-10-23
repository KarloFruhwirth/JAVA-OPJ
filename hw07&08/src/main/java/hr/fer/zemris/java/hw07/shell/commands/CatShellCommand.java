package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to read a file with a certain Charset
 * 
 * @author KarloFrühwirth
 *
 */
public class CatShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "cat";

	/**
	 * {@inheritDoc} CatShellCommand <br>
	 * Opens given file and writes its content to console using provided||default
	 * Charset
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[2].length() != 0) {
			env.writeln("Command cat must recive one or two argument.");
		} else {
			Path other = env.getCurrentDirectory().resolve(args[0]);
			File source = other.toFile();
			if (!source.exists()) {
				env.writeln("The first argument of the cat command must be a valid file.");
			} else {
				if (args[1].length() != 0) {
					try {
						Charset charset = Charset.forName(args[1]);
						printContent(source, charset, env);
					} catch (Exception e) {
						env.writeln("Illegal Charset.");
					}
					
				} else {
					Charset charset = Charset.defaultCharset();
					printContent(source, charset, env);
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method used to print out the file with the given Charset
	 * 
	 * @param source
	 *            File
	 * @param charset
	 *            Charset
	 * @param env
	 *            Environment
	 */
	private void printContent(File source, Charset charset, Environment env) {
		Path sourcePath = Paths.get(source.getAbsolutePath());
		try (InputStream is = Files.newInputStream(sourcePath, StandardOpenOption.READ)) {
			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				String line = new String(buff, charset);
				env.writeln(line);
			}
			System.out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory");
		description.add("The second argument is charset name that should be used to interpret chars from bytes");
		description.add("If not provided, a default platform charset should be used.");
		description.add("This command opens given file and writes its content to console.");
		return description;
	}

}
