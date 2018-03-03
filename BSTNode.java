package ds_binarytrees;

import java.util.ArrayList;

/**
 *
 * @author ogm2
 */
public class BSTNode<E extends Comparable<E>> extends TreeNode<E>{
    
    /**
     * The entry value stored on this node.
     */
    E value;
    
    /**
     * The number of duplicates of this entry.
     * Value 1 means this entry has no duplicates.
     */
    int counter;
    
    /**
     * The left child of this node.
     */
    BSTNode leftChild;
    
    /**
     * The right child of this node.
     */
    BSTNode rightChild;

    
    /****************/
    /* CONSTRUCTORS */
    /****************/
    
    /**
     * Constructs an empty node.
     */
    public BSTNode() {
        value = null;
        counter = 0;
        leftChild = null;
        rightChild = null;        
    }

    /**
     * Constructs a node with a single entry.
     * @param value  the entry value
     */
    public BSTNode(E newValue) {
        value = newValue;
        counter = 1;
        leftChild = null;
        rightChild = null;        
    }


    /*********************/
    /* GETTERS & SETTERS */
    /*********************/

    public E getValue() {
        return value;
    }

    
    public void setValue(E value) {
        this.value = value;
        counter = 1;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public BSTNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNode leftChild) {
        this.leftChild = leftChild;
    }

