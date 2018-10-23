package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionsEntry;

/**
 * Servlet used to get vote results in XLS format
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class VotingXLSServlet extends HttpServlet {

	/**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			OutputStream os = resp.getOutputStream();
			long id = Long.valueOf(req.getParameter("id"));
			PollOptionsEntry poe = DAOProvider.getDao().getPollOptionsEntry(id);
			long pollID = poe.getPollID();
			List<PollOptionsEntry> list = new ArrayList<>();
			for (PollOptionsEntry option : DAOProvider.getDao().getPollOptionsEntriesSorted()) {
				if (option.getPollID() == pollID) {
					list.add(option);
				}
			}
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
			try {
				resp.setContentType("application/vnd.ms-excel");
				resp.setHeader("Content-Disposition", "attachment; filename=voteResults_" + picks + ".xls");
				HSSFWorkbook hwb = createExclFile(list, picks);
				hwb.write(os);
				os.flush();
			} catch (Exception e) {
			}
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorInvalid.jsp").forward(req, resp);
			return;
		}

	}

	/**
	 * Creates HSSFWorkbook based on the List of PollOptionsEntry
	 * 
	 * @param list
	 *            List of BandResults
	 * @return HSSFWorkbook
	 */
	private HSSFWorkbook createExclFile(List<PollOptionsEntry> list, String picks) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Vote results");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue(picks);
		rowhead.createCell((short) 1).setCellValue("Votes");
		int i = 1;
		for (PollOptionsEntry poe : list) {
			HSSFRow row = sheet.createRow((short) i++);
			row.createCell((short) 0).setCellValue(poe.getOptionTitle());
			row.createCell((short) 1).setCellValue(poe.getVotesCount());
		}
		return hwb;
	}
}
