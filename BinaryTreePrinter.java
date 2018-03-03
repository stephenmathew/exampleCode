/*
 * This code was adapted from an original post by Laurent Demailly
 * http://stackoverflow.com/users/2901325/laurent-demailly
 */
package ds_binarytrees;

import java.io.IOException;

public class BinaryTreePrinter<T extends Comparable<T>> {

    
    public BinaryTreePrinter() {
    }

    public void printTree(TreeNode<T> node) throws IOException {
        if (node == null) {
            printNodeValue(node);
            return;
        }
        TreeNode<T> right = node.getRightChild();
        TreeNode<T> left = node.getLeftChild();
        if (right != null) {
            printTree(right, true, "");
        }
        printNodeValue(node);
        if (left != null) {
            printTree(left, false, "");
        }
    }
    
    private void printNodeValue(TreeNode<T> node) throws IOException {
        if (node == null) {
            System.out.print("<null>");
        } else {
            for (int i = 0; i < node.getCounter(); i++)
                System.out.print(node.getValue().toString() + " ");
        }
        System.out.print('\n');
    }
    
    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(TreeNode<T> node, boolean isRight, String indent) throws IOException {
        TreeNode<T> right = node.getRightChild();
        TreeNode<T> left = node.getLeftChild();
        if (right != null) {
            printTree(right, true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue(node);
        if (left != null) {
            printTree(left, false, indent + (isRight ? " |      " : "        "));
        }
    }
    
    
    
    
}
