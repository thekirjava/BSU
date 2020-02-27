package bsu.fpmi.artsiushkevich.devices;

import java.util.Objects;

public class Fridge extends Device {
    public Fridge() {
        super();
        temperature = 0;
    }

    public Fridge(String manufacturer, int powerUsage, boolean turnedOn, int temperature) {
        super(manufacturer, powerUsage, turnedOn);
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fridge)) return false;
        if (!super.equals(o)) return false;
        Fridge fridge = (Fridge) o;
        return temperature == fridge.temperature;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), temperature);
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "temperature=" + temperature +
                ", manufacturer='" + manufacturer + '\'' +
                ", powerUsage=" + powerUsage +
                ", turnedOn=" + turnedOn +
                '}';
    }

    private int temperature;
}
