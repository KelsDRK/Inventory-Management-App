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
import javafx.stage.Stage;
import kbur.c482.model.Errors;
import kbur.c482.model.Inventory;
import kbur.c482.model.Part;
import kbur.c482.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {


    @FXML TableView<Part> SelectedPartsTable;
    @FXML TableColumn<Part, Integer> PartIdColumnSecondary;
    @FXML TableColumn<Part, String> PartNameColumnSecondary;
    @FXML TableColumn<Part, Integer> InvLevelColumnSecondary;
    @FXML TableColumn<Part, Double> PriceColumnSecondary;

    @FXML TableView<Part> AllPartsTable;
    @FXML TableColumn<Part, Integer> PartIdColumn;
    @FXML TableColumn<Part, String> PartNameColumn;
    @FXML TableColumn<Part, Integer> InvLevelColumn;
    @FXML TableColumn<Part, Double> PriceColumn;

    @FXML TextField SearchFieldText;

    @FXML TextField IdField;
    @FXML TextField NameField;
    @FXML TextField InvField;
    @FXML TextField PriceField;
    @FXML TextField MaxField;
    @FXML TextField MinField;

    @FXML Button AddButton;
    @FXML Button RemoveAssociatedButton;
    @FXML Button SaveButton;
    @FXML Button CancelButton;
    @FXML Button SearchButton;

    Random random = new Random();

    private static Part partModify;
    private static Product productModify;


    private ObservableList<Part> productParts = FXCollections.observableArrayList();




    /*The Initializer will be created with empty Fields for the user to input data. The ID field will also be Disabled
    * and a Random ID will be generated for the user*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IdField.setDisable(true);
        IdField.setText(getNewPartID());

        PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<Part> parts = Inventory.getAllParts();
        AllPartsTable.setItems(parts);

        PartIdColumnSecondary.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumnSecondary.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvLevelColumnSecondary.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceColumnSecondary.setCellValueFactory(new PropertyValueFactory<>("price"));
        SelectedPartsTable.setItems(productParts);

    }

    //this function is the logic behind getting the random ID used in the initializer.
    public String getNewPartID() {
        int productId = random.nextInt();
        boolean partFound = false;
        // the new ID will be 4 digits or fewer
        while (productId < 1000 || productId > 9999) {
            productId = random.nextInt();
        }
        return Integer.toString(productId);
    }

    /*If the user selects the "Cancel" option then they will be returned to the main menu*/
    public void OnCancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
    }


    /*The search function will place the Information placed into the search text field into a variable "q". It will
    * create a list of results from the function "searchByPartName" which will be discussed further down. If there are
    * no results for searching by name, then the function will try searching by ID*/
    public void onSearchAction(ActionEvent actionEvent) {

        String q = SearchFieldText.getText();
        ObservableList<Part> searchedPart = searchByPartName(q);

        SearchFieldText.clear();

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
        AllPartsTable.setItems(searchedPart);
    }

    /*The logic used to search by a parts ID
    * We will iterate through the list of Parts and crosscheck their ID with the ID provided in the search.
    * */
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

    /*Creates a list of Parts that include a partial match of whatever is entered into the search. If there is a match
    * then it will be placed into the "Named Part" list and the list will be returned.*/
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

    /*This function places Parts from the Global Parts list into a list of Selected Parts that the Product needs.
    * The selected Part will be added to a list "Product Parts" and that list will populate the Selected Parts Table*/
    public void onAddButtonAction(ActionEvent actionEvent) throws IOException {

        Part addedToProduct = (Part) AllPartsTable.getSelectionModel().getSelectedItem();

        if (addedToProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected, Choose a part from the table to modify.");
            alert.showAndWait();
        } else {
            productParts.add(addedToProduct);
            SelectedPartsTable.setItems(productParts);
        }

    }

    /*This function highlights the item that the user has selected and then removes that item from the list "Product
    * Parts". The Selected Parts Table is then populated with whatever items are left in the Product Parts list*/
    public void onRemoveAssociatedAction(ActionEvent actionEvent) {

        partModify = SelectedPartsTable.getSelectionModel().getSelectedItem();
        if (partModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected, Choose a part from the table to modify.");
            alert.showAndWait();
        } else {
            productParts.remove(partModify);
            SelectedPartsTable.setItems(productParts);
        }

    }

    /*This function is parsing values from the form to use as arguments to create a new Product Object. "Linked Parts"
    * (parts moved to the Selected Parts table during creation of the Product) are also saved. The new Product Object
    * is added to Inventory.*/
    public void onSaveAction(ActionEvent actionEvent) {
        try {
            int index = Inventory.getAllProducts().indexOf(productModify);
            int max = Integer.parseInt(MaxField.getText());
            int min = Integer.parseInt(MinField.getText());
            int inventory = Integer.parseInt(InvField.getText());
            double price = Double.parseDouble(PriceField.getText());
            String name = NameField.getText();
            int id = Integer.parseInt(IdField.getText());

            boolean addPart = false;
            if (Errors.checkInventory(min, max, inventory) && Errors.checkMinValue(min, max)) {
                Product newProduct = new Product(id, name, price, inventory, min, max);
                newProduct.addLinkedParts(productParts);
                Inventory.addProduct(newProduct);
                addPart = true;
            }
            if (addPart) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 500);
                stage.setTitle("Modify Product");
                stage.setScene(scene);
            }

        } catch (IOException e) {
            Errors.alertError("Error", "Error Modifying Part", "Check that all fields are correctly filled and try again.");
        }


    }



}
