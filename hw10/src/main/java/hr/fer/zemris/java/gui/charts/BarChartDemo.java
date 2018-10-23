package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class representing an implementation of a BarChart and displays one if
 * correct arguments are provided
 * 
 * @author KarloFrühwirth
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main method
	 * 
	 * @param args
	 *            one argument is provided which represents the string value of a
	 *            path for testing there is a Barchart.txt file in resources.
	 */
	// iz nekog meni nepoznatog razloga ne radi full screen, ako otkrijes/otkrijete
	// gresku molim vas da mi napises/napisete :D

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("One argument must be provided to run this program!!!");
			System.exit(1);
		} else {
			SwingUtilities.invokeLater(() -> {
				JFrame frame = new BarChartDemo(args[0]);
				frame.setVisible(true);
			});
		}
	}

	/**
	 * Constructor for the BarChartDemo
	 *
	 * @param args
	 *            String of path
	 */
	public BarChartDemo(String args) {
		setMinimumSize(new Dimension(400, 250));
		setTitle("Bar chart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI(args);
	}

	/**
	 * Method used to initialize the GUI
	 */
	private void initGUI(String args) {
		Container cp = getContentPane();
		BarChart chart = parseChart(args);
		cp.add(new BarChartComponent(chart), BorderLayout.CENTER);

	}

	/**
	 * Reads the first 6 lines of a file and returns a new BarChart if no errors
	 * occur<br>
	 * otherwise the program will shut down
	 * 
	 * @param args
	 *            String of a path
	 * @return BarChart
	 */
	private BarChart parseChart(String args) {
		File file = new File(args);

		if (!file.exists()) {
			System.out.println("This file doesnt excist.");
			System.exit(1);
		}
		if (file.isDirectory()) {
			System.out.println("Cant read from directory...");
			System.exit(1);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String xDescription = br.readLine().trim();
			String yDescription = br.readLine().trim();
			String[] xyval = br.readLine().trim().split(" ");
			List<XYValue> list = new ArrayList<>();
			for (String val : xyval) {
				String[] elements = val.split(",");
				list.add(new XYValue(Integer.parseInt(elements[0].trim()), Integer.parseInt(elements[1].trim())));
			}
			int yMin = Integer.parseInt(br.readLine().trim());
			int yMax = Integer.parseInt(br.readLine().trim());
			int step = Integer.parseInt(br.readLine().trim());
			return new BarChart(list, xDescription, yDescription, yMin, yMax, step);

		} catch (NumberFormatException | IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

}
