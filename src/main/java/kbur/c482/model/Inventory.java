package kbur.c482.model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.crypto.spec.IvParameterSpec;
import java.nio.MappedByteBuffer;


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart (Part part) {
        allParts.add(part);
    }

    public static ObservableList<Part> getAllParts () {
        return allParts;
    }

    public static void addProduct (Product product) {
        allProducts.add(product);
    }

    public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }


}
