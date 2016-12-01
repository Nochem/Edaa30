package phonebook;


import java.util.Set;

public class myPhoneBook extends SimpleHashMap<String, Integer> implements PhoneBook {
	int size;
	Set<String> set;
	
	public myPhoneBook() {
		super();
		size=0;
	}

	@Override
	public boolean put(String name, String number) {
		int nbr=Integer.parseInt(number);
		if (super.names().contains(name)){
			if (super.put(name, nbr)==null){
				return true;
			}
			return false;
		}
		super.put(name, nbr);
		size++;
		return true;
		
	}

	@Override
	public boolean remove(String name) {
		if (super.remove(name)==null){
			return false;
		}
		size--;
		return true;
	}

	@Override
	public boolean removeNumber(String name, String number) {
		if (super.removeValue(name, number)!=null){
			return true;
		}
		return false;
	}

	@Override //Not sure hashset is the best here, consider alternatives
	public Set<String> findNumbers(String name) {
		return super.findNumbers(name);
	}

	@Override
	public Set<String> findNames(String number) {
		return super.findNames(number);
	}

	@Override
	public Set<String> names() {
		return super.names();
	}
	
	public int size(){
		return size;
	}
}
