package sda.homework1;

import java.util.LinkedList;
import java.util.Queue;

public class Unit {
	private String name;
	private LinkedList<Integer> elements;
	private LinkedList<String> contain;
	private LinkedList<Unit> unit = new LinkedList<>();
	private Queue<Unit> unitIn = new LinkedList<Unit>();

	public Unit(String name, LinkedList<Integer> elements) {
		this.elements = new LinkedList<>();
		setElements(elements);
		setName(name);
		initContain();
	}

	public Unit(String name) {
		this.elements = new LinkedList<>();
		setElements(elements);
		setName(name);
		initContain();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Integer> getElements() {
		return elements;
	}

	public void setElements(LinkedList<Integer> elements) {
		this.elements = elements;
	}

	public void addLast(LinkedList<Integer> items) {
		if (this.elements.peek() == 0)
			this.elements.poll();
		int size = elements.size();
		this.elements.addAll(size, items);
	}

	public void addAfter(int index, LinkedList<Integer> items) {
		if (this.elements.peek() == 0)
			this.elements.poll();
		this.elements.addAll(index, items);
	}

	private void initContain() {
		contain = new LinkedList<>();
		contain.add(this.name);
	}

	public void isContain(String str) {
		contain.add(str);
	}

	public String toString() {
		System.out.println(this.name);
		System.out.println(this.elements.toString());
		return "";
	}

	public LinkedList<String> getContain() {
		return contain;
	}

	public LinkedList<Unit> getUnit() {
		return unit;
	}

	public void setUnit(LinkedList<Unit> unit) {
		this.unit = unit;
	}

	public void addUnit(Unit unit) {
		this.unit.add(unit);
		this.elements.addAll(unit.elements);
		unit.addedIn(this);

	}

	public void addedIn(Unit unit) {
		this.unitIn.add(unit);
		if (this.unitIn.size() > 1) {
			this.unitIn.peek().elements.removeAll(this.elements);
			this.unitIn.poll();
		}
	}
}
