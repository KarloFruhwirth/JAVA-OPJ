package hr.fer.zemris.java.webapps.galerija;

/**
 * Class used to represent a single picture
 * 
 * @author KarloFrühwirth
 *
 */
public class Picture {

	/**
	 * Picture name
	 */
	private String name;
	/**
	 * Picture description
	 */
	private String description;
	/**
	 * array of tags
	 */
	private String[] tags;

	/**
	 * Constructor for Picture
	 * 
	 * @param name
	 *            name
	 * @param description
	 *            description
	 * @param tags
	 *            tags
	 */
	public Picture(String name, String description, String... tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter for tags
	 * 
	 * @return tags
	 */
	public String[] getTags() {
		return tags;
	}

}
