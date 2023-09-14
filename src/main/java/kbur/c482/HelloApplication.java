package kbur.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import kbur.c482.model.Inventory;
import kbur.c482.model.InHousePart;
import kbur.c482.model.OutsourcedPart;
import kbur.c482.model.Product;

import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }


}