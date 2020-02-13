package com.company;

import java.util.Objects;

public class Plane {

    private class Wing {
        public Wing(double wingArea, double wingspan) {
            this.wingArea = wingArea;
            this.wingspan = wingspan;
        }

        public Wing() {
            this.wingArea = 0;
            this.wingspan = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Wing)) return false;
            Wing wing = (Wing) o;
            return Double.compare(wing.getWingArea(), getWingArea()) == 0 &&
                    Double.compare(wing.getWingspan(), getWingspan()) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getWingArea(), getWingspan());
        }

        @Override
        public String toString() {
            return "Wing{" +
                    "wingArea=" + wingArea +
                    ", wingspan=" + wingspan +
                    '}';
        }

        public boolean fly() {
            if ((wingspan > 0) && (wingArea > 0)) {
                System.out.println("Wing is ready");
                return true;
            } else {
                System.out.println("Oh no, wing is gone!");
                return false;
            }
        }

        public double getWingArea() {
            return wingArea;
        }

        public double getWingspan() {
            return wingspan;
        }

        double wingArea;
        double wingspan;
    }

    private class Engine {
        public Engine(String model, int fuelCapacity) {
            this.model = model;
            this.fuelCapacity = fuelCapacity;
        }

        public Engine() {
            this.model = "";
            this.fuelCapacity = 0;
        }

        @Override
        public String toString() {
            return "Engine{" +
                    "model='" + model + '\'' +
                    ", fuelCapacity=" + fuelCapacity +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Engine)) return false;
            Engine engine = (Engine) o;
            return getFuelCapacity() == engine.getFuelCapacity() &&
                    getModel().equals(engine.getModel());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getModel(), getFuelCapacity());
        }

        public boolean fly() {
            if ((!model.equals("")) && (fuelCapacity > 0)) {
                System.out.println("Engine is full and roaring");
                return true;
            } else {
                System.out.println("How you're managed to fly without engine?");
                return false;
            }
        }

        public String getModel() {
            return model;
        }

        public int getFuelCapacity() {
            return fuelCapacity;
        }

        String model;
        int fuelCapacity;
    }

    private class Chassis {
        public Chassis(int amount, double wheelRadius) {
            this.amount = amount;
            this.wheelRadius = wheelRadius;
        }

        public Chassis() {
            this.amount = 0;
            this.wheelRadius = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Chassis)) return false;
            Chassis chassis = (Chassis) o;
            return getAmount() == chassis.getAmount() &&
                    Double.compare(chassis.getWheelRadius(), getWheelRadius()) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getAmount(), getWheelRadius());
        }

        @Override
        public String toString() {
            return "Chassis{" +
                    "amount=" + amount +
                    ", wheelRadius=" + wheelRadius +
                    '}';
        }

        public boolean fly() {
            if ((this.amount > 0) && (this.wheelRadius > 0)) {
                System.out.println("Chassis removed");
                return true;
            } else {
                System.out.println("Oh no, there is no chassis");
                return false;
            }
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getWheelRadius() {
            return wheelRadius;
        }

        public void setWheelRadius(double wheelRadius) {
            this.wheelRadius = wheelRadius;
        }

        int amount;
        double wheelRadius;
    }

    public Plane(double leftWingArea, double leftWingspan, double rightWingArea, double rightWingspan, String model, int fuelCapacity, int amount, int wheelRadius, int chassisAmount) {
        this.leftWing = new Wing(leftWingArea, leftWingspan);
        this.rightWing = new Wing(leftWingArea, leftWingspan);
        this.engine = new Engine(model, fuelCapacity);
        this.chassis = new Chassis(amount, wheelRadius);
        this.chassisAmount = chassisAmount;
        this.destination = "";
    }

    public Plane() {
        this.leftWing = new Wing();
        this.rightWing = new Wing();
        this.engine = new Engine();
        this.chassis = new Chassis();
        this.chassisAmount = 0;
        this.destination = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;
        Plane plane = (Plane) o;
        return getChassisAmount() == plane.getChassisAmount() &&
                getLeftWing().equals(plane.getLeftWing()) &&
                getRightWing().equals(plane.getRightWing()) &&
                getEngine().equals(plane.getEngine()) &&
                getChassis().equals(plane.getChassis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeftWing(), getRightWing(), getEngine(), getChassis(), getChassisAmount());
    }

    @Override
    public String toString() {
        return "Plane{" +
                "leftWing=" + leftWing +
                ", rightWing=" + rightWing +
                ", engine=" + engine +
                ", chassis=" + chassis +
                ", chassisAmount=" + chassisAmount +
                '}';
    }

    public void toFly() {
        boolean flag = true;
        flag &= chassis.fly();
        flag &= leftWing.fly();
        flag &= rightWing.fly();
        flag &= engine.fly();
        if (flag) {
            if (destination.equals("")) {
                System.out.println("Destination isn't chosen!");
            } else {
                System.out.println("Plane is perfectly flying!");
            }
        } else {
            System.out.println("Plane is falling down");
        }
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void printDestination() {
        System.out.println("Plane destination is " + this.destination);
    }

    public Wing getLeftWing() {
        return leftWing;
    }

    public Wing getRightWing() {
        return rightWing;
    }

    public Engine getEngine() {
        return engine;
    }

    public Chassis getChassis() {
        return chassis;
    }

    public int getChassisAmount() {
        return chassisAmount;
    }

    private Wing leftWing;
    private Wing rightWing;
    private Engine engine;
    private Chassis chassis;
    private int chassisAmount;
    private String destination;
}
