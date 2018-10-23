package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConditionalExpressionTest {

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
	public void TestSatisfies() {
		StudentDatabase dataBase = initilise();
		StudentRecord r = dataBase.forJMBAG("0000000001");
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Akš*",
				 ComparisonOperators.LIKE
				);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldValue().get(r), 
				 expr.getString()
				);
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	public void TestDoesntSatisfies() {
		StudentDatabase dataBase = initilise();
		StudentRecord r = dataBase.forJMBAG("0000000001");
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Ar*",
				 ComparisonOperators.LIKE
				);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldValue().get(r), 
				 expr.getString()
				);
		assertEquals(false, recordSatisfies);
	}
		

}
