package SDA;

import java.util.HashMap;
import java.util.LinkedList;


public class FrontBookkeeper61784 implements IFrontBookkeeper {

	private HashMap<String, LinkedList<Integer>> mapOfSoldiers = new HashMap<String, LinkedList<Integer>>();
	private HashMap<String, String> mapOfattached = new HashMap<String, String>();

	@Override
	public String updateFront(String[] news) {
		for (String story : news) {
			String[] singleStory = story.split(" ");
			if (story.contains(" = ")) {
				soldierAddAction(story);
			}
			if (story.contains("show")) {
				showAction(singleStory);
			}
			if (story.contains("attached")) {
				attachedAction(singleStory, story);
			}
			if (story.contains("died")) {
				dieAction(singleStory);
			}
		}
		return "Empty news sheet";
	}

	public void showAction(String[] singleStory) {
		if (singleStory.length == 2) {
			System.out.println(mapOfSoldiers.get(singleStory[1]));
		} else if (singleStory.length == 3) {
			System.out.println(getKeyFromValue(mapOfSoldiers, singleStory[2]));
		}
	}

	public void attachedAction(String[] singleStory, String story) {
		String firstSoldier = singleStory[0];
		LinkedList<Integer> firstSoldierValuesValuesValues = mapOfSoldiers
				.get(firstSoldier);

		String secondSoldier = singleStory[3];
		LinkedList<Integer> secondSoldierValues = mapOfSoldiers
				.get(secondSoldier);

		if (mapOfattached.containsKey(firstSoldier)) {
			removeWhoAttached(singleStory[0]);
		}
		mapOfattached.put(firstSoldier, secondSoldier);

		if (story.contains("after")) {
			String element = singleStory[6];
			int indexElement = secondSoldierValues.indexOf(Integer
					.parseInt(element));
			secondSoldierValues.addAll(indexElement + 1,
					firstSoldierValuesValuesValues);
		} else {
			secondSoldierValues.addAll(firstSoldierValuesValuesValues);
		}

	}

	public void soldierAddAction(String story) {
		String[] soldiersCharacteristics = story.split(" = ");
		String soldier = soldiersCharacteristics[0];
		LinkedList<Integer> soldierValues = convertToInt(soldiersCharacteristics[1]);

		mapOfSoldiers.put(soldier, soldierValues);
	}

	public void dieAction(String[] singleStory) {
		String deadSoldier = singleStory[3];
		String rangeIndexes = singleStory[1];
		String[] indexes = rangeIndexes.split("\\..");

		int firstIndex = mapOfSoldiers.get(deadSoldier).indexOf(
				Integer.parseInt(indexes[0]));
		int lastIndex = mapOfSoldiers.get(deadSoldier).indexOf(
				Integer.parseInt(indexes[1]));

		LinkedList<Integer> valuesSoldier = mapOfSoldiers.get(deadSoldier);
		LinkedList<Integer> dead = listOfDelete(valuesSoldier, firstIndex,
				lastIndex);
		
		valuesSoldier.removeAll(dead);
		
		for (String key : mapOfattached.keySet()) {
			mapOfSoldiers.get(key).removeAll(dead);
		}
	}

	private LinkedList<Integer> listOfDelete(LinkedList<Integer> valuesSoldier,
			int first, int last) {
		LinkedList<Integer> result = new LinkedList<>();
		for (int i = first; i <= last; i++) {
			int element = valuesSoldier.get(i);
			result.add(element);
		}
		return result;
	}

	private String getKeyFromValue(
			HashMap<String, LinkedList<Integer>> map, String value) {
		String result = "";
		for (String o : map.keySet()) {
			if (map.get(o).contains(Integer.parseInt(value))) {
				result += " " + o;
			}
		}
		return result;
	}
	
	private String getKey(HashMap<String, String> map, String value){
		String result = "";
		for (String o : map.keySet()) {

				if (map.get(o).contains(value)) {
					result+= " " + o;
				}
			}
		return result;
	}

	private void removeWhoAttached(String soldier) {
		String attachedSoldier = mapOfattached.get(soldier);
		LinkedList<Integer> valuesSoldier = mapOfSoldiers.get(attachedSoldier);
		LinkedList<Integer> valuesAttachedSoldier = mapOfSoldiers.get(soldier);

		valuesSoldier.removeAll(valuesAttachedSoldier);
	}

	private LinkedList<Integer> convertToInt(String str) {
		LinkedList<Integer> soldiers = new LinkedList<Integer>();
		if (str.equals("[]")) {
		} else {
			String newStr = str.substring(1, str.length() - 1);

			String[] arrs = newStr.split(", ");
			for (String arr : arrs) {
				soldiers.add(Integer.parseInt(arr));
			}
		}
		return soldiers;
	}
}