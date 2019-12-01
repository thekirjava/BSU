package com.company;


import java.util.StringTokenizer;

public abstract class Auto implements Comparable<Auto> {
    Auto() {
        this.name = "";
        this.color = "";
    }

    Auto(String name, String color, Fuel fuel) {
        this.name = name;
        this.color = color;
        this.fuel = fuel;
    }

    @Override
    public int compareTo(Auto x) {
        if (!this.name.equals(x.name)) {
            return this.name.compareTo(x.name);
        }
        return -this.fuel.compareTo(x.fuel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }
        Auto x = (Auto) o;
        return this.name.equals(x.name) && this.fuel.equals(x.fuel) && this.color.equals(x.color);
    }

    @Override
    public String toString() {
        return new String(name + " " + color + " " + fuel);
    }

    public void print() {
        System.out.print(name + " " + color + " " + fuel + " ");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setColor(String setColor) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public String getColor() {
        return color;
    }

    private String name;
    private Fuel fuel;
    private String color;
}
