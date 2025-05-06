package de.bbq.tagebuch;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CoverController {

    @FXML private TextField nameField;

    @FXML
    private void handleStart(ActionEvent event) {
        try {
            // FXML loader für das „Buch“-Layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("book.fxml"));
            Parent root = loader.load();

            // Controller herausziehen und den Namen setzen
            BookController bookCtrl = loader.getController();
            bookCtrl.setUserName(nameField.getText().trim());

            // Neues Fenster öffnen
            Stage stage = new Stage();
            stage.setTitle("Dein Tagebuch");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

            // Cover-Fenster schließen
            ((Stage) nameField.getScene().getWindow()).close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
