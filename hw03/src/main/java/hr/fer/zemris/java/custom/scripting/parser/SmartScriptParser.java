package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * @author KarloFrühwirth A parser is a software component that takes input data
 *         and builds a data structure
 */
public class SmartScriptParser {
	private SmartScriptLexer lexer;
	private SmartScriptToken token;
	private DocumentNode docNode;
	private Node node;
	private ObjectStack stack = new ObjectStack();

	/**
	 * Constructor that implements a SmartScriptParser and gives a string for the
	 * lexer
	 * 
	 * @param docBody
	 *            string that is being lexerd
	 */
	public SmartScriptParser(String docBody) {
		docNode = new DocumentNode();
		lexer = new SmartScriptLexer(docBody);
		stack.push(docNode);
		node = (Node) stack.peek();
		parsiraj(lexer);
	}

	/**
	 * Method that pases the lexer and checks if it is all by the books othervise
	 * throws SmartScriptParserException
	 * 
	 * @param lexer
	 */
	public void parsiraj(SmartScriptLexer lexer) {
		while (true) {
			token = lexer.nextToken();
			if (token.getType() == SmartScriptTokenType.EOF)
				break;
			else if (token.getType() == SmartScriptTokenType.TEXT) {
				String text = (String) token.getValue();
				TextNode txtNode = new TextNode(text);
				node = (Node) stack.peek();
				node.addChildNode(txtNode);
			} else if (token.getType() == SmartScriptTokenType.TAGSTART) {
				token = lexer.nextToken();
				if (token.getType() == SmartScriptTokenType.END) {
					token = lexer.nextToken();
					if (token.getType() == SmartScriptTokenType.TAGEND) {
						if (stack.size() > 1) {
							stack.pop();
						} else {
							throw new SmartScriptParserException("Cant pop from empty stack");
						}
					} else {
						throw new SmartScriptParserException("End  must be followed by the end tag $}");
					}
				} else {
					if (token.getType() == SmartScriptTokenType.SPACE) {
						token = lexer.nextToken();
					}
					if (token.getType() == SmartScriptTokenType.EQUALS) {
						Element[] elements = getElements();
						EchoNode echo = new EchoNode(elements);
						if (stack.peek() instanceof ForLoopNode) {
							node = (ForLoopNode) stack.peek();
							node.addChildNode(echo);
						} else if (stack.peek() instanceof DocumentNode) {
							node = (DocumentNode) stack.peek();
							node.addChildNode(echo);
						} else {
							throw new SmartScriptParserException("Exception");
						}
					} else if (token.getType() == SmartScriptTokenType.FOR) {
						Element[] elements = getElements();
						int size = elements.length;
						if (size < 3 || size > 4) {
							throw new SmartScriptParserException(
									"Illegal number of arguments for the for tag , your for tag has : " + size
											+ " elements");
						} else if (checkElements(elements, size)) {
							ForLoopNode forNode;
							if (size == 3) {
								forNode = new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2]);
							} else {
								forNode = new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2],
										elements[3]);
							}
							if (stack.peek() instanceof ForLoopNode) {
								node = (ForLoopNode) stack.peek();
								node.addChildNode(forNode);
								stack.push(forNode);
							} else if (stack.peek() instanceof DocumentNode) {
								node = (DocumentNode) stack.peek();
								node.addChildNode(forNode);
								stack.push(forNode);
							} else {
								throw new SmartScriptParserException("Wrong peek node");
							}
						} else {
							throw new SmartScriptParserException(
									"One of the elements is not an instance of ElementVariable||ElementConstantInteger||ElementConstantDouble");
						}
					}
				}
			}
		}
		if(stack.size()!=1 && stack.peek() instanceof DocumentNode) {
			throw new SmartScriptParserException("Stack must contain 1 DocumentNode element!");
		}

	}

	/**
	 * Method that is used to check if the elements array is valid for the for tag
	 * 
	 * @param elements
	 *            array of elements
	 * @param size
	 *            size of the array
	 * @return true|false
	 */
	public boolean checkElements(Element[] elements, int size) {
		if (elements[0] instanceof ElementVariable) {
			int brojac = 0;
			for (int i = 1; i < size; i++) {
				if (elements[i] instanceof ElementVariable || elements[i] instanceof ElementConstantInteger
						|| elements[i] instanceof ElementConstantDouble || elements[i] instanceof ElementString) {
					brojac++;
				}
			}
			if (brojac == size - 1) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Method used to goes through the echo or for tag and based on the token type
	 * set the element to the specified version of it
	 * 
	 * @return elements array
	 */
	public Element[] getElements() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		token = lexer.nextToken();
		while (true) {
			if (token.getType() == SmartScriptTokenType.SPACE) {
				token = lexer.nextToken();
			} else if (token.getType() == SmartScriptTokenType.TAGEND) {
				break;
			} else {
				if (token.getType() == SmartScriptTokenType.VARIABLE) {
					array.add(new ElementVariable((String) token.getValue()));
					token = lexer.nextToken();
				} else if (token.getType() == SmartScriptTokenType.STRING) {
					array.add(new ElementString((String) token.getValue()));
					token = lexer.nextToken();
				} else if (token.getType() == SmartScriptTokenType.INTEGER) {
					array.add(new ElementConstantInteger((int) token.getValue()));
					token = lexer.nextToken();
				} else if (token.getType() == SmartScriptTokenType.FUNCION) {
					array.add(new ElementFunction((String) token.getValue()));
					token = lexer.nextToken();
				} else if (token.getType() == SmartScriptTokenType.OPERATOR) {
					array.add(new ElementOperator((String) token.getValue()));
					token = lexer.nextToken();
				} else if (token.getType() == SmartScriptTokenType.DOUBLE) {
					array.add(new ElementConstantDouble((double) token.getValue()));
					token = lexer.nextToken();
				} else {
					throw new SmartScriptParserException(
							"This is not a representation of an element :" + (String) token.getValue());
				}
			}
		}
		Element[] elements = new Element[array.size()];
		for (int i = 0; i < array.size(); i++) {
			elements[i] = (Element) array.get(i);
		}
		return elements;
	}

	/**
	 * Getter for the DocumentNode
	 * 
	 * @return DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return docNode;
	}

}
