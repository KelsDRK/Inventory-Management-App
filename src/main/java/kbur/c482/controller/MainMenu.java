package kbur.c482.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;

import javafx.stage.Stage;
import javafx.stage.Window;
import kbur.c482.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;


public class MainMenu implements Initializable {

    @FXML public TableView<Part> PartsTable;
    @FXML public TableColumn<Part, Integer>partIdColumn;
    @FXML public TableColumn<Part, String>partNameColumn;
    @FXML public TableColumn<Part, Integer> partsInvLevelColumn;
    @FXML public TableColumn<Part, Double> partPriceColumn;

    @FXML public TableView<Product> ProductsTable;
    @FXML public TableColumn<Product, Integer> productIdColumn;
    @FXML public TableColumn<Product, String> productNameColumn;
    @FXML public TableColumn<Product, Integer> productInvLevelColumn;
    @FXML public TableColumn<Product, Double> productPriceColumn;

    @FXML public TextField searchParts;
    @FXML public TextField searchProducts;

    private  ObservableList<Part> allParts = FXCollections.observableArrayList();
    private  ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Button AddPartsButton;
    public Button ModifyPartsButton;
    public Button DeletePartsButton;

    public Button AddProductsButton;
    public Button ModifyProductsButton;
    public Button DeleteProductsButton;

    private static Part partModify;
    public static Part getTheModifyPart () {return partModify;}

    private static Product productModify;
    public static Product getProductModify () {return productModify;}


    /*Initializer is populated with the test data*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTestData();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<Part> parts = Inventory.getAllParts();
        PartsTable.setItems(parts);

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<Product> products = Inventory.getAllProducts();
        ProductsTable.setItems(products);
    }

    /* firstTest protects the initializer from duplicating data upon return to the main screen from a different screen.
    */
    private static boolean firstTest = true;

    /*Adds data to use for testing functionality in the program.*/
    private void addTestData () {

        if(!firstTest) {
            return;
        }

        firstTest = false;

        InHousePart inPart1 = new InHousePart(1, "A01", 3.99, 5, 1, 10, 1);
        InHousePart inPart2 = new InHousePart(2, "A23", 5.99, 8, 1, 10, 2);
        OutsourcedPart outPart1 = new OutsourcedPart(3, "A99", 8.89, 12, 1, 20, "C1");

        Inventory.addPart(inPart1);
        Inventory.addPart(inPart2);
        Inventory.addPart(outPart1);

        Product product1 = new Product(4, "B23", 4.99, 5, 1, 15);
        Product product2 = new Product(5, "B14", 7.99, 6, 1, 10);
        Product product3 = new Product(6, "B55", 8.99, 7, 1, 15);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
    }


