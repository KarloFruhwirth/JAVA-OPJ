package hr.fer.zemris.java.hw11.jnotepadpp.local;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Represents the connection between {@link LocalizationProvider} and
 * {@link JNotepadPP}
 * 
 * @author KarloFrühwirth
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * boolean if the bridge is connected
	 */
	private boolean connected;

	/**
	 * ILocalizationProvider parent
	 */
	private ILocalizationProvider parent;

	/**
	 * ILocalizationListener listener
	 */
	private ILocalizationListener listener = () -> fire();

	/**
	 * Constructor for LocalizationProviderBridge
	 * 
	 * @param parent
	 *            ILocalizationProvider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}

	/**
	 * disconnects the bridge
	 */
	public void disconnect() {
		if (connected == false)
			return;
		connected = false;
		parent.removeLocalizationListener(listener);
	}

	/**
	 * connects the bridge
	 */
	public void connect() {
		if (connected == true)
			return;
		connected = true;
		parent.addLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}

}
