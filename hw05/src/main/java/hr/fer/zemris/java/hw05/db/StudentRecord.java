package hr.fer.zemris.java.hw05.db;

/**
 * Class that holds the information about a student Contains setters and getters
 * for its atributes jmbag,lastName,firstName and finalGrade
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
	 * last name of the student
	 */
	private String lastName;
	/**
	 * first name of the student
	 */
	private String firstName;
	/**
	 * final grade of the student
	 */
	private int finalGrade;

	/**
	 * Constructor for StudentRecord
	 * 
	 * @param jmbag
	 *            jmbag
	 * @param lastName
	 *            lastName
	 * @param firstName
	 *            firstName
	 * @param finalGrade
	 *            finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
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

	/**
	 * Getter for jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Setter for jmbag
	 * 
	 * @param jmbag
	 *            jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Getter for lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastName
	 * 
	 * @param jmbag
	 *            lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for firstName
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstName
	 * 
	 * @param jmbag
	 *            firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for finalGrade
	 * 
	 * @return finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Setter for finalGrade
	 * 
	 * @param jmbag
	 *            finalGrade
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

}
