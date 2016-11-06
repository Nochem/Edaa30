package map;

public class SimpleHashMap<K, V> implements Map<K, V> {
	


	@Override
	public V get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V put(K arg0, V arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public static class Entry<K, V> implements Map.Entry<K, V>{
		K key;
		V value;
		Entry<K, V> next;
		int hashCode;
		
		
		public Entry(K key, V value, Entry<K, V> next){
			next=null;
			this.key=key;
			this.value=value;
			
		}
		
		public boolean equals(Entry<K, V> entry){
			if (entry.key==this.key && entry.value==this.value){
				return true;
			}
			return false;
		}
		public K getKey(){
			return key;
		}
		public V getValue(){
			return value;
		}
		public int hashCode(){
			return hashCode;
		}
		public V setValue(V value){
			V temp=this.value;
			this.value=value;
			return temp;
		}
		public String toString(){
			String string=key.toString() + "=" + value.toString();
			return string;
		}
	}

}
