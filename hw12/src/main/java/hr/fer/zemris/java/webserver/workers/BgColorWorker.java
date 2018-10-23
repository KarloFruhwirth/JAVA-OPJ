package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of IWebWorker used for seting the color used in Home
 * 
 * @author KarloFrühwirth
 *
 */
public class BgColorWorker implements IWebWorker {
	private String color;

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> parameters = context.getParameterNames();
		for (String param : parameters) {
			if (param.equals("bgcolor")) {
				color = context.getParameter(param);
			}
		}
		try {
			@SuppressWarnings("unused") // tests if string color is actually a color
			Color testColor = Color.getColor("#" + color);
			context.write("Color is updated!");
			context.setPersistentParameter("bgcolor", color);
		} catch (Exception e) {
			context.write("Color is not updated!");
		}
	}

}
