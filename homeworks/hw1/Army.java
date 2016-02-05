import java.util.HashMap;

public class Army {
	HashMap<Integer, Soldier> soldiers = new HashMap<>();
	HashMap<String, Brigade> brigades = new HashMap<>();
	HashMap<String, Division> divisions = new HashMap<>();
	
	public void killSoldiers(int s1, int s2, String division){
		Soldier s1Soldier = soldiers.get(s1);
		Soldier s2Soldier = soldiers.get(s2);
		Soldier.kill(s1Soldier,s2Soldier, divisions.get(division));
	}
	
	public void makeBrigade(String name, int[] soldiers){
		brigades.put(name, new Brigade(soldiers, this, name));
	}
	
	public void makeDivision(String name){
		divisions.put(name, new Division(name));
	}
	
	public void attachTo(String unit, String division){
		findUnit(unit).attachTo(divisions.get(division));
	}
	
	public void attachToAfter(String unitName, String division, int soldier){
		findUnit(unitName).attachTo(divisions.get(division), soldiers.get(soldier));
	}
	
	private Unit findUnit(String unit){
		if(brigades.containsKey(unit)) {
			return brigades.get(unit);
		} else {
			return divisions.get(unit);
		}
	}
	
	public String showUnit(String unitName){
		Unit unit = findUnit(unitName);
		if(unit instanceof Brigade){
			return ((Brigade) unit).show();
		} else {
			return ((Division) unit).show();
		}
	}
	
	public String showSoldier(int soldier){
		Soldier s = this.soldiers.get(soldier);
		if(s == null){
			return null;
			// да връща ли нов ред? празен ли е този?
		}
		return s.showSoldier();
	}

}
