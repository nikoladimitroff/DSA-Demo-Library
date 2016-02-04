package royalprogrammer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

public class FrontBookkeeperAuthor implements IFrontBookkeeper {
    // Let's use some regex to make input parsing easier
    Pattern unitDefinitionRegex = Pattern.compile("(?<unit>\\w+) = \\[(?<soldiers>.*)\\]");
    Pattern unitAttachmentRegex = Pattern.compile("(?<unit1>\\w+) attached to (?<unit2>\\w+)(?: after soldier (?<soldier>\\d+))?");
    Pattern soldiersDeathRegex = Pattern.compile("soldiers (?<soldier1>\\d+)..(?<soldier2>\\d+) from (?<unit>\\w+) died heroically");
    Pattern showUnitRegex = Pattern.compile("show (?<unit>\\w+)$");
    Pattern showSoldierRegex = Pattern.compile("show soldier (?<soldier>\\d+)$");

    HashMap<String, Unit> units;
    
    public FrontBookkeeperAuthor() {
        this.units = new HashMap<>();
    }
    
    @Override
    public String updateFront(String[] news) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < news.length; i++) {
            this.updateState(news[i], output);
        }
        return output.toString();
    }
    
    private void updateState(String line, StringBuilder output) {
        Matcher definitionMatch = unitDefinitionRegex.matcher(line);
        if (definitionMatch.matches()) {
            this.defineUnit(definitionMatch.group("unit"), definitionMatch.group("soldiers"));
            return;
        }
        Matcher attachmentMatch = unitAttachmentRegex.matcher(line);
        if (attachmentMatch.matches()) {
            int soldier = -1;
            String soldierIndex = attachmentMatch.group("soldier");
            if (soldierIndex != null) {
                // positional attachment
                soldier = Integer.parseInt(soldierIndex);
            }
            this.attachUnit(attachmentMatch.group("unit1"),
                    attachmentMatch.group("unit2"),
                    soldier);
            return;
        }
        Matcher deathMatch = soldiersDeathRegex.matcher(line);
        if (deathMatch.matches()) {
            int soldier1 = Integer.parseInt(deathMatch.group("soldier1"));
            int soldier2 = Integer.parseInt(deathMatch.group("soldier2"));
            String unitName = deathMatch.group("unit");
            this.killSoldiers(unitName, soldier1, soldier2);
            return;
        }
        Matcher showUnitMatch = showUnitRegex.matcher(line);
        if (showUnitMatch.matches()) {
            output.append(this.showUnit(showUnitMatch.group("unit")));
            return;
        }
        Matcher showSoldierMatch = showSoldierRegex.matcher(line);
        if (showSoldierMatch.matches()) {
            int soldier = Integer.parseInt(showSoldierMatch.group("soldier"));
            output.append(this.showSoldier(soldier));
            return;
        }
    }
    
    private void defineUnit(String unitName, String soldiers) {
        Unit unit = new Unit();
        unit.name = unitName;
        if (soldiers.length() > 0) {
            String[] soldierList = soldiers.replace(" ", "").split(",");
            for (int i = 0; i < soldierList.length; i++) {
                int id = Integer.parseInt(soldierList[i]);
                unit.addSoldier(id);
            }
        }
        this.units.put(unitName, unit);
    }
    
    private void attachUnit(String unitChild, String unitParent, int soldierId) {
        Unit child = this.units.get(unitChild);
        Unit parent = this.units.get(unitParent);
        parent.attach(child, soldierId);
    }
    
    private void killSoldiers(String unit, int soldier1, int soldier2) {
        assert(this.units.containsKey(unit));
        this.units.get(unit).killSoldiers(soldier1, soldier2);
    }
    
    private String showUnit(String unit) {
        assert(this.units.get(unit) != null);
        return this.units.get(unit).toString() + System.lineSeparator();
    }
    
    private String showSoldier(int soldier) {
        // This can be optimized:
        // Currently we are looping over all units,
        // some of the units are children of others which means
        // that'll loop over them several times. Think how to avoid that.
        for (Unit unit : this.units.values()) {
            String soldierUnitList = unit.getSoldierUnits(soldier);
            if (soldierUnitList != null) {
                return soldierUnitList + System.lineSeparator();
            }
        }
        // The soldier's dead
        return "" + System.lineSeparator();
    }
}

