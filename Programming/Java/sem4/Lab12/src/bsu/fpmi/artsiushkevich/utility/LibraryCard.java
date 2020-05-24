package bsu.fpmi.artsiushkevich.utility;

public class LibraryCard {
    public LibraryCard() {
        this.name = "John";
        this.name = "Dow";
        this.id = "000000";
        this.returned = 0;
        this.taken = 0;
    }

    public LibraryCard(String name, String surname, String id, int returned, int taken) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.returned = returned;
        this.taken = taken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public int getTaken() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        return "LibraryCard{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id='" + id + '\'' +
                ", returned=" + returned +
                ", taken=" + taken +
                '}';
    }

    public String name;
    public String surname;
    public String id;
    public int returned;
    public int taken;
}
