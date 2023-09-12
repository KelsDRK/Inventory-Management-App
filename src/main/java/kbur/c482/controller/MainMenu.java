package kbur.c482.controller;

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

import javafx.stage.Stage;
import kbur.c482.model.Inventory;
import kbur.c482.model.Part;
import kbur.c482.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainMenu implements Initializable {

    @FXML
    public TableView<Part> PartsTable;
    @FXML
    public TableColumn<Part, Integer>partIdColumn;
    @FXML
    public TableColumn<Part, String>partNameColumn;
    @FXML
    public TableColumn<Part, Integer> partsInvLevelColumn;
    @FXML
    public TableColumn<Part, Double> partPriceColumn;

    @FXML
    public TableView<Product> ProductsTable;
    @FXML
    public TableColumn<Product, Integer> productIdColumn;
    @FXML
    public TableColumn<Product, String> productNameColumn;
    @FXML
    public TableColumn<Product, Integer> productInvLevelColumn;
    @FXML
    public TableColumn<Product, Double> productPriceColumn;

    public TextField searchParts;
    public TextField searchProducts;


    private  ObservableList<Part> allParts = FXCollections.observableArrayList();
    private  ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private Inventory inventory;

    public Button AddPartsButton;
    public Button ModifyPartsButton;
    public Button DeletePartsButton;

    public Button AddProductsButton;
    public Button ModifyProductsButton;
    public Button DeleteProductsButton;

    public MainMenu() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartsTable.setItems(allParts);
        ProductsTable.setItems(allProducts);

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //partIdColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        ObservableList<Part> parts = Part.getAllParts();
        PartsTable.setItems(parts);

        ObservableList<Product> products = Product.getAllProducts();
        ProductsTable.setItems(products);


       /*( allParts.add(new Part(11, "Tool", 25.6, 5, 1, 4));
        allParts.add(new Part(12, "Hammer", 24.0, 44, 4, 7));
        allParts.add(new Part(13, "Wrench", 54.3, 3, 3, 8));
        allParts.add(new Part(14, "Claw", 64.4, 6, 8, 23)); */

        /*allProducts.add(new Product(35, "Ketchup", 3.00, 3, 1, 5));
        allProducts.add(new Product(3, "Mustard", 6.00, 66, 1, 5));
        allProducts.add(new Product(66, "BBq", 7.00, 76, 1, 5));
        allProducts.add(new Product(10, "Ranch", 8.00, 4, 1, 5)); */



    }



    public void OnExitButtonClicked(ActionEvent actionEvent) {
        System.out.println("Exit Button");
    }


    /*Search by part name is going to take in a String "partialname" and compare it to Part objects in the list "allParts" using the "getName" function from the class "Parts".
    If there is a match then it will return the observable list  with the included Part (partialName) that we are looking at for comparison.
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

    /*Search by product name is going to take in a String "partialname" and compare it to Product objects in the list "allProducts" using the "getName" function from the class "Products".
    If there is a match then it will return the observable list  with the included Product (partialName) that we are looking at for comparison.
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

    public void OnProductSearch(ActionEvent actionEvent) {
        String q = searchProducts.getText();

        ObservableList<Product> searchedProduct = searchByProductName(q);

        if (searchedProduct.size() == 0) {
            try {
                int productId = Integer.parseInt(q);
                Product product = searchByProductID(productId);
                if (product != null)
                    searchedProduct.add(product);
            }

            catch (NumberFormatException e) {

            }
        }


        ProductsTable.setItems(searchedProduct);
    }

    public void OnPartSearch(ActionEvent actionEvent) {
        String q = searchParts.getText();

        ObservableList<Part> searchedPart = searchByPartName(q);

        if (searchedPart.size() == 0) {
            try {
                int partId = Integer.parseInt(q);
                Part part = searchByPartID(partId);
                if (part != null)
                    searchedPart.add(part);
            }

            catch (NumberFormatException e) {

            }
        }

        PartsTable.setItems(searchedPart);
    }


    public void OnDeletePartsClicked(ActionEvent actionEvent) {
        Part deletePart = (Part) PartsTable.getSelectionModel().getSelectedItem();

        if (deletePart == null)
            return;

        allParts.remove(deletePart);

    }

    public void OnDeleteProductsClicked(ActionEvent actionEvent) {
        Product deleteProduct = (Product) ProductsTable.getSelectionModel().getSelectedItem();

        if (deleteProduct == null)
            return;

        allProducts.remove(deleteProduct);
    }

    public void OnAddPartsClicked (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
    }

    public void OnAddProductClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Add Product");
        stage.setScene(scene);
    }

    public void OnModifyProductsClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
    }

    public void OnModifyPartsClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
    }

    public void PartsTableController(SortEvent<TableView<Part>> tableViewSortEvent) {
    }


}