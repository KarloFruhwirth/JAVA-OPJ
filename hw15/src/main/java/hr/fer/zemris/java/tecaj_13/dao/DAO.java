package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Data Access Object which defines the methods used for the purpose of this
 * homework
 * 
 * @author KarloFrühwirth
 *
 */
public interface DAO {

	/**
	 * Returns BlogEntry under the provided id. If there is no BlogEntry for the
	 * provided id the method returns null
	 * 
	 * @param id
	 *            ID
	 * @return BlogEntry or null
	 * @throws DAOException
	 *             if an error occurs
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Returns a list of all of BlogUsers
	 * 
	 * @return
	 */
	public List<BlogUser> getUsers();

	/**
	 * Returns a BlogUser for the provided nick
	 * 
	 * @param nick
	 *            key
	 * @return BlogUser
	 */
	public BlogUser getBlogUser(String nick);

	/**
	 * Adds a new blog user
	 * 
	 * @param bu
	 *            BlogUser
	 */
	public void addBlogUser(BlogUser bu);

	/**
	 * Adds a new BlogEntry
	 * 
	 * @param be
	 *            BlogEntry
	 */
	public void addBlogEntry(BlogEntry be);

	/**
	 * Edits a BlogEntry
	 * 
	 * @param be
	 *            BlogEntry
	 */
	public void editBlogEntry(BlogEntry be);

	/**
	 * Adds a blog comment
	 * 
	 * @param bc
	 */
	public void addBlogComment(BlogComment bc);

	/**
	 * Returns a list of BlogEntrys for the provided user nick
	 * 
	 * @param userNick
	 *            user nick
	 * @return list of BlogEntrys
	 */
	public List<BlogEntry> getUserBlogEntries(String userNick);

}