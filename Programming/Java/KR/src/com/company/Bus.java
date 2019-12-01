package com.company;

public class Bus extends Auto {
    Bus() {
        super();
        this.doors = 0;
        this.chairs = 0;
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    Bus(String name, String color, Fuel fuel, int doors, int chairs) {
        super(name, color, fuel);
        this.doors = doors;
        this.chairs = chairs;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setChairs(int chairs) {
        this.chairs = chairs;
    }

    public int getDoors() {
        return doors;
    }

    public int getChairs() {
        return chairs;
    }

    int doors;
    int chairs;
}
