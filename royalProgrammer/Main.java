package first;


public class Main {

	public static void main(String[] args) {
		/*
		 * BufferedReader console = new BufferedReader(new InputStreamReader(
		 * System.in)); FrontBookkeeper61778 bkeeper = new
		 * FrontBookkeeper61778(); String text = ""; while
		 * (!text.equals("exit")) { try { text = console.readLine();
		 * bkeeper.updateFront(text); //
		 * System.err.println(bkeeper.updateFront(text)); } catch (IOException
		 * e) { e.printStackTrace(); }
		 * 
		 * }
		 */
		FrontBookkeeper61778 bkeeper = new FrontBookkeeper61778();
		String[] arr = {
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
				"brigade_Ignatov attached to division_Dimitroff # division_Dimitroff == [1, 2, 3, 13]",
				"show division_Dimitroff",
				"soldiers 2..3 from division_Dimitroff died heroically ",
				"show regiment_Stoykov", "show brigade_Ignatov",
				"show division_Dimitroff" };
		System.out.println(bkeeper.updateFront(arr));
	}
}
