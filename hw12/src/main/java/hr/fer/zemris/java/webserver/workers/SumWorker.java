package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of IWebWorker used calculate the sum of two numbers.<br>
 * If one or both arguments are not provided we use the default values for a and
 * b which are 1 and 2.
 * 
 * @author KarloFrühwirth
 *
 */
public class SumWorker implements IWebWorker {
	private int a = 1;
	private int b = 2;
	private int sum = 0;

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> parameters = context.getParameterNames();
		for (String param : parameters) {
			if (param.equals("a")) {
				try {
					a = Integer.parseInt(context.getParameter(param));
				} catch (Exception e) {
					a = 1;
				}
			}
			if (param.equals("b")) {
				try {
					b = Integer.parseInt(context.getParameter(param));
				} catch (Exception e) {
					b = 2;
				}
			}
		}
		if (!parameters.contains("a"))
			a = 1;
		if (!parameters.contains("b"))
			b = 2;
		sum = a + b;
		context.setTemporaryParameter("a+b", String.valueOf(sum));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter("a", String.valueOf(a));
		context.getIDispatcher().dispatchRequest("/private/calc.smscr");
		context.removeTemporaryParameter("a");
		context.removeTemporaryParameter("b");
		context.removeTemporaryParameter("a+b");
	}

}
