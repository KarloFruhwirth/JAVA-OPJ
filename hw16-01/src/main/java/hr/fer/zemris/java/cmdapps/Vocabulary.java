package hr.fer.zemris.java.cmdapps;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Creates a vocabulary based on the articles provided. Counts different words
 * that are not stopWords. Similarity is calculated based on the vocabulary.
 * 
 * @author KarloFrühwirth
 *
 */
public class Vocabulary {
	/**
	 * Word set
	 */
	private Set<String> words;
	/**
	 * Stop word set
	 */
	private Set<String> stopWords;
	/**
	 * Size
	 */
	private int size;
	/**
	 * List of articles
	 */
	private List<Article> listOfArticles;
	/**
	 * IDF
	 */
	private Map<String, Integer> idf;

	/**
	 * Constructor for Vocabulary
	 * 
	 * @param path
	 *            path to folder containing articles
	 * @param stopwordFilePath
	 *            path to stopWords file
	 */
	public Vocabulary(String path, String stopwordFilePath) {
		stopWords = readStopFile(stopwordFilePath);
		listOfArticles = addArticles(path);
		words = readArticles(listOfArticles);
		idf = IDFcomponent(listOfArticles);
		size = words.size();
	}

	/**
	 * Creates IDF map
	 * 
	 * @param listOfArticles
	 *            listOfArticles
	 * @return map of IDFs
	 */
	private Map<String, Integer> IDFcomponent(List<Article> listOfArticles) {
		Map<String, Integer> map = new HashMap<>();
		for (Article ar : listOfArticles) {
			Set<String> articleWords = addWords(ar.getText());
			for (String s : articleWords) {
				if (!map.containsKey(s)) {
					map.put(s, 1);
				} else {
					int value = map.get(s);
					value++;
					map.put(s, value);
				}
			}
		}
		return map;
	}

	/**
	 * Fills stopWords from file
	 * 
	 * @param stopwordFilePath
	 *            path to stopWords file
	 * @return set
	 */
	private Set<String> readStopFile(String stopwordFilePath) {
		Set<String> stopWords = new LinkedHashSet<>();
		try (Stream<String> stream = Files.lines(Paths.get(stopwordFilePath))) {
			stream.forEach(k -> stopWords.add(k));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return stopWords;
	}

	/**
	 * Fills words based on articles provided
	 * 
	 * @param listOfArticles
	 *            listOfArticles
	 * @return set
	 */
	private Set<String> readArticles(List<Article> listOfArticles) {
		Set<String> words = new HashSet<>();
		for (Article ar : listOfArticles) {
			Set<String> articleWords = addWords(ar.getText());
			words.addAll(articleWords);
		}
		return words;
	}

	/**
	 * Adds words to set of an article
	 * 
	 * @param text
	 *            Text
	 * @return set
	 */
	private Set<String> addWords(String text) {
		Set<String> set = new HashSet<>();
		char[] array = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0, size = array.length; i < size; i++) {
			if (Character.isAlphabetic(array[i])) {
				sb.append(array[i]);
			} else {
				String word = sb.toString().toLowerCase();
				if (!stopWords.contains(word)) {
					set.add(word);
				}
				sb.setLength(0);
			}
		}
		return set;
	}

	/**
	 * Adds articles to the list of articles
	 * 
	 * @param path
	 *            path of folder containing articles
	 * @return list of articles
	 */
	private List<Article> addArticles(String path) {
		List<Article> list = new ArrayList<>();
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				String words;
				try {
					words = readFile(file, StandardCharsets.UTF_8);
					Article ar = new Article(file.getName(), words);
					list.add(ar);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * Getter for words
	 * 
	 * @return words
	 */
	public Set<String> getWords() {
		return words;
	}

	/**
	 * Getter for stopWords
	 * 
	 * @return stopWords
	 */
	public Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * Getter for size
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Getter for listOfArticles
	 * 
	 * @return listOfArticles
	 */
	public List<Article> getListOfArticles() {
		return listOfArticles;
	}

	/**
	 * Getter for idf(w,d)
	 * 
	 * @param word
	 *            Word
	 * @return frequency
	 */
	public int getFrequency(String word) {
		return idf.get(word);
	}

	/**
	 * Method used to read file
	 * 
	 * @param file
	 *            File
	 * @param encoding
	 *            Charset
	 * @return String representation of text
	 * @throws IOException
	 */
	static String readFile(File file, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded, encoding);
	}

}
