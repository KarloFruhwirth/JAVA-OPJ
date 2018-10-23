package hr.fer.zemris.java.webserver;

/**
 * Interface which for the RequestContext provided creates content for the
 * client
 * 
 * @author KarloFrühwirth
 *
 */
public interface IWebWorker {

	/**
	 * Creates content for client.
	 * 
	 * @param context
	 *            RequestContext
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
