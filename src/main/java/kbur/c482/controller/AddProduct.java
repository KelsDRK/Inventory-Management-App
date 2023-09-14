package kbur.c482.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static kbur.c482.model.Inventory.getAllParts;

public class AddProduct implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnCancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
    }

    public int generateId () {
        int newID = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
            newID += 2;
        }

        return newID;
    }
}
