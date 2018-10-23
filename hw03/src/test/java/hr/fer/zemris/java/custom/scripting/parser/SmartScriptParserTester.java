package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTester {
	
	
	@Test
	public void TestExampeText() {
		String document = loader("document1.txt");
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test
	public void TestNumberOfChildren() {
		String document = loader("document1.txt");
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		int numberOfChildren = node.numberOfChildren();
		assertEquals(4,numberOfChildren);
	}
	
	@Test
	public void TestEmpty() {
		String document = "";
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test (expected=EmptyStackException.class)
	public void TestToManyEndTags() {
		String document = loader("document2.txt");
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test (expected=SmartScriptParserException.class)
	public void TestForTagSize() {
		String document = loader("document1.txt");
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test (expected=SmartScriptParserException.class)
	public void TestForEndTagIllegal() {
		String document = "{$END $}";
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test (expected=SmartScriptLexerException.class)
	public void TestIllegalElement() {
		String document = "{$ FOR i < 2 4 $}";
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test (expected=SmartScriptParserException.class)
	public void TestForTagInvalidElement() {
		String document = loader("document4.txt");
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void TestForNull() {
		String document = null;
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(node);
		assertEquals(document,originalDocumentBody);
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
	
	private static String createOriginalDocumentBody(Node node) {
		StringBuilder sb = new StringBuilder();
		int numberOfChildren = node.numberOfChildren();
		if(node instanceof DocumentNode) {
			for(int i=0;i<numberOfChildren;i++) {
				Node temp = new Node();
				temp=node.getChild(i);
				sb.append(createOriginalDocumentBody(temp));
			}
		}else if (node instanceof ForLoopNode) {
			sb.append(((ForLoopNode)node).toString());
			for(int i=0;i<numberOfChildren;i++) {
				Node temp = new Node();
				temp=node.getChild(i);
				sb.append(createOriginalDocumentBody(temp));
			}
			sb.append("{$END$}");
		}else if (node instanceof EchoNode) {
			sb.append(((EchoNode)node).toString());
		}else if (node instanceof TextNode) {
			sb.append(((TextNode)node).toString());
		}
		return sb.toString();
	}

}
