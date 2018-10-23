package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.demo.TreeWriter;

/**
 * The Visitor design pattern interface used for the output in
 * {@link TreeWriter}.<br>
 * It provides the traversal logical implementation
 * 
 * @author KarloFrühwirth
 *
 */
public interface INodeVisitor {
	/**
	 * Visits TextNode
	 * 
	 * @param node
	 *            TextNode
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits ForLoopNode
	 * 
	 * @param node
	 *            ForLoopNode
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits EchoNode
	 * 
	 * @param node
	 *            EchoNode
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits DocumentNode
	 * 
	 * @param node
	 *            DocumentNode
	 */
	public void visitDocumentNode(DocumentNode node);
}
