package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestContext {
	/**
	 * OutputStream
	 */
	private OutputStream outputStream;
	/**
	 * Charset
	 */
	private Charset charset;
	/**
	 * encoding, default set to UTF-8
	 */
	public String encoding = "UTF-8";
	/**
	 * statusCode, default set to 200
	 */
	public int statusCode = 200;
	/**
	 * statusText, default set to OK
	 */
	public String statusText = "OK";

	/**
	 * mimeType, default set to text/html
	 */
	public String mimeType = "text/html";

	/**
	 * headerGenerated,default set to false
	 */
	private boolean headerGenerated = false;

	/**
	 * Map of parameters, name,parameter
	 */
	private Map<String, String> parameters; // if null, treat as empty

	/**
	 * Map of temporaryParameters, name,temporaryParameter
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * Map of persistentParameters, name,persistentParameter
	 */
	private Map<String, String> persistentParameters; // if null, treat as empty

	/**
	 * List of RCCookie
	 */
	private List<RCCookie> outputCookies; // if null, treat as empty

	/**
	 * IDispatcher
	 */
	private IDispatcher dispatcher;

	/**
	 * Constructor for RequestContext
	 * 
	 * @param outputStream
	 *            OutputStream
	 * @param parameters
	 *            Map<String, String>
	 * @param persistentParameters
	 *            Map<String, String>
	 * @param outputCookies
	 *            List<RCCookie>
	 * @throws IllegalArgumentException
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) throws IllegalArgumentException {
		this(outputStream, parameters, persistentParameters, outputCookies, new HashMap<String, String>(), null);
	}

	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) throws IllegalArgumentException {
		if (outputStream == null) {
			throw new IllegalArgumentException("OutputStream mustn't be null!");
		}
		this.outputStream = outputStream;
		this.parameters = parameters != null ? parameters : new HashMap<String, String>();
		this.persistentParameters = persistentParameters != null ? persistentParameters : new HashMap<String, String>();
		this.outputCookies = outputCookies != null ? outputCookies : new ArrayList<>();
		this.temporaryParameters = temporaryParameters != null ? temporaryParameters : new HashMap<String, String>();
		this.dispatcher = dispatcher;
	}

	/**
	 * Setter for encoding
	 * 
	 * @param encoding
	 *            encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated == true)
			throw new RuntimeException("Header is generated cant change encoding!");
		this.encoding = encoding;
	}

	/**
	 * Setter for statusCode
	 * 
	 * @param statusCode
	 *            statusCode
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated == true)
			throw new RuntimeException("Header is generated cant change status code!");
		this.statusCode = statusCode;
	}

	/**
	 * Setter for statusText
	 * 
	 * @param statusText
	 *            statusText
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated == true)
			throw new RuntimeException("Header is generated cant change status text!");
		this.statusText = statusText;
	}

	/**
	 * Setter for mimeType
	 * 
	 * @param mimeType
	 *            mimeType
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated == true)
			throw new RuntimeException("Header is generated cant change mime type!");
		this.mimeType = mimeType;
	}

	/**
	 * Retrieves value from parameters map (or null if no association exists)
	 * 
	 * @param name
	 *            name
	 * @return parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in parameters map
	 * 
	 * @return read only set of parameters
	 */
	public Set<String> getParameterNames() {
		return parameterSet(parameters);
	}

	/**
	 * Retrieves value from persistentParameters map (or null if no association
	 * exists):
	 * 
	 * @param name
	 *            name
	 * @return persistentParameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in persistent parameters map
	 * 
	 * @return read only set of persistentParameter
	 */
	public Set<String> getPersistentParameterNames() {
		return parameterSet(persistentParameters);
	}

	/**
	 * Stores a value to persistentParameters map
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes a value from persistentParameters map:
	 * 
	 * @param name
	 *            name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Retrieves value from temporaryParameters map (or null if no association
	 * exists):
	 * 
	 * @param name
	 *            name
	 * @return TemporaryParameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in temporary parameters map
	 * 
	 * @return read only set of temporaryParameters
	 */
	public Set<String> getTemporaryParameterNames() {
		return parameterSet(temporaryParameters);
	}

	/**
	 * Retrieves value from parameters map (or null if no association exists)
	 * 
	 * @param name
	 *            name
	 * @return parameter
	 */
	public IDispatcher getIDispatcher() {
		return dispatcher;
	}

	/**
	 * Stores a value to temporaryParameters map
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes a value from temporaryParameters map
	 * 
	 * @param name
	 *            name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Writes data to outputStream
	 * 
	 * @param data
	 *            byte[]
	 * @return RequestContext
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (headerGenerated == false) {
			generateHeader();
		}
		try {
			outputStream.write(data, 0, data.length);
		} catch (IOException ignorable) {
			// TODO: handle exception
		}
		return this;
	}

	/**
	 * Writes string content to outputStream
	 * 
	 * @param text
	 *            String
	 * @return RequestContext
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if (headerGenerated == false) {
			generateHeader();
		}
		try {
			byte[] data = text.getBytes(charset);
			outputStream.write(data, 0, data.length);
		} catch (IOException ignorable) {
			// TODO: handle exception
		}
		return this;
	}

	/**
	 * Generates header
	 * 
	 * @throws IOException
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		headerGenerated = true;
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (outputCookies.size() != 0) {
			for (RCCookie cookie : outputCookies) {
				sb.append(setCookie(cookie));
			}
		}
		sb.append("\r\n");
		write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
	}

	/**
	 * Line emitted for each cookie
	 * 
	 * @param cookie
	 *            RCCookie
	 * @return string
	 */
	private String setCookie(RCCookie cookie) {
		StringBuilder sb = new StringBuilder();
		sb.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"");
		if (cookie.getDomain() != null) {
			sb.append("; Domain=" + cookie.getDomain());
		}
		if (cookie.getPath() != null) {
			sb.append("; Path=" + cookie.getPath());
		}
		if (cookie.getMaxAge() != null) {
			sb.append("; Max-Age=" + cookie.getMaxAge());
		}
		if (cookie.getHttpOnly() == true) {
			sb.append("; HttpOnly");
		}
		sb.append("\r\n");
		return sb.toString();
	}

	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}

	/**
	 * Helper method used to retrieve a set of parameters for the given map
	 * 
	 * @param parameters
	 *            Map<String, String>
	 * @return Set<String>
	 */
	private Set<String> parameterSet(Map<String, String> parameters) {
		Set<String> set = new HashSet<>();
		for (String key : parameters.keySet()) {
			set.add(key);
		}
		set = Collections.unmodifiableSet(set);
		return set;
	}

	/**
	 * Static class which represents the cookie we use to store temporary data <br>
	 * All the RCCookie are added to outputCookies and then used for RequestContext
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	public static class RCCookie {
		/**
		 * Name
		 */
		private String name;
		/**
		 * Value
		 */
		private String value;
		/**
		 * Domain
		 */
		private String domain;
		/**
		 * Path
		 */
		private String path;
		/**
		 * Max age
		 */
		private Integer maxAge;

		/**
		 * httpOnly
		 */
		private boolean httpOnly;

		/**
		 * Constructor for RCCookie
		 * 
		 * @param name
		 *            name
		 * @param value
		 *            value
		 * @param maxAge
		 *            max age
		 * @param domain
		 *            domain
		 * @param path
		 *            path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
			this.httpOnly = httpOnly;
		}

		/**
		 * Getter for name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for domain
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for path
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for maxAge
		 * 
		 * @return maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Getter for httpOnly
		 * 
		 * @return httpOnly
		 */
		public boolean getHttpOnly() {
			return httpOnly;
		}

	}
}
