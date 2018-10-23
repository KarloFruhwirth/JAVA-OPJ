package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * implementation of ILocalizationProvider, responsible for informing listeners 
 * 
 * @author KarloFrühwirth
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * list of ILocalizationListener
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * new abstract localization provider
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	/**
	 * Informs all listeners of a change
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);

	}

}
