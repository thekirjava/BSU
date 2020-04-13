package bsu.fpmi.artsiushkevich.main;

import bsu.fpmi.artsiushkevich.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new Controller());
        Parent root = FXMLLoader.load(getClass().getResource("window_scheme.fxml"));

        primaryStage.setTitle("Lab7");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
