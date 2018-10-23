package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO interface
 * 
 * @author KarloFrühwirth
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getUsers() {
		return JPAEMProvider.getEntityManager().createQuery("from BlogUser").getResultList();
	}

	@Override
	public BlogUser getBlogUser(String nick) {
		try {
			BlogUser bu = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("select c from BlogUser as c where c.nick=:nick").setParameter("nick", nick).getResultList()
					.get(0);
			return bu;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void addBlogUser(BlogUser bu) {
		JPAEMProvider.getEntityManager().persist(bu);		
	}

	@Override
	public void addBlogEntry(BlogEntry be) {
			JPAEMProvider.getEntityManager().persist(be);			
	}

	@Override
	public void addBlogComment(BlogComment bc) {
		JPAEMProvider.getEntityManager().persist(bc);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUserBlogEntries(String userNick) {
		if(userNick==null)return new ArrayList<>();
		BlogUser bu = getBlogUser(userNick);
		return JPAEMProvider.getEntityManager()
				.createQuery("select c from BlogEntry as c where c.creator=:bu").setParameter("bu", bu).getResultList();
	}

	@Override
	public void editBlogEntry(BlogEntry be) {
		JPAEMProvider.getEntityManager().merge(be);		
	}
	
	

}