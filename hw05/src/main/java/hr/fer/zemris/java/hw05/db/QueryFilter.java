package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to filter a {@link StudentDatabase}
 * 
 * @author KarloFrühwirth
 *
 */
public class QueryFilter implements IFilter{
	/**
	 * List of ConditionalExpression
	 */
	private List<ConditionalExpression> list = new ArrayList<ConditionalExpression>();

	/**
	 * Constructor for QueryFilter
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression e : list) {
			if(!(e.getComparisonOperator().satisfied(e.getFieldValue().get(record), e.getString()))) {
				return false;
			}
		}
		return true;
	}
	
	

}
