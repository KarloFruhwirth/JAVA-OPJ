package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Creates an intern list of StudentRecord and a {@link SimpleHashtable} 
 * Provides method forJMBAG for fast retrieval of student records when jmbag is known
 * 
 * @author KarloFrühwirth
 *
 */
public class StudentDatabase {
	/**
	 * List of StudentRecord
	 */
	private List<StudentRecord> listOfStudents;
	/**
	 * SimpleHashtable with key Integer and value StudentRecord
	 */
	private SimpleHashtable<Integer, StudentRecord> tableOfStudents;
	
	
	/**
	 * Constructor for StudentDatabase
	 * @param listOfStudents list of strings
	 */
	public StudentDatabase(List<String> listOfStudents) {
		this.listOfStudents = toStudentRecordList(listOfStudents);
		this.tableOfStudents = toSimpleHashtable(this.listOfStudents);
	}
	
	/**
	 * Returns a student for jmbag
	 * @param jmbag string
	 * @return StudentRecord
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return tableOfStudents.get(Integer.parseInt(jmbag));
	}
	
	/**
	 * Filters the list of StudentRecord
	 * @param filter {@link IFilter}
	 * @return filtered list
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> list = new ArrayList<>();
		for(StudentRecord record : listOfStudents) {
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		return list;
	}


	/**
	 * Creates a SimpleHashtable based on a List<StudentRecord>
	 * @param listOfStudents list of StudentRecord
	 * @return SimpleHashtable
	 */
	private SimpleHashtable<Integer, StudentRecord> toSimpleHashtable(List<StudentRecord> listOfStudents) {
		SimpleHashtable<Integer, StudentRecord> hashtable = new SimpleHashtable<>(listOfStudents.size());
		for(StudentRecord record : listOfStudents) {
			hashtable.put(Integer.parseInt(record.getJmbag()), record);
		}
		return hashtable;
	}


	/**
	 * Converts a list of strings to a list of StudentRecord
	 * @param listOfStudents list of strings
	 * @return list of StudentRecord
	 */
	private List<StudentRecord> toStudentRecordList(List<String> listOfStudents) {
		List<StudentRecord> list = new ArrayList<>();
		for (String line : listOfStudents) {
			String[] elements = line.split("\t+");
			if(elements.length!=4) throw new IllegalArgumentException("Wrong number of elements for student...");
			String jmbag = elements[0];
			String lastName = elements[1];
			String firstName = elements[2];
			int finalGrade = Integer.valueOf(elements[3]);
			StudentRecord newRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			list.add(newRecord);
			
		}
		return list;
	}

	/**
	 * Getter for StudentRecord list
	 * @return listOfStudents 
	 */
	public List<StudentRecord> getListOfStudents() {
		return listOfStudents;
	}

}
