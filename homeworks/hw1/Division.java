import java.util.ArrayList;
import java.util.Iterator;

public class Division extends Unit {
	public Unit head;
	public Unit tail;

	public ArrayList<Unit> units;

	public Division(String name) {
		units = new ArrayList<>();
		this.name = name;
	}

	public String showSoldiers() {
		String result = "";
		for (int index = 0; index < units.size() - 1; index++) {
			result = makeResultUnitDelimiter(result, index, false);
		}
		int size = units.size() - 1;
		if (size >= 0) {
			result = makeResultUnitDelimiter(result, size, true);
		}

		return result;
	}

	private String makeResultUnitDelimiter(String result, int index, boolean isTail){
		String strUnit = new String();
		if(units.get(index) instanceof Brigade){
			strUnit = ((Brigade) units.get(index)).showSoldiers() ;
		} else {
			strUnit = ((Division) units.get(index)).showSoldiers();
		}
		if(isTail && result.length() >= 2){
			result = strUnit != ""? result + strUnit : result.substring(0, result.length()-2);
		} else if (isTail) {
			result = strUnit != ""? result + strUnit : result;
		} else {
			result += strUnit != ""? result + strUnit + ", " : result;
		}
		
		return result;
	}

	public String show() {
		return "[" + showSoldiers() + "]";

	}
}
