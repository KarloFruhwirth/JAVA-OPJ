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

/**
 * Servlet which is used for displaying the vote results<br>
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class VotingResults extends HttpServlet {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			long id = Long.valueOf(req.getParameter("id"));
			DAOProvider.getDao().updateVotes(id);
			PollOptionsEntry poe = DAOProvider.getDao().getPollOptionsEntry(id);
			long pollID = poe.getPollID();

			List<PollOptionsEntry> list = new ArrayList<>();
			for (PollOptionsEntry option : DAOProvider.getDao().getPollOptionsEntriesSorted()) {
				if (option.getPollID() == pollID) {
					list.add(option);
				}
			}
			req.setAttribute("voteResults", list);

			// just for the esthetics on the page
			String picks = "";
			if (pollID == 1)
				picks = "Bands";
			if (pollID == 2)
				picks = "Player";
			if (pollID == 3)
				picks = "Player";
			if (pollID == 4)
				picks = "Team";
			req.setAttribute("pick", picks);

			List<PollOptionsEntry> votesList = new ArrayList<>();
			long votes = Long.MIN_VALUE;
			for (PollOptionsEntry option : list) {
				if (option.getVotesCount() > votes) {
					votes = option.getVotesCount();
				}
			}
			for (PollOptionsEntry option : list) {
				if (option.getVotesCount() == votes) {
					votesList.add(option);
				}
			}

			req.setAttribute("maxVotes", votesList);

			req.getRequestDispatcher("/WEB-INF/pages/votingResults.jsp").forward(req, resp);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorInvalid.jsp").forward(req, resp);
			return;
		}

	}
}
