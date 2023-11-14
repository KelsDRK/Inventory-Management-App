package kbur.c482.model;

import javafx.scene.control.Alert;

public class Errors {

    /** Checks that min value is greater than0 and less than max. */
    public static boolean checkMinValue (int min, int max) {
        boolean checkMin = true;
        if (min <= 0  || min >= max) {
            checkMin = false;
            alertError("Invalid Min Amount", "Minimum Value is Invalid", "Minimum value must be greater than 0 and less than the Maximum Amount");
        }
        return checkMin;
    }

    /** An alert error that we can use to write custom alerts. */
    public static void alertError(String Title, String Header, String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }

    /** Checks that inventory is less than max and greater than min. */
    public static boolean checkInventory (int min, int max, int inv) {
        boolean checkInventory = true;
        if (inv > max || inv < min) {
            checkInventory = false;
            alertError("Inventory Invalid", "Value for Inventory is Invalid", "Enter a Inventory amount greater than min and less than max");
        }
        return checkInventory;
    }


}

