
public class Brigade extends Unit {
	public Soldier head;
	public Soldier tail;
	public Army army;

	public Brigade(int[] soldiers, Army army, String name) {
		this.army = army;
		assignSoldiers(soldiers);
		this.name = name;
		
	}

	public Brigade() {
		head = null;
		tail = null;
	}

	public void assignSoldiers(int[] soldiers) {
		Soldier s = new Soldier(soldiers[0]);
		this.head = s;
		this.head.previous = null;

		Soldier previous = s;
		
		int i = 1;
		for (; i < soldiers.length; i++) {
			s = new Soldier(soldiers[i]);
			previous.brigade = this;
			army.soldiers.put(soldiers[i - 1], previous);
			s.previous = previous;
			previous.next = s;
			previous = s;
		}
		previous.brigade = this;
		army.soldiers.put(soldiers[i - 1], previous);
		this.tail = previous;
		this.tail.next = null;
		
	}

	public String show() {
		return "[" + showSoldiers() + "]";
	}

	public String showSoldiers() {
		String result = "";
		Soldier current = head;
		// middle section
		while (current != tail && current != null) {
			result += Integer.toString(current.indexSoldier) + ", ";
			current = current.next;
		}
		//tail
		if (tail != null) {
			result += Integer.toString(tail.indexSoldier);
		}
		return result;
	}
}
