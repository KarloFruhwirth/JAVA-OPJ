package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void TestLESS() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		boolean satisfied = oper.satisfied("Ana", "Jasna");
		assertEquals(true, satisfied);
	}
	
	@Test
	public void TestLESSorEQUAL() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		boolean satisfied = oper.satisfied("Ana", "Jasna");
		assertEquals(true, satisfied);
	}
	
	
	@Test
	public void TestGREATER() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		boolean satisfied = oper.satisfied("Ana", "Jasna");
		assertEquals(false, satisfied);
	}
	
	@Test
	public void TestGREATERorEQUAL() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		boolean satisfied = oper.satisfied("Ana", "Jasna");
		assertEquals(false, satisfied);
	}

	@Test
	public void TestNotEQUAL() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		boolean satisfied = oper.satisfied("Ana", "Jasna");
		assertEquals(true, satisfied);
	}
	
	@Test
	public void TestEQUAL() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		boolean satisfied = oper.satisfied("Ana", "Jasna");
		assertEquals(false, satisfied);
	}
	
	@Test
	public void TestLIKE() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		boolean satisfied = oper.satisfied("Zagreb", "Aba*");
		assertEquals(false, satisfied);
		satisfied = oper.satisfied("AAA", "AA*AA");
		assertEquals(false, satisfied);
		satisfied = oper.satisfied("AAAA", "AA*AA");
		assertEquals(true, satisfied);
	}
}