class Soldier {
    public int id;
    public Soldier next;
    public Soldier previous;
    public Soldier(int id) {
        this.id = id;
        this.next = null;
        this.previous = null;
    }
}

// Helper class used to return both the soldier and its immediate unit
class SoldierUnitPair {
    public Soldier soldier;
    public Unit unit;
    public SoldierUnitPair(Soldier soldier, Unit unit) {
        this.soldier = soldier;
        this.unit = unit;
    }
}

class Unit {
    public String name;
    
    public Soldier head;
    public Soldier tail;

    // The head of the list of children
    public Unit unitHead;
    public Unit nextUnit; // The next sibling unit
    public Unit previousUnit;
    // Also store the parent - should this unit be attached
    // to another one, we must remove it from it
    public Unit parentUnit;

    public Unit() {
        this.head = this.tail = null;
        this.unitHead = null;
        this.nextUnit = this.previousUnit = null;
    }
    public void addSoldier(int id) {
        Soldier soldier = new Soldier(id);
        if (this.head == null) {
            this.head = this.tail = soldier;
            return;
        }
        this.tail.next = soldier;
        soldier.previous = this.tail;
        this.tail = soldier;
    }
    private Soldier getActualTail() {
        if (this.tail != null) {
            return this.tail;
        }
        Unit current = this.unitHead;
        while (current != null) {
            current = current.nextUnit;
        }
        if (current != null) {
            return current.getActualTail();
        }
        return null;
    }
    private Unit findAttachmentUnitForSoldier(int soldierId) {
        // Units are attached only after the end of a previous unit
        // so only search the tails of the existing units
        for (Unit u = this.unitHead; u != null; u = u.nextUnit) {
            Soldier unitTail = u.getActualTail();
            if (unitTail.id == soldierId) {
                return u;
            }
        }
        return null;
    }
    private void detach(Unit child) {
        if (this.unitHead == child) {
            this.unitHead = child.nextUnit;
            child.nextUnit = child.previousUnit = null;
            if (this.unitHead != null && this.unitHead.nextUnit != null) {
                this.unitHead.nextUnit.previousUnit = this.unitHead;
            }
            return;
        }
        Unit leftSibling = this.unitHead;
        while (leftSibling.nextUnit != child) {
            leftSibling = leftSibling.nextUnit;
        }
        leftSibling.nextUnit = child.nextUnit;
        if (leftSibling.nextUnit != null) {
            leftSibling.nextUnit.previousUnit = leftSibling;
        }
        child.nextUnit = child.previousUnit = null;
    }
    public void attach(Unit newChild, int soldierId) {
        if (newChild.parentUnit != null) {
            newChild.parentUnit.detach(newChild);
        }
        newChild.parentUnit = this;
        if (soldierId < 0) {
            // Attach to end
            if (this.unitHead == null) {
                this.unitHead = newChild;
                return;
            }
            Unit current = this.unitHead;
            while (current.nextUnit != null) {
                current = current.nextUnit;
            }
            current.nextUnit = newChild;
            newChild.previousUnit = current;
            return;
        }
        Unit leftSibling = this.findAttachmentUnitForSoldier(soldierId);
        Unit sibling = leftSibling.nextUnit;
        leftSibling.nextUnit = newChild;
        newChild.nextUnit = sibling;
        newChild.previousUnit = leftSibling;
        if (sibling != null) {
            sibling.previousUnit = newChild;
        }
    }

    private SoldierUnitPair findSoldier(int soldierId) {
        for (Soldier s = this.head; s != null; s = s.next) {
            if (s.id == soldierId) {
                return new SoldierUnitPair(s, this);
            }
        }
        for (Unit u = this.unitHead; u != null; u = u.nextUnit) {
            SoldierUnitPair pair = u.findSoldier(soldierId);
            if (pair != null) {
                return pair;
            }
        }
        return null;
    }
    
    private void killAll() {
        this.head = this.tail = null;
        for (Unit u = this.unitHead; u != null; u = u.nextUnit) {
            u.killAll();
        }
    }
    private void killAfter(Soldier soldier) {
        if (this.head == soldier) {
            this.killAll();
            return;
        }
        this.tail = soldier.previous;
        this.tail.next = null;
        soldier.previous = soldier.next = null;
    }
    
