package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class used to demonstrate the functionality of the StudentDatabase and
 * its methods and other classes from this homework. User is required to enter
 * in the standard input through the console a query Here are several legal
 * examples of the this command. query jmbag="0000000003" query lastName =
 * "Blažić" query firstName>"A" and lastName LIKE "B*ć"
 * 
 * @author KarloFrühwirth
 *
 */
public class StudentDB {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<String> listOfStudents = loadFromFolder();
		StudentDatabase dataBase = new StudentDatabase(listOfStudents);

		while (true) {
			System.out.print("> ");
			String input = sc.nextLine();
			if (input.equals("exit"))
				break;

			QueryParser parser = new QueryParser(input);
			QueryFilter list = new QueryFilter(parser.getQuery());
			if (parser.isDirectQuery()) {
				List<StudentRecord> filtered = new ArrayList<>(); // tu sam imao napisano = null te je zbog toga padalo
																	// kod uvjeta minimalne implementacija
				filtered.add(dataBase.forJMBAG(parser.getQueriedJMBAG()));
				System.out.println("Using index for record retrieval.");
				printLine(filtered);
				StudentRecord r = dataBase.forJMBAG(parser.getQueriedJMBAG());
				System.out.println("| " + r.getJmbag() + " | " + r.getLastName()
						+ wightspacesRemaining(r.getLastName().length(), longestLastName(filtered)) + " | "
						+ r.getFirstName() + wightspacesRemaining(r.getFirstName().length(), longestFirstName(filtered))
						+ " | " + r.getFinalGrade() + " |");
				printLine(filtered);
				System.out.println("Records selected: " + filtered.size());
			} else {
				List<StudentRecord> filtered = dataBase.filter(list);
				if (filtered.size() > 0) {
					printLine(filtered);
					for (StudentRecord r : filtered) {
						System.out.println("| " + r.getJmbag() + " | " + r.getLastName()
								+ wightspacesRemaining(r.getLastName().length(), longestLastName(filtered)) + " | "
								+ r.getFirstName()
								+ wightspacesRemaining(r.getFirstName().length(), longestFirstName(filtered)) + " | "
								+ r.getFinalGrade() + " |");
					}
					printLine(filtered);
				}
				System.out.println("Records selected: " + filtered.size());
			}
		}
		System.out.println("Goodbye!");
		sc.close();
		System.exit(0);
	}

	/**
	 * Used to determine how many spaces are needed after a firstName or lastName so
	 * that the final print looks pretty
	 * 
	 * @param length
	 *            length of firstName or lastName
	 * @param longest
	 *            length of longest firstName or lastName
	 * @return
	 */
	private static String wightspacesRemaining(int length, int longest) {
		StringBuilder sb = new StringBuilder();
		while (length < longest) {
			sb.append(" ");
			length++;
		}
		return sb.toString();
	}

	/**
	 * Method used to print out the outer area of the "table" based on the data
	 * provided
	 * 
	 * @param filtered
	 *            list of StudentRecord that will be printed
	 */
	private static void printLine(List<StudentRecord> filtered) {
		int longestFirstName = longestFirstName(filtered);
		int longestLastName = longestLastName(filtered);
		StringBuilder sb = new StringBuilder();
		sb.append("+============+=");
		for (int i = 0; i < longestLastName; i++) {
			sb.append("=");
		}
		sb.append("=+=");
		for (int i = 0; i < longestFirstName; i++) {
			sb.append("=");
		}
		sb.append("=+===+");
		System.out.println(sb.toString());
	}

	/**
	 * Method to determine the longestLastName
	 * 
	 * @param filtered
	 *            list of StudentRecord that will be printed
	 * @return longestLastName
	 */
	private static int longestLastName(List<StudentRecord> filtered) {
		int lenght = 0;
		for (StudentRecord record : filtered) {
			if (record.getLastName().length() > lenght) {
				lenght = record.getLastName().length();
			}
		}
		return lenght;
	}

	/**
	 * Method to determine the longestFirstName
	 * 
	 * @param filtered
	 *            list of StudentRecord that will be printed
	 * @return longestFirstName
	 */
	private static int longestFirstName(List<StudentRecord> filtered) {
		int lenght = 0;
		for (StudentRecord record : filtered) {
			if (record.getFirstName().length() > lenght) {
				lenght = record.getFirstName().length();
			}
		}
		return lenght;
	}

	/**
	 * Method used to load data from txt folder
	 * 
	 * @return
	 */
	private static List<String> loadFromFolder() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./src/main/resources/database.txt"),
					StandardCharsets.UTF_8);
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
