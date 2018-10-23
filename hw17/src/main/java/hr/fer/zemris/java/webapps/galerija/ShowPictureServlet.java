package hr.fer.zemris.java.webapps.galerija;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to display full sized image
 * 
 * @author KarloFrühwirth
 *
 */
@WebServlet(urlPatterns = { "/servlets/full" })
public class ShowPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pic = req.getParameter("id");
		String srcPath = req.getServletContext().getRealPath("/WEB-INF/pictures");
		Path src = Paths.get(srcPath, pic);
		BufferedImage image = null;
		image = ImageIO.read(new File(src.toString()));
		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "png", os);
		os.flush();
	}

}
