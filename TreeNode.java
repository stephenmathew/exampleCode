package ds_binarytrees;

/**
 *
 * @author ogm2
 */
public class TreeNode<E extends Comparable<E>> {
    
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
    TreeNode leftChild;
    
    /**
     * The right child of this node.
     */
    TreeNode rightChild;
    
    /****************/
    /* CONSTRUCTORS */
    /****************/

    /**
     * Constructs an empty node.
     */
    public TreeNode() {
        value = null;
        counter = 0;
        leftChild = null;
        rightChild = null;
    }

    /**
     * Constructs a node with a single entry.
     * @param value  the entry value
     */
    public TreeNode(E value) {
        value = value;
        counter = 1;
        leftChild = null;
        rightChild = null;
    }

    /**
     * Constructs a new node that is a duplicate of another node.
     * @param n 
     */
    public TreeNode(TreeNode n) {
        setValue((E) n.getValue());
        counter = n.getCounter();
        leftChild = n.getLeftChild();
        rightChild = n.getRightChild();
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

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Replaces a child node with another node.
     * If the replaced child is the left (right) child then the new node
     * becomes the left (right) child of this node.
     * @param oldNode
     * @param newNode 
     */
    public void replaceChild(TreeNode oldNode, TreeNode newNode) {
        System.out.println("Replacing node "+oldNode.getValue());
        if (leftChild == oldNode) {
            leftChild = newNode;
        } else {
            rightChild = newNode;
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
    
    
}
