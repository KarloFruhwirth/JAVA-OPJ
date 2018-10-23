package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FieldValueGettersTest {

	public StudentDatabase initilise() {
		List<String> listOfStudents = new ArrayList<String>();
		listOfStudents.add("0000000001	Akšamović	Marin	2");
		listOfStudents.add("0000000002	Bakamović	Petra	3");
		listOfStudents.add("0000000003	Bosnić	Andrea	4");
		listOfStudents.add("0000000015	Glavinić Pecotić	Kristijan	4");
		StudentDatabase dataBase = new StudentDatabase(listOfStudents);
		return dataBase;
	}
	
	@Test
	public void TestFIRST_NAME() {
		StudentDatabase dataBase = initilise();
		StudentRecord r = dataBase.forJMBAG("0000000001");
		String check = FieldValueGetters.FIRST_NAME.get(r);
		assertEquals("Marin", check);
	}
	
	@Test
	public void TestLAST_NAME() {
		StudentDatabase dataBase = initilise();
		StudentRecord r = dataBase.forJMBAG("0000000001");
		String check = FieldValueGetters.LAST_NAME.get(r);
		assertEquals("Akšamović", check);
	}
	
	@Test
	public void TestJMBAG() {
		StudentDatabase dataBase = initilise();
		StudentRecord r = dataBase.forJMBAG("0000000001");
		String check = FieldValueGetters.JMBAG.get(r);
		assertEquals("0000000001", check);
	}

}
