package hr.fer.zemris.java.tecaj_13.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class which represents a blog user.<br>
 * A user can create and edit his blog entries.
 * 
 * @author KarloFrühwirth
 *
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * First name
	 */
	private String firstName;
	/**
	 * Last name
	 */
	private String lastName;
	/**
	 * Nick
	 */
	private String nick;
	/**
	 * Email
	 */
	private String email;
	/**
	 * Password Hash
	 */
	private String passwordHash;
	/**
	 * List of BlogEntrys
	 */
	private List<BlogEntry> blogEntries;

	/**
	 * Getter for id
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
	 * Getter for first name
	 * 
	 * @return first name
	 */
	@Column(length = 30, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for first name
	 * 
	 * @param firstName
	 *            first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for last name
	 * 
	 * @return last name
	 */
	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for last name
	 * 
	 * @param lastName
	 *            last naem
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for nick
	 * 
	 * @return
	 */
	@Column(length = 30, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick
	 *            nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	@Column(length = 30, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email
	 *            email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for passwordHash
	 * 
	 * @return passwordHash
	 */
	@Column(nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for passwordHash
	 * 
	 * @param passwordHash
	 *            passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for list of BlogEntries
	 * 
	 * @return list of BlogEntries
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Setter for list of BlogEntries
	 * 
	 * @param blogEntries
	 *            list of BlogEntries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blogEntries == null) ? 0 : blogEntries.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
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
		BlogUser other = (BlogUser) obj;
		if (blogEntries == null) {
			if (other.blogEntries != null)
				return false;
		} else if (!blogEntries.equals(other.blogEntries))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		return true;
	}

}
