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
    BinarySearchTree left;
    BinarySearchTree right;
    String key;
    int value;
    
    public BinarySearchTree(String key, int value) {
        this.key = key;
        this.value = value;
    }
    
    public void add(String key, int value) {
        if (this.key.compareTo(key) < 0) {
            if (this.right == null) {
                this.right = new BinarySearchTree(key, value);
                return;
            }
            this.right.add(key, value);
        }
        else if (this.key.compareTo(key) > 0) {
            if (this.left == null) {
                this.left = new BinarySearchTree(key, value);
                return;
            }
            this.left.add(key, value);
        }
        else {
            this.value = value;
        }            
    }
    
    public int get(String key) {
        if (this.key.compareTo(key) < 0) {
            if (this.right == null) {
                return -1;
            }
            return this.right.get(key);
        }
        else if (this.key.compareTo(key) > 0) {
            if (this.left == null) {
                return -1;
            }
            return this.left.get(key);
        }
        else {
            return this.value;
        }            
    }
    
    private void remove(String key, BinarySearchTree parent) {
        if (this.key.compareTo(key) < 0) {
            if (this.right == null) {
                return;
            }
            this.right.remove(key, this);
        }
        else if (this.key.compareTo(key) > 0) {
            if (this.left == null) {
                return;
            }
            this.left.remove(key, this);
        }
        else {
            if (this.left == null && this.right == null) {
                if (this == parent.left)
                    parent.left = null;
                else
                    parent.right = null;
            }
            else if (this.left == null && this.right != null) {
                if (this == parent.left)
                    parent.left = this.right;
                else
                    parent.right = this.right;
            }
            else if (this.right == null && this.left != null) {
                if (this == parent.left)
                    parent.left = this.left;
                else
                    parent.right = this.left;
            }
            else {
            }
        }            
    }
    
    public void remove(String key) {
        this.remove(key, this);
    }
    
    
    public void printPreorder(int indentation) {
        for (int i = 0; i < indentation - 1; i++)
            System.out.print(' ');
        System.out.print('|');
        System.out.print(this.key);
        System.out.print(' ');
        System.out.println(this.value);
        if (this.left != null)
            this.left.printPreorder(indentation + 4);
        if (this.right != null)
            this.right.printPreorder(indentation + 4);
    }
}
