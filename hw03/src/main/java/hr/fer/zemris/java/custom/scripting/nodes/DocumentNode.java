package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * @author KarloFrühwirth
 * A node representing an entire document. It inherits from Node class.
 */
public class DocumentNode extends Node{
	
	public DocumentNode() {
	}
	
	@Override
	public void accept(INodeVisitor nodeVisitor) {
		nodeVisitor.visitDocumentNode(this);
	}
}
