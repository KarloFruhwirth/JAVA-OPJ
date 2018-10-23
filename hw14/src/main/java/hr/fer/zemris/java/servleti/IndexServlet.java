package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollsEntry;

/**
 * Servlet which obtains a list of defined polls and renders it to user as a
 * list of clickable links
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PollsEntry> polls = DAOProvider.getDao().getPollsEntries();
		req.getSession().setAttribute("polls", polls);
		resp.getWriter().write("<html>\n");
		resp.getWriter().write("<body bgcolor=\"white\">\n");
		resp.getWriter().write("<h1>Here is a list of all of the currently active polls </h1>\n");
		resp.getWriter().write("<ul>\n");
		for (PollsEntry pe : polls) {
			resp.getWriter()
					.write("<li>" + pe.getTitle() + "<a href=\"glasanje?pollID=" + pe.getId() + "\">VOTE</a></li>\n");
		}
		resp.getWriter().write("</ul>\n");
		resp.getWriter().write("</body>\n");
		resp.getWriter().write("</html>\n");
	}
}
