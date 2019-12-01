package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void demo(Scanner s) throws EnumIncorrectException, EmptyException {
        AutoStructure<LightCar> lightCars = new AutoStructure<>();
        int size = Integer.parseInt(s.next());
        for (int i = 0; i < size; i++) {
            lightCars.add(new LightCar(s.next(), s.next(), Fuel.toType(s.next()), Material.toType(s.next())));
        }
        lightCars.printAll();
        System.out.println("Frequency: " + lightCars.frequency(new LightCar(s.next(), s.next(), Fuel.toType(s.next()), Material.toType(s.next()))));
        System.out.println("Search: " + lightCars.binarySearch(new LightCar(s.next(), s.next(), Fuel.toType(s.next()), Material.toType(s.next()))));
        System.out.println("Max :" + lightCars.max());
    }

    public static void main(String[] args) {
        try {
            Scanner s1 = new Scanner(new File("input1.txt"));
            demo(s1);
            Scanner s2 = new Scanner(new File("input2.txt"));
            demo(s2);
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
        } catch (EnumIncorrectException e) {
            System.out.println(e.getMessage());
        } catch (EmptyException e) {
            System.out.println("Structure is empty");
        }
    }
}
