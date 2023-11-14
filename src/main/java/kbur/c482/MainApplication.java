package kbur.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    // ********** RUNTIME ERROR **********
    // All Products Associated Parts list would be updated when only ONE was changed.
    // SOLUTION: Used getters to "get" a specific associated part from a "productmodify". The "productmodify" is a
    // product with its own associated parts list and not a universal parts list. I could then "get" the parts from the
    // product specific list.

    // ****** FUTURE ENHANCEMENT ******
    // In case of machine/company specific changes, Show Part Machine ID / Company Name inside Modify Product screen and
    // add Machine ID/Company Name into the search logic.
    // This can allow changes to be made based on MachineID/Company Name if required in the future.

    public static void main(String[] args) {
        launch();
    }


}