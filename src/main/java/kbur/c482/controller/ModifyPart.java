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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

    private int partId;

    @FXML public TableView<Part> PartsTable;

    @FXML private TextField IdField;
    @FXML private TextField NameField;
    @FXML private TextField InvField;
    @FXML private TextField PriceCostField;
    @FXML private TextField MaxField;
    @FXML private TextField MinField;
    @FXML private Text MachIneOrCompLabel;
    @FXML private TextField setTextForMachine;

    @FXML public RadioButton InHouseRadioButton;
    @FXML public RadioButton OutsourcedRadioButton;
    @FXML private Text inHouseOrCompany;

    private Part partModify;
    Inventory inv = new Inventory();



    @FXML void inHouseRadioSelected(ActionEvent actionEvent) {
        inHouseOrCompany.setText("Machine ID");
    }

    @FXML void outsourcedRadioSelected(ActionEvent actionEvent) {
        inHouseOrCompany.setText("Company Name");
    }



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


    public void onSaveAction(ActionEvent actionEvent) throws IOException {

        if (InHouseRadioButton.isSelected()) {
            Part part = new InHousePart(Integer.parseInt(IdField.getText()),
                    NameField.getText(),
                    Double.parseDouble(PriceCostField.getText()),
                    Integer.parseInt(InvField.getText()),
                    Integer.parseInt(MaxField.getText()),
                    Integer.parseInt(MinField.getText()),
                    Integer.parseInt(setTextForMachine.getText()));
            // Add to the inventory
            inv.addPart(part);

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
        }
        // if (outsourced radio button is selected) {add an outsourced part to the inventory}
        if (OutsourcedRadioButton.isSelected()) {
            Part outsourced = new OutsourcedPart(Integer.parseInt(IdField.getText()),
                    NameField.getText(),
                    Double.parseDouble(PriceCostField.getText()),
                    Integer.parseInt(InvField.getText()),
                    Integer.parseInt(MaxField.getText()),
                    Integer.parseInt(MinField.getText()),
                    setTextForMachine.getText());
            // Add this new part to the inventory
            inv.addPart(outsourced);

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);

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
