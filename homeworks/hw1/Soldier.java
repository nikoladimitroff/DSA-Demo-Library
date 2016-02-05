import java.util.ArrayList;

public class Soldier {
	public int indexSoldier;
	public Soldier next;
	public Soldier previous;
	public Brigade brigade;

	public Soldier(int indexSoldier) {
		this.indexSoldier = indexSoldier;
	}

	public ArrayList<Division> ancestors() {
		ArrayList<Division> ancestors = new ArrayList<>();
		Division current = this.brigade.attachedTo;
		while (current != null) {
			ancestors.add(current);
			current = current.attachedTo;
		}
		return ancestors;
	}

	public String showSoldier() {
		String listString = this.brigade.name + ", ";

		ArrayList<Division> ancestors = ancestors();
		for (Division s : ancestors) {
			listString += s.name + ", ";
		}

		return listString.substring(0, listString.length() - 2);
	}

	public static void kill(Soldier soldier1, Soldier soldier2, Division division) {

		if (soldier1.brigade == soldier2.brigade) {
			killFromSameBrigade(soldier1, soldier2);
			
		} else {
			ArrayList<Division> ancestors1 = soldier1.ancestors();
			ArrayList<Division> ancestors2 = soldier2.ancestors();
			// със сигурност имат поне 1 наследник - този, от който трием
			// войниците, този който сме посочили
			int[] indeces = findIndeces(ancestors1, ancestors2, division);
			
			//soldier1.next = null;
			//soldier2.previous = null;

			soldier2.brigade.head = soldier2.next;
			soldier1.brigade.tail = soldier1.previous;
			
			if (soldier1.brigade.head == soldier1){
				soldier1.brigade.head = null;
			}
			
			if (soldier2.brigade.tail == soldier2){
				soldier2.brigade.tail = null;
			}

			for (int i = 1; i <= indeces[0]; i++) {
				ancestors1.get(i).tail = ancestors1.get(i - 1);
				ancestors1.get(i).next = null;
			}

			ancestors1.get(indeces[0]).next = ancestors2.get(indeces[1]);
			ancestors2.get(indeces[1]).tail = ancestors1.get(indeces[0]);

			for (int i = 0; i < indeces[1]; i++) {
				ancestors2.get(i).head = ancestors2.get(i + 1);
				ancestors1.get(i).previous = null;
			}
		}

	}

	private static void killFromSameBrigade(Soldier soldier1, Soldier soldier2) {
		if(soldier1 == soldier1.brigade.head){
			soldier1.brigade.head = soldier2.next;
		} else {
			soldier1.previous.next = soldier2.next;
		}
		
		if (soldier2.next == null) {

			soldier2.brigade.tail = soldier1.previous;
			return;
		}
		soldier2.next.previous = soldier1.previous;
		return;
	}

	@Override
	public String toString() {
		return Integer.toString(this.indexSoldier);
	}

	private static int[] findIndeces(ArrayList<Division> ancestors1, ArrayList<Division> ancestors2, Division d) {
		int[] indeces = { ancestors1.indexOf(d), ancestors1.indexOf(d) };
		return indeces;
	}
}
