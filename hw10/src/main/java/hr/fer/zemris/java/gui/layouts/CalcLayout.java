package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Our implementation of LayoutManager2 which is used for creating
 * {@link Calculator}
 * 
 * <br>
 * <br>
 * <br>
 * Created referring to GridLayout.
 * 
 * @author KarloFrühwirth
 *
 */
public class CalcLayout implements java.awt.LayoutManager2 {

	/**
	 * Max row size
	 */
	private final static int MAX_ROW = 5;

	/**
	 * Max column size
	 */
	private final static int MAX_COLUMN = 7;

	/**
	 * Max number of elements
	 */
	private final static int MAX_ELEMENTS = 31;

	/**
	 * Result JLable, first element
	 */
	public static final RCPosition FIRST_ELEMENT = new RCPosition(1, 1);

	/**
	 * Gap between elements
	 */
	private int gap;

	/**
	 * Map of elements
	 */
	private Map<Component, RCPosition> elements = new HashMap<>();

	/**
	 * Default constructor for CalcLayout sets the gap to 0
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor for CalcLayout sets the gap to the provided value
	 * 
	 * @param gap
	 *            gap
	 * @throws IllegalArgumentException
	 *             if gap provided is less than 0
	 */
	public CalcLayout(int gap) {
		if (gap < 0)
			throw new IllegalArgumentException("Gap between elements must be greater or equal to 0");
		this.gap = gap;
	}

	@Override
	public void addLayoutComponent(String constraint, Component component) {
	}

	@Override
	public void layoutContainer(Container container) {
		Dimension dimension = getDimension();

		dimension.setSize((container.getWidth()-(MAX_COLUMN-1)*gap) / (MAX_COLUMN),
				(container.getHeight()-(MAX_ROW-1)*gap) / (MAX_ROW));

		for (Map.Entry<Component, RCPosition> element : elements.entrySet()) {
			RCPosition rcPosition = element.getValue();
			Component component = element.getKey();
			int row = rcPosition.getRow();
			int column = rcPosition.getColumn();

			if (rcPosition.equals(FIRST_ELEMENT)) {
				component.setBounds((column - 1), (row - 1), 5 * dimension.width + 4 * gap, dimension.height);
			} else {
				component.setBounds((column - 1) * (dimension.width + gap), (row - 1) * (dimension.height + gap),
						dimension.width, dimension.height);
			}
		}

	}

	/**
	 * Method used to get the dimension of element
	 * 
	 * @return Dimension
	 */
	private Dimension getDimension() {
		int width = 0;
		int height = 0;

		for (Map.Entry<Component, RCPosition> element : elements.entrySet()) {
			Component component = element.getKey();
			RCPosition rcPosition = element.getValue();
			if (component.getPreferredSize() != null) {
				height = height > component.getPreferredSize().height ? height : component.getPreferredSize().height;
				if (rcPosition.equals(FIRST_ELEMENT)) {
					width = width > (component.getPreferredSize().width - 4 * gap) / 5 ? width
							: (component.getPreferredSize().width - 4 * gap) / 5;
				} else {
					width = width > component.getPreferredSize().width ? width : component.getPreferredSize().width;
				}
			}
		}

		return new Dimension(width, height);

	}

	@Override
	public void removeLayoutComponent(Component component) {
		elements.remove(component);
	}

	@Override
	public void addLayoutComponent(Component component, Object constraint) {
		RCPosition rcPosition = null;
		if (constraint instanceof String) {
			try {
				String[] array = ((String) constraint).split(",");
				int row = Integer.parseInt(array[0]);
				int column = Integer.parseInt(array[1]);
				rcPosition = new RCPosition(row, column);
			} catch (Exception e) {
				System.out.println("Invalid string input");
			}
		} else if (constraint instanceof RCPosition) {
			rcPosition = (RCPosition) constraint;
		} else {
			throw new IllegalArgumentException("Ilegal constraint");
		}
		if (elements.size() == MAX_ELEMENTS) {
			throw new IllegalArgumentException("Layout is full");
		}
		if (checkRCPosition(rcPosition)) {
			elements.put(component, rcPosition);
		}
	}

