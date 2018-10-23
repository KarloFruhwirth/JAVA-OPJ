package hr.fer.zemris.java.cmdapps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class used to represent a single article Contains its title, article text and
 * its TF component which is created based on the word frequency within the
 * article which the vocabulary contains.
 * 
 * @author KarloFrühwirth
 *
 */
public class Article {
	/**
	 * Article title
	 */
	private String title;
	/**
	 * Article text
	 */
	private String text;
	/**
	 * TF component
	 */
	private List<Double> TFcomponent;

	/**
	 * Constructor for Article
	 * 
	 * @param title
	 *            title
	 * @param words
	 *            list of words
	 */
	public Article(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}

	/**
	 * Getter for title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for text
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for text
	 * 
	 * @param text
	 *            text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for TF component
	 * 
	 * @return TFcomponent
	 */
	public List<Double> getTFcomponent() {
		return TFcomponent;
	}

	/**
	 * Creates TFcomponent for provided Vocabulary
	 * 
	 * @param v
	 *            Vocabulary
	 */
	public void TFIDFvector(Vocabulary v) {
		Set<String> vocabularyWords = v.getWords();
		Map<String, Integer> articleWordFreqMeter = getWords(text);
		TFcomponent = new ArrayList<>(v.getSize());
		int index = 0;
		for (String s : vocabularyWords) {
			if (!articleWordFreqMeter.containsKey(s)) {
				TFcomponent.add(index, 0.0);
			} else {
				int freq = articleWordFreqMeter.get(s);
				int numOfArticles = v.getListOfArticles().size();
				int numOfArticlesContaining = v.getFrequency(s);
				double result = freq * Math.log(numOfArticles / numOfArticlesContaining);
				TFcomponent.add(index, result);
			}
			index++;
		}
	}

	/**
	 * Returns a map of word frequency
	 * 
	 * @param text
	 *            Text
	 * @return new Map
	 */
	private Map<String, Integer> getWords(String text) {
		Map<String, Integer> map = new HashMap<>();
		char[] array = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0, size = array.length; i < size; i++) {
			if (Character.isAlphabetic(array[i])) {
				sb.append(array[i]);
			} else {
				String word = sb.toString().toLowerCase();
				if (!map.containsKey(word)) {
					map.put(word, 1);
				} else {
					int value = map.get(word);
					value++;
					map.put(word, value);
				}
				sb.setLength(0);
			}
		}
		return map;
	}

}
