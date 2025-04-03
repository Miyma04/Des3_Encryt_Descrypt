package com.encryptiontool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("3DES Encryption Tool");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки UI: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
