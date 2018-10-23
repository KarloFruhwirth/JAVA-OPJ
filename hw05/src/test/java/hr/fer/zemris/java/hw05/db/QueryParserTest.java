package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueryParserTest {

	
	@Test
	public void TestIsDirectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\"");
		boolean check = qp1.isDirectQuery();
		assertEquals(true, check);
	}
	
	@Test
	public void TestGetQueriedJMBAG() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\"");
		String check = qp1.getQueriedJMBAG();
		assertEquals("0123456789", check);
	}
	
	@Test
	public void TestGetQueriedSize() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\"");
		int check = qp1.getQuery().size();
		assertEquals(1, check);
	}
	
	@Test
	public void TestIsDirectQueryFalse() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\"");
		boolean check = qp1.isDirectQuery();
		assertEquals(true, check);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestGetQueriedJMBAGnot() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"Js\"");
		String check = qp1.getQueriedJMBAG();
		assertEquals("0123456789", check);
	}

}
