package hr.fer.zemris.java.webapps.galerija;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

/**
 * Class which uses Gson to return json's for specified cases
 * 
 * @author KarloFrühwirth
 *
 */
@Path("/picture")
public class PictureJSON {

	/**
	 * ServletContext used to get attributes set by {@link Inicijalizacija}
	 */
	@Context
	private ServletContext context;

	/**
	 * Method used to get tags
	 * 
	 * @return Response
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		@SuppressWarnings("unchecked")
		Set<String> tags = (Set<String>) context.getAttribute("tags");

		String[] array = new String[tags.size()];
		tags.toArray(array);

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		return Response.status(Status.OK).entity(jsonText).build();
	}

	/**
	 * Method used to get thumbnails for the selected tag
	 * 
	 * @param name
	 *            tag name
	 * @return Response
	 * @throws IOException
	 */
	@Path("{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThumbnails(@PathParam("name") String name) throws IOException {
		@SuppressWarnings("unchecked")
		Map<String, List<Picture>> map = (Map<String, List<Picture>>) context.getAttribute("map");
		List<Picture> list = map.get(name);
		String fileName = context.getRealPath("/WEB-INF/thumbnails");
		
		if(!Files.exists(Paths.get(fileName))) {
			new File(fileName).mkdir();
		}
		
		for (Picture pic : list) {
			if (!Files.exists(Paths.get(fileName + "/small-" + pic.getName()))) {
				System.out.println(fileName);
				thumbnail(pic.getName(), fileName);
			}
		}
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i).getName();
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		return Response.status(Status.OK).entity(jsonText).build();
	}

	/**
	 * Method used to create thumbnails
	 * 
	 * @param name
	 *            Picture name
	 * @param fileName
	 *            path to WEB-INF/thumbnails
	 * @throws IOException
	 */
	private void thumbnail(String name, String fileName) throws IOException {
		String origin = context.getRealPath("/WEB-INF/pictures");
		java.nio.file.Path src = Paths.get(origin, name);
		java.nio.file.Path dest = Paths.get(fileName, "small-" + name);
		BufferedImage image = null;
		image = ImageIO.read(new File(src.toString()));
		Image small = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		BufferedImage thumbnail = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = thumbnail.createGraphics();
		g.drawImage(small, 0, 0, null);
		g.dispose();
		System.out.println(dest.toFile());
		ImageIO.write(thumbnail, "jpg", dest.toFile());
	}

	/**
	 * Method used to get picture details
	 * 
	 * @param name
	 *            Picture name
	 * @return Response
	 */
	@Path("/details/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPictureDetails(@PathParam("name") String name) {
		@SuppressWarnings("unchecked")
		Map<String, List<Picture>> map = (Map<String, List<Picture>>) context.getAttribute("map");
		List<Picture> list = new ArrayList<>();
		for (List<Picture> p : map.values()) {
			list.addAll(p);
		}
		Picture pic = null;
		for (Picture picture : list) {
			if (picture.getName().equals(name)) {
				pic = picture;
			}
		}

		String[] array = new String[3];
		array[0] = pic.getName();
		array[1] = pic.getDescription();
		StringBuilder sb = new StringBuilder();
		String[] tags = pic.getTags();
		for (String s : tags) {
			sb.append(s + " ");
		}
		array[2] = sb.toString();

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		return Response.status(Status.OK).entity(jsonText).build();
	}

}
