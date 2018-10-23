package hr.fer.zemris.java.hw07.crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import static hr.fer.zemris.java.hw07.crypto.Util.hextobyte;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	/**
	 * Digest to compare to the result
	 */
	public final static String DIGEST = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
	/**
	 * Buffer size
	 */
	public final static int BUFFER = 4096;

	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		if (checkArguments(args) == 0) {
			System.out.println("Dragi korisnice argumenti predani nisu podrzani...");
			return;
		} else if (checkArguments(args) == 1) {
			checkSha(args[1]);
		} else {
			encription_decription(args[0], args[1], args[2]);
		}

	}

	/**
	 * Method used for encription_decription of the string
	 * 
	 * @param mode
	 *            Mode
	 * @param source
	 *            src
	 * @param destination
	 *            dest
	 */
	private static void encription_decription(String mode, String source, String destination) {
		boolean encrypt = false;
		if (mode.equals("encrypt"))
			encrypt = true;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();
		sc.close();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			File inputFile = new File("./src/main/resources", source);
			inputFile.createNewFile();
			File outputFile = new File("./src/main/resources", destination);
			outputFile.createNewFile();
			Path sourcePath = Paths.get(inputFile.getAbsolutePath());
			Path destPath = Paths.get(outputFile.getAbsolutePath());
			try (InputStream is = Files.newInputStream(sourcePath, StandardOpenOption.READ);
					OutputStream os = Files.newOutputStream(destPath, StandardOpenOption.WRITE)) {
				byte[] buff = new byte[BUFFER];
				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					byte[] modified = cipher.update(buff, 0, r);
					os.write(modified);
				}
				os.write(cipher.doFinal());
			} catch (IOException | IllegalBlockSizeException | BadPaddingException ex) {
				ex.printStackTrace();
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IOException e) {
			e.printStackTrace();
		}
		System.out.printf((encrypt ? "Encryption" : "Decryption") + " completed. Generated file %s based on file %s.",
				source, destination);

	}

	/**
	 * Output for the checks SHA
	 * 
	 * @param arg
	 *            String
	 */
	private static void checkSha(String arg) {
		System.out.println("Please provide expected sha-256 digest for hw07test.bin:");
		System.out.print("> ");
		Scanner sc = new Scanner(System.in);
		String expectedDigest = sc.nextLine();
		sc.close();
		if (checksha().equals(expectedDigest)) {
			System.out.printf("Digesting completed. Digest of %s matches expected digest.\n", arg);
		} else {
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest. \nDigest was: %s",
					arg, DIGEST);
		}
	}

	/**
	 * Checks SHA for the hw07test.bin
	 */
	private static String checksha() {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(Files.readAllBytes(Paths.get("./src/main/resources/hw07test.bin")));
			byte[] result = messageDigest.digest();
			return Util.bytetohex(result);
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that checks arguments provided and determines which method is used
	 * 
	 * @param args
	 *            String[] args
	 * @return 0 if wrong number of arguments <br>
	 *         1 if checksha <br>
	 *         2 if encrypt/decrypt
	 */
	private static int checkArguments(String[] args) {
		if (args.length < 2 || args.length > 3)
			return 0;
		else if (args.length == 2) {
			File file = new File("./src/main/resources", args[1]);
			if (args[0].equals("checksha") && file.exists()) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if ((args[0].equals("encrypt") || args[0].equals("decrypt"))) {
				return 2;
			} else {
				return 0;
			}
		}
	}

}
