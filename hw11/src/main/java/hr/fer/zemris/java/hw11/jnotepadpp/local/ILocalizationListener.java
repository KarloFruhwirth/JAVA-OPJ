package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Listener interface, every class that processes ILocalization events must
 * implement this.
 * 
 * 
 * @author KarloFrühwirth
 *
 */
public interface ILocalizationListener {

	/**
	 * notifies the listeners that the localization has changed
	 */
	public void localizationChanged();

}
