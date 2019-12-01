package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void demo1(Scanner s) throws EnumIncorrectException, EmptyException {
        AutoStructure<LightCar> cars = new AutoStructure<>();
        while (s.hasNext()) {
            cars.add(new LightCar(s.next(), s.next(), Fuel.toType(s.next()), Material.toType(s.next())));
        }
        cars.printAll();
        System.out.println("Frequency: " + cars.frequency(new LightCar("Toyota", "Red", Fuel.BENSIN, Material.LEATHER)));
        System.out.println("Search: " + cars.binarySearch(new LightCar("Mazda", "Blue", Fuel.DIESEL, Material.FIBRE)));
        System.out.println("Min :" + cars.min());
    }

    public static void demo2(Scanner s) throws EnumIncorrectException, EmptyException {
        AutoStructure<Bus> cars = new AutoStructure<>();
        while (s.hasNext()) {
            cars.add(new Bus(s.next(), s.next(), Fuel.toType(s.next()), Integer.parseInt(s.next()), Integer.parseInt(s.next())));
        }
        cars.printAll();
        System.out.println("Frequency: " + cars.frequency(new Bus("Maz", "White", Fuel.BENSIN, 30, 1)));
        System.out.println("Search: " + cars.binarySearch(new Bus("Uaz", "Black", Fuel.DIESEL, 1, 8)));
        System.out.println("Min :" + cars.min());
    }

    public static void main(String[] args) {
        try {
            Scanner s1 = new Scanner(new File("input1.txt"));
            demo1(s1);
            //Scanner s2 = new Scanner(new File("input2.txt"));
            //demo2(s2);
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
        } catch (EnumIncorrectException e) {
            System.out.println(e.getMessage());
        } catch (EmptyException e) {
            System.out.println("Structure is empty");
        }
    }
}
