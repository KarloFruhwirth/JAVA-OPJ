package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used to add, edit blogs and add comments to existing blogs
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Checks if logged in user is the user on the author page
	 */
	private boolean isLoggedIn = false;
	/**
	 * Nick in URL
	 */
	private String nick;
	/**
	 * Path
	 */
	private String path;
	/**
	 * Entry id
	 */
	private Long eid;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		path = req.getPathInfo();
		if (req.getSession().getAttribute("current.user.id") != null) {
			isLoggedIn = true;
		}
		String[] array = path.substring(1).split("/");
		nick = array[0];
		if (array.length == 1) {
			listBlogEntries(req, resp);
			return;
		}
		if (array.length == 2) {
			if (array[1].equals("new")) {
				if (req.getSession().getAttribute("current.user.nick").equals(null)) {
					req.getSession().setAttribute("error",
							"Anonymus users cant create new blogs for existing user " + nick);
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
					req.getSession().setAttribute("error",
							"The Blog user " + req.getSession().getAttribute("current.user.nick")
									+ " cant create new blogs for user " + nick);
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				try {
					req.setAttribute("edit_entry", false);
					req.setAttribute("author_name", nick);
					req.getRequestDispatcher("/WEB-INF/pages/new-editBlog.jsp").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} else if (array[1].equals("edit")) {
				String id = req.getParameter("id");
				Long editID;
				try {
					editID = Long.parseLong(id);
				} catch (Exception e) {
					req.getSession().setAttribute("error", "Illegal edit id! " + nick);
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				if (req.getSession().getAttribute("current.user.nick") == null) {
					req.getSession().setAttribute("error", "Anonymus users cant edit blogs for existing user " + nick);
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
					req.getSession().setAttribute("error", "The Blog user "
							+ req.getSession().getAttribute("current.user.nick") + " cant edit blogs for user " + nick);
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				try {
					req.setAttribute("edit_entry", true);
					req.setAttribute("author_name", nick);
					List<BlogEntry> list = DAOProvider.getDAO().getUserBlogEntries(nick);
					boolean set = false;
					for (BlogEntry be : list) {
						if (be.getId().equals(editID)) {
							req.setAttribute("entry_title", be.getTitle());
							req.setAttribute("entry_text", be.getText());
							set = true;
						}
					}
					if (!set) {
						req.getSession().setAttribute("error", "Non existing entry ID");
						resp.sendRedirect(req.getContextPath() + "/servleti/error");
						return;
					}
					req.getRequestDispatcher("/WEB-INF/pages/new-editBlog.jsp").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Integer.parseInt(array[1]);
				} catch (Exception e) {
					req.getSession().setAttribute("error", "Invalid entry ID");
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				List<BlogEntry> list = DAOProvider.getDAO().getUserBlogEntries(nick);
				boolean display = false;
				eid = Long.parseLong(array[1]);
				for (BlogEntry be : list) {
					if (be.getId().equals(eid)) {
						req.setAttribute("entry_title", be.getTitle());
						req.setAttribute("entry_text", be.getText());
						req.setAttribute("enrty_id", be.getId());
						display = true;
					}
				}
				if (!display) {
					req.getSession().setAttribute("error", "Non existing entry ID");
					resp.sendRedirect(req.getContextPath() + "/servleti/error");
					return;
				}
				try {
					List<BlogComment> listBC = DAOProvider.getDAO().getBlogEntry(eid).getComments();
					req.setAttribute("comments", listBC);
					req.setAttribute("logged", nick.equals(req.getSession().getAttribute("current.user.nick")));
					req.setAttribute("author_name", nick);
					req.setAttribute("inputedEmail", req.getSession().getAttribute("current.user.em")!=null);
					req.getRequestDispatcher("/WEB-INF/pages/displayBlog.jsp").forward(req, resp);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		path = req.getPathInfo();
		if (req.getSession().getAttribute("current.user.id") != null) {
			isLoggedIn = true;
		}
		String[] array = path.substring(1).split("/");
		if (array.length == 1) {
			nick = array[0];
			listBlogEntries(req, resp);
			return;
		}
		if (array.length == 2) {
			if (array[1].equals("new")) {
				String title = req.getParameter("title");
				String text = req.getParameter("text");
				BlogEntry be = new BlogEntry();
				be.setTitle(title);
				be.setText(text);
				BlogUser bu = DAOProvider.getDAO().getBlogUser(nick);
				be.setCreator(bu);
				be.setCreatedAt(new Date());
				DAOProvider.getDAO().addBlogEntry(be);
				try {
					resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (array[1].equals("edit")) {
				String title = req.getParameter("title");
				String text = req.getParameter("text");
				BlogEntry be = DAOProvider.getDAO().getBlogEntry(eid);
				be.setTitle(title);
				be.setText(text);
				BlogUser bu = DAOProvider.getDAO().getBlogUser(nick);
				be.setCreator(bu);
				be.setLastModifiedAt(new Date());
				DAOProvider.getDAO().editBlogEntry(be);
				try {
					resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(array[1].equals("comment")) {
				String comment = req.getParameter("comment");
				String email = req.getParameter("e-mail");
				BlogEntry be = DAOProvider.getDAO().getBlogEntry(eid);
				BlogComment bc = new BlogComment();
				bc.setBlogEntry(be);
				bc.setMessage(comment);
				bc.setPostedOn(new Date());
				if(req.getSession().getAttribute("current.user.em")!=null) {
					email = (String) req.getSession().getAttribute("current.user.em");
				}
				bc.setUsersEMail(email);
				DAOProvider.getDAO().addBlogComment(bc);
				try {
					resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick +"/" +eid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void listBlogEntries(HttpServletRequest req, HttpServletResponse resp) {
		nick = req.getPathInfo().substring(1).split("/")[0];
		if (DAOProvider.getDAO().getBlogUser(nick) == null) {
			try {
				req.getSession().setAttribute("error", "The Blog user under this nick " + nick + " doesnt exsist.");
				resp.sendRedirect(req.getContextPath() + "/servleti/error");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		isLoggedIn = nick.equals(req.getSession().getAttribute("current.user.nick"));
		List<BlogEntry> entries = DAOProvider.getDAO().getUserBlogEntries(nick);
		if (entries.size() == 0) {
			req.setAttribute("noEntry", true);
		} else {
			req.setAttribute("noEntry", false);
		}

		req.setAttribute("entries", entries);
		req.setAttribute("author_name", nick);
		req.setAttribute("logged", isLoggedIn);
		try {
			req.getRequestDispatcher("/WEB-INF/pages/BlogList.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
