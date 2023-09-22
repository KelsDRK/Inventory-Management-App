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
    @FXML private TextField setTextForMachine;

    @FXML private Text MachIneOrCompLabel;
    @FXML private Text inHouseOrCompany;

    @FXML public RadioButton InHouseRadioButton;
    @FXML public RadioButton OutsourcedRadioButton;

    @FXML void inHouseRadioSelected(ActionEvent actionEvent) {
        inHouseOrCompany.setText("Machine ID");
    }
    @FXML void outsourcedRadioSelected(ActionEvent actionEvent) {
        inHouseOrCompany.setText("Company Name");
    }


    private Part partModify;
    Inventory inventory = new Inventory();





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
            OutsourcedRadioButton.setDisable(true);
            MachIneOrCompLabel.setText("Machine ID");
            setTextForMachine.setText(Integer.toString(modifyInHousePart.getMachineId()));
        }
        if (partModify instanceof OutsourcedPart) {
            OutsourcedPart modifyOutsourcedPart = (OutsourcedPart) partModify;
            OutsourcedRadioButton.setSelected(true);
            InHouseRadioButton.setDisable(true);
            MachIneOrCompLabel.setText("Company ID");
            setTextForMachine.setText((modifyOutsourcedPart.getCompanyName()));
        }

        IdField.setDisable(true);


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


    /*Saving will parse the values from the Form and those values will be used to create a Part object. The inventory
    * will be updated with the Modified part.*/
    public void onSaveAction(ActionEvent actionEvent) throws IOException {

        try {
            int index = Inventory.getAllParts().indexOf(partModify);
            int max = Integer.parseInt(MaxField.getText());
            int min = Integer.parseInt(MinField.getText());
            int inventory = Integer.parseInt(InvField.getText());
            boolean addPart = false;
            if (Errors.checkInventory(min, max, inventory) && Errors.checkMinValue(min, max)) {
                int id = Integer.parseInt(IdField.getText());
                String name = NameField.getText();
                Double price = Double.parseDouble(PriceCostField.getText());
                if (InHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(setTextForMachine.getText());
                    InHousePart modifyPart = new InHousePart(id, name, price, inventory, min, max, machineID);
                    Inventory.updatePart(index, modifyPart);
                    addPart = true;
                } else {
                    String companyName = setTextForMachine.getText();
                    OutsourcedPart outsourceModifyPart = new OutsourcedPart(id, name, price, inventory, min, max, companyName);
                    Inventory.updatePart(id, outsourceModifyPart);
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
            Errors.alertError("Error", "Error Modifying Part", "Check that all fields are correctly filled and try again.");

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
}

