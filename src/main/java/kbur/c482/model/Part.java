package kbur.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public abstract class Part extends Inventory{

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor for Part */
    public Part(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }


    /** Getter for Part ID. */
    public int getId() {
        return id;
    }

    /** Getter for Part ID. */
    public void setId(int id) {this.id = id;}

    /** Getter for Part Name. */
    public String getName() {
        return name;
    }

    /** Setter for Part Name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for Part Price. */
    public double getPrice() {
        return price;
    }

    /** Setter for Part Price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter for Part Inventory. */
    public int getStock() {
        return stock;
    }

    /** Setter for Part Inventory. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter for Part Min. */
    public int getMin() {
        return min;
    }

    /** Setter for Part Min. */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter for Part Max. */
    public int getMax() {
        return max;
    }

    /** Setter for Part Max. */
    public void setMax(int max) {
        this.max = max;
    }





}
