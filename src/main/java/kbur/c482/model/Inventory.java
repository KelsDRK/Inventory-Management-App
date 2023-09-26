package kbur.c482.model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.crypto.spec.IvParameterSpec;
import java.nio.MappedByteBuffer;


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }

    public static void updatePart(int id, Part updatedPart) {
        allParts.set(id, updatedPart);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public void setAllProducts(ObservableList<Product> products) {
        this.allProducts = products;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void updateProduct(int index, Product updateProduct) {
        allProducts.set(index, updateProduct);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }



}
