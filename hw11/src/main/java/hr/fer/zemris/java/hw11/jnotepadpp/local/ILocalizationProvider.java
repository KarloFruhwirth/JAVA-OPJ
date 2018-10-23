package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Provider interface which when implemented enables you to change current
 * language
 * 
 * @author KarloFrühwirth
 *
 */
public interface ILocalizationProvider {

	/**
	 * add ILocalizationListener
	 * 
	 * @param listener
	 *            listener
	 */
	public void addLocalizationListener(ILocalizationListener listener);

	/**
	 * remove ILocalizationListener
	 * 
	 * @param listener
	 *            listener
	 */
	public void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Gets the string for the provided key.
	 * 
	 * @param key
	 *            key in properties file
	 * @return string
	 */
	public String getString(String key);

	/**
	 * Gets current language
	 * 
	 * @return current language
	 */
	public String getCurrentLanguage();

}
