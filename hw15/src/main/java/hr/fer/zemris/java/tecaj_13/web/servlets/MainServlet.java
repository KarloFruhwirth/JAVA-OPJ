package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * Servlet used for functionality of main page
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

	/**
	 * Method used to process the activities on main page
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (metoda != null) {
			if (metoda.equals("Login"))
				try {
					validate(req);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
		}
		List<BlogUser> users = DAOProvider.getDAO().getUsers();
		req.getSession().setAttribute("register", null);
		if (users.size() > 0) {
			req.setAttribute("users_set", true);
		}
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);

	}

	/**
	 * Method used to validate inputed information
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @throws NoSuchAlgorithmException
	 */
	private void validate(HttpServletRequest req) throws NoSuchAlgorithmException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		sha.update(password.getBytes(StandardCharsets.UTF_8));
		password = Util.bytetohex(sha.digest());

		req.getSession().setAttribute("current.user.nick", nick);
		BlogUser bu = DAOProvider.getDAO().getBlogUser(nick);
		if (bu != null) {
			req.setAttribute("non_exsisting_user", false);
			if (!DAOProvider.getDAO().getUsers().contains(bu)) {
				req.setAttribute("invalid_username", true);
			}
			req.setAttribute("nick", nick);
			if (bu.getPasswordHash().equals(password)) {
				req.getSession().setAttribute("current.user.fn", bu.getFirstName());
				req.getSession().setAttribute("current.user.ln", bu.getLastName());
				req.getSession().setAttribute("current.user.id", bu.getId());
				req.getSession().setAttribute("current.user.em", bu.getEmail());
				req.setAttribute("invalid_password", false);
				req.setAttribute("invalid_username", false);
			} else {
				req.setAttribute("invalid_password", true);
			}
		} else {
			req.setAttribute("non_exsisting_user", true);
		}
	}

}
