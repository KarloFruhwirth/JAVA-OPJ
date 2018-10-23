package hr.fer.zemris.java.p12.dao;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.p12.model.PollOptionsEntry;
import hr.fer.zemris.java.p12.model.PollsEntry;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	/**
	 * Method used to get a list of PollsEntries in table POLLS
	 * 
	 * @return list of PollsEntries
	 * @throws DAOException
	 */
	public List<PollsEntry> getPollsEntries() throws DAOException;

	/**
	 * Method used to get a certain PollsEntry based on the provided id
	 * 
	 * @param id
	 *            ID
	 * @return PollsEntry
	 * @throws DAOException
	 *             if id is out of bounds
	 */
	public PollsEntry getPollsEntry(long id) throws DAOException;

	/**
	 * Method used to get a list of PollOptionsEntries in table POLLOPTIONS
	 * 
	 * @return list of PollOptionsEntries
	 * @throws DAOException
	 */
	public List<PollOptionsEntry> getPollOptionsEntries() throws DAOException;

	/**
	 * Method used to get a certain PollOptionsEntry based on the provided id
	 * 
	 * @param id
	 *            ID
	 * @return PollOptionsEntry
	 * @throws DAOException
	 *             if id is out of bounds
	 */
	public PollOptionsEntry getPollOptionsEntry(long id) throws DAOException;

	/**
	 * Method used to get a list of PollOptionsEntries in table POLLOPTIONS sorted
	 * descending based on the vote count
	 * 
	 * @return list of PollOptionsEntries
	 * @throws DAOException
	 */
	public List<PollOptionsEntry> getPollOptionsEntriesSorted() throws DAOException;

	/**
	 * Method used to update the vote count for the PollOptionsEntry stored in the
	 * table under the provided id
	 * 
	 * @param id
	 *            ID
	 * @throws DAOException
	 *             if id is out of bounds
	 */
	public void updateVotes(long id) throws DAOException;

	/**
	 * Checks if the table called tableName is empty or not
	 * 
	 * @param ds
	 *            DataSource
	 * @param tableName
	 *            name of the table
	 * @return true||false
	 * @throws IOException
	 *             if DB is unavailable
	 */
	public boolean isEmpty(DataSource ds, String tableName) throws IOException;

	/**
	 * Inserts data into POLLS and POLLOPTIONS tables
	 * 
	 * @param ds
	 *            DataSource
	 * @param filePath
	 *            path for file used to fill the POLLS table
	 * @throws IOException
	 *             if DB is unavailable
	 */
	public void insertData(DataSource ds, String filePath) throws IOException;

	/**
	 * Creates tables POLLS and POLLOPTIONS
	 * 
	 * @param ds
	 *            DataSource
	 * @throws IOException
	 *             if DB is unavailable
	 */
	public void createTables(DataSource ds) throws IOException;
}