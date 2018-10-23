package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
/**
 * @author KarloFrühwirth
 * 
 *Class with a main method used for testing the drawing of a LSystem
 *using defined methods to define the parameters for the LSystem
 */
public class Glavni2 {
	/**
	 * Main method that draws on the canvas 
	 * @param args does not do anything
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	
	/**
	 * Method that that creates a LSystemBuilder based on which the LSystem is drawn
	 * 
	 * @param provider
	 *            LSystemBuilderProvider
	 * @return provider that creates a LSystemBuilder
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin 0.05 0.4",
		"angle 0",
		"unitLength 0.9",
		"unitLengthDegreeScaler 1.0 / 3.0",
		"",
		"command F draw 1",
		"command + rotate 60",
		"command - rotate -60",
		"",
		"axiom F",
		"",
		"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
		}
}
