package phonebook;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class myPhoneBook extends TreeMap<String, TreeSet<String>> implements PhoneBook {
	int size;
	

	/** Places a Name and phone number into the TreeMap.
	 * True if success. False if number already exists for that name.
	 */
	@Override
	public boolean put(String name, String number) {
		if (super.containsKey(name)){
			TreeSet<String> rSet = super.get(name);
			if (rSet.contains(number)){
				return false;
			}
			rSet.add(number);
			super.replace(name, rSet);
			return true;	
		}
		TreeSet<String> set = new TreeSet<String>();
		set.add(number);
		super.put(name, set);
		size++;
		return true;
	}

	/**Removes the person from the phonebook. Deletes all numbers tied to said person
	 * returns true if success, false if there is no such person
	 */
	@Override
	public boolean remove(String name) {
		if (super.containsKey(name)) {
			super.remove(name);
			size--;
			return true;
		}
		return false;
	}

	/** Removes a specific number from Name */
	@Override
	public boolean removeNumber(String name, String number) {
		TreeSet<String> rSet = super.get(name);
		try {
		Iterator<String> itr = rSet.iterator();
		while (itr.hasNext()) {
			if (itr.next().equals(number)) {
				itr.remove();
				return true;
			}
		}
		return false;
		}
		catch (NullPointerException e) {
			return false;
		}
	}

	/** Returns a Set of numbers tied to Name */
	@Override
	public Set<String> findNumbers(String name) {
		TreeSet<String> rSet = super.get(name);
		if (rSet==null){
			rSet=new TreeSet<String>();
		}
		return rSet;
	}

	/**Returns a set of names that has the Number as value*/
	@Override
	public Set<String> findNames(String number) {
		TreeSet<String> rSet = new TreeSet<String>();
		Iterator<Entry<String, TreeSet<String>>> itr = super.entrySet().iterator();
		while (itr.hasNext()){
			Entry<String, TreeSet<String>> current = itr.next();
			if (current.getValue().contains(number)){
				rSet.add(current.getKey());
			}
		}
		return rSet;
	}

	/**Returns a set of all names in the phonebook*/
	@Override
	public Set<String> names() {
		TreeSet<String> set = new TreeSet<String>();
		Iterator<Entry<String, TreeSet<String>>> itr = super.entrySet().iterator();
		while (itr.hasNext()){
			set.add(itr.next().getKey());
		}
		return set;
	}
}
