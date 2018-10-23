package hr.fer.zemris.java.webserver;

/**
 * Interface which defines method dispatchRequest
 * 
 * @author KarloFrühwirth
 *
 */
public interface IDispatcher {
	/**
	 * Method used for executing private scripts in {@link SmartHttpServer}
	 * 
	 * @param urlPath
	 *            String
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
