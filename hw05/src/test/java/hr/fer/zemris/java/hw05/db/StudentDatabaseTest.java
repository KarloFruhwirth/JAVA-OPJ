package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class StudentDatabaseTest {
	
	public StudentDatabase initiliseDB() {
		List<String> listOfStudents = new ArrayList<String>();
		listOfStudents.add("0000000001	Akšamović	Marin	2");
		listOfStudents.add("0000000002	Bakamović	Petra	3");
		listOfStudents.add("0000000003	Bosnić	Andrea	4");
		listOfStudents.add("0000000015	Glavinić Pecotić	Kristijan	4");
		StudentDatabase dataBase = new StudentDatabase(listOfStudents);
		return dataBase;
	}
	
	@Test
	public void TestForJMBAG() {
		StudentDatabase dataBase = initiliseDB();
		StudentRecord r = dataBase.forJMBAG("0000000001");
		StudentRecord r2 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals(r, r2);
	}
	
	@Test
	public void TestForFilterTrue() {
		StudentDatabase dataBase = initiliseDB();
		List<StudentRecord> rec = dataBase.filter(filter -> true);
		assertEquals(rec, dataBase.getListOfStudents());
	}
	
	@Test
	public void TestForFilterFalse() {
		StudentDatabase dataBase = initiliseDB();
		List<StudentRecord> rec = dataBase.filter(filter -> false);
		assertEquals(rec.size(), 0);
	}
}
