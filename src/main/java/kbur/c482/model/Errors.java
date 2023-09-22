package kbur.c482.model;

import javafx.scene.control.Alert;

public class Errors {

    public static boolean checkMinValue (int min, int max) {
        boolean checkMin = true;
        if (min <= 0  || min >= max) {
            checkMin = false;
            alertError("Invalid Min Amount", "Minimum Value is Invalid", "Minimum value must be greater than 0 and less than the Maximum Amount");
        }
        return checkMin;
    }

    public static void alertError(String alertTitle, String alertHeader, String alertContent){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    public static boolean checkInventory (int min, int max, int inv) {
        boolean checkInventory = true;
        if (inv > max || inv < min) {
            checkInventory = false;
            alertError("Inventory Invalid", "Value for Inventory is Invalid", "Enter a Inventory amount greater than min and less than max");
        }
        return checkInventory;
    }


}

