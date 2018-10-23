package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * @author KarloFrühwirth
 * 
 *         Class with a main method used for testing the drawing of a LSystem
 *         using defined methods to define the parameters for the LSystem On
 *         open you can choose one of the predefined examples located in
 *         src/main/resources
 */
public class Glavni3 {
	/**
	 * Main method that draws on the canvas
	 * 
	 * @param args
	 *            does not do anything
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}

}
