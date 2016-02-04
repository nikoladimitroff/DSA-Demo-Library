import java.util.ArrayList;
import java.util.List;

public class Unit{
	//Constructor for DataContainer Unit e.g. [1, 2, 3]
	Unit(List<Integer> data_, Unit parent){
		this.data = data_;
		this.parent = parent;
		this.type = 0;
	}
	//Constructor for first level Unit e.g. a = [1, 2, 3]
	Unit(List<Integer> data_, String name){
		this.children.add(new Unit(data_, this));
		this.type = 2;
		this.name = name;
	}
	//Custom constructor (type 1 is for attached units)
	Unit(int type_, String name){
		this.type = type_;
		this.name = name;
	}
	public int type;
	public String name = "";
    public List<Integer> data = new ArrayList<Integer>();
    public Unit parent;
    public List<Unit> children = new ArrayList<Unit>();
}