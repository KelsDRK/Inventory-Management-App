package kbur.c482.model;

import javafx.fxml.FXML;

public class InHousePart extends Part{

    private int machineId;

    /** Constructor for in house part. */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /** Getter for Machine ID. */
    public int getMachineId() {
        return machineId;
    }

    /** Setter fpr Machine ID in house. */
    public void setMachineId() {this.machineId = machineId;}

}
