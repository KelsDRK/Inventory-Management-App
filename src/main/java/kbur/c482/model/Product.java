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

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    static {
        init();
    }

    private static void init() {
        if (allProducts.size() == 0) {

            allProducts.add(new Product(35, "Ketchup", 3.00, 3, 1, 5));
            allProducts.add(new Product(3, "Mustard", 6.00, 66, 1, 5));
            allProducts.add(new Product(66, "BBq", 7.00, 76, 1, 5));
            allProducts.add(new Product(10, "Ranch", 8.00, 4, 1, 5));

        }
    }



    //Constructor
    public Product(int id, String name, double price, int stock, int max, int min) {
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

    /*public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }
     */
}
