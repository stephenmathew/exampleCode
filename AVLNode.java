package ds_binarytrees;

/**
 *
 * @author ogm2
 */
public class AVLNode<E extends Comparable<E>> extends TreeNode<E> {
    
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
    AVLNode leftChild;
    
    /**
     * The right child of this node.
     */
    AVLNode rightChild;
    
    /****************/
    /* CONSTRUCTORS */
    /****************/

    /**
     * Constructs an empty node.
     */
    public AVLNode() {
        this.value = null;
        this.counter = 0;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Constructs a node with a single entry.
     * @param value  the entry value
     */
    public AVLNode(E value) {
        this.value = value;
        this.counter = 1;
        this.leftChild = null;
        this.rightChild = null;
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

    
    public AVLNode getLeftChild() {
        return leftChild;
    }

    
    public void setLeftChild(AVLNode leftChild) {
        this.leftChild = leftChild;
    }

    
    public AVLNode getRightChild() {
        return rightChild;
    }

    
    public void setRightChild(AVLNode rightChild) {
        this.rightChild = rightChild;
    }

    
    /**************************************/
    /* SUBTREE EXPLORATION & MODIFICATION */
    /**************************************/
    
    public AVLNode add(E newValue) {
        if (newValue.compareTo(value) == 0) {
            counter++;
            return this;
        }
        if (newValue.compareTo(value) < 0) {
            if (leftChild == null) {
                leftChild = new AVLNode(newValue);
                return this;
            } else {
                leftChild = leftChild.add(newValue);
                return rebalance();
            }
        } else {
            if (rightChild == null) {
                rightChild = new AVLNode(newValue);
                return this;
            } else {
                rightChild = rightChild.add(newValue);
                return rebalance();
            }
        }
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
    
    public boolean isLeaf() {
        return ((leftChild == null) && (rightChild == null));
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

    public AVLNode rebalance() {
        /* Compute balance */
        int leftHeight = 0;
        if (leftChild != null) {
            leftHeight = leftChild.height();
        }
        int rightHeight = 0;
        if (rightChild != null) {
            rightHeight = rightChild.height();
        }
        AVLNode newRoot = this;
        if (Math.abs(leftHeight - rightHeight) > 1) { //Imbalanced
            if (leftHeight > rightHeight) {
                //Heavy on the left
                int leftLeftHeight = 0;
                if (leftChild.leftChild != null) {
                    leftLeftHeight = leftChild.leftChild.height();
                }
                int leftRightHeight = 0;
                if (leftChild.rightChild != null) {
                    leftRightHeight = leftChild.rightChild.height();
                }
                if(leftLeftHeight > leftRightHeight) {
                    newRoot = rotateFromLeft();
                } else {
                    newRoot = doubleRotateFromLeft();
                }
            } else {
                //Heavy on the right
                int rightLeftHeight = 0;
                if (rightChild.leftChild != null) {
                    rightLeftHeight = rightChild.leftChild.height();
                }
                int rightRightHeight = 0;
                if (rightChild.rightChild != null) {
                    rightRightHeight = rightChild.rightChild.height();
                }
                if(rightLeftHeight < rightRightHeight) {
                    newRoot = rotateFromRight();
                } else {
                    newRoot = doubleRotateFromRight();
                }
            }
        }
        return newRoot;
    }
    
    private AVLNode rotateFromLeft() {
        AVLNode swap = this.leftChild;
        this.leftChild = swap.rightChild;
        swap.rightChild = this;
        return swap;
    }
    
    private AVLNode rotateFromRight() {
        AVLNode swap = this.rightChild;
        this.rightChild = swap.leftChild;
        swap.leftChild = this;
        return swap;
    }

    private AVLNode doubleRotateFromLeft() {
        leftChild = leftChild.rotateFromRight();
        return rotateFromLeft();
    }
    
    private AVLNode doubleRotateFromRight() {
        rightChild = rightChild.rotateFromLeft();
        return rotateFromRight();
    }
    
}
