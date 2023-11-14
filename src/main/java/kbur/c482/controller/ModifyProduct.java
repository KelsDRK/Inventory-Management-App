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

    /**
     * observable lost of associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * gets the product to be modified
     */
    private static Product productModify;

    /**
     * Retrieves the Product we are modifying from the main menu and then initializes the Modify Product form based on the
     * information that the Product contains. ID Field is disabled.
     */
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

    /** The user will select a Part from the Parts table and upon hitting the add button, the selected part will be
      added to the Selected Parts table. */
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

    /** The user will select a Part from the Selected Parts table and upon hitting the remove button, the selected part
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

    /** String is passed as argument and function attempts to parse a Double. */
    private static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    /** String is passed as an arguments and function attempts to parse an Integer. */
    private static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }


    /** In the beginning of this function the errors are being checked first. We start by checking if there are any empty
    fields and returning the corresponding error message if so. Then we step through the individual fields to make sure
    they contain the correct value type (String, Integer, Double). After we are parsing the different fields of the
    AddProduct form and then using those values to create a new Product object. The Product and any associated parts
     are then added to the Inventory.

    // ********** RUNTIME ERROR **********
    // All Products Associated Parts list would be updated when only ONE was changed.
    // SOLUTION: Used getters to "get" a specific associated part from a "productmodify". The "productmodify" is a
    // product with its own associated parts list and not a universal parts list. I could then "get" the parts from the
    // product specific list.

    // ****** FUTURE ENHANCEMENT ******
    // In case of machine/company specific changes, Show Part Machine ID / Company Name inside Modify Product screen and
    // add Machine ID/Company Name into the search logic.
    // This can allow changes to be made based on MachineID/Company Name if required in the future. */

    public void onSaveAction(ActionEvent actionEvent) throws IOException {

        if (NameField.getText().trim().isEmpty() || PriceCostField.getText().trim().isEmpty() ||
                InvField.getText().trim().isEmpty() || MaxField.getText().trim().isEmpty() ||
                MinField.getText().trim().isEmpty()) {
            showError(1);
            return;

        }

        if ((!isDouble(PriceCostField.getText().trim()) || (Double.parseDouble(PriceCostField.getText().trim())) <= 0)){
            showError(3);
            return;
        }

        if ((!isInteger(MinField.getText().trim()) || (Integer.parseInt(MinField.getText().trim())) <= 0)){
            showError(4);
            return;
        }
        if ((!isInteger(MaxField.getText().trim()) || (Integer.parseInt(MaxField.getText().trim())) < Integer.parseInt(MinField.getText().trim()))){

            showError(5);
            return;
        }
        if (!isInteger(InvField.getText().trim())){
            showError(6);
            return;
        }
        if ((Integer.parseInt(InvField.getText().trim()) > Integer.parseInt(MaxField.getText().trim())) ||
                (Integer.parseInt(InvField.getText().trim()) < Integer.parseInt(MinField.getText().trim()))){
            showError(7);
            return;
        }

        try {

            int index = Inventory.getAllProducts().indexOf(productModify);

            int max = Integer.parseInt(MaxField.getText());
            int min = Integer.parseInt(MinField.getText());
            int inventory = Integer.parseInt(InvField.getText());
            double price = Double.parseDouble(PriceCostField.getText());
            int id = Integer.parseInt(IdField.getText());
            String name = NameField.getText();

            boolean addPart = false;

            if (Errors.checkMinValue(min, max) && Errors.checkInventory(min, max, inventory)) {
                Product newProduct = new Product(id, name, price, inventory, min, max);
                newProduct.setAssociatedParts(SelectedPartsTable.getItems());
                addPart = true;
            }

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

    /**This is our list of errors we are using in the save function. Each error code corresponds to a different error for
    ease of calling.*/
    private void showError(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");

        if (errorCode == 1){
            //all fields required
            alert.setHeaderText("Missing Fields");
            alert.setContentText("All Fields Are Required.");
        }

        if (errorCode == 2){
            // machine ID error
            alert.setHeaderText("Machine ID Error");
            alert.setContentText("Machine ID must be an Integer. Ex: 25");
        }

        if (errorCode == 3){
            //price error
            alert.setHeaderText("Invalid Price");
            alert.setContentText("Price must be number greater than 0 (Ex: 2.99)");
        }

        if (errorCode == 4){
            //min error
            alert.setHeaderText("Min Error");
            alert.setContentText("Min must be an integer greater than 0");
        }

        if (errorCode == 5){
            //max error
            alert.setHeaderText("Max Error");
            alert.setContentText("Max must be an integer greater than or equal to the Min.");
        }

        if (errorCode == 6){
            //inv error
            alert.setHeaderText("Inv Error");
            alert.setContentText("The current Inv must be an Integer. Ex: 25");
        }

        if (errorCode == 7){
            //inv out of min max range
            alert.setHeaderText("Inv out of range Error");
            alert.setContentText("The current Inv must be between the min and max.");
        }
        alert.showAndWait();
    }


    /**Selecting the cancel option will prompt the user to confirm cancellation. If the user confirms then they will be
     * returned to the main menu. If a user does not confirm then they will continue the Modify Product form.*/
    public void onCancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
    }


    /** OnSearchAction is storing the textField entry into a String variable called "q". We are creating a list using the
    function we created earlier called searchByPartName with an argument of the textField "q". If the Observable list
    we have created "searchPart" is == o (a name was not passed therefor we will look for an Integer "id") we will
    try to parse the Integer from the String entered into the textField and use that Integer as an argument to call the
    method "searchByPartID". */
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


    /** SearchByPartID iterates through a list of "allParts". If the argument passed in "partId" matches the getter call
    "part.getId" we return the Object (Part) otherwise we return null as are not interested in doing anything if there
    is not a match. */
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


    /** SearchByPartName is going to take in a String "partialName" and compare it to Part objects in the list
    "allParts" using the "getName" function from the class "Parts". If there is a match then it will return the
    observable list  with the included Part (partialName) that we are looking at for comparison. */
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
