package bsu.fpmi.artsiushkevich.devices;

import java.util.Objects;

public class Kettle extends Device {
    public Kettle() {
        super();
        volume = 0;
    }

    public Kettle(String manufacturer, int powerUsage, boolean turnedOn, int volume) {
        super(manufacturer, powerUsage, turnedOn);
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kettle)) return false;
        if (!super.equals(o)) return false;
        Kettle kettle = (Kettle) o;
        return volume == kettle.volume;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), volume);
    }

    @Override
    public String toString() {
        return "Kettle{" +
                "volume=" + volume +
                ", manufacturer='" + manufacturer + '\'' +
                ", powerUsage=" + powerUsage +
                ", turnedOn=" + turnedOn +
                '}';
    }

    private int volume;
}
