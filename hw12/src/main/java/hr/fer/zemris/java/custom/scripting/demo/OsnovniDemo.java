package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * 
 * Demo class representing the functionality of smart script osnovni
 * 
 * @author KarloFrühwirth
 *
 */
public class OsnovniDemo {
	/**
	 * @param args
	 *            not used for this class
	 * @throws IOException
	 *             in method readFile
	 */
	public static void main(String[] args) throws IOException {
		String documentBody = readFile("./scripts/osnovni.smscr", StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Method used to read file
	 * 
	 * @param path
	 *            path to file
	 * @param encoding
	 *            Charset
	 * @return String
	 * @throws IOException
	 *             if file can't be read
	 */
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
