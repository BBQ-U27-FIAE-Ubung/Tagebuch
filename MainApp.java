package de.bbq.tagebuch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(MainApp.class.getResource("cover.fxml"));
        Scene scene = new Scene(root, 800, 600);
        // Stylesheet einbinden
        scene.getStylesheets().add(getClass().getResource("book.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Dein Tagebuch");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
