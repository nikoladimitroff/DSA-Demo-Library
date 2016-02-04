
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String commands[] = 
		{
				"regiment_Stoykov = [1, 2, 3]",
				"show regiment_Stoykov",
				"regiment_Chaushev = [13]",
				"show soldier 13",
				"division_Dimitroff = []",
				"regiment_Stoykov attached to division_Dimitroff",
				"regiment_Chaushev attached to division_Dimitroff",
				"show division_Dimitroff",
				"show soldier 13",
				"brigade_Ignatov = []",
				"regiment_Stoykov attached to brigade_Ignatov",
				"regiment_Chaushev attached to brigade_Ignatov after soldier 3",
				"show brigade_Ignatov",
				"show division_Dimitroff",
				"brigade_Ignatov attached to division_Dimitroff",
				"show division_Dimitroff",
				"soldiers 2..3 from division_Dimitroff died heroically",
				"show regiment_Stoykov",
				"show brigade_Ignatov",
				"show division_Dimitroff"
		};

		FrontBookkeeper61834 test = new FrontBookkeeper61834();
		System.out.println(test.updateFront(commands));
	}

}
