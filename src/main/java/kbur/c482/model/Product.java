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

    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public void addAssociatedPart (Part part) {
        this.associatedParts.add(part);
    }

    /*Sets associated parts list when modifying / adding products to the List that is passed in.*/
    public void setAssociatedParts (ObservableList<Part> parts) {
        this.associatedParts = parts;
    }

    //will delete any parts associated with the product
    public boolean deleteAssociatedPart(Part partToDelete) {
        if (associatedParts.contains(partToDelete)) {
            associatedParts.remove(partToDelete);
            return true;
        }
        else
            return false;
    }



}
