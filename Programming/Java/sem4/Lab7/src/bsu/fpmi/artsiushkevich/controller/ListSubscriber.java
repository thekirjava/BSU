package bsu.fpmi.artsiushkevich.controller;

import bsu.fpmi.artsiushkevich.observer.Subscriber;
import javafx.collections.ObservableList;

public class ListSubscriber implements Subscriber {
    public ListSubscriber(ObservableList<String> model) {
        this.model = model;
    }

    @Override
    public void update(String data) {
        this.model.add(data);
    }

    private ObservableList<String> model;
}
