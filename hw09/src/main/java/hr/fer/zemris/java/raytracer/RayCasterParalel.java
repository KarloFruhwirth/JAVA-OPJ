package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class which uses ray-casting to create an image of 2 3D spheres this time
 * parallely.
 * 
 * @author KarloFrühwirth
 *
 */
public class RayCasterParalel {

	/**
	 * tolerance for Double.compare
	 */
	private static final double tolerance = 1E-2;

	/**
	 * Main method
	 * 
	 * @param args
	 *            not used for the purpose of this class
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * IRayTracerProducer used to calculate the image data
	 * 
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D OG = view.sub(eye).normalize();
				Point3D VUP = viewUp.normalize();
				// Point3D zAxis = null; // radimo 2D projekciju

				Point3D yAxis = VUP.sub(OG.scalarMultiply(OG.scalarProduct(VUP))).normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Calculate(scene, eye, view, horizontal, vertical, width, height, 0, height, xAxis,
						yAxis, screenCorner, red, green, blue));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Class implementing RecursiveAction. Based on the info provided though the
	 * constructor it performs its job.<br>
	 * Multiple threads work to compute a task parallelly in recursive action. <br>
	 * On the basis of some condition we divide the task and submitted to perform
	 * computation;<br>
	 * in our case if (ymax-ymin+1)<=THRESHOLD
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	private static class Calculate extends RecursiveAction {
		/**
		 * default generated serialVersionUID
		 */
		private static final long serialVersionUID = 6803383875278427179L;

		/**
		 * treshold
		 */
		final int THRESHOLD = 16;

		/**
		 * Scene
		 */
		private Scene scene;
		/**
		 * Point3D eye
		 */
		private Point3D eye;
		/**
		 * Point3D view
		 */
		private Point3D view;
		/**
		 * horizontal size
		 */
		private double horizontal;
		/**
		 * vertical size
		 */
		private double vertical;
		/**
		 * width
		 */
		private int width;
		/**
		 * height
		 */
		private int height;
		/**
		 * ymin used to divide "jobs"
		 */
		private int ymin;
		/**
		 * ymax used to divide "jobs"
		 */
		private int ymax;
		/**
		 * Point3D xAxis
		 */
		private Point3D xAxis;
		/**
		 * Point3D yAxis
		 */
		private Point3D yAxis;
		/**
		 * Point3D screenCorner
		 */
		private Point3D screenCorner;
		/**
		 * short[] red
		 */
		private short[] red;
		/**
		 * short[] green
		 */
		private short[] green;
		/**
		 * short[] blue
		 */
		private short[] blue;

		/**
		 * Constructor for C Calculate
		 * 
		 * @param scene
		 *            Scene
		 * @param eye
		 *            Point3D
		 * @param view
		 *            Point3D
		 * @param horizontal
		 *            double
		 * @param vertical
		 *            double
		 * @param width
		 *            int
		 * @param height
		 *            int
		 * @param ymin
		 *            int
		 * @param ymax
		 *            int
		 * @param xAxis
		 *            Point3D
		 * @param yAxis
		 *            Point3D
		 * @param screenCorner
		 *            Point3D
		 * @param red
		 *            short[]
		 * @param green
		 *            short[]
		 * @param blue
		 *            short[]
		 */
		public Calculate(Scene scene, Point3D eye, Point3D view, double horizontal, double vertical, int width,
				int height, int ymin, int ymax, Point3D xAxis, Point3D yAxis, Point3D screenCorner, short[] red,
				short[] green, short[] blue) {
			this.scene = scene;
			this.eye = eye;
			this.view = view;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.ymin = ymin;
			this.ymax = ymax;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html
		@Override
		protected void compute() {
			if ((ymax - ymin + 1) <= THRESHOLD) {
				short[] rgb = new short[3];
				int offset = ymin * width;
				for (int y = ymin; y < ymax; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.0) * horizontal))
								.sub(yAxis.scalarMultiply(y / (height - 1.0) * vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				return;
			} else {
				int ymid = (ymin + ymax) >>> 1;
				invokeAll(
						new Calculate(scene, eye, view, horizontal, vertical, width, height, ymin, ymid, xAxis, yAxis,
								screenCorner, red, green, blue),
						new Calculate(scene, eye, view, horizontal, vertical, width, height, ymid, ymax, xAxis, yAxis,
								screenCorner, red, green, blue));
			}
		}

	}

