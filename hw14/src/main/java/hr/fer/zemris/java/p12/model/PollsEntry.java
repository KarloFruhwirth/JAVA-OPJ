package hr.fer.zemris.java.p12.model;

/**
 * Class used to represent polls which are stored in POLLS table
 * 
 * @author KarloFrühwirth
 *
 */
public class PollsEntry {
	/**
	 * Poll id
	 */
	private long id;
	/**
	 * Poll title
	 */
	private String title;
	/**
	 * Poll message
	 */
	private String message;

	/**
	 * Default constructor
	 */
	public PollsEntry() {
	}

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id
	 *            id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message
	 * 
	 * @param message
	 *            message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PoolsEntry id=" + id;
	}
}