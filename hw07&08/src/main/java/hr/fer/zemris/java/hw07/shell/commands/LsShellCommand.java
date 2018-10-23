package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to list directory non recursive
 * 
 * @author KarloFrühwirth
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Name of the command
	 */
	private String commandName = "ls";

	/**
	 * {@inheritDoc} LsShellCommand <br>
	 * Command takes a single argument which is a directory. For the given directory
	 * it writes a directory listing non recursive.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length()!=0) {
			env.writeln("Command ls must recive one argument.");
		} else {
			Path other = env.getCurrentDirectory().resolve(args[0]);
			File file = other.toFile();
			if (!file.isDirectory()) {
				env.writeln("Command ls must recive an excisting directory as an argument.");
			} else {
				File[] elements = file.listFiles();
				if (elements != null) {
					for (File child : elements) {
						try {
							String result = String.format("%s %10d %s %s", attributes(child), (int) child.length(),
									formattedDateTime(child), child.getName());
							env.writeln(result);
						} catch (IOException e) {
							// this line won't be reached
							e.printStackTrace();
						}
					}
				} else {
					env.write("Directory is empty.");
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	private String formattedDateTime(File child) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = child.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}

	/**
	 * Method used to get attributes for the given File
	 * 
	 * @param child
	 *            File being checked
	 * @return String of the attributes
	 */
	private String attributes(File child) {
		StringBuilder sb = new StringBuilder();
		if (child.isDirectory()) {
			sb.append("d");
		} else {
			sb.append("-");
		}
		if (child.canRead()) {
			sb.append("r");
		} else {
			sb.append("-");
		}
		if (child.canWrite()) {
			sb.append("w");
		} else {
			sb.append("-");
		}
		if (child.canExecute()) {
			sb.append("x");
		} else {
			sb.append("-");
		}
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command takes a single argument which is a directory.");
		description.add("It writes a directory listing non recursive.");
		description.add("The output consists of 4 columns");
		description.add(
				"First column indicates if current object is directory (d), readable (r), writable (w) and executable (x)");
		description
				.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		description.add("Follows file creation date/time and finally file name");
		return description;
	}

}
