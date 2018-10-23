package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KarloFrühwirth
 *
 */
public class NameBuilderList implements NameBuilder {
	/**
	 * List<NameBuilder> list of NameBuilder
	 */
	private List<NameBuilder> list = new ArrayList<>();

	/**
	 * Constructor for NameBuilderList
	 * 
	 * @param list
	 *            List<NameBuilder>
	 */
	public NameBuilderList(List<NameBuilder> list) {
		this.list = list;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		for (NameBuilder nb : list) {
			nb.execute(info);
		}

	}

}
