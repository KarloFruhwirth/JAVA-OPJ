package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implementation of ShellCommand used to write a hex-output based on the file.
 * 
 * @author KarloFrühwirth
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/**
	 * Name of the command
	 */
	private String commandName = "hexdump";

	/**
	 * {@inheritDoc} HexdumpShellCommand <br>
	 * Command expects a single argument file name, and produces a hex-output
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentChecker ac = new ArgumentChecker();
		String[] args = ac.getElements(arguments);
		if (args[0].length() == 0 || args[1].length() != 0) {
			env.writeln("Command hexdump must recive one argument.");
		} else {
			Path other = env.getCurrentDirectory().resolve(args[0]);
			File file = other.toFile();
			if (!file.exists()) {
				env.writeln("Command hexdump must recive an excisting directory as an argument.");
			} else {
				printContent(file, env);
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method used to print hex dump
	 * 
	 * @param dir
	 *            File
	 * @param env
	 *            Environment
	 */
	private void printContent(File dir, Environment env) {
		Path sourcePath = Paths.get(dir.getAbsolutePath());
		try (InputStream is = Files.newInputStream(sourcePath, StandardOpenOption.READ)) {
			int i = 0;
			byte[] buff = new byte[16];
			while (true) {
				int r = is.read(buff);
				if (r < 16) {
					if (r > 8) {
						byte[] one = new byte[8];
						for (int j = 0; j < 8; j++) {
							one[j] = buff[j];
						}
						byte[] remaining = Arrays.copyOfRange(buff, 8, r);
						String result = String.format("%07d0:%s|%s | %s", i, byteArray(one), byteArray(remaining),
								print(Arrays.copyOfRange(buff, 0, r)));
						env.writeln(result);
					} else {
						byte[] remaining = Arrays.copyOfRange(buff, 0, r);
						String result = String.format("%07d0:%s|%s | %s", i, byteArray(remaining),
								byteArray(new byte[8]), print(remaining));
						env.writeln(result);
					}
					break;
				}
				String result = String.format("%07d0:%s|%s | %s", i, byteArray(Arrays.copyOfRange(buff, 0, 7)),
						byteArray(Arrays.copyOfRange(buff, 8, 15)), print(buff));
				i++;
				env.writeln(result);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to convert a byte[] to String which converts byts to char if
	 * 32<byte<127
	 * 
	 * @param array
	 *            byte[]
	 * @return
	 */
	private String print(byte[] array) {
		StringBuilder sb = new StringBuilder();
		for (byte c : array) {
			if (c < 32 || c > 127) {
				sb.append(".");
			} else {
				char a = (char) c;
				sb.append(a);
			}
		}
		return sb.toString();
	}

	/**
	 * Method to convert a byte[] to String of hex values
	 * 
	 * @param array
	 *            byte[]
	 * @return String
	 */
	private String byteArray(byte[] array) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (byte c : array) {
			i++;
			if ((c & 0xFF) == 0) {
				sb.append("   ");
			} else {
				sb.append(String.format("%02X ", c));
			}
		}
		if (i != 8) {
			for (; i < 8; i++) {
				sb.append("   ");
			}
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
		description.add("Command expects a single argument file name, and produces hex-output");
		description.add("On the right side of the image only a standard subset of characters is shown;");
		description.add("for all other characters a '.' is printed instead");
		description.add("i.e. replace all bytes whose value is less than 32 or greater than 127 with '.'");
		return description;
	}

}
