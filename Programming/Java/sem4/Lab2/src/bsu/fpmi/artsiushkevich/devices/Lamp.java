package bsu.fpmi.artsiushkevich.devices;

import java.util.Objects;

public class Lamp extends Device {
    public Lamp() {
        brightness = 0;
        color = "";
    }

    public Lamp(String manufacturer, int powerUsage, boolean turnedOn, int brightness, String color) {
        super(manufacturer, powerUsage, turnedOn);
        this.brightness = brightness;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lamp)) return false;
        if (!super.equals(o)) return false;
        Lamp lamp = (Lamp) o;
        return brightness == lamp.brightness &&
                Objects.equals(color, lamp.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), brightness, color);
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "brightness=" + brightness +
                ", color='" + color + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", powerUsage=" + powerUsage +
                ", turnedOn=" + turnedOn +
                '}';
    }

    private int brightness;
    private String color;
}
