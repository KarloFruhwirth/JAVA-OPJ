package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Implementation of {@link NameBuilder} for cons Strings gradovi- in the
 * example in the hw
 * 
 * @author KarloFrühwirth
 *
 */
public class NameBuilderString implements NameBuilder {
	/**
	 * String constant
	 */
	private String string;

	/**
	 * Constructor for NameBuilderString
	 * 
	 * @param constant
	 *            String
	 */
	public NameBuilderString(String constant) {
		this.string = constant;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		StringBuilder sb = info.getStringBuilder();
		sb.append(string);
	}

}
