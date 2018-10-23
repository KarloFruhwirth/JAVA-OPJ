package hr.fer.zemris.java.webapps.galerija;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletContextListener used to initialize the context and using its methods
 * creates the tables and fills them with data.
 * 
 * @author KarloFrühwirth
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String fileName = sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt");
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> tags = setTags(lines);
		sce.getServletContext().setAttribute("tags", tags);
		List<Picture> pictures = setPictures(lines);
		Map<String, List<Picture>> picturesForTag = createPicturesMap(pictures, tags);
		sce.getServletContext().setAttribute("map", picturesForTag);
	}

	/**
	 * Method used to create a map which for the given tag has a list of pictures
	 * with that tag
	 * 
	 * @param pictures
	 *            list of pictures
	 * @param tags
	 *            tags set
	 * @return map
	 */
	private Map<String, List<Picture>> createPicturesMap(List<Picture> pictures, Set<String> tags) {
		Map<String, List<Picture>> map = new HashMap<>();
		for (String s : tags) {
			List<Picture> list = new ArrayList<>();
			for (Picture pic : pictures) {
				for (String tag : pic.getTags()) {
					if (s.equals(tag.trim())) {
						list.add(pic);
					}
				}
			}
			map.put(s, list);
		}
		return map;
	}

	/**
	 * Method used to create a list of pictures based on the opisnik.txt file
	 * 
	 * @param lines
	 *            lines from opisnik.txt
	 * @return list of {@link Picture}s
	 */
	private List<Picture> setPictures(List<String> lines) {
		List<Picture> list = new ArrayList<>();
		for (int i = 0, size = lines.size(); i + 2 < size; i += 3) {
			String name = lines.get(i);
			String description = lines.get(i + 1);
			String[] tags = lines.get(i + 2).split(",");
			String[] tagsTrimed = new String[tags.length];
			for (int j = 0; j < tags.length; j++) {
				tagsTrimed[j] = tags[j].trim();
			}
			list.add(new Picture(name, description, tags));
		}
		return list;
	}

	/**
	 * Method used to get the different tags from the opisnik.txt file
	 * 
	 * @param lines
	 *            lines from opisnik.txt
	 * @return set of tags
	 */
	private Set<String> setTags(List<String> lines) {
		Set<String> set = new HashSet<>();
		for (int i = 2, size = lines.size(); i < size; i += 3) {
			String[] tags = lines.get(i).split(",");
			for (String s : tags) {
				set.add(s.trim());
			}
		}
		return set;
	}

}
