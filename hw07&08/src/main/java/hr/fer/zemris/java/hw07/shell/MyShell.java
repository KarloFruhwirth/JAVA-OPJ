package hr.fer.zemris.java.hw07.shell;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Main class which is a shell and uses {@link ShellCommand}s. When started,
 * writes a greeting message to user. works until exit command is inputed.
 * 
 * @author KarloFrühwirth
 *
 */
public class MyShell {

	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		EnvironmentImplementation env = new EnvironmentImplementation();
		env.writeln("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;
		Scanner sc = new Scanner(System.in);
		while (status == ShellStatus.CONTINUE) {
			String lines = env.readLine().trim();
			String commandName, arguments = "";
			if (lines.length() == 0) {
				continue;
			} else {
				if (lines.contains(" ")) {
					commandName = lines.substring(0, lines.indexOf(" "));
					arguments = lines.substring(lines.indexOf(" ") + 1, lines.length()).trim();
				} else {
					commandName = lines.substring(0, lines.length());
				}
				ShellCommand command = env.commands().get(commandName);
				if (command == null) {
					env.writeln("This is not a supported command");
				} else {
					try {
						status = command.executeCommand(env, arguments);
					} catch (ShellIOException e) {
						env.writeln("The system cannot find the path specified.");
					} catch (InvalidPathException ex) {
						env.writeln("Windows Path Parser doesnt allow us to resolve something with \" as a name. ");
						env.writeln(ex.getMessage());
					} catch (IllegalArgumentException f) {
						env.writeln(f.getMessage());
					}
				}
			}
		}
		sc.close();
	}

	/**
	 * Nested class which is an implementation of the {@link Environment} <br>
	 * Used for setup and functionality of {@link MyShell}
	 * 
	 * @author KarloFrühwirth
	 */
	private static class EnvironmentImplementation implements Environment {
		/**
		 * Prompt symbol, default set to '>'
		 */
		private char PROMPTSYMBOL = '>';
		/**
		 * MoreLines symbol, default set to '\'
		 */
		private char MORELINESSYMBOL = '\\';
		/**
		 * MultLines symbol, default set to '|'
		 */
		private char MULTILINESYMBOL = '|';
		/**
		 * Path of the current directory, default set to "."
		 */
		private Path currentDirectory = Paths.get(".");

		Map<String, Object> sharedData = new HashMap<>();

		Scanner sc = new Scanner(System.in);

		@Override
		public String readLine() throws ShellIOException {
			StringBuilder sb = new StringBuilder();
			write(getPromptSymbol().toString() + " ");
			sb.append(sc.nextLine());
			if (sb.toString().length() == 0) {
				return "";
			}
			while (sb.charAt(sb.length() - 1) == getMorelinesSymbol()) {
				sb.deleteCharAt(sb.length() - 1);
				write(getMultilineSymbol().toString() + " ");
				sb.append(sc.nextLine());
			}
			return sb.toString();
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			SortedMap<String, ShellCommand> commands = new TreeMap<>();
			commands.put("exit", new ExitShellCommand());
			commands.put("ls", new LsShellCommand());
			commands.put("symbol", new SymbolShellCommand());
			commands.put("charsets", new CharsetShellCommand());
			commands.put("tree", new TreeShellCommand());
			commands.put("mkdir", new MkdirShellCommand());
			commands.put("copy", new CopyShellCommand());
			commands.put("help", new HelpShellCommand());
			commands.put("cat", new CatShellCommand());
			commands.put("hexdump", new HexdumpShellCommand());
			commands.put("pwd", new PwdShellCommand());
			commands.put("cd", new CdShellCommand());
			commands.put("pushd", new PushDShellCommand());
			commands.put("popd", new PopDShellCommand());
			commands.put("listd", new ListDShellCommand());
			commands.put("dropd", new DropDShellCommand());
			commands.put("rmtree", new RmtreeShellCommand());
			commands.put("cptree", new CptreeShellCommand());
			commands.put("massrename", new MassrenameShellCommand());
			return commands;
		}

		@Override
		public Character getMultilineSymbol() {
			return MULTILINESYMBOL;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.MULTILINESYMBOL = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return PROMPTSYMBOL;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.PROMPTSYMBOL = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return MORELINESSYMBOL;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.MORELINESSYMBOL = symbol;
		}

		@Override
		public Path getCurrentDirectory() {
			return this.currentDirectory.toAbsolutePath().normalize();
		}

		@Override
		public void setCurrentDirectory(Path path) {
			this.currentDirectory = path;
		}

		@Override
		public Object getSharedData(String key) {
			return this.sharedData.get(key);
		}

		@Override
		public void setSharedData(String key, Object value) {
			this.sharedData.put(key, value);
		}

	}

}
