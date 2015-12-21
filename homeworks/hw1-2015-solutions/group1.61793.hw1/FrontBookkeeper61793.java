package hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface IFrontBookkeeper {
    String updateFront(String[] news);
}

public class FrontBookkeeper61793 implements IFrontBookkeeper {
    
    /**
     * A Unit can only be exactly one of the following things ::
     * 1) An Unit can only contain soldiers (list of integers).
     * or
     * 2) An Unit can only be composed of other Units.
     */
    public static class Unit {
        public String label;
        public Unit parent;
        public boolean isComposite;

        private LinkedList<Integer> soldiers;
        private LinkedList<Unit> children;
        
        public Unit(String label, boolean isComposite) {
            this.isComposite = isComposite;
            this.label = label;
            if (isComposite) {
                this.children = new LinkedList<Unit>();
            } else {
                this.soldiers = new LinkedList<Integer>();        
            }
        }
        
        public LinkedList<Unit> getChildren() {
            if (children == null) {
                throw new IllegalStateException("The unit " + this.label
                        + " is NOT composite, it contains only soldiers!");
            }
            return children;
        }
        
        public LinkedList<Integer> getSoldiers() {
            if (soldiers == null) {
                throw new IllegalStateException("The unit " + this.label
                        + " is composite and contains only other units (no soldiers)!");
            }
            return soldiers;
        }
        
        @Override
        public String toString() {
            return soldiers.toString();
        }
    }

    private Map<String, Unit> units;        // units: unit label -> unit
    private Map<Integer, Unit> soldiers;    // soldiers: soldier ID -> initial unit
    
    public FrontBookkeeper61793() {
        units = new HashMap<String, Unit>();
        soldiers = new HashMap<Integer, Unit>();
    }
    
    @Override
    public String updateFront(String[] news) {
        StringBuilder sb = new StringBuilder();
        
        for (String string : news) {
            if (string.contains("= [")) {
                unitAssignment(string);
            } else if (string.contains("after soldier")) {
                unitPositionalAttachment(string);
            } else if (string.contains("attached to")) {
                unitAttachment(string);
            } else if (string.contains("died heroically")) {
                soldierDeath(string);
            } else if (string.contains("show soldier")) {
                sb.append(soldierDisplay(string)).append("\n");
            } else {
                sb.append(unitDisplay(string)).append("\n");
            }
        }
        
        return sb.toString().trim();
    }
    
    private void unitAssignment(String command) {
        Matcher match = Pattern.compile("^(.*?) = \\[(.*?)\\]$").matcher(command);
        if (!match.find() && match.groupCount() != 2) {
            throw new IllegalArgumentException(command + " is illegally formatted!");
        }

        String unitId = match.group(1);
        String soldiersString = match.group(2);
        
        int[] soldiers = null;
        if (soldiersString.isEmpty()) { // no soldier ids, thus a composite unit
            this.units.put(unitId, new Unit(unitId, true));
            return;
        }
        else if (soldiersString.contains(",")) {    // at least 2 soldier ids
            soldiers = Arrays.stream(soldiersString.split(",\\s"))
                    .mapToInt((s) -> {return Integer.valueOf(s);}).toArray();
        } else {    // only one soldier id
            soldiers = new int[1];
            soldiers[0] = Integer.valueOf(soldiersString);
        }

        Unit unit = new Unit(unitId, false);    // this unit contains only soldiers
        for (int i = 0; i < soldiers.length; ++i) {
            unit.getSoldiers().add(soldiers[i]);
            this.soldiers.put(soldiers[i], unit);
        }
        this.units.put(unitId, unit);
    }
    
    private void unitAttachment(String command) {
        Matcher match = Pattern.compile("^(.*?) attached to (.*?)$").matcher(command);
        if (!match.find() && match.groupCount() != 2) {
            throw new IllegalArgumentException(command + " is illegally formatted!");
        }
        Unit unit1 = this.units.get(match.group(1));
        Unit unit2 = this.units.get(match.group(2));

        if (unit1.parent != null) { // if unit1 is already attached to another unit
            unit1.parent.getChildren().remove(unit1);   // detach unit1 from its current parent
        }
        unit1.parent = unit2;
        unit2.getChildren().add(unit1); // we need to append unit1 to the rightmost sub-unit of unit2
    }
        
