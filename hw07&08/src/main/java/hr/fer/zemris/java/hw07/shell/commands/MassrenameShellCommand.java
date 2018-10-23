package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderParser;

/**
 * Implementation of ShellCommand used to filter files of a certain folder. The
 * command expects 4||5 arguments. massrename DIR1 DIR2 CMD REGEX {EXPRESION}
 * Based on the CMD subcommand we have 4 different executions. <br>
 * Filter-> for DIR1 writes out all files that match the REGEX <br>
 * Groups-> for DIR1 writes out all groups for the files that match the
 * REGEX<br>
 * Show-> displays how the files that match the REGEX from DIR1 will be renamed
 * based on EXPRESION <br>
 * Execute-> Moves and renames.
 * 
 * @author KarloFrühwirth
 *
 */
public class MassrenameShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "massrename";

	/**
	 * {@inheritDoc} MassrenameShellCommand <br>
	 * Based on the 4th argument executes different commands.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() == 0 || args[2].length() == 0 || args[3].length() == 0) {
			env.writeln("Command massrename must recive 4 or 5 arguments.");
		} else {
			File src = new File(args[0]);
			File dest = new File(args[1]);
			if (!src.isDirectory() || !dest.isDirectory()) {
				env.writeln("Provide existing directorys.");
			} else {
				String cmd = args[2];
				String mask = args[3];
				switch (cmd) {
				case "filter":
					if (args[4].length() != 0)
						throw new IllegalArgumentException("Filter command accepts only 4 args.");
					List<File> filter = filter(src, mask, env);
					filter.forEach((k) -> env.writeln(k.getName()));
					break;
				case "groups":
					if (args[4].length() != 0)
						throw new IllegalArgumentException("Groups command accepts only 4 args.");
					List<String> groups = groups(src, mask, env);
					groups.forEach((k) -> env.writeln(k));
					break;
				case "show":
					String expresion = args[4];
					if (args[5].length() != 0)
						throw new IllegalArgumentException("Show command accepts only 5 args.");
					List<String> show = show(src, dest, mask, expresion, env);
					show.forEach((k) -> env.writeln(k));
					break;
				case "execute":
					try {
						expresion = args[4];
						if (args[5].length() != 0)
							throw new IllegalArgumentException("Execute command accepts only 5 args.");
						List<String> execute = execute(src, dest, mask, expresion, env);
						execute.forEach((k) -> env.writeln(k));
						break;
					} catch (Exception e) {
						throw new IllegalArgumentException("An error occured.");
					}
				default:
					env.writeln("CMD command " + cmd + " is not supported.");
					break;
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Moves and renames files from DIR1 to DIR2
	 * 
	 * @param src
	 *            File
	 * @param dest
	 *            File
	 * @param mask
	 *            REGEX
	 * @param expresion
	 *            EXPRESION
	 * @param env
	 *            Environment
	 * @return List<String>
	 * @throws IOException
	 *             if an I/O error occurs when using Files.move
	 */
	private List<String> execute(File src, File dest, String mask, String expresion, Environment env)
			throws IOException {
		Pattern pattern = Pattern.compile(mask, Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		List<File> filtered = filter(src, mask, env);
		List<String> result = new ArrayList<>();
		NameBuilderParser parser = new NameBuilderParser(expresion);
		NameBuilder builder = parser.getNameBuilder();
		for (File f : filtered) {
			Matcher matcher = pattern.matcher(f.getName());
			if (matcher.find()) {
				NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
				builder.execute(info);
				String newName = info.getStringBuilder().toString();
				StringBuilder sb = new StringBuilder();
				sb.append(src.getPath() + "/" + f.getName() + " =>" + dest.getPath() + "/" + newName);
				result.add(sb.toString());
				Files.move(src.toPath().resolve(f.getName()), dest.toPath().resolve(newName),
						StandardCopyOption.REPLACE_EXISTING);
			}
		}
		return result;
	}

	/**
	 * For DIR1 writes out all groups for the files that match the REGEX
	 * 
	 * @param src
	 *            File
	 * @param mask
	 *            REGEX
	 * @param env
	 *            Environment
	 * @return List<String>
	 */
	private List<String> groups(File src, String mask, Environment env) {
		Pattern pattern = Pattern.compile(mask, Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		List<File> filtered = filter(src, mask, env);
		List<String> result = new ArrayList<>();
		for (File f : filtered) {
			Matcher matcher = pattern.matcher(f.getName());
			int groupCount = matcher.groupCount();
			if (matcher.find()) {
				StringBuilder sb = new StringBuilder();
				sb.append(f.getName());
				for (int i = 0; i <= groupCount; i++) {
					sb.append(" " + i + ": ");
					sb.append(matcher.group(i));
				}
				result.add(sb.toString());
			}
		}
		return result;
	}

	/**
	 * For DIR1 writes out all files that match the REGEX
	 * 
	 * @param src
	 *            File
	 * @param mask
	 *            REGEX
	 * @param env
	 *            Environment
	 * @return List<File>
	 */
	private List<File> filter(File src, String mask, Environment env) {
		List<File> list = new ArrayList<>();
		Pattern pattern = Pattern.compile(mask, Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		File[] elements = src.listFiles();
		for (File f : elements) {
			if (f.isDirectory()) {
				continue;
			} else {
				Matcher matcher = pattern.matcher(f.getName());
				if (matcher.find()) {
					list.add(f);
				} else {
					continue;
				}
			}
		}
		return list;
	}

	/**
	 * Displays how the files that match the REGEX from DIR1 will be renamed based
	 * on EXPRESION
	 * 
	 * @param src
	 *            File
	 * @param dest
	 *            File
	 * @param mask
	 *            REGEX
	 * @param expresion
	 *            EXPRESION
	 * @param env
	 *            Environment
	 * @return List<String>
	 */
	private List<String> show(File src, File dest, String mask, String expresion, Environment env) {
		Pattern pattern = Pattern.compile(mask, Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		List<File> filtered = filter(src, mask, env);
		List<String> result = new ArrayList<>();
		NameBuilderParser parser = new NameBuilderParser(expresion);
		NameBuilder builder = parser.getNameBuilder();
		for (File f : filtered) {
			Matcher matcher = pattern.matcher(f.getName());
			if (matcher.find()) {
				NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
				builder.execute(info);
				String newName = info.getStringBuilder().toString();
				StringBuilder sb = new StringBuilder();
				sb.append(f.getName() + "=>" + newName);
				result.add(sb.toString());
			}
		}
		return result;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command expects 4 or 5 arguments (DIR1 DIR2 CMD MASK {rest}).");
		description.add("The CMD determains which subcommand will be executed.");
		description.add("The subcommands are : filter, groups, show, execute.");
		description.add("Filter writes out the names of files that match the MASK in the DIR1");
		description.add("Groups writes out all of the groups for the filtered files.");
		description.add("example : slika(\\d+)-([^.]+)\\.jpg mask has 2 groups plus the implicite groupe 0.");
		description.add(
				"Show changes the names of the files that match the MASK to something determaind by a regex in rest.");
		description.add("Execute finaly moves the files from DIR1 to DIR2.");
		return description;
	}

}
