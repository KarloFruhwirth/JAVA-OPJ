package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class which represents a blog comment
 * 
 * @author KarloFrühwirth
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * ID
	 */
	private Long id;
	/**
	 * BlogEntry
	 */
	private BlogEntry blogEntry;
	/**
	 * Email of user
	 */
	private String usersEMail;
	/**
	 * Message
	 */
	private String message;
	/**
	 * Time of comment
	 */
	private Date postedOn;

	/**
	 * Getter for ID
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for BlogEntry
	 * 
	 * @return BlogEntry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for BlogEntry
	 * 
	 * @param blogEntry
	 *            BlogEntry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for usersEMail
	 * 
	 * @return usersEMail
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for usersEMail
	 * 
	 * @param usersEMail
	 *            usersEMail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for message
	 * 
	 * @return message
	 */
	@Column(length = 4096, nullable = false)
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

	/**
	 * Getter for postedOn
	 * 
	 * @return Date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for postedOn
	 * 
	 * @param postedOn
	 *            Date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}