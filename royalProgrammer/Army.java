package first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Army {
	private HashMap<String, ArrayList<String>> units;
	private HashMap<String, ArrayList<String>> soldiersMap;
	private HashMap<String, String> unitsMap;

	public Army() {
		units = new HashMap<>();
		soldiersMap = new HashMap<>();
		unitsMap = new HashMap<>();
	}

	public void addingUnit(String news) {
		String[] temp = news.split(" = "); // works fine, tested
		temp[1] = temp[1].substring(1);
		String[] tempSoldiers = temp[1].split("\\D+");
		ArrayList<String> soldiers = new ArrayList<String>(
				Arrays.asList(tempSoldiers));
		units.put(temp[0], soldiers);

		for (int i = 0; i < tempSoldiers.length; i++) {
			addToList(tempSoldiers[i], temp[0]);
		}

	}

	public void killEm(String from, String to, String unit) {
		ArrayList<String> temp = units.get(unit);
		ArrayList<String> temp2 = new ArrayList<>();
		for (int i = temp.indexOf(from); i <= temp.indexOf(to); i++) {
			temp2.add(temp.get(i));
			soldiersMap.put(temp.get(i), null);
		}
		removeSolUnit(unit, temp2);
		for (String key : units.keySet()) {
			if (units.get(key).contains(to)) {
				removeSolUnit(key, temp2);
			}
		}

	}

	public ArrayList<String> searchByUnitName(String unitName) {
		ArrayList<String> soldiersInside = new ArrayList<String>();
		soldiersInside = units.get(unitName);
		return soldiersInside;
	}

	public String searchByIdentifier(String identifier) {
		ArrayList<String> temp = soldiersMap.get(identifier);
		String unitIncludedIn = "";
		if (temp == null) {
			return unitIncludedIn;
		} else {
			for (int i = 0; i < temp.size(); i++) {
				unitIncludedIn = unitIncludedIn + ", " + temp.get(i);
			}
			unitIncludedIn = unitIncludedIn.substring(2); // removing leading ,
			return unitIncludedIn;
		}

	}

	public void attach(String where, String what) {
		ArrayList<String> toBeAdded = units.get(what);
		addSolUnit(where, toBeAdded);
		for (int i = 0; i < toBeAdded.size(); i++) {
			addToList(toBeAdded.get(i), where);
		}

	}

	public void attachingUnits(String where, String what) {
		if (unitsMap.containsKey(what)) {
			removeSolUnit(unitsMap.get(what), units.get(what));
		}
		ArrayList<String> toBeAdded = units.get(what);
		addSolUnit(where, toBeAdded);
		for (int i = 0; i < toBeAdded.size(); i++) {
			addToList(toBeAdded.get(i), where);
		}
		unitsMap.put(what, where);
	}

	private synchronized void addSolUnit(String unitName,
			ArrayList<String> toBeAdded) {
		ArrayList<String> list = units.get(unitName);
		list.addAll(toBeAdded);

	}

	private synchronized void removeSolUnit(String unitName,
			ArrayList<String> toBeRemoved) {
		ArrayList<String> list = units.get(unitName);
		list.removeAll(toBeRemoved);

	}

	private synchronized void addToList(String id, String unit) {
		ArrayList<String> list = soldiersMap.get(id);
		if (list == null) {
			list = new ArrayList<String>();
			list.add(unit);
			soldiersMap.put(id, list);
		} else {
			list.add(unit);
		}

	}

}
