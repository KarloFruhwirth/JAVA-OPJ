package hr.fer.zemris.java.p12.model;

/**
 * Class used to represent poll options which are stored in POLLOPTIONS table
 * 
 * @author KarloFrühwirth
 *
 */
public class PollOptionsEntry {
	/**
	 * id
	 */
	private long id;
	/**
	 * Title of the option
	 */
	private String optionTitle;
	/**
	 * Link
	 */
	private String optionLink;
	/**
	 * ID of the poll for which it is an option
	 */
	private long pollID;
	/**
	 * Vote count
	 */
	private long votesCount;

	/**
	 * Default constructor
	 */
	public PollOptionsEntry() {
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
	 * Getter for optionTitle
	 * 
	 * @return optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for optionTitle
	 * 
	 * @param optionTitle
	 *            optionTitle
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for optionLink
	 * 
	 * @return optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for optionLink
	 * 
	 * @param optionLink
	 *            optionLink
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for pollID
	 * 
	 * @return pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for pollID
	 * 
	 * @param pollID
	 *            pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter for votesCount
	 * 
	 * @return votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for votesCount
	 * 
	 * @param votesCount
	 *            votesCount
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public String toString() {
		return "PollOptionsEntry id=" + id + " optionTitle:" + optionTitle + " pollID:" + pollID;
	}
}