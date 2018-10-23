package hr.fer.zemris.java.p12.dao.sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollOptionsEntry;
import hr.fer.zemris.java.p12.model.PollsEntry;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<PollsEntry> getPollsEntries() throws DAOException {
		List<PollsEntry> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from POLLS order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollsEntry poll = new PollsEntry();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return polls;
	}

	@Override
	public PollsEntry getPollsEntry(long id) throws DAOException {
		List<PollsEntry> polls = getPollsEntries();
		for (PollsEntry pe : polls) {
			if (pe.getId() == id) {
				return pe;
			}
		}
		throw new DAOException("Polls table doesn't contain a poll under this id!");
	}

	@Override
	public List<PollOptionsEntry> getPollOptionsEntries() throws DAOException {
		List<PollOptionsEntry> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from POLLOPTIONS order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOptionsEntry option = new PollOptionsEntry();
						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setPollID(rs.getLong(4));
						option.setVotesCount(rs.getLong(5));
						pollOptions.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return pollOptions;
	}

	@Override
	public PollOptionsEntry getPollOptionsEntry(long id) throws DAOException {
		List<PollOptionsEntry> polloptions = getPollOptionsEntries();
		for (PollOptionsEntry poe : polloptions) {
			if (poe.getId() == id) {
				return poe;
			}
		}
		throw new DAOException("PollOptions table doesn't contain an option under this id!");
	}

	@Override
	public boolean isEmpty(DataSource ds, String tableName) throws IOException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}

		SQLConnectionProvider.setConnection(con);
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT COUNT(*) AS count FROM " + tableName);
			ResultSet rset = pst.executeQuery();
			try {
				rset.next();
				int num = rset.getInt("count");
				return num > 0 ? false : true;
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void insertData(DataSource ds, String filePath) throws IOException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}

		SQLConnectionProvider.setConnection(con);
		PreparedStatement pst = null;
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
			for (String line : lines) {
				String[] elems = line.split("\t");
				pst = con.prepareStatement("INSERT INTO POLLS (title,message) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, elems[0]);
				pst.setString(2, elems[1]);
				pst.executeUpdate();
				ResultSet rset = pst.getGeneratedKeys();
				try {
					if (rset != null && rset.next()) {
						long newID = rset.getLong(1);
						addOptions(newID, con, filePath.substring(0, filePath.lastIndexOf("\\") + 1) + "PollOptions_"
								+ elems[2] + ".txt");
					}
				} finally {
					try {
						rset.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

	}

	private void addOptions(long newID, Connection con, String filePath) {
		PreparedStatement pst = null;
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
			for (String line : lines) {
				String[] elems = line.split("\t");
				pst = con.prepareStatement(
						"INSERT INTO POLLOPTIONS (optionTitle,optionLink,pollID,votesCount) values (?,?,?,0)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, elems[1]);
				pst.setString(2, elems[2]);
				pst.setLong(3, newID);
				pst.executeUpdate();
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTables(DataSource ds) throws IOException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}

		SQLConnectionProvider.setConnection(con);
		PreparedStatement pst = null;

		try {
			List<String> tableNames = new ArrayList<>();
			DatabaseMetaData meta = con.getMetaData();
			ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (res.next()) {
				tableNames.add(res.getString("TABLE_NAME"));
			}
			if (!tableNames.contains("POLLS")) {
				System.out.println("Create Polls table");
				pst = con.prepareStatement(
						"CREATE TABLE Polls\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
								+ " title VARCHAR(150) NOT NULL,\r\n" + " message CLOB(2048) NOT NULL\r\n" + ")");
				pst.execute();
			}
			if (!tableNames.contains("POLLOPTIONS")) {
				System.out.println("Create PollOptions table");
				pst = con.prepareStatement(
						"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
								+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
								+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
								+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
				pst.execute();
			}
			System.out.println("Polls & PollOptions table exist");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateVotes(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		SQLConnectionProvider.setConnection(con);
		PreparedStatement pst = null;

		PollOptionsEntry poe = getPollOptionsEntry(id);
		long votes = poe.getVotesCount();
		votes++;
		try {
			pst = con.prepareStatement("UPDATE PollOptions SET votesCount =  " + votes + " WHERE id = ? ");
			pst.setLong(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<PollOptionsEntry> getPollOptionsEntriesSorted() throws DAOException {
		List<PollOptionsEntry> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from POLLOPTIONS order by votesCount DESC");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOptionsEntry option = new PollOptionsEntry();
						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setPollID(rs.getLong(4));
						option.setVotesCount(rs.getLong(5));
						pollOptions.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return pollOptions;
	}

}