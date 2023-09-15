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
import kbur.c482.model.InHousePart;
import kbur.c482.model.Inventory;
import kbur.c482.model.OutsourcedPart;
import kbur.c482.model.Part;

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

    @FXML private Button SaveButton;
    @FXML private Button CancelButton;

    @FXML private TableView<Part> PartsTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partsInvLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    @FXML private RadioButton OutsourcedRadioButton;
    @FXML private RadioButton InHouseRadioButton;
    @FXML private Text inHouseOrCompany;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    Inventory inv = new Inventory();
    Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdField.setDisable(true);
        IdField.setText(getNewPartID());

    }

    public String getNewPartID() {
        int partID = random.nextInt();
        boolean partFound = false;
        // the new ID will be 4 digits or fewer
        while (partID < 1000 || partID > 9999) {
            partID = random.nextInt();
        }
        return Integer.toString(partID);
    }

    public void radioFunction(ActionEvent actionEvent) {
        if (OutsourcedRadioButton.isSelected())
            inHouseOrCompany.setText("Company Name");
        else {
            inHouseOrCompany.setText("Machine ID");
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
                    Integer.parseInt(MachineID.getText()));
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
                    MachineID.getText());
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



    public void OnCancelAction (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
        }
    }