    public BSTNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNode rightChild) {
        this.rightChild = rightChild;
    }


    /**************************************/
    /* SUBTREE EXPLORATION & MODIFICATION */
    /**************************************/
    
    public void add(E newValue) {
        if (value.compareTo(newValue) == 0) {
            counter++;
        } else if (newValue.compareTo(value) < 0) {
            if (leftChild == null) {
                leftChild = new BSTNode(newValue);
            } else {
                leftChild.add(newValue);
            }
        } else {
            if (rightChild == null) {
                rightChild = new BSTNode(newValue);
            } else {
                rightChild.add(newValue);
            }
        }
    }
    
    /**
     * Replaces a child node with another node.
     * If the replaced child is the left (right) child then the new node
     * becomes the left (right) child of this node.
     * @param oldNode
     * @param newNode 
     */
    public void replaceChild(BSTNode oldNode, BSTNode newNode) {
        System.out.println("Replacing node "+oldNode.getValue());
        if (leftChild == oldNode) {
            leftChild = newNode;
        } else {
            rightChild = newNode;
        }
    }
    
    /**
     * Removes this node from the tree.
     * If this node is a leaf, get parent to replace this node with null.
     * If this node has only one child, get parent to replace this node with
     * its child.
     * If this node has two children, get parent to replace this node with its
     * successor (ie. the node that stores the closest yet greater value in the
     * tree), and remove successor from its original place in the tree.
     * @param parent  the parent node of the current node
     */
    public void removeNode(BSTNode parent) {
        System.out.println("Removing "+this.getValue());
        if (this.isLeaf()) {
            parent.replaceChild(this, null);
        } else if (this.hasSingleChild()) {
            if (leftChild != null) {
                parent.replaceChild(this, leftChild);
            } else {
                parent.replaceChild(this, rightChild);                
            }
        } else { //this node has two children
            //Find successor
            BSTNode successor = rightChild;
            BSTNode parentOfSuccessor = this;
            while (successor.getLeftChild() != null) {
                parentOfSuccessor = successor;
                successor = successor.getLeftChild();
            }
            //Remove successor
            parentOfSuccessor.replaceChild(successor, successor.getRightChild());
            //Update removed node value and counter with those of its successor
            this.counter = successor.getCounter();
            this.value = (E)successor.getValue();
        }
    }

    /**
     * Determines whether the node is a leaf.
     * @return  true if the node is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return ((leftChild == null) && (rightChild == null));
    }

    /**
     * Determines whether the node has only one child.
     * @return  true if the node has only one child, false otherwise
     */
    public boolean hasSingleChild() {
        // Operator ^ is XOR, ie. one or the other but neither both nor none
        return ((leftChild == null) ^ (rightChild == null));        
    }

    /**
     * Explores the subtree to find an entry.
     * @param value  the entry value to look for
     * @return  the node that stores the value if the value appears in 
     * the subtree, null otherwise
     */
    public BSTNode find(E value) {
        BSTNode result = null;
        if (value.compareTo(this.value) == 0) {
            result = this;
        } else if ((value.compareTo(this.value) < 0) && (leftChild != null)) {
            result = leftChild.find(value);
        } else if ((value.compareTo(this.value) > 0) && (rightChild != null)) {
            result = rightChild.find(value);
        }
        return result;
    }

    /**
     * Displays the subtree entries via a recursive in-order traversal.
     */
    public void displayInOrder() {
        if (leftChild != null) {
            leftChild.displayInOrder();
        }
        for (int i = 0; i < counter; i++) {
            System.out.print(value.toString() + " ");
        }
        if (rightChild != null) {
            rightChild.displayInOrder();
        }        
    }
    

    
    /**
     * Displays the subtree entries via a recursive pre-order traversal.
     */
    public void displayPreOrder() {
        for (int i = 0; i < counter; i++) {
            System.out.print(value.toString() + " ");
        }
        if (leftChild != null) {
            leftChild.displayPreOrder();
        }
        if (rightChild != null) {
            rightChild.displayPreOrder();
        }                
    }
    
    /**
     * Displays the subtree entries via a recursive post-order traversal.
     */
    public void displayPostOrder() {
        if (rightChild != null) {
            rightChild.displayPostOrder();
        }        
        if (leftChild != null) {
            leftChild.displayPostOrder();
        }
        for (int i = 0; i < counter; i++) {
            System.out.print(value.toString() + " ");
        }        
    }

    public void displayLevelOrder() {
        // TO DO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Computes the total number of nodes in this node's subtree.
     * @return  the total number of nodes in this node's subtree,
     *          1 if this node is a leaf
     */
    public int nbOfNodes() {
        int sum = 1; //Adding self to the count
        if (leftChild != null) {
            sum += leftChild.nbOfNodes();
        }
        if (rightChild != null) {
            sum += rightChild.nbOfNodes();
        }
        return sum;
    }

    /**
     * Computes the total number of leaves in this node's subtree.
     * @return  the total number of leaves in this node's subtree
     */
    public int nbOfLeaves() {
        int sum = 0;
        if (this.isLeaf()) {//Adding self to the count
            sum = 1;
        } else {
            if (leftChild != null) {
                sum += leftChild.nbOfLeaves();
            }
            if (rightChild != null) {
                sum += rightChild.nbOfLeaves();
            }
        }
        return sum;
    }

    /**
     * Computes the height (number of levels) of this node's subtree.
     * @return  the height of this node's subtree
     */
    public int height() {
        int depth = 0;
        if (!(this.isLeaf())) {
            if (leftChild != null) {
                depth = Math.max(depth, leftChild.height());
            }
            if (rightChild != null) {
                depth = Math.max(depth, rightChild.height());
            }
        }        
        return depth + 1;
    }
    
    public void reverseTree() {
        BSTNode swap = leftChild;
        leftChild = rightChild;
        rightChild = swap;
        if (rightChild != null) {
            rightChild.reverseTree();
        }        
        if (leftChild != null) {
            leftChild.reverseTree();
        }
    }
    
    public void getAllInRange(E min, E max, ArrayList<E> l) {
        if ((value.compareTo(min) > 0) && (leftChild != null)) {
            leftChild.getAllInRange(min, max, l);
        }
        if ((value.compareTo(min) >= 0) && (value.compareTo(max) <= 0)) {
//            System.out.println("Adding "+value.toString());
            l.add(value);
        }
        if ((value.compareTo(max) < 0) && (rightChild != null)) {
            rightChild.getAllInRange(min, max, l);
        }        
    }
    
}
