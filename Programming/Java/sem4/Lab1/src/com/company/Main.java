package com.company;

import java.util.Scanner;

public class Main {

    static void printCommands() {
        System.out.println("set: Set plane destination");
        System.out.println("print: Print plane destination");
        System.out.println("fly: Fly to destination");
        System.out.println("printPlane: Print plane info");
        System.out.println("hash: Print plane hashcode");
        System.out.println("equals: Input plane and compare to this");
        System.out.println("help: print commands again");
        System.out.println("clear: Clear window");
        System.out.println("exit: Finish work");
    }

    static Plane planeInput() throws NumberFormatException {
        Scanner scanner = new Scanner(System.in);
        double leftWingArea, leftWingspan, rightWingArea, rightWingspan;
        String model;
        int fuelCapacity, amount, wheelRadius, chassisAmount;
        String command = "";
        while ((!command.equals("y")) && (!command.equals("n"))) {
            System.out.println("Input custom plane?");
            command = scanner.next();
            command = command.toLowerCase();
        }
        Plane p;
        if (command.equals("y")) {
            System.out.println("Input left wing area and wingspan");
            leftWingArea = Double.parseDouble(scanner.next());
            leftWingspan = Double.parseDouble(scanner.next());
            System.out.println("Input right wing area and wingspan");
            rightWingArea = Double.parseDouble(scanner.next());
            rightWingspan = Double.parseDouble(scanner.next());
            System.out.println("Input engine model and fuel capacity");
            model = scanner.next();
            fuelCapacity = Integer.parseInt(scanner.next());
            System.out.println("Input amount of wheels in one chassis and wheel radius");
            amount = Integer.parseInt(scanner.next());
            wheelRadius = Integer.parseInt(scanner.next());
            System.out.println("Input amount of chassis");
            chassisAmount = Integer.parseInt(scanner.next());
            p = new Plane(leftWingArea, leftWingspan, rightWingArea, rightWingspan, model, fuelCapacity, amount, wheelRadius, chassisAmount);
        } else {
            p = new Plane();
        }
        return p;
    }

    public static void main(String[] args) {
        Plane p;
        try {
            p = planeInput();
        } catch (NumberFormatException e) {
            System.out.println("NumberFormat Exception" + e.getMessage());
            return;
        }
        printCommands();
        String command = "";
        Scanner scanner = new Scanner(System.in);
        while (command != "exit") {
            command = scanner.next();
            switch (command) {
                case "set":
                    String destination;
                    System.out.println("Input flight destination");
                    destination = scanner.next();
                    p.setDestination(destination);
                    break;
                case "print":
                    p.printDestination();
                    break;
                case "fly":
                    p.toFly();
                    break;
                case "printPlane":
                    System.out.println(p.toString());
                    break;
                case "hash":
                    System.out.println(p.hashCode());
                    break;
                case "equals":
                    try {
                        Plane plane = planeInput();
                        if (p.equals(plane)) {
                            System.out.println("Equals");
                        } else {
                            System.out.println("Not equals");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("NumberFormat Exception" + e.getMessage());
                    }
                    break;
                case "help":
                    printCommands();
                    break;
                case "clear":
                    System.out.flush();
                    break;
                case "exit":
                    continue;
                default:
                    System.out.println("Wrong command");
                    break;
            }
        }
    }
}
