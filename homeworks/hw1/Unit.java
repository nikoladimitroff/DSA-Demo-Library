import java.util.ArrayList;

public abstract class Unit {
	public Unit tail;
	public Unit head;
	public Unit previous;
	public Unit next;
	public Division attachedTo = null;
	public String name;
	
	
	public void attachTo(Division division){
		checkDetach();
		division.next = this;
		this.previous = division;
		attachedTo = division;
		division.units.add(this);
	}
	
	public void attachTo(Division division, Soldier soldier){
		checkDetach();
		ArrayList<Division> ancestors = soldier.ancestors();
		int length = ancestors.size();
		int index = ancestors.indexOf(division) - 1;
		
		//Ако се съдържа в 1 дивизия само и това е най-външният слой - division-а
		if(index < 0) {
			attachBetween(soldier.brigade);
		} else {
			//В противен случай променям долния слой, който съдържа и двете.
			attachBetween(ancestors.get(index));
		}
		
		attachedTo = division;
		division.units.add(this);
		
	}
	
	protected void attachBetween(Unit unit){
		if (unit.next != null) {
			unit.next.previous = this;	
		}
		this.next = unit.next;
		this.previous = unit;
		unit.next = this;
	}
	protected void checkDetach(){
		if(this.attachedTo != null){
			if (this.next != null){
				this.next.previous = this.previous;
			}
			this.attachedTo.units.remove(this);
			this.previous.next = null;
			this.previous = this.next = null;
			this.attachedTo = null;
		}
		
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
