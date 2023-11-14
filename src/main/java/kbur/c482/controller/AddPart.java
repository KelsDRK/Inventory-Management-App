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
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kbur.c482.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Random;


public class AddPart implements Initializable {
    @FXML private TextField IdField;
    @FXML private TextField NameField;
    @FXML private TextField InvField;
    @FXML private TextField PriceCostField;
    @FXML private TextField MaxField;
    @FXML private TextField MinField;
    @FXML private TextField MachineID;

    @FXML private RadioButton OutsourcedRadioButton;
    @FXML private RadioButton InHouseRadioButton;
    @FXML private Text inHouseOrCompany;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    Inventory inv = new Inventory();
    Random random = new Random();




    //setting an empty initializer where the ID field will be disabled and a random ID will be generated.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdField.setDisable(true);
        IdField.setText(getNewPartID());

    }

    //this function is the logic behind getting the random ID used in the initializer.
    public String getNewPartID() {
        int partID = random.nextInt();
        boolean partFound = false;
        // the new ID will be 4 digits or fewer
        while (partID < 1000 || partID > 9999) {
            partID = random.nextInt();
        }
        return Integer.toString(partID);
    }

    //This function will change the Text of the Machine ID/ Company name field when the related Radio button is selected.
    public void radioFunction(ActionEvent actionEvent) {
        if (OutsourcedRadioButton.isSelected())
            inHouseOrCompany.setText("Company Name");
        else {
            inHouseOrCompany.setText("Machine ID");
        }
    }

    //A function to determine if a String value is a double.
    private static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    //A function to determine if a string value is an Integer.
    private static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    /*In the beginning of this function the errors are being checked first. We start by checking if there are any empty
    fields and returning the corresponding error message if so. Then we step through the individual fields to make sure
    they contain the correct value type (String, Integer, Double). After we are parsing the different fields of the
    AddPart form and then using those values to create a new Part object. If the In-House Radio button is selected the
    user will need to provide a Machine ID. If the Outsourced radio button is selected then the user will need to
    provide a Company Name. The Part is then added to the Inventory.*/
    public void onSaveAction(ActionEvent actionEvent) throws IOException {

        boolean addPart = false;

        if (NameField.getText().trim().isEmpty() || PriceCostField.getText().trim().isEmpty() ||
                InvField.getText().trim().isEmpty() || MaxField.getText().trim().isEmpty() ||
                MinField.getText().trim().isEmpty() ||
                (OutsourcedRadioButton.isSelected() && MachineID.getText().trim().isEmpty()) ||
                (InHouseRadioButton.isSelected() && MachineID.getText().trim().isEmpty())) {
            //Fields Required Error Here
            showError(1);
            return;

        }
        if (InHouseRadioButton.isSelected() && !isInteger(MachineID.getText().trim())){
            //Machine ID error
            showError(2);
            return;
        }

        if ((!isDouble(PriceCostField.getText().trim()) || (Double.parseDouble(PriceCostField.getText().trim())) <= 0)){
            //Price error
            showError(3);
            return;
        }

        if ((!isInteger(MinField.getText().trim()) || (Integer.parseInt(MinField.getText().trim())) <= 0)){
            //Min error
            showError(4);
            return;
        }
        if ((!isInteger(MaxField.getText().trim()) || (Integer.parseInt(MaxField.getText().trim())) < Integer.parseInt(MinField.getText().trim()))){
            //Max error
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
            if (InHouseRadioButton.isSelected()) {
                Part part = new InHousePart(Integer.parseInt(IdField.getText()),
                        NameField.getText(),
                        Double.parseDouble(PriceCostField.getText()),
                        Integer.parseInt(InvField.getText()),
                        Integer.parseInt(MinField.getText()),
                        Integer.parseInt(MaxField.getText()),
                        Integer.parseInt(MachineID.getText()));
                int min = Integer.parseInt(MinField.getText());
                int max = Integer.parseInt(MaxField.getText());
                int invError = Integer.parseInt(InvField.getText());

                if (Errors.checkMinValue(min, max) && Errors.checkInventory(min, max, invError)) {
                    addPart = true;
                    inv.addPart(part);
                }
            }
            if (OutsourcedRadioButton.isSelected()) {
                Part outsourced = new OutsourcedPart(Integer.parseInt(IdField.getText()),
                        NameField.getText(),
                        Double.parseDouble(PriceCostField.getText()),
                        Integer.parseInt(InvField.getText()),
                        Integer.parseInt(MinField.getText()),
                        Integer.parseInt(MaxField.getText()),
                        MachineID.getText());

                int min = Integer.parseInt(MinField.getText());
                int max = Integer.parseInt(MaxField.getText());
                int invError = Integer.parseInt(InvField.getText());

                if (Errors.checkMinValue(min, max) && Errors.checkInventory(min, max, invError)) {
                    addPart = true;
                    inv.addPart(outsourced);
                }
            }

            if (!InHouseRadioButton.isSelected() && !OutsourcedRadioButton.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Error ");
                alert.setHeaderText("Part Type Not Selected!");
                alert.setContentText("Select part type In-House / Outsourced");
                Optional<ButtonType> result = alert.showAndWait();
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


    /*If the user selects the "Cancel" option then they will be returned to the main menu*/
    public void OnCancelAction (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
        }
    }

