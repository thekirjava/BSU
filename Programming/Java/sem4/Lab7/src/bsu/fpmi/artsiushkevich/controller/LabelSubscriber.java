package bsu.fpmi.artsiushkevich.controller;

import bsu.fpmi.artsiushkevich.observer.Subscriber;
import javafx.scene.control.Label;

public class LabelSubscriber implements Subscriber {
    private Label label;

    public LabelSubscriber(Label label) {
        this.label = label;
    }

    @Override
    public void update(String data) {
        this.label.setText(data);
        int a = 8;
        a+=5;
    }
}
