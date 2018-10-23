package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Provides i18n for {@link JNotepadPP}
 * 
 * @author KarloFrühwirth
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * language
	 */
	private String language;

	/**
	 * LocalizationProvider
	 */
	private static LocalizationProvider instance = null;

	/**
	 * ResourceBundle
	 */
	private ResourceBundle bundle;

	/**
	 * constructor for LocalizationProvider<br>
	 * sets language default to English
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.prijevodi",
				Locale.forLanguageTag(this.language));
	}

	/**
	 * Changes the language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		if (this.language.equals(language))
			return;
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.prijevodi",
				Locale.forLanguageTag(this.language));
		fire();
	}

	/**
	 * singleton instance of LocalizationProvider
	 * 
	 * @return LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
