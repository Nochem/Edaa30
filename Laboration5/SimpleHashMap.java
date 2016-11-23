package map;

public class SimpleHashMap<K, V> implements Map<K, V> {
	Entry<K, V>[] table;
	int capacity;
	double loadfactor, size; // Size and capacity could be int, but it's easier
								// to calculate this way

	/**
	 * Constructs an empty hashmap with the default initial capacity (16) and
	 * the default load factor (0.75).
	 */
	public SimpleHashMap() {
		table = new Entry[16];
		loadfactor = 0.75;
		size = 0;
		capacity = 16;
	}

	/**
	 * Constructs an empty hashmap with the specified initial capacity and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap(int capacity) {
		table = new Entry[capacity];
		loadfactor = 0.75;
		size = 0;
		this.capacity = capacity;
	}

	/** Returns the value of key arg0, null if no such key */
	@Override
	public V get(Object key) {
		K kKey= (K) key;
		Entry<K, V> entry = find(index(kKey), kKey);
		if (entry != null) {
			return entry.value;
		}
		return null;
	}

	/** Returns true if table is empty, otherwise false */
	@Override
	public boolean isEmpty() {
		if (size > 0) {
			return false;
		}
		return true;
	}

	/**
	 * Places the V value and K key in the hashmap. Returns old value if exists,
	 * null otherwise. Also calls a rehash if size/capacity > loadfactor after
	 * adding
	 */
	@Override
	public V put(K key, V value) {
		int index = index(key);
		Entry<K, V> newEntry = new Entry(key, value, null), current = table[index], previous=null;
		if (table[index] == null) {
			table[index] = newEntry;
			size++;
			if ((size / capacity) > loadfactor) {
				rehash();
			}
			return null;
		}
		if (table[index].key.equals(newEntry.key)) {
			table[index] = newEntry;
			return current.value;
		} else {
			while (current != null) {
				if (current.key.equals(newEntry.key)) {
					previous.next = newEntry; // Will this cause a nullpointer
												// if current==table[0]?
					newEntry.next = current.next;
					return current.value;
				}
				previous = current;
				current = current.next;
			}
			previous.next = newEntry;
			size++;
			if ((size / capacity) > loadfactor) {
				rehash();
			}
			return null;
		}
	}

	/**
	 * Reorganizes the table in a larger array, big O notation is large here
	 */
	private void rehash() {
		Entry<K, V>[] oldTable = table;
		int oldCapacity = capacity;
		capacity = capacity * 2;
		table = new Entry[capacity];
		size = 0;
		for (int i = 0; i < oldCapacity; i++) {
			if (oldTable[i] != null) {
				put(oldTable[i].key, oldTable[i].value);
				Entry<K, V> tempEntry = oldTable[i];
				while (tempEntry.next != null) {
					put(tempEntry.next.key, tempEntry.next.value);
					tempEntry = tempEntry.next;
				}
			}
		}
	}
/** Removes the element with Key key from the map. *
 *  Returns removed value if success, null if there is no such key*/
	@Override
	public V remove(Object key) {
		if (size==0){
			return null;
		}
		K kKey=(K)key;
		int index = index(kKey);
		Entry<K, V> current = table[index], previous; //Current becomes null instead of 40
		if (table[index]!=null){
			if (table[index].key.equals(key)){
				table[index]=current.next;
				size--;
				return current.value;
			}
			while (current.next!=null){
				previous=current;
				current=current.next;
				if (current.key.equals(key)){
					previous.next=current.next;
					size--;
					return current.value;
				}
			}
		}
		return null;
	}

	/**
	 * Returns a String with all Keys and Values in table, returns an empty
	 * string if table is empty
	 */
	public String show() {
		String outputString = "";
		for (int i = 0; i < capacity; i++) {
			outputString = outputString + recursiveShow(table[i]) + '\n';
		}
		return outputString;
	}

	/** Supportmethod that recursivly steps through the linked lists */
	private String recursiveShow(Entry<K, V> entry) {
		if (entry != null) {
			String output = entry.toString() + " ";
			if (entry.next != null) {
				output = output + recursiveShow(entry.next);
			}
			return output;
		}
		return "";
	}

	/** Returns the number of elements in the table */
	// The int casting isn't pretty, but it simplifies the calc of current load.
	@Override
	public int size() {
		return (int) size;
	}

	/** Decides where to place objects with K key */
	// Same goes here
	private int index(K key) {
		int index = Math.abs((key.hashCode() % (int) (capacity - 1)));
		return index;
	}

	/**
	 * Locates the entry with K key at index. Returns null if no such key at
	 * index. Actually returns the element that points at the element with K if
	 * it's in a chain.
	 */
	// Eftersom index beror av key så känns det överflödigt att skicka med båda?
	private Entry<K, V> find(int index, K key) {
		Entry<K, V> tempEntry = table[index]; // this is for the while case
		if (table[index] == null) {
			return null;
		}
		if (table[index].key.equals(key)) {
			return table[index];
		}
		while (tempEntry.next != null) {
			if (tempEntry.next.key.equals(key)) { // Checks tempEn.next to avoid
													// doublchecking the first
													// entry
				return tempEntry.next;
			}
			tempEntry = tempEntry.next;
		}
		return null;
	}

	/** Nestled class below */
	public static class Entry<K, V> implements Map.Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;
		int hashCode;

		public Entry(K key, V value, Entry<K, V> next) {
			next = null;
			this.key = key;
			this.value = value;

		}

		public boolean equals(Entry<K, V> entry) {
			if (entry.key == this.key && entry.value == this.value) {
				return true;
			}
			return false;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public int hashCode() {
			return hashCode;
		}

		public V setValue(V value) {
			V temp = this.value;
			this.value = value;
			return temp;
		}

		public String toString() {
			String string = key.toString() + "=" + value.toString();
			return string;
		}
	}
}
