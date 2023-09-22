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
import javafx.scene.text.Text;
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

    /*Here we are parsing the different fields of the AddPart form and then using those values to create a new Part object.
      If the In-House Radio button is selected the user will need to provide a Machine ID. If the Outsourced radio
      button is selected then the user will need to provide a Company Name. The Part is then added to the Inventory.
    */
    public void onSaveAction(ActionEvent actionEvent) throws IOException {

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
            inv.addPart(part);

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);

        }

        if (OutsourcedRadioButton.isSelected()) {
            Part outsourced = new OutsourcedPart(Integer.parseInt(IdField.getText()),
                    NameField.getText(),
                    Double.parseDouble(PriceCostField.getText()),
                    Integer.parseInt(InvField.getText()),
                    Integer.parseInt(MinField.getText()),
                    Integer.parseInt(MaxField.getText()),
                    MachineID.getText());
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


    /*If the user selects the "Cancel" option then they will be returned to the main menu*/
    public void OnCancelAction (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
        }
    }

