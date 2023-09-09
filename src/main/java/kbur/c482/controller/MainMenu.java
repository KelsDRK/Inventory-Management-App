package kbur.c482.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void OnAddPartsClicked(ActionEvent actionEvent) {
        System.out.println("Add Parts");
    }

    public void OnModifyPartsClicked(ActionEvent actionEvent) {
        System.out.println("Modify parts");
    }

    public void OnDeletePartsClicked(ActionEvent actionEvent) {
        System.out.println("Delete Parts");
    }

    public void OnModifyProductsClicked(ActionEvent actionEvent) {
        System.out.println("Modify Products");
    }

    public void OnDeleteProductsClicked(ActionEvent actionEvent) {
        System.out.println("Delete Products");
    }

    public void OnExitButtonClicked(ActionEvent actionEvent) {
        System.out.println("Exit Button");
    }

    public void SearchProductsFunction(ActionEvent actionEvent) {
    }

    public void SearchPartsFunction(ActionEvent actionEvent) {
    }

    public void OnAddProductClicked(ActionEvent actionEvent) {
        System.out.println("Add Product");
    }
}