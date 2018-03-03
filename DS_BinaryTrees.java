/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_binarytrees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ogm2
 */
public class DS_BinaryTrees {

    public final static int SIZE = 20;

    public DS_BinaryTrees() {
    }
    
    public void testBST(int size) {
        System.out.println("BINARY SEARCH TREE");
        System.out.println("\n\n######## GENERATION ########");
        ExtendedTreeIF<Integer> tree = new BinarySearchTree();
        tree.displayInOrder();
        Random rd = new Random();
        int i, x = 0;
        for(i = 0; i < size; i++) {
            x = rd.nextInt(101);
            tree.add(x);
            System.out.print("(" + i + " " + x + ") ");
        }

        System.out.println("\n\n######## DISPLAYS ########");
        System.out.println("\nThis tree has a height of " + tree.height());
        BinaryTreePrinter print = new BinaryTreePrinter();
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }
        tree.displayInOrder();
        tree.displayPreOrder();
        tree.displayPostOrder();
        tree.displayLevelOrder();
        
        System.out.println("\n\n######## SEARCH & REMOVAL ########");        
        if (tree.find(x) != null) {
            System.out.println(x + " is in the tree");
        }
        if (tree.find(-1) == null) {
            System.out.println("-1 is not in the tree");
        }
        System.out.println("What value would you like to remove?");
        Scanner sc  = new Scanner(System.in);
        x = sc.nextInt();
        tree.remove(x);
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("What value would you like to remove?");
        x = sc.nextInt();
        tree.remove(x);
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void testAssignment(int size) {
        System.out.println("BINARY SEARCH TREE");
        System.out.println("\n\n######## GENERATION ########");
        ExtendedTreeIF<Integer> tree = new BinarySearchTree();
        tree.displayInOrder();
        Random rd = new Random();
        int i, x = 0;
        for(i = 0; i < size; i++) {
            x = rd.nextInt(101);
            tree.add(x);
            System.out.print("(" + i + " " + x + ") ");
        }
        System.out.println("");
        BinaryTreePrinter print = new BinaryTreePrinter();
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("\n\n######## STATISTICS ########");
        System.out.print("This tree has: ");
        System.out.print(tree.nbOfNodes() + " nodes - ");
        System.out.print(tree.nbOfLeaves() + " leaves - ");
        System.out.println(" a height of " + tree.height());

        
        System.out.println("\n\n######## RANGE & REVERSAL ########");
        tree.displayInOrder();
        System.out.println("Values in range (15, 65)");
        ArrayList<Integer> list = tree.getAllInRange(15, 65);
        for (int v:list) {
            System.out.print(" " + v);
        }
        System.out.println("\n");
        tree.reverseTree();
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void testAVLT(int size) {
        System.out.println("AVL TREE");
        System.out.println("\n\n######## GENERATION ########");
        TreeIF<Integer> tree = new AVLTree();
        tree.displayInOrder();
        Random rd = new Random();
        int i, x = 0;
        for(i = 0; i < size; i++) {
            x = rd.nextInt(101);
            tree.add(x);
            System.out.print("(" + i + " " + x + ") ");
        }
        System.out.println("\n\n######## STATISTICS ########");
        System.out.print("This tree has: ");
        System.out.println(" a height of " + tree.height());

        System.out.println("\n\n######## DISPLAYS ########");
        tree.displayInOrder();
        
        BinaryTreePrinter print = new BinaryTreePrinter();
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public void testSkewedAVLT(int size) {
        System.out.println("\n\n######## SUPER SKEWED GENERATION ########");
        TreeIF<Integer> tree = new AVLTree();
        tree.displayInOrder();
        Random rd = new Random();
        tree = new AVLTree();
        tree.displayInOrder();
        for(int i = 1; i <= size; i++) {
            tree.add(i);
            System.out.print("(" + i + " " + i + ") ");
        }
        System.out.println("\nThis tree has a height of " + tree.height());
        tree.displayInOrder();
        
        BinaryTreePrinter print = new BinaryTreePrinter();
        try {
            print.printTree(tree.getRoot());
        } catch (IOException ex) {
            Logger.getLogger(DS_BinaryTrees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DS_BinaryTrees bt = new DS_BinaryTrees();
//        bt.testBST(SIZE);
//        bt.testAssignment(SIZE);
//        bt.testAVLT(SIZE);
        bt.testSkewedAVLT(SIZE);
    }
    
}
