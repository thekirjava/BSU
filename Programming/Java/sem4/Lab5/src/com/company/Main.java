package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    static class TypeHandler implements EventHandler {

        @Override
        public void handle(Event event) {
            switch (comboBox.getValue()) {
                case "int":
                    try {
                        Integer.parseInt(field.getText());
                        circle.setFill(Color.GREEN);
                    }
                    catch (NumberFormatException e) {
                        circle.setFill(Color.RED);
                    }
                    break;
                case "double":
                    try {
                        Double.parseDouble(field.getText());
                        circle.setFill(Color.GREEN);
                    }
                    catch (NumberFormatException e) {
                        circle.setFill(Color.RED);
                    }
                    break;
                case"date":
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
                        dateFormat.parse(field.getText());
                        circle.setFill(Color.GREEN);
                    }
                    catch (ParseException e) {
                        circle.setFill(Color.RED);
                    }
                    break;
                case "time":
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                        dateFormat.parse(field.getText());
                        circle.setFill(Color.GREEN);
                    }
                    catch (ParseException e) {
                        circle.setFill(Color.RED);
                    }
                    break;
                case"email":
                    Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                    Matcher mat = pattern.matcher(field.getText());
                    if(mat.matches()){
                        circle.setFill(Color.GREEN);
                    }
                    else{
                        circle.setFill(Color.RED);
                    }
                    break;
            }
        }

        public TypeHandler(Circle circle, TextField field, ComboBox<String> comboBox) {
            this.circle = circle;
            this.field = field;
            this.comboBox = comboBox;
        }

        Circle circle;
        TextField field;
        ComboBox<String> comboBox;
    }
    @Override
    public void start(Stage stage) {
        ObservableList<String> options = FXCollections.observableArrayList("int", "double", "date", "time", "email");
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("int");

        TextField field = new TextField();
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(Color.RED);
        comboBox.setOnAction(new TypeHandler(circle, field, comboBox));
        field.setOnAction(new TypeHandler(circle, field, comboBox));
        BorderPane.setAlignment(comboBox, Pos.CENTER_LEFT);
        FlowPane flow = new FlowPane(10, 10, circle, field);
        BorderPane.setAlignment(flow, Pos.CENTER_RIGHT);
        BorderPane root = new BorderPane();
        root.setLeft(comboBox);
        root.setBottom(flow);
        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(900);
        stage.setTitle("Lab5");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
