package hr.fer.zemris.java.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionsEntry;

/**
 * Servlet used to create pie chart for the voting results
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class VotingPieChartServlet extends HttpServlet {
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream os = resp.getOutputStream();
		try {
			long id = Long.valueOf(req.getParameter("id"));
			PollOptionsEntry poe = DAOProvider.getDao().getPollOptionsEntry(id);
			long pollID = poe.getPollID();
			List<PollOptionsEntry> list = new ArrayList<>();
			for (PollOptionsEntry option : DAOProvider.getDao().getPollOptionsEntriesSorted()) {
				if (option.getPollID() == pollID) {
					list.add(option);
				}
			}
			try {
				resp.setContentType("image/png");
				JFreeChart chart = createChart(list);
				BufferedImage objBufferedImage = chart.createBufferedImage(500, 270);
				ImageIO.write(objBufferedImage, "png", os);
				os.flush();
			} catch (Exception e) {
			}
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorInvalid.jsp").forward(req, resp);
			return;
		}
	}

	/**
	 * Method used to create JFreeChart
	 * 
	 * @param list
	 *            List<BandResults>
	 * @return JFreeChart
	 */
	private JFreeChart createChart(List<PollOptionsEntry> list) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (PollOptionsEntry poe : list) {
			if (poe.getVotesCount() > 0) {
				result.setValue(poe.getOptionTitle(), poe.getVotesCount());
			}
		}
		JFreeChart chart = ChartFactory.createPieChart3D("", result);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		return chart;
	}
}
