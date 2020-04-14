package bsu.fpmi.artsiushkevich.controller;

import bsu.fpmi.artsiushkevich.observer.Publisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;


public class Controller {
    @FXML
    public void initialize() {
        publisher = new Publisher();
        listModel = FXCollections.observableArrayList();
        publisher.subscribe(new LabelSubscriber(keyLabel));
        publisher.subscribe(new ListSubscriber(listModel));
        keyList.setItems(listModel);
    }

    @FXML
    public void Pressed(KeyEvent keyEvent) {
        publisher.notifySubs(keyEvent.getCode().getName());
    }

    @FXML
    Label keyLabel;
    @FXML
    ListView<String> keyList;
    Publisher publisher;
    ObservableList<String> listModel;
}
