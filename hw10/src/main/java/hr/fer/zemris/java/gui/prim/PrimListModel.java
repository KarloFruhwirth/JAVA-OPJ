package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * 
 * Our implementation of {@link ListModel}. <br>
 * Additional method next is used to add next prime number to the list
 * 
 * @author KarloFrühwirth
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * List of prime numbers
	 */
	private List<Integer> primeList = new ArrayList<>();
	/**
	 * List of ListDataListener
	 */
	private List<ListDataListener> list = new ArrayList<>();
	/**
	 * Prime number
	 */
	private int prim = 2;

	public PrimListModel(){
		primeList.add(1);
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		list.add(arg0);
	}

	@Override
	public Integer getElementAt(int arg0) {
		return primeList.get(arg0);
	}

	@Override
	public int getSize() {
		return primeList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		list.remove(arg0);

	}

	/**
	 * Adds to primeList next prime number
	 */
	public void next() {
		int position = primeList.size();
		for (int i = 2; i <= Math.sqrt(prim); i++) {
			if (prim % i == 0) {
				prim++;
				i = 2;
			}
		}
		primeList.add(prim++);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		list.forEach(a -> a.intervalAdded(event));

	}

}
