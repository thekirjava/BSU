package bsu.fpmi.artsiushkevich.observer;

import java.awt.*;
import java.util.ArrayList;

public class Observable {
    public Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.handleEvent(message);
        }
    }

    public void notifyObservers(String message, Graphics g) {
        for (Observer observer : observers) {
            observer.handleEvent(message, g);
        }
    }

    ArrayList<Observer> observers;
}
