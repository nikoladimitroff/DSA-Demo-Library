public class IO {
	public static String doLine(String line, Army army) {
		String[] lineInstructions = line.split(" ");

		if (lineInstructions[0].equals("show") && lineInstructions[1].equals("soldier")) {
			return army.showSoldier(Integer.parseInt(lineInstructions[2]));
		} else if (lineInstructions[0].equals("show")) {
			return army.showUnit(lineInstructions[1]);
		} else if (lineInstructions[0].equals("soldiers")) {
			String[] soldiers = lineInstructions[1].split("\\.\\.");
			army.killSoldiers(Integer.parseInt(soldiers[0]), Integer.parseInt(soldiers[1]), lineInstructions[3]);
		} else if (lineInstructions[1].equals("=")) {
			if (lineInstructions[2].length() == 2) {
				army.makeDivision(lineInstructions[0]);
			} else {
				army.makeBrigade(lineInstructions[0], makeArray(line.split(" = ")[1]));
			}
		} else if (lineInstructions[1].equals("attached") && lineInstructions.length == 4) {
			army.attachTo(lineInstructions[0], lineInstructions[3]);
		} else {
			army.attachToAfter(lineInstructions[0], lineInstructions[3], Integer.parseInt(lineInstructions[6]));
		}
		return null;
	}

	public static int[] makeArray(String line) {
		String[] soldiersStr = line.substring(1, line.length() - 1).split(", ");
		int[] soldiers = new int[soldiersStr.length];
		for (int i = 0; i < soldiers.length; i++) {
			soldiers[i] = Integer.parseInt(soldiersStr[i]);
		}
		return soldiers;
	}

	public static String doAllNews(String[] AllNews, Army army) {
		StringBuilder s = new StringBuilder();

		String results = new String();
		for (int i = 0; i < AllNews.length; i++) {
			// results += ;
			String res = doLine(AllNews[i], army);
			if (res != null)
				s.append(res + "\n");
		}

		return s.toString();
	}
}
