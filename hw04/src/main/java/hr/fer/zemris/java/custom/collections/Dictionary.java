package hr.fer.zemris.java.custom.collections;

public class Dictionary {
	private ArrayIndexedCollection col = new ArrayIndexedCollection();

	private static class Entry {
		Object key;
		Object value;

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

		public Entry(Object key, Object value) {
			if (key == null)
				throw new NullPointerException("Key cant be null!");
			this.key = key;
			this.value = value;
		}
	}

	public boolean isEmpty() {
		return col.isEmpty();
	}

	public int size() {
		return col.size();
	}

	public void clear() {
		col.clear();
	}

	public void put(Object key, Object value) {
		Entry entry = new Entry(key, value);
		boolean put = false;
		for (int i = 0, size = col.size(); i < size; i++) {
			Entry e = (Entry) col.get(i);
			if (e.getKey() == key) {
				e = entry;
				put = true;
			}
		}
		if (!put) {
			col.add(entry);
		}
	}

	public Object get(Object key) {
		for (int i = 0, size = col.size(); i < size; i++) {
			Entry e = (Entry) col.get(i);
			if (e.getKey() == key) {
				return e.getValue();
			}
		}
		return null;
	}
}
