package hr.fer.zemris.java.webserver.workers;


import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of IWebWorker used to demonstrate all the workers and scripts
 * functionalities.<br>
 * The change of the color occurs once you refresh the old page after the
 * submission of new color! <br>
 * Default color is gray.
 * @author KarloFrühwirth
 *
 */
public class Home implements IWebWorker {
	private String color;
	@Override
	public void processRequest(RequestContext context) throws Exception {
		if (context.getPersistentParameter("bgcolor") == null) {
			color = "7F7F7F";
		} else {
			color = context.getPersistentParameter("bgcolor");
		}
		System.err.println(color);
		context.setTemporaryParameter("background", color);
		context.getIDispatcher().dispatchRequest("/private/home.smscr");
	}

}
