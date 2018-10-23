package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.regex.Matcher;

/**
 * Implementation of the NameBuilderInfo
 * 
 * @author KarloFrühwirth
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {
	/**
	 * Matcher
	 */
	private Matcher matcher;
	/**
	 * StringBuilder
	 */
	private StringBuilder sb = new StringBuilder();

	/**
	 * Constructor for NameBuilderInfoImpl
	 * 
	 * @param matcher
	 *            Matcher
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public StringBuilder getStringBuilder() {
		return sb;
	}

	@Override
	public String getGroup(int index) {
		int groupCount = matcher.groupCount();
		if (index > groupCount) {
			throw new IllegalArgumentException("There is no group for this index.");
		} else {
			return matcher.group(index);
		}
	}

}
