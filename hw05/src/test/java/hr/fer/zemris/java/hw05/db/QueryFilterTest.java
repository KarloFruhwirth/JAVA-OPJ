package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {

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
	public void TestFilterSize() {
		StudentDatabase dataBase = initilise();
		QueryParser parser = new QueryParser("query lastName LIKE \"B*\"");
		QueryFilter list = new QueryFilter(parser.getQuery());
		List<StudentRecord> filtered = dataBase.filter(list);
		int i = filtered.size();
		assertEquals(2, i);
	}
	
	@Test
	public void TestFilterEmpty() {
		StudentDatabase dataBase = initilise();
		QueryParser parser = new QueryParser("query lastName LIKE \"B*\" and jmbag>\"0000000009\"");
		QueryFilter list = new QueryFilter(parser.getQuery());
		List<StudentRecord> filtered = dataBase.filter(list);
		int i = filtered.size();
		assertEquals(0, i);
	}
	
	@Test
	public void TestFilterSame() {
		StudentDatabase dataBase = initilise();
		QueryParser parser = new QueryParser("query jmbag>\"0000000000\"");
		QueryFilter list = new QueryFilter(parser.getQuery());
		List<StudentRecord> filtered = dataBase.filter(list);
		int i = filtered.size();
		assertEquals(4, i);
	}
	
}
