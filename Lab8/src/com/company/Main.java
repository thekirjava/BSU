package com.company;

import javax.naming.Name;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;
import static java.lang.Integer.parseInt;

public class Main {

    static class NameFormatException extends Exception {
        NameFormatException(String s) {
            super(s);
        }
    }

    public static void main(String[] args) {
        Tree t1 = new Tree();
        Scanner s = new Scanner(System.in);
        String cmd = " ";
        System.out.println("Tree of Integer");
        try {
            Scanner f = new Scanner(new File("../Graph1.txt"));
            System.out.println("Tree is already have been built from the file");
            while (f.hasNext()) {
                t1.add(parseInt(f.next()));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File with graph doesn't exist");
        }
        while (!cmd.equals("exit")) {
            cmd = s.next();
            switch (cmd) {
                case "add": {
                    try {
                        Integer x = parseInt(s.next());
                        t1.add(x);
                    } catch (NumberFormatException e) {
                        System.out.println("It is not an Integer");
                    }
                }
                break;
                case "find": {
                    try {
                        Integer x = parseInt(s.next());
                        boolean ans = t1.find(x);
                        if (ans) {
                            System.out.println("Tree contains this value");
                        } else {
                            System.out.println("Tree isn't contain this value");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("It is not an Integer");
                    }
                }
                break;
                case "delete": {
                    try {
                        Integer x = parseInt(s.next());
                        t1.delete(x);
                    } catch (NumberFormatException e) {
                        System.out.println("It is not an Integer");
                    }
                }
                break;
                case "inorder": {
                    t1.inOrder();
                }
                break;
                case "postorder": {
                    t1.postOrder();
                }
                break;
                case "preorder": {
                    t1.preOrder();
                }
                break;
                case "help": {
                    System.out.println("Add:  add x");
                    System.out.println("Find:  find x");
                    System.out.println("Delete:  delete x");
                    System.out.println("Print root-left-right:  preorder");
                    System.out.println("Print left-root-right:  inorder");
                    System.out.println("Print left-right-root:  postorder");
                    System.out.println("Print help: help");
                    System.out.println("Finish work: exit");
                }
                break;
                case "exit": {

                }
                break;
                default: {
                    System.out.println(cmd + " is a wrong command. Print 'help' to see list of commands");
                }
                break;
            }
        }
        cmd = " ";
        Tree t2 = new Tree();
        System.out.println("Tree of Person");
        System.out.println("Person contains two strings: name and surname");
        try {
            Scanner f = new Scanner(new File("../Graph2.txt"));
            System.out.println("Tree is already have been built from the file");
            while (f.hasNext()) {
                t2.add(new Person(f.next() + " " + f.next()));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File with graph doesn't exist");
        }
        while (!cmd.equals("exit")) {
            cmd = s.next();
            switch (cmd) {
                case "add": {
                    try {
                        String s1 = s.next();
                        String s2 = s.next();
                        if ((!(isUpperCase(s1.charAt(0)))) || (!(isUpperCase(s2.charAt(0))))) {
                            throw new NameFormatException("First letter must be uppercase");
                        }
                        if ((s1.length() < 2) || (s2.length() < 2)) {
                            throw new NameFormatException("Name is too short");
                        }
                        for (int i = 1; i < s1.length(); i++) {
                            if (isDigit(s1.charAt(i))) {
                                throw new NameFormatException("Name can't contain numbers");
                            }
                            if (isUpperCase(s1.charAt(i))) {
                                throw new NameFormatException("All letters except the first must be lowercase");
                            }
                        }
                        for (int i = 1; i < s2.length(); i++) {
                            if (isDigit(s2.charAt(i))) {
                                throw new NameFormatException("Name can't contain numbers");
                            }
                            if (isUpperCase(s2.charAt(i))) {
                                throw new NameFormatException("All letters except the first must be lowercase");
                            }
                        }
                        Person x = new Person(s1 + " " + s2);
                        t2.add(x);
                    } catch (NameFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case "find": {
                    try {
                        String s1 = s.next();
                        String s2 = s.next();
                        if ((!(isUpperCase(s1.charAt(0)))) || (!(isUpperCase(s2.charAt(0))))) {
                            throw new NameFormatException("First letter must be uppercase");
                        }
                        if ((s1.length() < 2) || (s2.length() < 2)) {
                            throw new NameFormatException("Name is too short");
                        }
                        for (int i = 1; i < s1.length(); i++) {
                            if (isDigit(s1.charAt(i))) {
                                throw new NameFormatException("Name can't contain numbers");
                            }
                            if (isUpperCase(s1.charAt(i))) {
                                throw new NameFormatException("All letters except the first must be lowercase");
                            }
                        }
                        for (int i = 1; i < s2.length(); i++) {
                            if (isDigit(s2.charAt(i))) {
                                throw new NameFormatException("Name can't contain numbers");
                            }
                            if (isUpperCase(s1.charAt(i))) {
                                throw new NameFormatException("All letters except the first must be lowercase");
                            }
                        }
                        Person x = new Person(s1 + " " + s2);
                        boolean ans = t2.find(x);
                        if (ans) {
                            System.out.println("Tree contains this value");
                        } else {
                            System.out.println("Tree isn't contain this value");
                        }
                    } catch (NameFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case "delete": {
                    try {
                        String s1 = s.next();
                        String s2 = s.next();
                        if ((!(isUpperCase(s1.charAt(0)))) || (!(isUpperCase(s2.charAt(0))))) {
                            throw new NameFormatException("First letter must be uppercase");
                        }
                        if ((s1.length() < 2) || (s2.length() < 2)) {
                            throw new NameFormatException("Name is too short");
                        }
                        for (int i = 1; i < s1.length(); i++) {
                            if (isDigit(s1.charAt(i))) {
                                throw new NameFormatException("Name can't contain numbers");
                            }
                            if (isUpperCase(s1.charAt(i))) {
                                throw new NameFormatException("All letters except the first must be lowercase");
                            }
                        }
                        for (int i = 1; i < s2.length(); i++) {
                            if (isDigit(s2.charAt(i))) {
                                throw new NameFormatException("Name can't contain numbers");
                            }
                            if (isUpperCase(s2.charAt(i))) {
                                throw new NameFormatException("All letters except the first must be lowercase");
                            }
                        }
                        Person x = new Person(s1 + " " + s2);
                        t2.delete(x);
                    } catch (NameFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                case "inorder": {
                    t2.inOrder();
                }
                break;
                case "postorder": {
                    t2.postOrder();
                }
                break;
                case "preorder": {
                    t2.preOrder();
                }
                break;
                case "help": {
                    System.out.println("Add:  add n s");
                    System.out.println("Find:  find n s");
                    System.out.println("Delete:  delete n s");
                    System.out.println("Print root-left-right:  preorder");
                    System.out.println("Print left-root-right:  inorder");
                    System.out.println("Print left-right-root:  postorder");
                    System.out.println("Print help: help");
                    System.out.println("Finish work: exit");
                }
                break;
                case "exit": {

                }
                break;
                default: {
                    System.out.println(cmd + " is a wrong command. Print 'help' to see list of commands");
                }
                break;
            }
        }
    }
}
