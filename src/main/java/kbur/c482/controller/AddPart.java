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
import javafx.stage.Stage;
import kbur.c482.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    public ObservableList<Part> allParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartsTable.setItems(allParts);

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /* radioFunction will check to see if the In-House or Outsourced radio button is selected and then set the
       corresponding text.
     */
    public void radioFunction () {
        if (OutsourcedRadioButton.isSelected())
            inHouseOrCompany.setText("Company Name");
        else {
            inHouseOrCompany.setText("Machine ID");
        }


    }

    public void onSaveAction(ActionEvent actionEvent) throws IOException {
    }


    public void OnCancelAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
    }


}
