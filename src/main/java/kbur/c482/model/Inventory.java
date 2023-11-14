package kbur.c482.model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.crypto.spec.IvParameterSpec;
import java.nio.MappedByteBuffer;


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Method adds part to Inventory. */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** Method gets an Observable list of Parts from Inventory. */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Setter sets parts to Inventory. */
    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }

    /** Method updates parts in Inventory. */
    public static void updatePart(int id, Part updatedPart) {
        allParts.set(id, updatedPart);
    }

    /** Method adds Product to Inventory. */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** Method sets Products in Inventory. */
    public void setAllProducts(ObservableList<Product> products) {
        this.allProducts = products;
    }

    /** Method gets an Observable list of Products from Inventory. */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Method updates Products in Inventory. */
    public static void updateProduct(int index, Product updateProduct) {
        allProducts.set(index, updateProduct);
    }

    /** Method deletes a Product in Inventory. */
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