	/**
	 * Traces the image using ray-cast
	 * 
	 * @param scene
	 *            Scene
	 * @param ray
	 *            Ray
	 * @param rgb
	 *            RGB color
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		double[] rgb2 = new double[3];
		rgb2[0] = 15;
		rgb2[1] = 15;
		rgb2[2] = 15;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest != null) {
			calculateRGB(scene, ray, rgb2, closest);
		}
		rgb[0] = (short) rgb2[0];
		rgb[1] = (short) rgb2[1];
		rgb[2] = (short) rgb2[2];
	}

	/**
	 * Calculates the color for intersections. <br>
	 * Based on diffused,reflected and ambient component.
	 * 
	 * @param scene
	 *            Scene
	 * @param ray
	 *            Ray
	 * @param rgb2
	 *            double[]
	 * @param intersection
	 *            RayIntersection
	 */
	private static void calculateRGB(Scene scene, Ray ray, double[] rgb2, RayIntersection intersection) {
		List<LightSource> ls = scene.getLights();
		for (LightSource light : ls) {
			Ray r = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			RayIntersection intersection2 = findClosestIntersection(scene, r);
			if (intersection2 != null) {
				if (Double.compare(light.getPoint().sub(intersection2.getPoint()).norm() + tolerance,
						light.getPoint().sub(intersection.getPoint()).norm()) < 0) {
					continue; // skip this light source
				} else {
					reflectiveComponent(ray, light, rgb2, intersection2);
					diffuseComponent(light, rgb2, intersection2);
				}
			}
		}

	}

	/**
	 * reflectiveComponent of the RGB <br>
	 * Is = Ii * ks * (r*v)^n
	 * 
	 * @param ray
	 *            Ray
	 * @param light
	 *            LightSource
	 * @param rgb2
	 *            double[]
	 * @param intersection
	 *            RayIntersection
	 */
	private static void reflectiveComponent(Ray ray, LightSource light, double[] rgb2, RayIntersection intersection) {
		Point3D n = intersection.getNormal();
		Point3D l = light.getPoint().sub(intersection.getPoint());
		Point3D ln = l.scalarMultiply(n.scalarProduct(l));
		Point3D r = ln.add(l.sub(ln).scalarMultiply(-1.0));
		Point3D v = ray.start.sub(intersection.getPoint());
		double cos = r.normalize().scalarProduct(v.normalize());
		rgb2[0] += light.getR() * intersection.getKrr() * Math.pow(cos, intersection.getKrn());
		rgb2[1] += light.getG() * intersection.getKrg() * Math.pow(cos, intersection.getKrn());
		rgb2[2] += light.getB() * intersection.getKrb() * Math.pow(cos, intersection.getKrn());

	}

	/**
	 * diffuseComponent of the RGB <br>
	 * Id=Ii*kd*(l*n)
	 * 
	 * @param light
	 *            LightSource
	 * @param rgb2
	 *            double[]
	 * @param intersection
	 *            RayIntersection
	 */
	private static void diffuseComponent(LightSource light, double[] rgb2, RayIntersection intersection) {
		Point3D l = light.getPoint().sub(intersection.getPoint()).normalize();
		Point3D n = intersection.getNormal();
		double scaleLN = l.scalarProduct(n);
		rgb2[0] += light.getR() * intersection.getKdr() * Math.max(scaleLN, 0);
		rgb2[1] += light.getG() * intersection.getKdg() * Math.max(scaleLN, 0);
		rgb2[2] += light.getB() * intersection.getKdb() * Math.max(scaleLN, 0);
	}

	/**
	 * Method used to fint the closest intersection
	 * 
	 * @param scene
	 *            Scene
	 * @param ray
	 *            Ray
	 * @return RayIntersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closest = null;
		List<GraphicalObject> objects = scene.getObjects();
		for (GraphicalObject g : objects) {
			RayIntersection inter = g.findClosestRayIntersection(ray);
			if (inter != null && (closest == null || inter.getDistance() < closest.getDistance())) {
				closest = inter;
			}
		}
		return closest;
	}

}
