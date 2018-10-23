package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program for calculation and display of Newton-Raphson fractal
 * @see <a href="https://en.wikipedia.org/wiki/Newton%27s_method">LINK</a>
 * @author KarloFrühwirth
 *
 */
public class Newton {

	/**
	 * ComplexRootedPolynomial
	 */
	private static ComplexRootedPolynomial polynomial;
	/**
	 * ComplexPolynomial
	 */
	private static ComplexPolynomial derived;
	/**
	 * moduleLimit used in call()
	 */
	private static final double moduleLimit = 1E-3;
	/**
	 * rootClosenessLimit used for indexOfClosestRootFor
	 */
	private static final double rootClosenessLimit = 1E-3;

	/**
	 * Main method. Writes out welcome message.<br>
	 * Expects the user to input at least two roots for the program to start and
	 * produce an image. <br>
	 * If there are less then two roots provided before key word done is inputed the
	 * program will terminate it self.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		List<Complex> list = new ArrayList<>();
		int counter = 1;
		while (true) {
			System.out.printf("Root %d> ", counter);
			String input = sc.nextLine();
			if (input.equals("done")) {
				sc.close();
				break;
			} else {
				try {
					Complex c = parseComplex(input.trim());
					list.add(c);
					counter++;
				} catch (Exception e) {
					System.out.println("Input complex number or done...");
					continue;
				}
			}
		}
		if (counter < 3) {
			System.out.println("Next time provide at least two roots..");
			System.exit(0);
			// since two roots are expected in case there are less then 2 terminate program
		} else {
			System.out.println("Image of fractal will appear shortly. Thank you.");
			polynomial = new ComplexRootedPolynomial(list.toArray(new Complex[list.size()]));
			derived = polynomial.toComplexPolynom().derive();
			FractalViewer.show(new MojProducer());
		}
	}

	/**
	 * Static class which implements Callable<Void>. Implemented based on the
	 * FraktalParalelno3 and FraktalSlijednoProsireno shown during the class.<br>
	 * Its method call calculates the closest index for every pixel in the image.
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	public static class PosaoIzracuna implements Callable<Void> {
		/**
		 * Real minimum
		 */
		double reMin;
		/**
		 * Real maximum
		 */
		double reMax;
		/**
		 * Imaginary minimum
		 */
		double imMin;
		/**
		 * Imaginary maximum
		 */
		double imMax;
		/**
		 * Width
		 */
		int width;
		/**
		 * Height
		 */
		int height;
		/**
		 * y minimum
		 */
		int yMin;
		/**
		 * y maximum
		 */
		int yMax;
		/**
		 * max number of iterations
		 */
		int m;
		/**
		 * data
		 */
		short[] data;

		/**
		 * Constructor for PosaoIzracuna
		 * 
		 * @param reMin
		 *            reMin
		 * @param reMax
		 *            reMax
		 * @param imMin
		 *            imMin
		 * @param imMax
		 *            imMax
		 * @param width
		 *            width
		 * @param height
		 *            height
		 * @param yMin
		 *            yMin
		 * @param yMax
		 *            yMax
		 * @param m
		 *            m
		 * @param data
		 *            data
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					//mapiranje z-a
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					double module = 0;
					int iter = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						Complex zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
						// System.out.println("numerator : "+numerator);
						// System.out.println("denominator: "+denominator);
						// System.out.println("fraction: "+fraction);
						// System.out.println("zn: "+zn1);
						// System.out.println("module:"+module);
					} while (iter < m && module > moduleLimit);
					short index = (short) polynomial.indexOfClosestRootFor(zn, rootClosenessLimit);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) (index + 1);
					}

				}
			}
			return null;
		}

	}

	/**
	 * Static class which implements IFractalProducer. Implemented based on the
	 * FraktalParalelno3 and FraktalSlijednoProsireno shown during the class.<br>
	 * Its method produce accepts the result of the IFractalResultObserver.
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	public static class MojProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Zapocinjem izracun...");
			int m = 16 * 16 * 16;
			int numOfProcessors = Runtime.getRuntime().availableProcessors();
			short[] data = new short[width * height];
			final int dividers = height / (8 * numOfProcessors);
			int numOfYPerDivider = height / dividers;

			ExecutorService pool = Executors.newFixedThreadPool(numOfProcessors);
			List<Future<Void>> rezultati = new ArrayList<>();

			for (int i = 0; i < dividers; i++) {
				int yMin = i * numOfYPerDivider;
				int yMax = (i + 1) * numOfYPerDivider - 1;
				if (i == dividers - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data);
				rezultati.add(pool.submit(posao));
			}
			for (Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			pool.shutdown();
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);

		}

	}

	/**
	 * Method used to parse the users input of the complex number.
	 * 
	 * @param input
	 *            String
	 * @return Complex
	 * @throws IllegalArgumentException
	 *             if the string is empty or the input is invalid.
	 */
	private static Complex parseComplex(String input) {
		if (input == null)
			throw new IllegalArgumentException("Empty string");
		String pattern = "(^[-]?\\d*\\.?\\d*)\\s*(\\+|-)?\\s*(i(\\d*\\.?\\d*)?$)?";
		Pattern p1 = Pattern.compile(pattern);
		Matcher m1 = p1.matcher(input);
		if (m1.find()) {
			if (m1.group(0) == null || m1.group(0).equals("") || m1.group(2) != null && m1.group(3) == null) {
				throw new IllegalArgumentException("Invalid input");
			}
			double real = 0, imaginary = 0;
			if (input.equals("-i")) {
				imaginary = -1;
			} else if (input.equals("i") || input.equals("+i")) {
				imaginary = 1;
			} else {
				if (m1.group(1) != null) {
					real = Double.parseDouble(m1.group(1));
				}
				if (m1.group(3) != null) {
					if (m1.group(2).equals("-")) {
						if (m1.group(3).equals("i")) {
							imaginary = -1;
						} else {
							imaginary = -Double.parseDouble(m1.group(4));
						}
					} else {
						if (m1.group(2).equals("+")) {
							if (m1.group(3).equals("i")) {
								imaginary = 1;
							} else {
								imaginary = Double.parseDouble(m1.group(4));
							}

						}
					}
				}
			}
			return new Complex(real, imaginary);
		} else {
			throw new IllegalArgumentException("Error while parsing..");
		}
	}

}
