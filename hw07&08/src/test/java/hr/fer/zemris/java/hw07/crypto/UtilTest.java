package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class UtilTest {
	
	@Test
	public void TestHextobyte() {
		byte[] rez = Util.hextobyte("01aE22");
		byte[] expected = {1, -82, 34};
		assertEquals(rez[0], expected[0]);
		assertEquals(rez[1], expected[1]);
		assertEquals(rez[2], expected[2]);
	}
	@Test
	public void TestBytetoHex() {
		String rez = Util.bytetohex(new byte[] {1, -82, 34});
		String expected = "01ae22";
		assertEquals(rez, expected);
	}

}
