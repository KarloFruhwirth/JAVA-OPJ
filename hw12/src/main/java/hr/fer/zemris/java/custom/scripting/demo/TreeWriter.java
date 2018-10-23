package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Prints out the elements of SmartScript(similar to HW3 print of parsed text)
 * who's path is provided as an argument
 * 
 * @author KarloFrühwirth
 *
 */
public class TreeWriter {

	/**
	 * @param args
	 *            path to SmartScript
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.printf("Invalid number of arguments!");
			System.exit(1);
		}
		try {
			String document = readFile(args[0], StandardCharsets.UTF_8);
			SmartScriptParser parser = new SmartScriptParser(document);
			WriteVisitor wv = new WriteVisitor();
			DocumentNode document1 = parser.getDocumentNode();
			document1.accept(wv);
		} catch (IOException ignorable) {
			ignorable.printStackTrace();
		}
	}

	/**
	 * Method used to read file
	 * 
	 * @param path
	 *            path to file
	 * @param encoding
	 *            Charset
	 * @return String
	 * @throws IOException
	 *             if file can't be read
	 */
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	/**
	 * Implementation of INodeVisitor
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	private static class WriteVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i++) {
				Node temp = new Node();
				temp = node.getChild(i);
				temp.accept(this);
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i++) {
				Node temp = new Node();
				temp = node.getChild(i);
				temp.accept(this);
			}
		}

	}

}
