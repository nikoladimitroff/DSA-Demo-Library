package first;

import java.util.ArrayList;
import java.util.HashMap;

public class Test {
	HashMap<String, ArrayList<String>> myMap = new HashMap<>();

	public static void main(String[] args) {
		Test test = new Test();
		test.addToList("13", "division1");
		test.addToList("13", "division2");
		test.addToList("13", "division3");
		test.show("13");

	}

	public synchronized void addToList(String mapKey, String id) {
		ArrayList<String> list = myMap.get(mapKey);
		if (list == null) {
			list = new ArrayList<String>();
			list.add(id);
			myMap.put(mapKey, list);
		} else {
			list.add(id);
		}

	}

	public void show(String id) {
		ArrayList<String> list = myMap.get(id);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

}
