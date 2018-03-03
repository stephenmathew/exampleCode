/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_binarytrees;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author ogm2
 */
public interface ExtendedTreeIF<E extends Comparable<E>> 
                        extends TreeIF<E>{


    /**
     * Displays the tree entries via a recursive post-order traversal.
     */
    void displayPostOrder();

    /**
     * Displays the tree entries via a recursive pre-order traversal.
     */
    void displayPreOrder();
    
    /**
     * Computes the total number of nodes in the tree.
     * @return  the total number of nodes in the tree
     */
    int nbOfNodes();
    
    /**
     * Computes the total number of leaves in the tree.
     * @return  the total number of leaves in the tree
     */
    int nbOfLeaves();
    
    /**
     * Reverses the order of the tree.
     * NOTE: further insertions/removals cannot work after this operation
     */
    void reverseTree();
    
    /**
     * Lists all values stored in the tree that are within a given range.
     * @param min  inclusive lower bound of the range
     * @param max  inclusive upper bound of the range
     * @return  the list of tree values v s.t. min ≤ v ≤ max
     */
    public ArrayList<E> getAllInRange(E min, E max);

}
