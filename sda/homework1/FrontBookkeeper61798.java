package sda.homework1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FrontBookkeeper61798 implements IFrontBookkeeper {
	private static HashMap<String, Unit> units = new HashMap<>();

	public String updateFront(String[] news) {
		for (int i = 0; i < news.length; ++i) {
			function(news[i]);
		}
		return null;
	}

	private static void function(String s) {
		if (s.contains("="))
			assign(s);
		if (s.contains("after"))
			attachAfter(s);
		else if (s.contains("attached"))
			attach(s);
		if (s.contains("died"))
			death(s);
		if (s.contains("show soldier"))
			showSoldier(s);
		else if (s.contains("show"))
			show(s);

	}

	// ASSIGN
	public static void assign(String str) {
		String[] subStr = str.split(" = ");
		if (subStr[1].length() < 3)
			units.put(subStr[0], new Unit(subStr[0]));
		else {
			LinkedList<Integer> elements = new LinkedList<>();
			elements.addAll(addEl(subStr[1]));
			units.put(subStr[0], new Unit(subStr[0], elements));
		}
	}

	private static LinkedList<Integer> addEl(String str) {
		String[] items = str.replaceAll("\\[", "").replaceAll("\\]", "")
				.split(", ");
		LinkedList<Integer> elements = new LinkedList<>();
		for (int i = 0; i < items.length; ++i) {
			try {
				elements.add(Integer.parseInt(items[i]));
			} catch (NumberFormatException nfe) {
			}
			;
		}
		return elements;
	}

	// ATTACH
	public static void attach(String str) {
		String[] subStr = str.split(" attached to ");
		if (units.containsKey(subStr[0])
				&& ((!(units.get(subStr[1]).getElements().size() < 1))
						&& !(units.get(subStr[1]).getUnit().isEmpty()) || units
						.get(subStr[1]).getElements().size() < 1)) {
			units.get(subStr[1]).addUnit(units.get(subStr[0]));
		}
		units.get(subStr[0]).isContain(subStr[1]);
		units.get(subStr[1]).getContain()
				.addAll(units.get(subStr[0]).getContain());
	}

	// ATTACH AFTER
	public static void attachAfter(String str) {
		String[] subStr = str.split(" ");
		units.get(subStr[3]).addAfter(Integer.parseInt(subStr[6]),
				units.get(subStr[0]).getElements());
		units.get(subStr[0]).isContain(subStr[3]);
		units.get(subStr[3]).getContain()
				.addAll(units.get(subStr[0]).getContain());
	}

	// DEATH
	public static void death(String str) {
		String[] subStr = str.split(" ");
		String[] soldiers = subStr[1].split("\\..");
		int first = Integer.parseInt(soldiers[0]);
		int last = Integer.parseInt(soldiers[1]);
		String unit = subStr[3];
		if (!units.get(unit).getContain().isEmpty()) {
			int len = units.get(unit).getContain().size();
			for (int i = 0; i < len; ++i) {
				removeSoldiers(first, last, units.get(unit).getContain().get(i));
			}
		}
	}

	// removeSoldiers
	private static void removeSoldiers(int first, int last, String unit) {
		for (int i = first; i <= last; ++i) {
			Object x = (Integer) i;
			if (units.get(unit).getElements().contains(x)) {
				units.get(unit).getElements().remove(x);
			}
		}
	}

	// SHOW
	public static void show(String str) {
		String[] subStr = str.split(" ");
		if (units.get(subStr[1]).getElements().size() < 2) {
			System.out.println("[]");
		} else
			System.out.println(units.get(subStr[1]).getElements().toString());
	}

	// SHOW_SOLDIER
	public static String showSoldier(String str) {
		LinkedList<String> unitsIn = new LinkedList<>();
		String[] subStr = str.split(" ");
		for (Map.Entry<String, Unit> entry : units.entrySet()) {
			String key = entry.getKey();
			Unit value = entry.getValue();
			if (value.getElements().contains(Integer.parseInt(subStr[2]))) {
				unitsIn.add(key);
			}
		}
		System.out.println(unitsIn.toString());
		return unitsIn.toString();
	}
}
