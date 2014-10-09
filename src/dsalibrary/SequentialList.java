package dsalibrary;

public class SequentialList {
    private static final int INITIAL_SIZE = 2;
    private static final double SIZE_MULTIPLIER = 2;
    
    private int[] array;
    private int elementCount;
    
    public SequentialList() {
        this.array = new int[INITIAL_SIZE];
        this.elementCount = 0;
    }
    
    public void add(int element) {
        this.tryGrow();
        this.array[this.elementCount] = element;
        this.elementCount++;
    }
    
    public void insert(int element, int index) {
        if (index >= this.elementCount) {
            this.add(element);
            return;
        }
        this.tryGrow();
        for (int i = this.elementCount - 1; i >= index ; i--) {
            this.array[i + 1] = this.array[i]; 
        }
        this.array[index] = element;
        this.elementCount++;
    }
    
    public int get(int index)
    {
        return this.array[index];
    }
    
    public int size() {
        return this.elementCount;
    }
    
    public int indexOf(int element) {
        for (int i = 0; i < this.elementCount; i++) {
            if (this.array[i] == element)
                return i;
        }
        return -1;
    }
    
    private void tryGrow() {        
        if (this.elementCount >= this.array.length) {
            int[] newArray = new int[(int)(this.array.length * SIZE_MULTIPLIER)];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        }
    }
}
