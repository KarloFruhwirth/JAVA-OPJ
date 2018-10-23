package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * @author KarloFrühwirth
 * 
 *         Class with a main method used for testing the drawing of a LSystem
 *         using defined methods to define the parameters for the LSystem
 */
public class Glavni1 {

	/**
	 * Main method that draws on the canvas
	 * 
	 * @param args
	 *            does not do anything
	 */
	public static void main(String[] args) {

		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

	}

	/**
	 * Method that that creates a LSystemBuilder based on which the LSystem is drawn
	 * 
	 * @param provider
	 *            LSystemBuilderProvider
	 * @return provider that creates a LSystemBuilder
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}

}
