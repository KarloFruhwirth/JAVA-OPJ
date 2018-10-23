package hr.fer.zemris.java.cmdapps;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main class which is used to find the 10 most similar articles based on TF-IDF
 * criteria<br>
 * For the program to run as expected it is required to provided a path to a
 * folder containing the articles based on which a vocabulary is then
 * created.<br>
 * The program communicates with the user through the console.<br>
 * Supported commands are: query,type,results,exit.<br>
 * For further info about the commands and features regarding this program see
 * {@link <a href=
 * "https://ferko.fer.hr/ferko/DownloadFile.action?courseInstanceID=2017L%2F38047&fileID=854">Link</a>}
 * 
 * @author KarloFrühwirth
 *
 */
public class Konzola {

	private static final String STOPWORD_FILE_PATH = ".\\src\\main\\resources\\hrvatski_stoprijeci.txt";

	/**
	 * String path
	 */
	private static String path;

	/**
	 * Vocabulary
	 */
	private static Vocabulary vocabulary;

	/**
	 * Map of similarities between articles
	 */
	private static Map<Article, Double> similarity = new HashMap<>();

	/**
	 * Main method for this program
	 * 
	 * @param args
	 *            used to set path to folder with articles->
	 *            ./src/main/resources/clanci
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Please provide an argument representing a path to a folder containing articles");
			System.exit(1);
		} else if (!Files.isDirectory(Paths.get(args[0]))) {
			System.out.println("Please provide an argument representing a path to a folder containing articles");
			System.exit(1);
		}

		path = args[0];
		Scanner sc = new Scanner(System.in);
		vocabulary = new Vocabulary(path, STOPWORD_FILE_PATH);
		System.out.printf("Vocabulary contains %d words\n", vocabulary.getSize());
		while (true) {
			System.out.print("Enter command > ");
			String input = sc.nextLine().trim();
			if (input.equals("exit")) {
				break;
			} else if (input.startsWith("query")) {
				queryCommand(input.substring(5));
			} else if (input.startsWith("type")) {
				typeCommand(input.substring(4));
			} else if (input.equals("results")) {
				resultsCommand();
			} else {
				System.out.print("Unknown command.\n");
			}
		}
		sc.close();
		System.out.println("Goodbye");
	}

	/**
	 * Result command
	 */
	private static void resultsCommand() {
		int iter = 0;
		for (Map.Entry<Article, Double> d : similarity.entrySet()) {
			if (d.getValue() > 0) {
				System.out.printf("[%02d] (%.4f) %s\n", iter++, d.getValue(),
						Paths.get(path).toAbsolutePath().normalize() + "\\" + d.getKey().getTitle());
			}
		}
	}

	/**
	 * Type command
	 * 
	 * @param input
	 *            index
	 */
	private static void typeCommand(String input) {
		if (similarity.size() == 0) {
			System.out.println("No query results...");
			return;
		}
		if (input.trim().equals("") && similarity.size() > 0) {
			System.out.println("No type provided");
			return;
		}
		input = input.trim();
		try {
			int index = Integer.valueOf(input);
			int i = 0;
			for (Map.Entry<Article, Double> d : similarity.entrySet()) {
				if (index == i) {
					System.out.printf("Article: %s\n\n",
							Paths.get(path).toAbsolutePath().normalize() + "\\" + d.getKey().getTitle());
					System.out.println(d.getKey().getText());
					return;
				}
				i++;
			}
			System.out.println("Unsuported query result index");
		} catch (NumberFormatException e) {
			System.out.println("Type comand expects a number as the second argument!");
			return;
		}

	}

	/**
	 * @param input
	 *            words
	 */
	private static void queryCommand(String input) {
		if (input.trim().equals("")) {
			System.out.println("Empty query provided");
			return;
		}
		input = input.trim();
		String[] words = input.toLowerCase().split("\\s+");
		StringBuilder sb = new StringBuilder();
		for (String s : words) {
			if (vocabulary.getWords().contains(s)) {
				sb.append(s + " ");
			}
		}
		String text = sb.toString();
		System.out.printf("Query is: [%s]\n", text);
		Article article = new Article(null, text);
		article.TFIDFvector(vocabulary);
		for (Article a : vocabulary.getListOfArticles()) {
			a.TFIDFvector(vocabulary);
			double sim = cosine(article.getTFcomponent(), a.getTFcomponent());
			if (sim > 0) {
				similarity.put(a, sim);
			}
		}
		Map<Article, Double> sortedMap = similarity.entrySet().stream()
				.sorted(Entry.<Article, Double>comparingByValue().reversed()).limit(10)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		similarity = sortedMap;
		int iter = 0;
		for (Map.Entry<Article, Double> d : similarity.entrySet()) {
			if (d.getValue() > 0) {
				System.out.printf("[%02d] (%.4f) %s\n", iter++, d.getValue(),
						Paths.get(path).toAbsolutePath().normalize() + "\\" + d.getKey().getTitle());
			}
		}
	}

	/**
	 * Calculates cosine between vectors
	 * 
	 * @param v1
	 *            Vector1
	 * @param v2
	 *            Vector2
	 * @return angle
	 */
	private static double cosine(List<Double> v1, List<Double> v2) {
		double dot = calculateDot(v1, v2);
		double norm1 = norm(v1);
		double norm2 = norm(v2);
		try {
			double result = dot / (norm1 * norm2);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	private static double norm(List<Double> v1) {
		double sum = 0.0;
		for (int i = 0; i < v1.size(); i++) {
			sum += v1.get(i) * v1.get(i);
		}
		return Math.sqrt(sum);
	}

	private static double calculateDot(List<Double> v1, List<Double> v2) {
		double sum = 0.0;
		for (int i = 0; i < v1.size(); i++) {
			sum += v1.get(i) * v2.get(i);
		}
		return sum;
	}

}
