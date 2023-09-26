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
import kbur.c482.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.jar.Attributes;


public class ModifyProduct implements Initializable {

    @FXML TableView<Part> AllPartsTable;
    @FXML TableColumn<Part, Integer> PartIdColumn;
    @FXML TableColumn<Part, String> PartNameColumn;
    @FXML TableColumn<Part, Integer> InvLevelColumn;
    @FXML TableColumn<Part, Double> PriceColumn;

    @FXML TableView<Part> SelectedPartsTable;
    @FXML TableColumn<Part, Integer> selectedPartIdColumn;
    @FXML TableColumn<Part, String> selectedPartNameColumn;
    @FXML TableColumn<Part, Integer> selectedInvLevelColumn;
    @FXML TableColumn<Part, Double> selectedPriceColumn;

    @FXML TextField SearchFieldText;

    @FXML TextField IdField;
    @FXML TextField NameField;
    @FXML TextField InvField;
    @FXML TextField PriceCostField;
    @FXML TextField MaxField;
    @FXML TextField MinField;

    @FXML Button AddButton;
    @FXML Button RemoveAssociatedButton;
    @FXML Button SaveButton;
    @FXML Button CancelButton;
    @FXML Button SearchButton;


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> bottomList = FXCollections.observableArrayList();

    //gets the product to be modified
    public Product productModify;





    /*Retrieves the Product we are modifying from the main menu and then initializes the Modify Product form based on the
     * information that the Product contains. ID Field is disabled.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productModify = MainMenu.getProductModify();
        associatedParts = productModify.getAllAssociatedParts();


        PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AllPartsTable.setItems(Inventory.getAllParts());

        selectedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        SelectedPartsTable.setItems(associatedParts);

        IdField.setText(String.valueOf(productModify.getId()));
        NameField.setText(String.valueOf(productModify.getName()));
        InvField.setText(String.valueOf(productModify.getStock()));
        PriceCostField.setText(String.valueOf(productModify.getPrice()));
        MaxField.setText(String.valueOf(productModify.getMax()));
        MinField.setText(String.valueOf(productModify.getMin()));

        IdField.setDisable(true);

    }


    /*The user will select a Part from the Parts table and upon hitting the add button, the selected part will be
    * added to the Selected Parts table.*/
    public void onAddAction(ActionEvent actionEvent) {

        Part addedToProduct = AllPartsTable.getSelectionModel().getSelectedItem();

        if (addedToProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected, Choose a part from the table to modify.");
            alert.showAndWait();
        } else {
            associatedParts.add(addedToProduct);
            SelectedPartsTable.setItems(associatedParts);
        }

    }

    /*The user will select a Part from the Selected Parts table and upon hitting the remove button, the selected part
    will be removed from the Selected Parts table.*/
    public void onRemovePartAction(ActionEvent actionEvent) {
        Part partToRemove = SelectedPartsTable.getSelectionModel().getSelectedItem();
        if (partToRemove == null) {
            Errors.alertError("Error", "Select a part","Select a part to remove from the product");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove part from the product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                productModify.deleteAssociatedPart(partToRemove);
                associatedParts.remove(partToRemove);
                SelectedPartsTable.setItems(associatedParts);
            }
        }
    }


    /*Saving will parse the values from the Form and those values will be used to create a Part object. The inventory
     will be updated with the Modified part.*/
    public void onSaveAction(ActionEvent actionEvent) throws IOException {

        try {

            int index = Inventory.getAllProducts().indexOf(productModify); //get id not index, create setters

            int max = Integer.parseInt(MaxField.getText());
            int min = Integer.parseInt(MinField.getText());
            int inventory = Integer.parseInt(InvField.getText());
            double price = Double.parseDouble(PriceCostField.getText());
            int id = Integer.parseInt(IdField.getText());
            String name = NameField.getText();

            boolean addPart = true;

            if (addPart) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 500);
                stage.setTitle("Modify Product");
                stage.setScene(scene);
            }

            Product modifyProduct = new Product(id, name, price, inventory, min, max);

            modifyProduct.setAssociatedParts(associatedParts);

            Inventory.updateProduct(index, modifyProduct);

        }
        catch (Exception e) {
            //Errors.alertError("Error", "Error Modifying Part", "Check that all fields are correctly filled and try again.");

        }


    }


    /*Selecting the cancel option will prompt the user to confirm cancellation. If the user confirms then they will be
     * returned to the main menu. If a user does not confirm then they will continue the Modify Product form.*/
    public void onCancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
    }


    /*OnSearchAction is storing the textField entry into a String variable called "q". We are creating a list using the
    function we created earlier called searchByPartName with an argument of the textField "q". If the Observable list
    we have created "searchPart" is == o (a name was not passed therefor we will look for an Integer "id") we will
    try to parse the Integer from the String entered into the textField and use that Integer as an argument to call the
    method "searchByPartID"
     */
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


    /*SearchByPartID iterates through a list of "allParts". If the argument passed in "partId" matches the getter call
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
}
