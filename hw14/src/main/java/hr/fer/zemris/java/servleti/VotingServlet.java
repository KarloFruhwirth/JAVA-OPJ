package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionsEntry;
import hr.fer.zemris.java.p12.model.PollsEntry;

/**
 * Servlet used to setAttributes for the selected PollsEntry and its
 * PollOptionsEntries for HttpServletRequest
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/glasanje")
public class VotingServlet extends HttpServlet {
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			long id = Long.valueOf(req.getParameter("pollID"));
			PollsEntry pe = DAOProvider.getDao().getPollsEntry(id);
			List<PollOptionsEntry> pollOptions = DAOProvider.getDao().getPollOptionsEntries();
			List<PollOptionsEntry> list = new ArrayList<>();
			for (PollOptionsEntry poe : pollOptions) {
				if (poe.getPollID() == id) {
					list.add(poe);
				}
			}
			req.setAttribute("pollOptions", list);
			req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req, resp);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorInvalid.jsp").forward(req, resp);
			return;
		}

	}
}
