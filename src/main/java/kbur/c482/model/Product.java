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

    private static ObservableList<Part> linkedParts = FXCollections.observableArrayList();

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

    public static ObservableList<Part> getLinkedParts() {
        return linkedParts;
    }

    public void addLinkedParts(ObservableList<Part> part) {
        this.linkedParts.addAll(part);
    }

    public static boolean deleteLinkedPart(Part partToDelete) {
        if (linkedParts.contains(partToDelete)) {
            linkedParts.remove(partToDelete);
            return true;
        }
        else
            return false;
    }

}
