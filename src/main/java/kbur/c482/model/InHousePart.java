package kbur.c482.model;

import javafx.fxml.FXML;

public class InHousePart extends Part{

    private int machineId;

    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }



    public int getMachineId() {
        return machineId;
    }

    public void setMachineId() {
        this.machineId = machineId;
    }



}
