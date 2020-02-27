package bsu.fpmi.artsiushkevich.devices;

import java.util.Objects;

public abstract class Device {
    public Device() {
        this.manufacturer = "";
        powerUsage = 0;
        this.turnedOn = false;
    }

    public Device(String manufacturer, int powerUsage, boolean turnedOn) {
        this.manufacturer = manufacturer;
        this.powerUsage = powerUsage;
        this.turnedOn = turnedOn;
    }

    public void switchDevice() {
        turnedOn = !turnedOn;
    }

    public boolean isTurnedOn(){
        return turnedOn;
    }

    public int getPowerUsage() {
        return powerUsage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return getPowerUsage() == device.getPowerUsage() &&
                isTurnedOn() == device.isTurnedOn() &&
                Objects.equals(manufacturer, device.manufacturer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(manufacturer, getPowerUsage(), isTurnedOn());
    }

    protected String manufacturer;
    protected int powerUsage;
    protected boolean turnedOn;
}
