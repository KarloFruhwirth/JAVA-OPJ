package hr.fer.zemris.java.raytracer.model;

/**
 * Class which defines a Sphere GraphicalObject for ray-casting
 * 
 * @author KarloFrühwirth
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Point3D center
	 */
	private Point3D center;
	/**
	 * radius
	 */
	private double radius;
	/**
	 * Coefficient of diffusion for red
	 */
	private double kdr;
	/**
	 * Coefficient of diffusion for green
	 */
	private double kdg;
	/**
	 * Coefficient of diffusion for blue
	 */
	private double kdb;
	/**
	 * Coefficient of reflection for red
	 */
	private double krr;
	/**
	 * Coefficient of reflection for green
	 */
	private double krg;
	/**
	 * Coefficient of reflection for blue
	 */
	private double krb;
	/**
	 * Coefficient of reflection for the surface
	 */
	private double krn;

	/**
	 * Constructor for the Sphere
	 * 
	 * @param center
	 *            center
	 * @param radius
	 *            radius
	 * @param kdr
	 *            kdr
	 * @param kdg
	 *            kdg
	 * @param kdb
	 *            kdb
	 * @param krr
	 *            krr
	 * @param krg
	 *            krg
	 * @param krb
	 *            krb
	 * @param krn
	 *            krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	// http://www.ambrsoft.com/TrigoCalc/Sphere/SpherLineIntersection_.htm reference
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D rayStart = ray.start;
		Point3D center = this.center;
		Point3D vectorRC = rayStart.sub(center);
		Point3D rayDirection = ray.direction;
		double radius = this.radius;

		double a = rayDirection.scalarProduct(rayDirection);
		double b = vectorRC.scalarMultiply(2).scalarProduct(rayDirection);
		double c = vectorRC.scalarProduct(vectorRC) - radius * radius;

		double discriminant = b * b - 4 * a * c;

		if (discriminant < 0)
			return null;

		double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

		Point3D intersection1 = rayStart.add(rayDirection.scalarMultiply(t1));
		Point3D intersection2 = rayStart.add(rayDirection.scalarMultiply(t2));
		Point3D closer;
		double distance1 = intersection1.sub(rayStart).norm();
		double distance2 = intersection2.sub(rayStart).norm();
		double distanceCloser;
		if (distance1 < distance2) {
			closer = intersection1;
			distanceCloser = distance1;
		} else {
			closer = intersection2;
			distanceCloser = distance2;
		}

		return new RayIntersection(closer, distanceCloser, false) {

			@Override
			public Point3D getNormal() {
				return closer.sub(getCenter()).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

	/**
	 * Getter for center
	 * 
	 * @return center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Getter for radius
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Getter for kdr
	 * 
	 * @return kdr
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * Getter for kdg
	 * 
	 * @return kdg
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * Getter for kdb
	 * 
	 * @return kdb
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * Getter for krr
	 * 
	 * @return krr
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * Getter for krg
	 * 
	 * @return krg
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * Getter for krb
	 * 
	 * @return krb
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Getter for krn
	 * 
	 * @return krn
	 */
	public double getKrn() {
		return krn;
	}

}
