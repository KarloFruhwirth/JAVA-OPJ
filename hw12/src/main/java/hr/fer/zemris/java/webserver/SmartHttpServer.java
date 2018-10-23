package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {
	/**
	 * Server address
	 */
	private String address;
	/**
	 * Domain name
	 */
	private String domainName;
	/**
	 * Port used
	 */
	private int port;
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	/**
	 * Time of sesion timeout
	 */
	private int sessionTimeout;
	/**
	 * Map of mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread
	 */
	private ServerThread serverThread;
	/**
	 * Thread which periodically checks for expired sessions and removes them
	 */
	private RemoveExpiredSessionsThread resThread;
	/**
	 * Thread pool
	 */
	private ExecutorService threadPool;
	/**
	 * Path to document root
	 */
	private Path documentRoot;
	/**
	 * Map of workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/**
	 * Session map
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random session
	 */
	private Random sessionRandom = new Random();

	/**
	 * Constructor for SmartHttpServer
	 * 
	 * @param configFileName
	 *            name of cconfiguration file
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		InputStreamReader inStream = null;

		try {
			inStream = new InputStreamReader(new FileInputStream(new File(configFileName)), StandardCharsets.UTF_8);
			properties.load(inStream);

			this.address = properties.getProperty("server.address");
			this.domainName = properties.getProperty("server.domainName");
			this.port = Integer.parseInt(properties.getProperty("server.port"));
			this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			this.documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
			this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
			Path mimePath = Paths.get(properties.getProperty("server.mimeConfig"));
			Path workersPath = Paths.get(properties.getProperty("server.workers"));
			setMimeTypes(mimePath);
			setWorkers(workersPath);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		serverThread = new ServerThread();
	}

	/**
	 * Adds workers to workersMap based on the path provided as argument
	 * 
	 * @param workersPath
	 *            path to workers.properties
	 */
	private void setWorkers(Path workersPath) {
		try {
			List<String> lines = Files.readAllLines(workersPath, StandardCharsets.UTF_8);
			for (String line : lines) {
				if (line.isEmpty() || line.startsWith("#"))
					continue;
				String[] elementsArray = line.split("=");
				String path = elementsArray[0].trim();
				String fqcn = elementsArray[1].trim();
				if (workersMap.containsKey(path))
					throw new IllegalArgumentException("Same path allready provided...");
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			}
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds mimeTypes to mimeTypes map based on the path provided as argument
	 * 
	 * @param mimePath
	 *            path to mime.properties
	 */
	private void setMimeTypes(Path mimePath) {
		try {
			List<String> lines = Files.readAllLines(mimePath, StandardCharsets.UTF_8);
			for (String line : lines) {
				if (line.isEmpty() || line.startsWith("#"))
					continue;
				String[] elementsArray = line.split("=");
				mimeTypes.put(elementsArray[0].trim(), elementsArray[1].trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Starts server thread if not already running and initializes threadpool
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
		}
		serverThread.start();
		// Creates a thread pool that reuses a fixed number of threads operating off a
		// shared unbounded queue.
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if (resThread == null) {
			resThread = new RemoveExpiredSessionsThread();
		}
		resThread.start();
	}

	/**
	 * Signals server thread to stop running and shuts down threadpool
	 */
	protected synchronized void stop() {
		// .join() as an alternative
		serverThread.stopRunning();
		resThread.stopRunning();
		threadPool.shutdown();
	}

	/**
	 * 
	 * Thread which periodically checks for expired sessions and removes them
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	protected class RemoveExpiredSessionsThread extends Thread {

		/**
		 * Is server still running
		 */
		private boolean running = true;
		/**
		 * Periodical check time-out, 5 minutes
		 */
		private static final long SLEEP = 5 * 60 * 1000;

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(SLEEP);
					Set<String> sessionsSIDS = sessions.keySet();
					for (String sid : sessionsSIDS) {
						SessionMapEntry sme = sessions.get(sid);
						long time = new Date().getTime();
						if (sme.validUntil < time) {
							sessions.remove(sid);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Stops the execution of work by the thread
		 */
		public void stopRunning() {
			running = false;
		}
	}

	/**
	 * 
	 * Thread which starts the server
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * Is server still running
		 */
		private boolean running = true;

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (running) {
				try {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Stops the execution of work by the thread
		 */
		public void stopRunning() {
			running = false;
		}
	}

	/**
	 * Class which holds the information about the current session
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID
		 */
		String sid;
		/**
		 * Host
		 */
		String host;
		/**
		 * Time until its valid
		 */
		long validUntil;
		/**
		 * Map
		 */
		Map<String, String> map = new ConcurrentHashMap<>();
	}

	/**
	 * @author KarloFrühwirth
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * ClientWorker Socket
		 */
		private Socket csocket;
		/**
		 * PushbackInputStream
		 */
		private PushbackInputStream istream;
		/**
		 * OutputStream
		 */
		private OutputStream ostream;
		/**
		 * Version
		 */
		private String version;
		/**
		 * Method
		 */
		private String method;
		/**
		 * Host
		 */
		private String host;
		/**
		 * Map of parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Map of tempParams
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Map of permPrams
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * List of output RCCookie
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * SID
		 */
		private String SID;
		/**
		 * RequestContext
		 */
		private RequestContext context = null;

		/**
		 * Upper case letters
		 */
		public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		/**
		 * Constructor for ClientWorker
		 * 
		 * @param csocket
		 *            Socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String requestStr = null;
			try {
				byte[] requestArray = readRequest(istream);
				requestStr = new String(requestArray, StandardCharsets.US_ASCII);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			List<String> request = getRequest(requestStr);
			if (request.size() < 1) {
				sendError(400, "Bad request");
				return;
			}
			String firstLine = request.get(0);
			String requestedPath = null;
			try {
				String[] array = firstLine.split(" ");
				method = array[0].trim();
				requestedPath = array[1].trim();
				version = array[2].trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!method.equals("GET") || (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1"))) {
				sendError(400, "Bad request");
				return;
			}
			for (String header : request) {
				if (header.startsWith("Host:")) {
					String temp = header.substring(5).trim();
					host = temp.substring(0, temp.indexOf(':'));
				}
			}
			if (host == null) {
				host = domainName;
			}
			String path = null;
			String paramString = null;
			try {
				String[] elements = requestedPath.split("\\?");
				path = elements[0];
				if (elements.length == 2) {
					paramString = elements[1];
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// SID
			String sidCandidate = checkSID(request);
			if (sidCandidate == null) {
				generateSID();
			} else {
				SessionMapEntry obj = sessions.get(sidCandidate);
				if (obj == null) {
					generateSID();
				} else if (obj.validUntil < new Date().getTime()) {
					sessions.remove(sidCandidate);
					generateSID();
				} else {
					obj.validUntil = new Date().getTime() + sessionTimeout * 1000;
					permPrams = obj.map;
					SID = obj.sid;
				}
			}
			parseParameters(paramString);
			try {
				internalDispatchRequest(path, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Path filePath = documentRoot.resolve(path.substring(1));
			if (!filePath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}
			if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
				sendError(404, "Not found");
				return;
			}

			String mime = getMimeType(filePath);
			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setMimeType(mime);
			rc.setStatusCode(200);
			BufferedInputStream stream = null;
			try {
				stream = new BufferedInputStream(new FileInputStream(filePath.toFile()));
				byte[] buf = new byte[1024];
				while (true) {
					int r = stream.read(buf);
					if (r < 1)
						break;
					rc.write(buf);
				}
				try {
					ostream.flush();
					csocket.close();
				} catch (Exception e2) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		/**
		 * Generates new SID, and SessionMapEntry and adds them to the sessions map<br>
		 * Also adds to outputCookies appropriate new RCCookie
		 */
		private void generateSID() {
			char[] buf = new char[20];
			for (int i = 0; i < 20; i++) {
				buf[i] = upper.charAt(sessionRandom.nextInt(upper.length()));
			}
			SID = new String(buf);
			SessionMapEntry sesionEntry = new SessionMapEntry();
			sesionEntry.sid = SID;
			sesionEntry.validUntil = new Date().getTime() + sessionTimeout * 1000;
			permPrams = new HashMap<>();
			sesionEntry.map = permPrams;
			sessions.put(SID, sesionEntry);
			RCCookie rcc = new RCCookie("sid", SID, null, host, "/", true);
			outputCookies.add(rcc);
		}

		/**
		 * Checks if List request contains sid
		 * 
		 * @param request
		 *            header lines, List
		 * @return String if there is an sid and null otherwise
		 */
		private String checkSID(List<String> request) {
			for (String headerLine : request) {
				if (!headerLine.startsWith("Cookie:"))
					continue;
				String temp = headerLine.substring(7).trim();
				String[] elems = temp.split(";");
				if (!elems[0].startsWith("sid"))
					continue;
				return elems[0].split("=")[1].substring(1, elems[0].split("=")[1].length() - 1);
			}
			return null;
		}

		/**
		 * Gets request
		 * 
		 * @param requestStr
		 *            String
		 * @return List of Strings
		 */
		private List<String> getRequest(String requestStr) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestStr.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Returns mimeType if it exists in mimeTypes map. Otherwise returns
		 * application/octet-stream
		 * 
		 * @param filePath
		 *            Path
		 * @return String
		 */
		private String getMimeType(Path filePath) {
			try {
				String extension = filePath.toFile().getName()
						.substring(filePath.toFile().getName().lastIndexOf('.') + 1);
				return mimeTypes.get(extension);
			} catch (Exception e) {
			}
			return "application/octet-stream";
		}

		/**
		 * Method used to parse through the parameters in url
		 * 
		 * @param paramString
		 *            String
		 */
		private void parseParameters(String paramString) {
			if (paramString == null)
				return;
			try {
				String[] arrayOfElements = paramString.split("&");
				for (int i = 0; i < arrayOfElements.length; i++) {
					String[] parameter = arrayOfElements[i].split("=");
					params.put(parameter[0].trim(), parameter[1].trim());
				}
			} catch (Exception e) {
			}
		}

		/**
		 * Method used to send error message to the user
		 * 
		 * @param statusCode
		 *            statusCode
		 * @param statusText
		 *            statusText
		 */
		private void sendError(int statusCode, String statusText) {
			RequestContext rc = new RequestContext(ostream, null, null, null);
			rc.setStatusCode(statusCode);
			rc.setStatusText(statusText);
			try {
				rc.write(statusText);
				ostream.flush();
				csocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Reads Request, method provided by MC
		 * 
		 * @param istream2
		 *            PushbackInputStream
		 * @return byte[]
		 * @throws IOException
		 */
		private byte[] readRequest(PushbackInputStream istream2) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream2.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();

		}

		/**
		 * Method used to execute convention-over-configuration, configuration-based and
		 * scripts<br>
		 * Depending on the method used different results occur
		 * 
		 * @param urlPath
		 *            String
		 * @param directCall
		 *            directCall
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (urlPath.startsWith("/private") && directCall == true) {
				sendError(404, "Not found");
				return;
			} else if (urlPath.startsWith("/ext/")) {// convention-over-configuration
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
				}
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(context);
				ostream.flush();
				csocket.close();
				return;
			} else if (workersMap.containsKey(urlPath)) {// configuration-based
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
				}
				workersMap.get(urlPath).processRequest(context);
				ostream.flush();
				csocket.close();
				return;
			} else if (urlPath.endsWith(".smscr")) {
				byte[] data;
				try {
					data = Files.readAllBytes(Paths.get(documentRoot.resolve(urlPath.substring(1)).toString()));
					String documentBody = new String(data, StandardCharsets.UTF_8);
					if (context == null) {
						context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
					}
					context.setMimeType("text/plain");
					new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
					ostream.flush();
					csocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}

		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
	}

	/**
	 * Main method used to start the server and demonstrate its glory
	 * 
	 * @param args
	 *            provide server.properties from config map.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Očekivao sam config file");
			return;
		}
		try {
			SmartHttpServer shs = new SmartHttpServer(args[0]);
			shs.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
