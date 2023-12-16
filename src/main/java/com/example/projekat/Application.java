package com.example.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Postavljanje kontrolera
        MainController controller = fxmlLoader.getController();

        // Inicijalizacija view-a
        controller.initializeView(stage);
        stage.setTitle("Prikaz Vozila");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
