package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class which defines the implementation of DAO we use in this
 * project
 * 
 * @author KarloFrühwirth
 *
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();

	/**
	 * Get DAO
	 * 
	 * @return DAO
	 */
	public static DAO getDAO() {
		return dao;
	}

}