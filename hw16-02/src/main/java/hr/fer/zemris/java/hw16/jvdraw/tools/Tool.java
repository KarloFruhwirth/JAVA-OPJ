package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Tool interface, used to define methods used in {@link CircleTool},
 * {@link LineTool}, {@link FilledCircleTool}
 * 
 * @author KarloFrühwirth
 *
 */
public interface Tool {
	/**
	 * mousePressed listener
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * mouseReleased listener
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * mouseClicked listener
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * mouseMoved listener
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * mouseDragged listener
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints the object when the mouse is moving..
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void paint(Graphics2D g2d);
}