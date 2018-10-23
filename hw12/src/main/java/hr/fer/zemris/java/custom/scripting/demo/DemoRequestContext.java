package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo class representing the functionality of RequestContext, provided by
 * Marko Čupić
 * 
 * @author KarloFrühwirth
 *
 */
public class DemoRequestContext {

	/**
	 * @param args
	 *            not used for this class
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] args) throws IOException, IllegalAccessException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Creates file primjer1.txt
	 * 
	 * @param filePath
	 *            String representation of path to file
	 * @param encoding
	 *            encoding
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	private static void demo1(String filePath, String encoding) throws IOException, IllegalAccessException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Creates files primjer2.txt, primjer3.txt
	 * 
	 * @param filePath
	 *            String representation of path to file
	 * @param encoding
	 *            encoding
	 * @throws IOException
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/", false));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", false));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
