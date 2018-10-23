package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {

	/**
	 * DocumentNode
	 */
	private DocumentNode documentNode;
	/**
	 * RequestContext
	 */
	private RequestContext requestContext;
	/**
	 * ObjectMultistack
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Implementation of INodeVisitor
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException ignorable) {
				ignorable.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			String startExpression = node.getStartExpression().asText();
			String endExpression = node.getEndExpression().asText();
			String stepExpression = node.getStepExpression() != null ? node.getStepExpression().asText() : "1";

			multistack.push(variable, new ValueWrapper(startExpression));

			while (multistack.peek(variable).numCompare(endExpression) <= 0) {
				int numberOfChildren = node.numberOfChildren();
				for (int i = 0; i < numberOfChildren; i++) {
					Node temp = new Node();
					temp = node.getChild(i);
					temp.accept(this);
				}
				multistack.peek(variable).add(stepExpression);
			}

			multistack.pop(variable);

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<String> stack = new Stack<>();
			Element[] elements = node.getElements();

			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof ElementString || elements[i] instanceof ElementConstantDouble
						|| elements[i] instanceof ElementConstantInteger) {
					stack.push(elements[i].asText());
				} else if (elements[i] instanceof ElementVariable) {
					String currentVarValue = String.valueOf(multistack.peek(elements[i].asText()).getValue());
					stack.push(currentVarValue.toString());
				} else if (elements[i] instanceof ElementOperator) {
					if (stack.size() < 2)
						throw new IllegalArgumentException("2 elements are expected to perform a single operation !");
					ValueWrapper operator1 = new ValueWrapper(stack.pop());
					ValueWrapper operator2 = new ValueWrapper(stack.pop());
					switch (elements[i].asText()) {
					case ("+"):
						operator1.add(operator2.getValue());
						stack.push(String.valueOf(operator1.getValue()));
						break;
					case ("-"):
						operator1.subtract(operator2.getValue());
						stack.push(String.valueOf(operator1.getValue()));
						break;
					case ("*"):
						operator1.multiply(operator2.getValue());
						stack.push(String.valueOf(operator1.getValue()));
						break;
					case ("/"):
						operator1.divide(operator2.getValue());
						stack.push(String.valueOf(operator1.getValue()));
						break;
					default:
						break;
					}
				} else if (elements[i] instanceof ElementFunction) {
					performFunction(stack, (ElementFunction) elements[i]);
				}
			}
			Stack<String> reversedStack = new Stack<>();
			while (stack.size() != 0) {
				String element = stack.pop();
				reversedStack.push(element);
			}
			while (reversedStack.size() != 0) {
				try {
					String element = reversedStack.pop();
					requestContext.write(element);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	};

	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	private void performFunction(Stack<String> stack, ElementFunction element) {
		switch (element.getValue()) {
		case ("sin"):
			String value = stack.pop();
			String result = Double.toString(Math.sin(Math.toRadians(Double.valueOf(value))));
			stack.push(result);
			break;
		case ("decfmt"):
			String format = stack.pop();
			String valueToFormat = stack.pop();
			DecimalFormat df = new DecimalFormat(format);
			String formatedResult = df.format(Double.parseDouble(valueToFormat));
			stack.push(formatedResult);
			break;
		case ("dup"):
			String valueToDuplecate = stack.pop();
			stack.push(valueToDuplecate);
			stack.push(valueToDuplecate);
			break;
		case ("swap"):
			String a = stack.pop();
			String b = stack.pop();
			stack.push(a);
			stack.push(b);
			break;
		case ("setMimeType"):
			String mime = stack.pop();
			requestContext.setMimeType(mime);
			break;
		case ("paramGet"):
			String defValue = stack.pop();
			String variableName = stack.pop();
			String val = requestContext.getParameter(variableName);
			stack.push(val == null ? defValue : val);
			break;
		case ("pparamGet"):
			String defValue1 = stack.pop();
			String variableName1 = stack.pop();
			String val1 = requestContext.getPersistentParameter(variableName1);
			stack.push(val1 == null ? defValue1 : val1);
			break;
		case ("pparamSet"):
			String parameterName = stack.pop();
			String parameterValue = stack.pop();
			requestContext.setPersistentParameter(parameterName, parameterValue);
			break;
		case ("pparamDel"):
			String removeParameter = stack.pop();
			requestContext.removePersistentParameter(removeParameter);
			break;
		case ("tparamGet"):
			String defValue2 = stack.pop();
			String variableName2 = stack.pop();
			String val2 = requestContext.getTemporaryParameter(variableName2);
			stack.push(val2 == null ? defValue2 : val2);
			break;
		case ("tparamSet"):
			String tempParameterName = stack.pop();
			String tempParameterValue = stack.pop();
			requestContext.setTemporaryParameter(tempParameterName, tempParameterValue);
			break;
		case ("tparamDel"):
			String removeTempParameter = stack.pop();
			requestContext.removeTemporaryParameter(removeTempParameter);
			break;
		default:
			break;
		}

	}

	public void execute() {
		documentNode.accept(visitor);
	}

}
