package bsu.fpmi.artsiushkevich.controller;

import bsu.fpmi.artsiushkevich.observer.Subscriber;
import javafx.scene.control.Label;

public class LabelSubscriber implements Subscriber {
    public LabelSubscriber(Label label) {
        this.label = label;
    }

    @Override
    public void update(String data) {
        this.label.setText(data);
    }

    private Label label;
}
