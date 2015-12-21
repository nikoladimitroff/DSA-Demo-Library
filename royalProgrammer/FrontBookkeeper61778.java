package first;

public class FrontBookkeeper61778 implements IFrontBookkeeper {
	private Army myArmy = new Army();

	@Override
	public String updateFront(String[] news) {
		String result = "";
		for (int i = 0; i < news.length; i++) {
			String[] temp = news[i].split(" ");
			if (news[i].contains("show")) {
				if (temp.length == 3) {
					result = result.concat(myArmy.searchByIdentifier(temp[2])
							+ "\r\n");
				} else {
					result = result.concat(myArmy.searchByUnitName(temp[1])
							.toString() + "\r\n");
				}

			} else if (news[i].contains("died")) {
				String numbers[] = temp[1].split("\\D+");
				String from = numbers[0];
				String to = numbers[1];
				myArmy.killEm(from, to, temp[3]);
			} else if (news[i].contains("attached")
					&& (!news[i].contains("after"))) {
				myArmy.attachingUnits(temp[3], temp[0]);
			} else if (news[i].contains("after")) {
				myArmy.attachingUnits(temp[3], temp[0]);
			} else {
				myArmy.addingUnit(news[i]);
			}
		}

		return result;

	}
}
