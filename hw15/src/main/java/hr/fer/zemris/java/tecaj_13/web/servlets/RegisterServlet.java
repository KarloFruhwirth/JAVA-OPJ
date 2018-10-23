package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * Servlet used for registration
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

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
	 * Method used to process the activities on register page
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("metoda") != null) {
			if (req.getParameter("metoda").equals("Cancle")) {
				req.getSession().setAttribute("register", null);
				resp.sendRedirect(req.getContextPath() + "/servleti/main");
				return;
			}

		}
		if (req.getSession().getAttribute("register") == null) {
			req.getSession().setAttribute("register", true);
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		} else {
			BlogUser bu = new BlogUser();
			try {
				if (validate(req, bu, resp)) {
					req.getSession().setAttribute("register", null);
					DAOProvider.getDAO().addBlogUser(bu);
					resp.sendRedirect(req.getContextPath() + "/servleti/main");
				} else {
					req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
					return;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Method used to validate inputed information
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param bu
	 *            BlogUser
	 * @param resp
	 *            HttpServletResponse
	 * @return boolean
	 * @throws NoSuchAlgorithmException
	 */
	private boolean validate(HttpServletRequest req, BlogUser bu, HttpServletResponse resp)
			throws NoSuchAlgorithmException {
		boolean correct = true;
		String firstName = (String) req.getParameter("fn");
		String lastName = (String) req.getParameter("ln");
		String email = (String) req.getParameter("email");
		String nick = (String) req.getParameter("nick");
		String password = (String) req.getParameter("password");
		if (firstName.equals("")) {
			correct = false;
			req.setAttribute("firstNameError", true);
		}
		if (lastName.equals("")) {
			correct = false;
			req.setAttribute("lastNameError", true);
		}
		if (email.equals("")) {
			correct = false;
			req.setAttribute("emailError", true);
		}
		if (!email.equals("")) {
			if (!email.matches("^[\\w-_\\.*]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
				correct = false;
				req.setAttribute("emailError", true);
			}
		}
		if (nick.equals("")) {
			correct = false;
			req.setAttribute("registration_nick", true);
		}
		if (!nick.equals("")) {
			for (BlogUser user : DAOProvider.getDAO().getUsers()) {
				if (user.getNick().equals(nick)) {
					correct = false;
					req.setAttribute("registration_nick_exsist", true);
				}
			}
		}
		if (password.equals("")) {
			correct = false;
			req.setAttribute("registration_password", true);
		}
		if (!password.equals("")) {
			if (password.length() < 6) {
				correct = false;
				req.setAttribute("registration_password_len", true);
			}
		}
		if (correct) {
			bu.setFirstName(firstName);
			bu.setLastName(lastName);
			bu.setEmail(email);
			bu.setNick(nick);
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(password.getBytes(StandardCharsets.UTF_8));
			bu.setPasswordHash(Util.bytetohex(sha.digest()));
			return true;
		} else {
			bu.setFirstName(firstName == null ? "" : firstName);
			bu.setLastName(lastName == null ? "" : lastName);
			bu.setNick(nick == null ? "" : nick);
			bu.setEmail(email == null ? "" : email);
			bu.setPasswordHash(password == null ? "" : password);

			req.setAttribute("zapis", bu);
			return false;
		}
	}

}
