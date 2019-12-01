package com.company;

public class LightCar extends Auto {
    LightCar() {
        super();
    }

    LightCar(String name, String color, Fuel fuel, Material material) {
        super(name, color, fuel);
        this.material = material;
    }

    @Override
    public void print() {
        super.print();
        System.out.println(this.material);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LightCar x = (LightCar) o;
        return this.material == x.material;
    }

    @Override
    public String toString() {
        return super.toString() + " " + material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    private Material material;

}
