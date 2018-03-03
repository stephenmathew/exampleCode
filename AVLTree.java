/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_binarytrees;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 *
 * @author ogm2
 * @param <E>
 */
public class AVLTree<E extends Comparable<E>> implements TreeIF<E> {

    /**
     * The root node of the BST.
     */
    AVLNode root;

    public AVLTree() {
    }

    @Override
    public AVLNode getRoot() {
        return root;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }

    @Override
    public void add(E value) {
        if (root == null) {
            root = new AVLNode<>(value);
        } else {
            root = root.add(value);
        }
    }

    @Override
    public void displayInOrder() {
        if (this.isEmpty()) {
            System.out.println("Tree is empty");
        } else {
            System.out.println("In Order - Root Value " + root.getValue());
            root.displayInOrder();
            System.out.println("");
        }
    }


    @Override
    public void displayLevelOrder() {
        if (this.isEmpty()) {
            System.out.println("Tree is empty");
        } else {
            System.out.println("Level Order - Root Value " + root.getValue());
            ArrayDeque<AVLNode> nodes = new ArrayDeque<>();
            ArrayDeque<Integer> levels = new ArrayDeque<>();
            nodes.addLast(root);
            levels.addLast(1);
            int lvl = 1;
            while(!(nodes.isEmpty())) {
                AVLNode current = nodes.removeFirst();
                int newlvl = levels.removeFirst();
                if (newlvl != lvl) {
                    lvl = newlvl;
                    System.out.println("");
                }
                System.out.print("(lvl " + lvl + " - v "
                                + current.getValue().toString() + ") ");
                if (current.getLeftChild() != null) {
                    nodes.addLast(current.getLeftChild());
                    levels.addLast(lvl + 1);
                }
                if (current.getRightChild() != null) {
                    nodes.addLast(current.getRightChild());
                    levels.addLast(lvl + 1);
                }
            }
            System.out.println("");
        }
        
    }


    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public int height() {
        int height = 0;
        if (!(this.isEmpty())) {
            height = root.height();
        }
        return height;        
    }

    @Override
    public TreeNode find(E value) {
        // TO DO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(E value) throws NoSuchElementException {
        // TO DO
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