    // OnAddProductClicked takes the user to the "Add Product" form.
    public void OnAddProductClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Add Product");
        stage.setScene(scene);
    }

    // OnAddPartsClicked takes the user to the "Add part" form.
    public void OnAddPartClicked(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);

    }

    /*Gets the Product that the user has selected and uses this data to populate the "Modify Product" screen.*/
    public void OnModifyProductsClicked(ActionEvent actionEvent) throws IOException {

        productModify = (Product) ProductsTable.getSelectionModel().getSelectedItem();

        if (productModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected, Choose a part from the table to modify.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProduct.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
        }
    }

    /*Gets the Part that the user has selected and uses this data to populate the "Modify Part" screen.*/
    public void OnModifyPartsClicked(ActionEvent actionEvent) throws IOException {

        partModify = PartsTable.getSelectionModel().getSelectedItem();
        if (partModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected, Choose a part from the table to modify.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
        }
    }


    /* OnDeleteProductsClicked will look to see if there has been an object "Product" selected. If a Product has been
    selected we will remove the Product from the list of allProducts. If a Part has not been selected then nothing
    will happen.
     */
    public void OnDeleteProductsClicked(ActionEvent actionEvent) {

        ObservableList<Product> allProducts = Product.getAllProducts();

        Product deleteProduct = (Product) ProductsTable.getSelectionModel().getSelectedItem();

        if (deleteProduct == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Delete!");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            allProducts.remove(deleteProduct);
        } else {
            System.out.println("You clicked cancel. Please complete form.");
        }
    }

    /* OnDeletePartsClicked will look to see if there has been an object "Part" selected. If a Part has been selected
    then we will remove the Part from the list of allParts. If a Part has not been selected then nothing will happen.
     */
    public void OnDeletePartsClicked(ActionEvent actionEvent) {

        ObservableList<Part> allParts = Inventory.getAllParts();

        Part deletePart = (Part) PartsTable.getSelectionModel().getSelectedItem();

        if (deletePart == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Delete!");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            allParts.remove(deletePart);
        } else {
            System.out.println("You clicked cancel. Please complete form.");
        }
    }

    //  OnExitButtonClicked closes the program window after clicking the button "Exit"
    public void OnExitButtonClicked(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Exit!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            System.out.println("You clicked cancel. Please complete form.");
        }
    }


    /*SearchByPartName is going to take in a String "partialName" and compare it to Part objects in the list
    "allParts" using the "getName" function from the class "Parts". If there is a match then it will return the
    observable list  with the included Part (partialName) that we are looking at for comparison.
     */
    private ObservableList<Part> searchByPartName (String partialName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Part.getAllParts();

        for (Part p : allParts) {
            if (p.getName().contains(partialName)) {
                namedPart.add(p);
            }
        }

        return namedPart;
    }


    /*SearchByProductName is going to take in a String "partialName" and compare it to Product objects in the list
    "allProducts" using the "getName" function from the class "Products".If there is a match then it will return the
    observable list  with the included Product (partialName) that we are looking at for comparison.
     */
    private ObservableList<Product> searchByProductName (String partialName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProduct = Product.getAllProducts();

        for (Product p : allProduct) {
            if (p.getName().contains(partialName)) {
                namedProduct.add(p);
            }
        }

        return namedProduct;
    }


    /*SearchByPartsID iterates through a list of "allParts". If the argument passed in "partId" matches the getter call
    "part.getId" we return the Object (Part) otherwise we return null as are not interested in doing anything if there
    is not a match.
     */
    private Part searchByPartID (int partId) {
        ObservableList<Part> allParts = Part.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            if (part.getId() == partId) {
                return part;
            }
        }

        return null;
    }


    /*SearchByProductID iterates through a list of "allProducts". If the argument passed in "productId" matches the
    getter call "product.getId" we return the Object (Product) otherwise we return null as are not interested in doing
    anything if there is not a match.
     */
    private Product searchByProductID (int productId) {
        ObservableList<Product> allProducts = Product.getAllProducts();


        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            if (product.getId() == productId) {
                return product;
            }
        }

        return null;
    }

    /*OnPartSearch is storing the textField entry into a String variable called "q". We are creating a list using the
    function we created earlier called searchByPartName with an argument of the textField "q". If the Observable list
    we have created "searchPart" is == o (a name was not passed therefor we will look for an Integer "id") we will
    try to parse the Integer from the String entered into the textField and use that Integer as an argument to call the
    method "searchByPartID"
     */
    public void OnPartSearch(ActionEvent actionEvent) {

        String q = searchParts.getText();

        ObservableList<Part> searchedPart = searchByPartName(q);

        searchParts.clear();

        if (searchedPart.size() == 0) {
            try {
                int partId = Integer.parseInt(q);
                Part part = searchByPartID(partId);
                if (part != null)
                    searchedPart.add(part);
            }

            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No Product found.. Try another search.");
                alert.showAndWait();

            }
        }

        PartsTable.setItems(searchedPart);
    }

    /*OnProductSearch is storing the textField entry into a String variable called "q". We are creating a list using
    the function we created earlier called searchByProductName with an argument of the textField "q". If the Observable
    list we have created "searchProduct" is == o (a name was not passed therefor we will look for an Integer "id") we
    will try to parse the Integer from the String entered into the textField and use that Integer as an argument to call
     the method "searchByProductID"
     */
    public void OnProductSearch(ActionEvent actionEvent) {

        String q = searchProducts.getText();

        ObservableList<Product> searchedProduct = searchByProductName(q);

        searchProducts.clear();



        if (searchedProduct.size() == 0) {
            try {
                int productId = Integer.parseInt(q);
                Product product = searchByProductID(productId);
                if (product != null)
                    searchedProduct.add(product);
            }

            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No Product found.. Try another search.");
                alert.showAndWait();

            }

        }


        ProductsTable.setItems(searchedProduct);
    }

}