	/**
	 * Checks if the provided RCPosition is valid<br>
	 * If r<1||r>5 or c<1||c>7 or r=1 and 1<c<6
	 * 
	 * @param rcPosition
	 *            RCPosition
	 * @return true || false
	 */
	private boolean checkRCPosition(RCPosition rcPosition) {
		int row = rcPosition.getRow();
		int column = rcPosition.getColumn();

		if (row < 1 || row > 5 || column < 1 || column > 7)
			throw new CalcLayoutException("Row must be between 1 and 5 and column must be between 1 and 7");
		if (row == 1 && column > 1 && column < 6)
			throw new CalcLayoutException("Row 1 be has only elements (1,1), (1,6) , (1,7)");
		if (elements.containsValue(rcPosition))
			throw new CalcLayoutException("For the same constraint you can't add more components");

		return true;
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container arg0) {
	}

	@Override
	public Dimension minimumLayoutSize(Container container) {
		Insets inset = container.getInsets();
		int width = 0;
		int height = 0;

		for (Map.Entry<Component, RCPosition> element : elements.entrySet()) {
			Component component = element.getKey();
			RCPosition rcPosition = element.getValue();
			if (component.getMinimumSize() != null) {
				height = height > component.getMinimumSize().height ? height : component.getMinimumSize().height;
				if (rcPosition.equals(FIRST_ELEMENT)) {
					width = width > (component.getMinimumSize().width - 4 * gap) / 5 ? width
							: (component.getMinimumSize().width - 4 * gap) / 5;
				} else {
					width = width > component.getMinimumSize().width ? width : component.getMinimumSize().width;
				}
			}
		}

		return new Dimension(inset.left + inset.right + MAX_COLUMN * width + (MAX_COLUMN - 1) * gap,
				inset.bottom + inset.top + MAX_ROW * height + (MAX_ROW - 1) * gap);
	}

	@Override
	public Dimension preferredLayoutSize(Container container) {
		Insets inset = container.getInsets();
		int width = 0;
		int height = 0;

		for (Map.Entry<Component, RCPosition> element : elements.entrySet()) {
			Component component = element.getKey();
			RCPosition rcPosition = element.getValue();
			if (component.getPreferredSize() != null) {
				height = height > component.getPreferredSize().height ? height : component.getPreferredSize().height;
				if (rcPosition.equals(FIRST_ELEMENT)) {
					width = width > (component.getPreferredSize().width - 4 * gap) / 5 ? width
							: (component.getPreferredSize().width - 4 * gap) / 5;
				} else {
					width = width > component.getPreferredSize().width ? width : component.getPreferredSize().width;
				}
			}
		}

		return new Dimension(inset.left + inset.right + MAX_COLUMN * width + (MAX_COLUMN - 1) * gap,
				inset.bottom + inset.top + MAX_ROW * height + (MAX_ROW - 1) * gap);
	}

	@Override
	public Dimension maximumLayoutSize(Container container) {
		Insets inset = container.getInsets();
		int width = 0;
		int height = 0;

		for (Map.Entry<Component, RCPosition> element : elements.entrySet()) {
			Component component = element.getKey();
			RCPosition rcPosition = element.getValue();
			if (component.getMaximumSize() != null) {
				height = height > component.getMaximumSize().height ? height : component.getMaximumSize().height;
				if (rcPosition.equals(FIRST_ELEMENT)) {
					width = width > (component.getMaximumSize().width - 4 * gap) / 5 ? width
							: (component.getMaximumSize().width - 4 * gap) / 5;
				} else {
					width = width > component.getMaximumSize().width ? width : component.getMaximumSize().width;
				}
			}
		}

		return new Dimension(inset.left + inset.right + MAX_COLUMN * width + (MAX_COLUMN - 1) * gap,
				inset.bottom + inset.top + MAX_ROW * height + (MAX_ROW - 1) * gap);
	}

}