    private void killBefore(Soldier soldier) {
        if (this.tail == soldier) {
            this.killAll();
            return;
        }
        this.head = soldier.next;
        this.head.previous = null;
        soldier.previous = soldier.next = null;
    }
    private void killInBetween(Soldier soldier1, Soldier soldier2) {
        boolean log = false;
        if (log) {
            System.out.print("BEFORE: ");
            System.out.print(soldier1.id);
            System.out.print(" | ");
            System.out.println(this.head.id);
        }

        if (this.head == soldier1) {
            this.killBefore(soldier2);
        }
        else if (this.tail == soldier2) {
            this.killAfter(soldier1);
        }
        else {
            soldier1.previous.next = soldier2.next;
            soldier2.next.previous = soldier1.previous;
            soldier1.next = soldier1.previous = null;
            soldier2.next = soldier2.previous = null;
        }
        if (log) {
            System.out.print("AFTER: ");
            System.out.print(soldier1.id);
            System.out.print(" | ");
            System.out.println(this.head.id);
        }
    }
    
    public void killSoldiers(int leftId, int rightId) {
        // This can be further optimized:
        // 1. Check if left and right are the same, don't search twice
        // 2. Enhance findSoldier to start searching from a given
        // unit / soldier node - this way we won't start from the beginning the
        // second search
        SoldierUnitPair leftPair = this.findSoldier(leftId);
        SoldierUnitPair rightPair = this.findSoldier(rightId);
        // If left and right are different units, kill everyone
        // after / before the given soldiers
        if (leftPair.unit != rightPair.unit) {
            leftPair.unit.killAfter(leftPair.soldier);
            rightPair.unit.killBefore(rightPair.soldier);
            // Kill all soldiers in all units in between left and right ancestors
            // Since it might be the case that units are in different branches
            // we need to climb the hierarchy
            Unit leftAncestor = leftPair.unit;
            Unit rightAncestor = rightPair.unit;
            do {
                for (Unit left = leftAncestor.nextUnit; left != rightAncestor && left != null; left = left.nextUnit) {
                    left.killAll();
                }
                for (Unit right = rightAncestor.previousUnit; right != leftAncestor && right != null; right = right.previousUnit) {
                    right.killAll();
                }
                leftAncestor = leftAncestor.parentUnit;
                rightAncestor = rightAncestor.parentUnit;
            }
            while (leftAncestor != rightAncestor);
            // This is the nastiest part, we need to search in depth for all 
        }
        else {
            leftPair.unit.killInBetween(leftPair.soldier, rightPair.soldier);
        }
    }
     
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        this.toStringWithoutBrackets(result);
        result.append("]");
        return result.toString();
    }
    private void toStringWithoutBrackets(StringBuilder result) {
        // We've got soldiers
        if (this.head != null) {
            // Prepend a comma if other soldiers have been written already
            if (result.length() > 1) {
                result.append(", ");
            }
            for (Soldier soldier = this.head; soldier != this.tail; soldier = soldier.next) {
                result.append(String.valueOf(soldier.id));
                result.append(", ");
            }
            if (this.tail != null) {
                result.append(String.valueOf(this.tail.id));
            }
        }
        // We've got other units
        else {
            for (Unit unit = this.unitHead; unit != null; unit = unit.nextUnit) {
                unit.toStringWithoutBrackets(result);
            }
        }
    }
    public String getSoldierUnits(int soldierId) {
        SoldierUnitPair pair = this.findSoldier(soldierId);
        if (pair == null) {
            return null;
        }
        String result = "";
        Unit unit = pair.unit;
        // Traverse up the hierarchy
        while (unit.parentUnit != null) {
            result += unit.name + ", ";
            unit = unit.parentUnit;
        }
        result += unit.name;
        return result;
    }
}

/**
 *
 * @author Nikola
 */
class RoyalProgrammer {

    /**
     * @param args the command line arguments
     */
    public static void notmain(String[] args) throws IOException {
        IFrontBookkeeper bookkeeper = new FrontBookkeeperAuthor();
        String newsFile = "C:\\Users\\Nikola\\Desktop\\interlist_removal.txt";
        String news = new String(Files.readAllBytes(Paths.get(newsFile)),
                StandardCharsets.UTF_8);
        String[] lines = news.split(System.lineSeparator());
        double now = System.currentTimeMillis();
        String result = bookkeeper.updateFront(lines);
        
        double time = System.currentTimeMillis() - now;
        //System.out.println(result);
        System.out.print("TIME TAKEN: ");
        System.out.println(time);
        
    }
    
}
