package hr.fer.zemris.java.tecaj_13.util;
/**
 * Class that defines methods hextobyte and bytetohex
 * 
 * @author KarloFrühwirth
 *
 */
public class Util {

	/**
	 * Converts a string which is written in hex and returns a byte[]
	 * 
	 * @param hextext
	 *            String
	 * @return byte[]
	 */
	public static byte[] hextobyte(String hextext) {
		byte[] b = new byte[hextext.length() / 2];
		for (int i = 0, size = b.length; i < size; i++) {
			int index = i * 2;
			int v = Integer.parseInt(hextext.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

	/**
	 * Converts a byte[] array to a hex string
	 * 
	 * @param array
	 *            byte[]
	 * @return String
	 */
	public static String bytetohex(byte[] array) {
		StringBuilder sb = new StringBuilder(array.length * 2);
		for (byte b : array)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

}