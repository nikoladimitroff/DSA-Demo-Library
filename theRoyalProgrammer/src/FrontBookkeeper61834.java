import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrontBookkeeper61834 implements IFrontBookkeeper {
	private HashMap<String, Unit> units;
	private HashMap<Integer, Unit> soldiers;
	
	FrontBookkeeper61834(){
		units = new HashMap<String, Unit>();
		soldiers = new HashMap<Integer, Unit>();
	}
	/**
	 * @param news
	 */
	public String updateFront(String[] news) {
		String result = "";
		for (String line : news) {
			result+=executeCommand(line);
		}
		return result.trim();
	}
	
	private String executeCommand(String line){
		/**
		 * pattern detecting assigment operation,
		 * returns groups containing tokens with info
		**/
		Pattern assignmentPattern = Pattern.compile("^(\\w*)\\s\\=\\s\\[([\\d\\,\\s]*)\\]$");
		Matcher assignmentMatcher = assignmentPattern.matcher(line);
		
		/**
		 * pattern detecting attachment operation,
		 * returns groups containing division name and brigades
		**/
		Pattern attachmentPattern = Pattern.compile("^(\\w*)\\sattached\\sto\\s(\\w*)$");
		Matcher attachmentMatcher = attachmentPattern.matcher(line);
		
		/**
		 * pattern detecting attachment AFTER operation,
		 * returns groups containing division name and brigades
		**/
		Pattern attachmentAfterPattern = Pattern.compile("^(\\w*)\\sattached\\sto\\s(\\w*)\\safter\\ssoldier\\s(\\d*)$");
		Matcher attachmentAfterMatcher = attachmentAfterPattern.matcher(line);
		
		/**
		 * pattern detecting death operation,
		 * returns groups with soldiers interval and division name
		**/
		Pattern soldierDeathPattern = Pattern.compile("^soldiers\\s(\\d*)..(\\d*)\\sfrom\\s(\\w*)\\sdied\\sheroically$");
		Matcher soldierDeathMatcher = soldierDeathPattern.matcher(line);
		
		/**
		 * pattern detecting show operation,
		 * returns groups with soldiers interval and division name
		**/
		Pattern showPattern = Pattern.compile("^show\\s(\\w*)$");
		Matcher showMatcher = showPattern.matcher(line);
		
		/**
		 * pattern detecting show soldier operation,
		 * returns groups with soldiers interval and division name
		**/
		Pattern showSoldierPattern = Pattern.compile("^show\\ssoldier\\s(\\d*)$");
		Matcher showSoldierMatcher = showSoldierPattern.matcher(line);
		
		if (assignmentMatcher.find()) {
		   String nameParam = assignmentMatcher.group(1);
		   String values = assignmentMatcher.group(2);
		   List<Integer> ints = new ArrayList<Integer>();
		   Pattern lastDigitPattern = Pattern.compile("(\\d+)[^\\d]*$");
		   Matcher lastDigitMatcher = lastDigitPattern.matcher(values);
		   Pattern digitPattern = Pattern.compile("(\\d*)\\,\\s");
		   Matcher m = digitPattern.matcher(values);
		   while(m.find()){
			   ints.add(Integer.parseInt(m.group(1)));
		   }
		   if(lastDigitMatcher.find()){
			  ints.add(Integer.parseInt(lastDigitMatcher.group(1)));
		   }
		   assignOperation(nameParam, ints);
		}
		else if(attachmentMatcher.find()){
			String nameA = attachmentMatcher.group(1);
			String nameB = attachmentMatcher.group(2);
			
			attachmentOperation(nameA, nameB);
		}
		else if(attachmentAfterMatcher.find()){
			String nameA = attachmentAfterMatcher.group(1);
			String nameB = attachmentAfterMatcher.group(2);
			int afterIndex = Integer.parseInt(attachmentAfterMatcher.group(3));
			
			attachmentAfterOperation(nameA, nameB, afterIndex);
		}
		else if(soldierDeathMatcher.find()){
			int intervalFrom = Integer.parseInt(soldierDeathMatcher.group(1));
			int intervalTo = Integer.parseInt(soldierDeathMatcher.group(2));
			String divisionName = soldierDeathMatcher.group(3);
			soldierDeath(intervalFrom, intervalTo, divisionName);
		}
		else if(showMatcher.find()){
			String divisionName = showMatcher.group(1);
			return showOperation(divisionName);
		}
		else if(showSoldierMatcher.find()){
			int soldierId = Integer.parseInt(showSoldierMatcher.group(1));
			return showSoldierOperation(soldierId);
		}else{
			System.out.println("Invalid command near \""+line+"\"");
		}
		return "";
	};
	private void soldierDeath(int from, int to, String unit){
		for (int i = from; i <= to; i++) {
			if(!soldiers.containsKey(i))continue;
			Unit parent = soldiers.get(i);
			parent.data.remove(parent.data.indexOf(i));
			soldiers.remove(i);
		}
	}
	private List<Integer> getUnit(String nameElement){
		Unit parentElement = units.get(nameElement);
		List<Integer> childElements = new ArrayList<Integer>();
		if(parentElement.type == 2){
			for (Unit unit : parentElement.children) {
				if(unit.type == 0){
					childElements.addAll(unit.data);
				}
			}
			return childElements;
		}
		Stack<Unit> dfsStack = new Stack<Unit>();
		dfsStack.push(parentElement);
		ArrayList<Unit> visited = new ArrayList<Unit>();
		while(dfsStack.size()>0){
			Unit current = dfsStack.pop();
			if(visited.indexOf(current)<0){
				visited.add(current);
				if(current.type == 0){
					childElements.addAll(current.data);
				}else{
					for (int i = current.children.size()-1; i >= 0; i--) {
						dfsStack.push(current.children.get(i));
					}
				}
			}
		}
		return childElements;
	}
	private List<String> getSoldier(Integer soldier){
		Unit currentNode = soldiers.get(soldier);
		List<String> parentUnits = new ArrayList<String>();
		while(currentNode != null){
			if(currentNode.name != ""){
				parentUnits.add(currentNode.name);
			}
			currentNode = currentNode.parent;
		}
		return parentUnits;
	}
	private void assignOperation(String nameElement, List<Integer> integers){
		if(integers.size() == 0){
			units.put(nameElement, new Unit(1, nameElement));
		}else{
			units.put(nameElement, new Unit(integers, nameElement));
			Unit parentNode = units.get(nameElement);
			for (Integer soldier : integers) {
				soldiers.put(soldier, parentNode.children.get(0));
			}
		}
	}
	
	/**
	 * Shows unit
	 * @param nameElement
	 */
	private String showOperation(String nameElement){
		List<Integer> selectedUnits = getUnit(nameElement);
		StringJoiner joiner = new StringJoiner(", ");
		for (Integer unit : selectedUnits) {
			joiner.add(unit.toString());
		}
		String joinedString = "["+joiner.toString()+"]";
		return joinedString+"\n";
	}
	
	/**
	 * Shows soldier
	 * @param soldierId
	 */
	private String showSoldierOperation(int soldierId){
		List<String> selectedUnits = getSoldier(soldierId);
		StringJoiner joiner = new StringJoiner(", ");
		for (String soldier : selectedUnits) {
			joiner.add(soldier);
		}
		String joinedString = "["+joiner.toString()+"]";
		return joinedString+"\n";
	}
	
	/**
	 * Attaches elementA to elementB after specified unit
	 * @param elementA
	 * @param elementB
	 * @param afterIndex
	 */
	private void attachmentAfterOperation(String elementA, String elementB, int afterIndex){
		Unit nodeA = units.get(elementA);
		Unit nodeB = units.get(elementB);
		if(nodeA.parent != null){
			int indexOfA = nodeA.parent.children.indexOf(nodeA);
			 nodeA.parent.children.remove(indexOfA);
		}
		Unit dataContainer = soldiers.get(afterIndex);
		ArrayList<Integer> leftChild = new ArrayList<Integer>( dataContainer.data.subList(0, dataContainer.data.indexOf(afterIndex)+1));
		ArrayList<Integer> rightChild = new ArrayList<Integer>( dataContainer.data.subList(dataContainer.data.indexOf(afterIndex)+1, dataContainer.data.size() ));
		Unit leftNode = new Unit(leftChild, dataContainer.parent);
		Unit rightNode = new Unit(rightChild, dataContainer.parent);
		for (Integer soldier : rightChild) {
			soldiers.put(soldier, rightNode);
		}
		for (Integer soldier : leftChild) {
			soldiers.put(soldier, leftNode);
		}
		ArrayList<Unit> newChildren = new ArrayList<Unit>();
		newChildren.add(leftNode);
		newChildren.add(nodeA);
		newChildren.add(rightNode);
		nodeA.parent = dataContainer;
		int indexOfContainer = dataContainer.parent.children.indexOf(dataContainer);
		dataContainer.parent.children.remove(indexOfContainer);
		dataContainer.parent.children.addAll(indexOfContainer, newChildren);
		
	}
	
	/**
	 * Attaches elementA to elementB
	 * @param elementA - name of elementA to be attached to elementB
	 * @param elementB - name of elementB
	 */
	private void attachmentOperation(String elementA, String elementB){
		Unit nodeA = units.get(elementA);
		Unit nodeB = units.get(elementB);
		if(nodeB.type == 0) return;
		if(nodeA.parent != null){
			if(nodeA.type == 2){
				
				for (Unit child : nodeA.children) {
					if(child.type != 0){
						child.parent.parent.children.remove(child.parent.parent.children.indexOf(child));
						child.parent = null;
						attachmentOperation(child.name, nodeA.parent.name);
					}
				}
			}
			int indexOfA = nodeA.parent.children.indexOf(nodeA);
			nodeA.parent.children.remove(indexOfA);
			
		}
		nodeA.parent = nodeB;
		nodeB.children.add(nodeA);
	}

}
