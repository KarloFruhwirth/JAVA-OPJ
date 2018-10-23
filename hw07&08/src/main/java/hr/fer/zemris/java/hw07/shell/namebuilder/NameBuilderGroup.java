package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Implementation of {@link NameBuilder} for the groups
 * 
 * @author KarloFrühwirth
 *
 */
public class NameBuilderGroup implements NameBuilder {
	/**
	 * String of the group value ${aaa} -> aaa
	 */
	private String group;

	/**
	 * Constructor for NameBuilderGroup
	 * 
	 * @param group
	 *            String
	 */
	public NameBuilderGroup(String group) {
		this.group = group;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		StringBuilder sb = info.getStringBuilder();
		try {
			String[] array = group.split(",");
			if (array.length == 2) {
				int index = Integer.parseInt(array[0]);
				String result;
				if (array[1].charAt(0) == '0') {
					int spec = Integer.parseInt(array[1]);
					result = String.format("%0" + spec + "d", Integer.parseInt(info.getGroup(index)));
				} else {
					int spec = Integer.parseInt(array[1]);
					result = String.format("%" + spec + "d", Integer.parseInt(info.getGroup(index)));
				}
				sb.append(result);
			} else {
				int index = Integer.parseInt(array[0]);
				sb.append(info.getGroup(index));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
