package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void demo1(Scanner s) throws EmptyCollectionException, NoSuchElementException {
        TreeCollection<Tree> trees = new TreeCollection<>();
        while (s.hasNext()) {
            trees.add(new ForestTree(s.next(), Integer.parseInt(s.next()), TreeType.toType(s.next()), Integer.parseInt(s.next())));
        }
        trees.printAll();
        System.out.println();
        ForestTree test1 = new ForestTree("Oak", 1000, TreeType.LEAF, 1000);
        System.out.println("Frequency of " + test1 + " : " + trees.frequency(test1));
        System.out.println("Min: " + trees.min());
        TreeType test2 = TreeType.NEEDLE;
        System.out.println("Amount of " + test2 + ": " + trees.count(test2));
        System.out.println();
        System.out.println();
    }

    public static void demo2(Scanner s) throws EmptyCollectionException, NoSuchElementException {
        TreeCollection <Tree> trees = new TreeCollection<>();
        while (s.hasNext()) {
            trees.add(new FruitTree(s.next(), Integer.parseInt(s.next()), TreeType.toType(s.next()), Integer.parseInt(s.next())));
        }
        trees.printAll();
        System.out.println();
        FruitTree test1 = new FruitTree("Apple", 2000, TreeType.LEAF, 100);
        System.out.println("Frequency of " + test1 + " : " + trees.frequency(test1));
        System.out.println("Min: " + trees.min());
        TreeType test2 = TreeType.NEEDLE;
        System.out.println("Amount of " + test2 + ": " + trees.count(test2));
    }

    public static void main(String[] args) {
        try {
            Scanner s1 = new Scanner(new File("input1.txt"));
            demo1(s1);
            Scanner s2 = new Scanner(new File("input2.txt"));
            demo2(s2);
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist!");
        } catch (EmptyCollectionException e) {
            System.out.println("Collection is empty!");
        }
        catch (NoSuchElementException e) {
            System.out.println("Not enough data in input!");
        }
    }
}
