package kbur.c482.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kbur.c482.model.*;
import kbur.c482.model.InHousePart;
import kbur.c482.model.Inventory;
import kbur.c482.model.Errors;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {


    @FXML private TextField IdField;
    @FXML private TextField NameField;
    @FXML private TextField InvField;
    @FXML private TextField PriceCostField;
    @FXML private TextField MaxField;
    @FXML private TextField MinField;
    @FXML private TextField MachineID;

    @FXML private Text MachIneOrCompLabel;

    @FXML public RadioButton InHouseRadioButton;
    @FXML public RadioButton OutsourcedRadioButton;

    private Part partModify;
    Inventory inventory = new Inventory();



    //If In-House Label is selected then Machine ID will be prompted.
    public void InHouseRadio(ActionEvent actionEvent) {
        MachIneOrCompLabel.setText("Machine ID");
    }

    //If Outsourced label is selected then Company Name will be prompted.
    public void OutsourcedRadio(ActionEvent actionEvent) {
        MachIneOrCompLabel.setText("Company Name");
    }


    /*Retrieves the Part we are modifying from the main menu and then initializes the Modify Part form based on the
    * information that the Part contains. InHouse parts will have a machine ID. Outsourced parts
    * will have a company name. ID Field is disabled.*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partModify = MainMenu.getTheModifyPart();

        IdField.setText(String.valueOf(partModify.getId()));
        NameField.setText(String.valueOf(partModify.getName()));
        InvField.setText(String.valueOf(partModify.getStock()));
        PriceCostField.setText(String.valueOf(partModify.getPrice()));
        MaxField.setText(String.valueOf(partModify.getMax()));
        MinField.setText(String.valueOf(partModify.getMin()));

        if (partModify instanceof InHousePart) {
            InHousePart modifyInHousePart = (InHousePart) partModify;
            InHouseRadioButton.setSelected(true);
            MachIneOrCompLabel.setText("Machine ID");
            MachineID.setText(Integer.toString(modifyInHousePart.getMachineId()));
        }
        if (partModify instanceof OutsourcedPart) {
            OutsourcedPart modifyOutsourcedPart = (OutsourcedPart) partModify;
            OutsourcedRadioButton.setSelected(true);
            MachIneOrCompLabel.setText("Company ID");
            MachineID.setText((modifyOutsourcedPart.getCompanyName()));
        }

        IdField.setDisable(true);


    }

    //String is passed as argument and function attempts to parse a Double.
    private static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    //String is passed as an arguments and function attempts to parse an Integer.
    private static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }



    /*Selecting the cancel option will prompt the user to confirm cancellation. If the user confirms then they will be
    * returned to the main menu. If a user does not confirm then they will continue the Modify Part form.*/
    public void OnCancelAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Modify Cancellation");
        alert.setContentText("Cancel modifying part? You will be returned to the main menu.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
        } else {
            System.out.println("You clicked cancel. Please complete form.");
        }

    }


    /*In the beginning of this function the errors are being checked first. We start by checking if there are any empty
    fields and returning the corresponding error message if so. Then we step through the individual fields to make sure
    they contain the correct value type (String, Integer, Double). After we are parsing the different fields of the
    AddPart form and then using those values to create a new Part object. If the In-House Radio button is selected the
    user will need to provide a Machine ID. If the Outsourced radio button is selected then the user will need to
    provide a Company Name. The Part is then added to the Inventory.*/
    public void onSaveAction(ActionEvent actionEvent) throws IOException {

        if (NameField.getText().trim().isEmpty() || PriceCostField.getText().trim().isEmpty() ||
                InvField.getText().trim().isEmpty() || MaxField.getText().trim().isEmpty() ||
                MinField.getText().trim().isEmpty() ||
                (OutsourcedRadioButton.isSelected() && MachineID.getText().trim().isEmpty()) ||
                (InHouseRadioButton.isSelected() && MachineID.getText().trim().isEmpty())) {
            showError(1);
            return;

        }
        if (InHouseRadioButton.isSelected() && !isInteger(MachineID.getText().trim())){
            //Invalid Machine ID
            showError(2);
            return;
        }

        if ((!isDouble(PriceCostField.getText().trim()) || (Double.parseDouble(PriceCostField.getText().trim())) <= 0)){
            //Invalid Price/Cost
            showError(3);
            return;
        }

        if ((!isInteger(MinField.getText().trim()) || (Integer.parseInt(MinField.getText().trim())) <= 0)){
            //Invalid Min
            showError(4);
            return;
        }
        if ((!isInteger(MaxField.getText().trim()) || (Integer.parseInt(MaxField.getText().trim())) < Integer.parseInt(MinField.getText().trim()))){
            //Invalid Max
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
            int index = Inventory.getAllParts().indexOf(partModify);
            int max = Integer.parseInt(MaxField.getText());
            int min = Integer.parseInt(MinField.getText());
            int inventory = Integer.parseInt(InvField.getText());
            boolean addPart = false;
            if (Errors.checkMinValue(min, max) && Errors.checkInventory(min, max, inventory)) {
                int id = Integer.parseInt(IdField.getText());
                String name = NameField.getText();
                Double price = Double.parseDouble(PriceCostField.getText());
                if (InHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(MachineID.getText());
                    InHousePart modifyPart = new InHousePart(id, name, price, inventory, min, max, machineID);
                    Inventory.updatePart(index, modifyPart);
                    addPart = true;
                } else {
                    String companyName = MachineID.getText();
                    OutsourcedPart outsourceModifyPart = new OutsourcedPart(id, name, price, inventory, min, max, companyName);
                    Inventory.updatePart(index, outsourceModifyPart);
                    addPart = true;
                }
            }
            if (addPart) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 500);
                stage.setTitle("Modify Product");
                stage.setScene(scene);
            }
        }
        catch (Exception e) {
            Errors.alertError("Error", "Error Modifying Part", "Check that all fields are filled and try again.");

        }

        if (!InHouseRadioButton.isSelected() && !OutsourcedRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Error ");
            alert.setHeaderText("Part Type Not Selected!");
            alert.setContentText("Select part type In-House / Outsourced");
            Optional<ButtonType> result = alert.showAndWait();
        }


    }
    /*This is our list of errors we are using in the save function. Each error code corresponds to a different error for
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



}

