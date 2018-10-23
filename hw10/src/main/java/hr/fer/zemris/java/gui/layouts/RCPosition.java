package hr.fer.zemris.java.gui.layouts;

/**
 * Class used to position elements for the {@link CalcLayout}
 * 
 * @author KarloFrühwirth
 *
 */
public class RCPosition {
	/**
	 * Row
	 */
	private int row;
	/**
	 * Column
	 */
	private int column;

	/**
	 * Constructor for RCPosition
	 * 
	 * @param row
	 *            row
	 * @param column
	 *            column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column
	 * 
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