    private void unitPositionalAttachment(String command) {
        Matcher match = Pattern.compile("^(.*?) attached to (.*?) after soldier (.*?)$").matcher(command);
        if (!match.find() && match.groupCount() != 3) {
            throw new IllegalArgumentException(command + " is illegally formatted!");
        }
        Unit unit1 = this.units.get(match.group(1));    // the unit to be attached
        Unit unit2 = this.units.get(match.group(2));
        
        int soldierId = Integer.valueOf(match.group(3));
        // the unit in which the soldier was initially put on creation
        Unit soldierContainer = this.soldiers.get(soldierId);
        int indexOfSoldierContainer = soldierContainer.parent.getChildren().indexOf(soldierContainer);
        
        if (unit1.parent != null) { // if unit1 is already attached to another unit
            unit1.parent.getChildren().remove(unit1);   // detach unit1 from its current parent
        }
        // insert unit1 after the unit which contains the given soldierID
        soldierContainer.parent.getChildren().add(indexOfSoldierContainer + 1, unit1);
        unit1.parent = unit2;
    }
    
    private void soldierDeath(String command) {
        Matcher match = Pattern.compile("^soldiers (.*?)\\.\\.(.*?) from (.*?) died heroically$").matcher(command);
        if (!match.find() && match.groupCount() != 3) {
            throw new IllegalArgumentException(command + " is illegally formatted!");
        }
        int low = Integer.valueOf(match.group(1)), high = Integer.valueOf(match.group(2));
        Unit unit = this.units.get(match.group(3));
        
        dfsDelete(unit, low, high);
    }
    
    private void dfsDelete(Unit unit, int low, int high) {
        if (!unit.isComposite) {    // bottom
            for (Iterator<Integer> iter = unit.getSoldiers().iterator(); iter.hasNext();) {
                int temp = iter.next();
                if (low <= temp && temp <= high) {
                      iter.remove();
                }
            }
            return;
        }
        // otherwise the unit is composite
        
        for (Unit subUnit : unit.getChildren()) {
            dfsDelete(subUnit, low, high);
        }
    }
    
    private String unitDisplay(String command) {
        Matcher match = Pattern.compile("^show (.*?)$").matcher(command);
        if (!match.find() && match.groupCount() != 1) {
            throw new IllegalArgumentException(command + " is illegally formatted!");
        }
        Unit unit = this.units.get(match.group(1));

        return accumulateSoldiers(unit).toString();
    }
    
    /**
     * Get all soldiers in-order (DFS) belonging to the given unit
     * @param   unit
     * @return  a list of all soldier IDs
     */
    private Collection<Integer> accumulateSoldiers(Unit unit) {
        if (!unit.isComposite) {    // bottom
            return unit.getSoldiers();
        }
        // otherwise the unit is composite
        
        Collection<Integer> soldiers = new LinkedList<Integer>();
        for (Unit subUnit : unit.getChildren()) {
            soldiers.addAll(accumulateSoldiers(subUnit));
        }
        
        return soldiers;
    }
    
    private String soldierDisplay(String command) {
        Matcher match = Pattern.compile("^show soldier (.*?)$").matcher(command);
        if (!match.find() && match.groupCount() != 1) {
            throw new IllegalArgumentException(command + " is illegally formatted!");
        }
        Unit initialUnit = this.soldiers.get(Integer.valueOf(match.group(1)));

        LinkedList<String> unitLabels = new LinkedList<String>();
        Unit iter = initialUnit;
        while (iter != null) {
            unitLabels.addLast(iter.label);
            iter = iter.parent;
        }

        String result = unitLabels.toString();
        return result.substring(1, result.length() - 1);    // remove the brackets
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FrontBookkeeper61793 book = new FrontBookkeeper61793();
        
        List<String> strings = new ArrayList<String>();
        String temp = null;
        while (!(temp = scanner.nextLine()).isEmpty()) {
            strings.add(temp);
        }
        
        String[] news = new String[strings.size()];
        for (int i = 0; i < news.length; ++i) {
            news[i] = strings.get(i);
        }
        
        System.out.print(book.updateFront(news));
        scanner.close();
    }

}
