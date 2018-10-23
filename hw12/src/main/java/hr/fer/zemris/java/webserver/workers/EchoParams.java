package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of IWebWorker used to print out the parameter name and value
 * in a table
 * 
 * @author KarloFrühwirth
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		StringBuilder sb = new StringBuilder("<html>\r\n" + "  <body>\r\n" + "    <h1>Echo parameters table:</h1>\r\n"
				+ "    <table border='1'>\r\n");
		Set<String> names = context.getParameterNames();
		for (String name : names) {
			sb.append("      <tr><td>").append(name).append("</td><td>").append(context.getParameter(name))
					.append("</td></tr>\r\n");
		}
		sb.append("    </table>\r\n" + "  </body>\r\n" + "</html>\r\n");
		context.setMimeType("text/html");
		context.write(sb.toString());
	}

}
