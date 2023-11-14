package kbur.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product extends Inventory {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private  ObservableList<Part> bottomList = FXCollections.observableArrayList();

    /** Constructor for Product. */
    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getter for Product ID. */
    public int getId() {
        return id;
    }

    /** Setter for Product ID. */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for Product Name. */
    public String getName() {
        return name;
    }

    /** Setter for Product Name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for Product Price. */
    public double getPrice() {
        return price;
    }

    /** Setter for Product Price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter for Product Inventory. */
    public int getStock() {
        return stock;
    }

    /** Setter for Product Inventory. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter for Product Min. */
    public int getMin() {
        return min;
    }

    /** Setter for Product Min. */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter for Product Max. */
    public int getMax() {
        return max;
    }

    /** Setter for Product Max. */
    public void setMax(int max) {
        this.max = max;
    }

    /** Creates a list of observable parts. */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /** Method adds Associated Parts. */
    public void addAssociatedPart (Part part) {
        this.associatedParts.add(part);
    }

    /** Method sets associated parts. */
    public void setAssociatedParts (ObservableList<Part> parts) {
        this.associatedParts = parts;
    }

    /** Method deletes associated parts. */
    public boolean deleteAssociatedPart(Part partToDelete) {
        if (associatedParts.contains(partToDelete)) {
            associatedParts.remove(partToDelete);
            return true;
        }
        else
            return false;
    }

}
