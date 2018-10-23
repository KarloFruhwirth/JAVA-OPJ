package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demo class which using streams and functions filters the data of
 * {@link StudentRecord}s which are imported from the studenti.txt
 * 
 * @author KarloFrühwirth
 *
 */
public class StudentDemo {

	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		List<String> lines = loadFromFolder();
		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj + "\n");
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5 + "\n");

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		for (StudentRecord s : odlikasi) {
			System.out.println(s);
		}
		System.out.println();

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		for (StudentRecord s : odlikasiSortirano) {
			System.out.println(s);
		}
		System.out.println();

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		for (String s : nepolozeniJMBAGovi) {
			System.out.println(s);
		}
		System.out.println();

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		for (Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
			System.out.print("grade : " + entry.getKey() + "{\n");
			for (StudentRecord record : entry.getValue()) {
				System.out.println(record);
			}
			System.out.print("}");
		}
		System.out.println();
		System.out.println();

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		for (Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
		}
		System.out.println();

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		for (Map.Entry<Boolean, List<StudentRecord>> entry : prolazNeprolaz.entrySet()) {
			System.out.print(entry.getKey() + "{\n");
			for (StudentRecord record : entry.getValue()) {
				System.out.println(record);
			}
			System.out.print("}");
		}
	}

	/**
	 * Method which sorts out the records by determining if a Student has passed or
	 * failed
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return Map<Boolean, List<StudentRecord>>
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		Map<Boolean, List<StudentRecord>> map = records.stream()
				.collect(Collectors.partitioningBy(s -> s.getGrade() > 1));
		return map;
	}

	/**
	 * Method that maps number Students with same grades
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return Map<Integer, Integer>
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		Map<Integer, Integer> map = records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, s -> 1, Integer::sum));
		return map;
	}

	/**
	 * Method that maps Students by grades
	 * 
	 * @param records
	 *            List<StudentRecord>>
	 * @return Map<Integer, List<StudentRecord>>
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		Map<Integer, List<StudentRecord>> map = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
		return map;
	}

	/**
	 * Returns a list of students that have failed
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return List<String>
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		List<String> list = records.stream()
				.filter(s -> s.getGrade() == 1)
				.map(s -> s.getJmbag())
				.collect(Collectors.toList());
		return list;
	}

	/**
	 * Returns a sorted list by number of points of students that have their grade=5
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return List<StudentRecord>
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> record = records.stream()
				.filter(s -> s.getGrade() == 5)
				.sorted(Comparator.comparing(StudentRecord::getPoints).reversed())
				.collect(Collectors.toList());
		return record;
	}

	/**
	 * Returns a list of students that have their grade=5
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return List<StudentRecord>
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> record = records.stream()
				.filter(s -> s.getGrade() == 5)
				.collect(Collectors.toList());
		return record;
	}

	/**
	 * Returns the number of students with the grade 5
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return number of students
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj = records.stream()
				.filter(s -> s.getGrade() == 5)
				.count();
		return broj;
	}

	/**
	 * Returns the number of students with a total number of points > 25
	 * 
	 * @param records
	 *            List<StudentRecord>
	 * @return number of students
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream()
				.filter(s -> s.getPointsMI() + s.getPointsZI() + s.getPointsLAB() > 25)
				.count();
		return broj;
	}

	/**
	 * Converts a list of Strings from the studenti.txt to list of
	 * {@link StudentRecord}s
	 * 
	 * @param lines
	 *            List<String> studenti.txt
	 * @return List<StudentRecord>
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> studentRecords = new ArrayList<>();
		for (String s : lines) {
			String[] elements = s.split("\\t+");
			String jmbag = elements[0];
			String lastName = elements[1];
			String firstName = elements[2];
			double pointsMI = Double.parseDouble(elements[3]);
			double pointsZI = Double.parseDouble(elements[4]);
			double pointsLAB = Double.parseDouble(elements[5]);
			int grade = Integer.valueOf(elements[6]);
			StudentRecord newRecord = new StudentRecord(jmbag, lastName, firstName, pointsMI, pointsZI, pointsLAB,
					grade);
			studentRecords.add(newRecord);
		}
		return studentRecords;
	}

	/**
	 * Method used to load data from txt folder
	 * 
	 * @return List<String>
	 */
	private static List<String> loadFromFolder() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./src/main/resources/studenti.txt"),
					StandardCharsets.UTF_8);
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
