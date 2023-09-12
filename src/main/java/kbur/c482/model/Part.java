package kbur.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Part extends Inventory{

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    static {
        init();
    }

   private static void init() {
        if (allParts.size() == 0) {

            allParts.add(new Part(11, "Tool", 25.6, 5, 1, 4));
            allParts.add(new Part(12, "Hammer", 24.0, 44, 4, 7));
            allParts.add(new Part(13, "Wrench", 54.3, 3, 3, 8));
            allParts.add(new Part(14, "Claw", 64.4, 6, 8, 23));

        }
    }

    //Constructor
    public Part(int id, String name, double price, int stock, int min, int max) {

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

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }





}
