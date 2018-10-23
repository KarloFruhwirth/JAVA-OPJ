package hr.fer.zemris.java.custom.collections;

/**
 * @author KarloFrühwirth
 * @version 1.0
 * 
 * This class represents an implementation of linked list-backed collection of objects
 * it extends class Collection and contains ListNode as its private static class
 */
public class LinkedListIndexedCollection extends Collection{
	
	/**
	 * @author KarloFrühwirth
	 * static class that represents a single node in the LinkedListIndexedCollection
	 */
	private static class ListNode{
		ListNode next;
		ListNode previous;
		Object value;
		
		public ListNode(Object value) {
			this.value=value;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Default constructor that creates an empty collection
	 */
	public LinkedListIndexedCollection() {
		this.first=this.last=null;
	}
	
	/**
	 * Collection that adds the given collection to the new one
	 * @param collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		addAll(collection);
		
	}
	
	@Override
	public void add(Object value) {
		if(value==null) {
			throw new NullPointerException("Cant add null to LinkedListIndexedCollection");
		}
		ListNode input = new ListNode(value);
		if(this.first==null&&this.last==null) {
			this.first=this.last=input;
			this.first.previous=this.first.next=this.last.previous=this.last.next=null;
		}else {
			this.last.next=input;
			input.previous=this.last;
			this.last=input;
		}
		this.size++;
	}
	
	/**
	 * Method inserts the given value at the given position in linked-list
	 * If the position is not legal method throws IndexOutOfBoundsException
	 * @param value
	 * @param position
	 */
	void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Cant insert value at the given postion: " + position);
		}
		ListNode input = new ListNode(value);
		if(position==0) {
			this.first.previous=input;
			input.next=this.first;
			this.first=input;
		}else if(position==this.size) {
			this.last.next=input;
			input.previous=this.last;
			this.last=input;
		}else {
			if(position<=(this.size/2)){
				ListNode pom1 = this.first;
				ListNode pom2 = this.first.next;
				for(int i=0;i<position-1;i++) {
					pom1=pom1.next;
					pom2=pom2.next;
				}
				pom1.next=input;
				pom2.previous=input;
				input.next=pom2;
				input.previous=pom1;
			}else {
				ListNode pom1 = this.last;
				ListNode pom2 = this.last.previous;
				for(int i=size;i>position+1;i--) {
					pom1=pom1.previous;
					pom2=pom2.previous;
				}
				pom1.previous=input;
				pom2.next=input;
				input.previous=pom2;
				input.next=pom1;
			}
		}
		this.size++;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i =0;i<this.size;i++) {
			processor.process(this.get(i));
		}
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index
	 * If the index is not legal method throws IndexOutOfBoundsException
	 * @param index
	 * @return Object
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException(
					"The index that you entered is invalid. It should be between 0 and " + (this.size - 1));
		}
		Object[] rez= this.toArray();
		return rez[index];
	}
	
	@Override
	public boolean contains(Object value) {
		Object[] rez = this.toArray();
		for (int i = 0; i < this.size; i++) {
			if (rez[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method searches the collection and returns the index of the first 
	 * occurrence of the given value or -1 if the value is not found
	 * @param value
	 * @return
	 */
	public int indexOf(Object value) {
		if(value==null) {
			return -1;
		}
		Object[] rez = this.toArray();
		for(int i=0;i<this.size;i++) {
			if(rez[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	
	@Override
	public Object[] toArray() {
		ListNode pom = new ListNode(null);
		pom=this.first;
		Object[] rez = new Object[size];
		for(int i=0;i<size;i++) {
			rez[i]=pom.value;
			pom=pom.next;
		}
		return rez;
	}
	
	@Override
	public void clear() {
		this.first=this.last=null;
		this.size=0;
	}
	
	/**
	 * Removes element at specified index from collection
	 * If the index is not legal method throws IndexOutOfBoundsException
	 * @param index
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException("Cant remove value at the given postion: " + index);
		}else {
			ListNode pom = new ListNode(null);
			ListNode temp = new ListNode(null);
			if(index==0) {
				if(!(this.first.next.equals(null))) {
					pom=this.first.next;
					pom.previous=null;
					this.first=pom;
				}else {
					this.first=null;
				}
			}else if(index==this.size-1) {
				if(!(this.last.previous.equals(null))) {
					pom=this.last.previous;
					pom.next=null;
					this.last=pom;
				}else {
					this.last=null;
				}
			}else {
				pom=this.first;
				temp=this.first.next.next;
				for(int i=0;i<index-1;i++) {
					pom=pom.next;
					temp=temp.next;
				}
				pom.next=temp;
				temp.previous=pom;
			}
			this.size--;
		}
	}
}
