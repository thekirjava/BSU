package com.company;

public class LightCar extends Auto {
    LightCar() {
        super();
    }

    LightCar(String name, String color, Fuel fuel, Material material) {
        super(name, color, fuel);
        this.material = material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public void print() {
        System.out.println(this);
    }
    private Material material;

}
