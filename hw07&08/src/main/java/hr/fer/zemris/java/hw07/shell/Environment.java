package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;

/**
 * Interface used to communicate between the user from the shell and the
 * commands. Only trough this interface is such communication allowed
 * 
 * 
 * @author KarloFrühwirth
 *
 */
public interface Environment {

	/**
	 * Method used to read the users input from std.in. it reads line by line the
	 * input if the line ends with MorelinesSymbol
	 * 
	 * @return String of read elements
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method used to write to the console
	 * 
	 * @param text
	 *            Text being written
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method used to write to the console with new line at the end
	 * 
	 * @param text
	 *            Text being written
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method used to get all the commands implemented
	 * 
	 * @return unmodifiable map
	 */
	SortedMap<String, ShellCommand> commands(); // return unmodifiable map

	Path getCurrentDirectory();

	void setCurrentDirectory(Path path);

	Object getSharedData(String key);

	void setSharedData(String key, Object value);

	/**
	 * Method used to return MultilineSymbol used in {@link SymbolShellCommand}
	 * 
	 * @return MultilineSymbol
	 */
	Character getMultilineSymbol();

	/**
	 * Method used to return MultilineSymbol used in {@link SymbolShellCommand}
	 * 
	 * @param symbol
	 *            MultilineSymbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Method used to return PromptSymbol used in {@link SymbolShellCommand}
	 * 
	 * @return PromptSymbol
	 */
	Character getPromptSymbol();

	/**
	 * Method used to set PromptSymbol used in {@link SymbolShellCommand}
	 * 
	 * @param symbol
	 *            PromptSymbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Method used to set MorelinesSymbol used in {@link SymbolShellCommand}
	 * 
	 * @param symbol
	 *            MorelinesSymbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Method used to set MorelinesSymbol used in {@link SymbolShellCommand}
	 * 
	 * @param symbol
	 *            MorelinesSymbol
	 */
	void setMorelinesSymbol(Character symbol);
}
