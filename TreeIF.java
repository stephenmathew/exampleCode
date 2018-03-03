/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_binarytrees;

import java.util.NoSuchElementException;

/**
 *
 * @author ogm2
 */
public interface TreeIF<E extends Comparable<E>> {


    /**
     * Returns the reference to the node at the root of the tree.
     * @return  the reference to the root node, null if the tree is empty
     */
    TreeNode<E> getRoot();
    
    /**
     * Determines whether the tree is empty.
     * @return  true if the tree is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Adds an entry to the tree.
     * If this entry does not appear already in the tree, a new leaf node is 
     * inserted to account for this value. If the entry already appears, a 
     * counter is incremented on the corresponding node.
     * @param value  the entry to add
     */
    void add(E value);
    
    /**
     * Finds an entry inside the tree.
     * @param value  the entry value to look for
     * @return  the node that stores the value if the value appears in the tree,
     *          null otherwise
     */
    TreeNode find(E value);
    
    /**
     * Removes an entry from the tree.
     * If this entry has duplicates then the associated counter is decremented,
     * otherwise the node that stores the value is removed from the tree and
     * the nodes are reorganized to preserve the ordering of the tree.
     * @param value  the entry value to remove.
     * @throws NoSuchElementException if the entry value does not appear 
     *          in the tree
     */
    void remove(E value) throws NoSuchElementException;
    
    /**
     * Displays the tree entries via a recursive in-order traversal.
     */
    void displayInOrder();

    /**
     * Displays the tree entries via an iterative level-order traversal.
     * HINT: Use a queue.
     */
    void displayLevelOrder();

    /**
     * Computes the height (number of levels) of the tree.
     * @return  the height of the tree
     */
    int height();

}
