/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsalibrary;

/**
 *
 * @author Nikola
 */
public class BinarySearchTree {
    public BinarySearchTree left;
    public BinarySearchTree right;
    
    String key;
    int value;
    
    public BinarySearchTree(String key, int value) {
        this.key = key;
        this.value = value;
    }
    
    public void add(String key, int value) {
        if (key.compareTo(this.key) < 0) {
            if (this.left == null) {
                this.left = new BinarySearchTree(key, value);
                return;
            }
            this.left.add(key, value);
        }
        else if (key.compareTo(this.key) > 0) {
            if (this.right == null) {
                this.right = new BinarySearchTree(key, value);
                return;                
            }
            this.right.add(key, value);
        }
        else {
            this.value = value;
        }
    }
    
    public int get(String key) {
        if (key.compareTo(this.key) < 0) {
            if (this.left == null) {
                return -1;
            }
            return this.left.get(key);
        }
        else if (key.compareTo(this.key) > 0) {
            if (this.right == null) {
                return -1;
            }
            return this.right.get(key);
        }
        else {
            return this.value;
        }
    }
    
    private void removeFromParent(String key, BinarySearchTree parent) {
        if (key.compareTo(this.key) < 0) {
            if (this.left == null) {
                return;
            }
            this.left.removeFromParent(key, this);
        }
        else if (key.compareTo(this.key) > 0) {
            if (this.right == null) {
                return;
            }
            this.right.removeFromParent(key, this);
        }
        else {
            if (this.left == null && this.right == null) {
                if (parent.left == this)
                    parent.left = null;
                else
                    parent.right = null;
            }
            else if (this.left != null && this.right == null) {
                if (parent.left == this)
                    parent.left = this.left;
                else
                    parent.right = this.left;
            }
            else if (this.right != null && this.left == null) {
                if (parent.left == this)
                    parent.left = this.right;
                else
                    parent.right = this.right;
            }
            else {
                BinarySearchTree rightmostParent;
                BinarySearchTree rightmost;
                if (this.left.right != null) {
                    rightmostParent = this.left;
                    while (rightmostParent.right.right != null) {
                        rightmostParent = rightmostParent.right;
                    }
                    rightmost = rightmostParent.right;
                }
                else {
                    rightmostParent = this;
                    rightmost = this.left;
                }
                String thisKey = this.key;
                int thisValue = this.value;
                this.key = rightmost.key;
                this.value = rightmost.value;
                rightmost.key = thisKey;
                rightmost.value = thisValue;
                
                rightmost.removeFromParent(thisKey, rightmostParent);
            }
        }        
    }
    
    
    public void remove(String key) {
        this.removeFromParent(key, this);
    }
    
    
    private void printInorderIndented(int indentation) {
        for (int i = 0; i < indentation; i++)
            System.out.print(' ');

        System.out.println(this.key);
        if (this.left != null)
            this.left.printInorderIndented(indentation + 1);
        if (this.right != null)
            this.right.printInorderIndented(indentation + 1);
    }
    
    public void printInorder() {
        this.printInorderIndented(0);
    }
}
