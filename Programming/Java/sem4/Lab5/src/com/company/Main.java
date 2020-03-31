package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    static class TypeHandler implements EventHandler {

        @Override
        public void handle(Event event) {
            Pattern pattern = null;
            switch (comboBox.getValue()) {
                case "natural":
                    pattern = Pattern.compile("[1-9]+[0-9]*");
                    break;
                case "int":
                    pattern = Pattern.compile("[+-]?[0-9]+");
                    break;
                case "double":
                    pattern = Pattern.compile("[+-]?(([0-9]+[.,]?[0-9]*)|([0-9]*[.,]?[0-9]+))(e[+-]?[0-9]+)?");
                    break;
                case "date":
                    pattern = Pattern.compile("(((0?[1-9]|[1-2][0-9]|3[0-1])[/.](1|3|5|7|8|10|12|01|03|05|07|08)|" +
                            "(0?[1-9]|[1-2][0-9]|30)[/.](4|6|9|11|04|06|09))|" +
                            "(0?[1-9]|1[0-9]|2[0-8])[/.](2|02))" +
                            "[/.][0-9]+");
                    break;
                case "time":
                    pattern = Pattern.compile("(([01][0-9])|(2[0-3])):[0-5][0-9]");
                    break;
                case "email":
                    pattern = Pattern.compile("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+");
                    break;
            }
            Matcher matcher = pattern.matcher(field.getText());
            if (matcher.matches()) {
                circle.setFill(Color.GREEN);
            } else {
                circle.setFill(Color.RED);
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
        TabPane tabPane = new TabPane();
        ObservableList<String> options = FXCollections.observableArrayList("natural", "int", "double", "date", "time", "email");
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
        GridPane gridPane = new GridPane();
        gridPane.add(comboBox, 0, 0);
        gridPane.add(flow, 1, 0);
        gridPane.setAlignment(Pos.CENTER);
        BorderPane root1 = new BorderPane();
        root1.setCenter(gridPane);
        GridPane root2 = new GridPane();
        Button button = new Button("->");
        TextArea textArea = new TextArea();
        ObservableList<String> model = FXCollections.observableArrayList();
        button.setOnAction(event -> {
            model.clear();
            Pattern pattern = Pattern.compile("(((0?[1-9]|[1-2][0-9]|3[0-1])[/.](1|3|5|7|8|10|12|01|03|05|07|08)|" +
                    "(0?[1-9]|[1-2][0-9]|30)[/.](4|6|9|11|04|06|09))|" +
                    "(0?[1-9]|1[0-9]|2[0-8])[/.](2|02))" +
                    "[/.][0-9]+");
            int start = 0;
            Matcher matcher = pattern.matcher(textArea.getText());
            while (matcher.find(start)) {
                model.add(textArea.getText().substring(matcher.start(), matcher.end()));
                start = matcher.end();
            }
        });
        ListView<String> listView = new ListView<>(model);
        root2.add(textArea, 0, 0);
        root2.add(button, 1, 0);
        root2.add(listView, 2, 0);
        Tab tab1 = new Tab("Task 1");
        Tab tab2 = new Tab("Task 2");
        tab1.setContent(root1);
        tab2.setContent(root2);
        tab1.setClosable(false);
        tab2.setClosable(false);
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        Scene scene = new Scene(tabPane, 900, 500);
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
