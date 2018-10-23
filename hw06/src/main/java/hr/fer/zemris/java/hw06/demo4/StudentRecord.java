package hr.fer.zemris.java.hw06.demo4;

/**
 * Class which represents all of the data associated for a certain Student.
 * 
 * @author KarloFrühwirth
 *
 */
public class StudentRecord {
	/**
	 * JMBAG of the student
	 */
	private String jmbag;
	/**
	 * Students last name
	 */
	private String lastName;
	/**
	 * Students first name
	 */
	private String firstName;
	/**
	 * Points from mid term exam
	 */
	private double pointsMI;
	/**
	 * Points from final exam
	 */
	private double pointsZI;
	/**
	 * Points from labs
	 */
	private double pointsLAB;
	/**
	 * Students grade
	 */
	private int grade;

	/**
	 * Constructor for the StudentRecord
	 * 
	 * @param jmbag
	 *            jmbag
	 * @param lastName
	 *            lastName
	 * @param firstName
	 *            firstName
	 * @param pointsMI
	 *            pointsMI
	 * @param pointsZI
	 *            pointsZI
	 * @param pointsLAB
	 *            pointsLAB
	 * @param grade
	 *            grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double pointsMI, double pointsZI,
			double pointsLAB, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pointsMI = pointsMI;
		this.pointsZI = pointsZI;
		this.pointsLAB = pointsLAB;
		this.grade = grade;
	}

	/**
	 * Getter for the jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Setter for the jmbag
	 * 
	 * @param jmbag
	 *            jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Getter for the lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for the lastName
	 * 
	 * @param lastName
	 *            lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the firstName
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for the firstName
	 * 
	 * @param firstName
	 *            firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for the pointsMI
	 * 
	 * @return pointsMI
	 */
	public double getPointsMI() {
		return pointsMI;
	}

	/**
	 * Setter for the pointsMI
	 * 
	 * @param pointsMI
	 *            pointsMI
	 */
	public void setPointsMI(double pointsMI) {
		this.pointsMI = pointsMI;
	}

	/**
	 * Getter for the pointsZI
	 * 
	 * @return pointsZI
	 */
	public double getPointsZI() {
		return pointsZI;
	}

	/**
	 * Setter for the pointsZI
	 * 
	 * @param pointsZI
	 */
	public void setPointsZI(double pointsZI) {
		this.pointsZI = pointsZI;
	}

	/**
	 * Getter for the pointsLAB
	 * 
	 * @return pointsLAB
	 */
	public double getPointsLAB() {
		return pointsLAB;
	}

	/**
	 * Setter for pointsLAB
	 * 
	 * @param pointsLAB
	 *            pointsLAB
	 */
	public void setPointsLAB(double pointsLAB) {
		this.pointsLAB = pointsLAB;
	}

	/**
	 * Getter for grade
	 * 
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Setter for the grade
	 * 
	 * @param grade
	 *            grade
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * Getter for total number of points
	 * 
	 * @return sum of LAB+MI+ZI
	 */
	public double getPoints() {
		return pointsLAB + pointsMI + pointsZI;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %f %f %f %d", jmbag, lastName, firstName, pointsMI, pointsZI, pointsLAB, grade);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}
