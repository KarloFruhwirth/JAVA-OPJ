package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out.printf("Invalid number of arguments!");
			System.exit(1);
		}
		
		String document = readFile(args[0], StandardCharsets.UTF_8);
		System.out.println("Original text \n" + document);
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(document);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (SmartScriptLexerException e) {
			System.out.println(e.getMessage());
			System.out.println("lexer faild me");
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println("null faild me");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document1 = parser.getDocumentNode();
		System.out.println("\nNumber of children for DocumentNode " + document1.numberOfChildren() + "\n");
		String originalDocumentBody = createOriginalDocumentBody(document1);
		System.out.println("Almost identical text after parsing... \n" + originalDocumentBody); // should write
																								// something like
																								// original content of
																								// docBody
	}

	private static String createOriginalDocumentBody(Node node) {
		StringBuilder sb = new StringBuilder();
		int numberOfChildren = node.numberOfChildren();
		if (node instanceof DocumentNode) {
			for (int i = 0; i < numberOfChildren; i++) {
				Node temp = new Node();
				temp = node.getChild(i);
				sb.append(createOriginalDocumentBody(temp));
			}
		} else if (node instanceof ForLoopNode) {
			sb.append(((ForLoopNode) node).toString());
			for (int i = 0; i < numberOfChildren; i++) {
				Node temp = new Node();
				temp = node.getChild(i);
				sb.append(createOriginalDocumentBody(temp));
			}
			sb.append("{$END$}");
		} else if (node instanceof EchoNode) {
			sb.append(((EchoNode) node).toString());
		} else if (node instanceof TextNode) {
			sb.append(((TextNode) node).toString());
		}
		return sb.toString();
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
