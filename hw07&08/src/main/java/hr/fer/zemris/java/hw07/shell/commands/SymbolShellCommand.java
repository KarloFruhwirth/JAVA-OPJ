package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to return the symbol currently used for
 * that function or set the symbol to the newly given char
 * 
 * @author KarloFrühwirth
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "symbol";

	/**
	 * {@inheritDoc} SymbolShellCommand <br>
	 * If number of arguments is one it must be a Symbol name and then it returns
	 * the symbol currently used for that function. If number of arguments is two it
	 * must provide a new char for the symbol
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elements = arguments.split("\\s+");
		if (elements.length < 1 || elements.length > 2) {
			env.writeln("Symbol command must have valid arguments... see description");
		} else if (elements.length == 1) {
			switch (elements[0]) {
			case "PROMPT":
				env.writeln("Symbol for " + elements[0] + " is '" + env.getPromptSymbol() + "'");
				break;
			case "MORELINES":
				env.writeln("Symbol for " + elements[0] + " is '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for " + elements[0] + " is '" + env.getMultilineSymbol() + "'");
				break;
			default:
				env.writeln("Symbol command must have valid arguments... see description");
				break;
			}
		} else {
			if (elements[1].length() != 1)
				env.writeln("Symbol command must have valid arguments... see description");
			switch (elements[0]) {
			case "PROMPT":
				env.write("Symbol for " + elements[0] + " changed from '" + env.getPromptSymbol() + "' to '");
				env.setPromptSymbol(elements[1].charAt(0));
				env.write(env.getPromptSymbol() + "'\n");
				break;
			case "MORELINES":
				env.write("Symbol for " + elements[0] + " changed from '" + env.getMorelinesSymbol() + "' to '");
				env.setMorelinesSymbol(elements[1].charAt(0));
				env.write(env.getMorelinesSymbol() + "'\n");
				break;
			case "MULTILINE":
				env.write("Symbol for " + elements[0] + " changed from '" + env.getMultilineSymbol() + "' to '");
				env.setMultilineSymbol(elements[1].charAt(0));
				env.write(env.getMultilineSymbol() + "'\n");
				break;
			default:
				env.writeln("Symbol command must have valid arguments... see description");
				break;
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
		description.add(
				"Command expects a single argument, the name of the Symbol or two when it changes the current symbol.");
		description.add("Symbol names are PROMPT,MORELINES,MULTILINE");
		description.add("The second argument must be a char");
		return description;
	}
}
