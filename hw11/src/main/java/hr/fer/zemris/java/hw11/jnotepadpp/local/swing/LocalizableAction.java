package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Wrapper for {@link AbstractAction} so when a language changes the elements of
 * {@link JNotepadPP} change accordingly
 * 
 * @author KarloFrühwirth
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * key
	 */
	private String key;

	/**
	 * Constructor for LocalizableAction
	 * 
	 * @param key
	 *            key
	 * @param provider
	 *            ILocalizationProvider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		putValue(Action.NAME, provider.getString(this.key));

		try {
			putValue(Action.SHORT_DESCRIPTION, provider.getString(this.key + ".sd"));
			putValue(Action.MNEMONIC_KEY, Integer.decode(provider.getString(this.key + ".mn")));
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(provider.getString(this.key + ".ac")));
		} catch (Exception e) {
		}

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.NAME, provider.getString(key));
				try {
					putValue(Action.SHORT_DESCRIPTION, provider.getString(key + ".sd"));
					putValue(Action.MNEMONIC_KEY, Integer.decode(provider.getString(key + ".mn")));
					putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(provider.getString(key + ".ac")));
				} catch (Exception e) {
				}

			}
		});
	}

}
