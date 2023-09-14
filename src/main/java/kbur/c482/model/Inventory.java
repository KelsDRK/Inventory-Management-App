package kbur.c482.model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Inventory {

    // creates list with type of object that list is going to contain <Part>.
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();


    // adds parts/products to observable list
    public  void addInHousePart (InHousePart newPart) {
        allParts.add(newPart);

    }

    public void addOutsourcedPart (OutsourcedPart newPart) {

        allParts.add(newPart);
    }

    public  void addProduct (Product newProduct) {
        allProducts.add(newProduct);
    }

    //returns all parts/products from observable lists
    public void setAllParts (ObservableList<Part> allParts) {
        this.allParts = allParts;
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    public Part lookUpPartById (int partId) {
        ObservableList<Part> allParts = Part.getAllParts();

        Part refpart = null;

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            if(part.getId() == partId) {
                refpart = part;
                break;
            }

        }

        return refpart;

    }

    public Product lookUpProductById (int productId) {
        ObservableList<Product> allProducts = Product.getAllProducts();

        Product refproduct = null;

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            if(product.getId() == productId) {
                refproduct = product;
                break;
            }

        }

        return refproduct;

    }

    public ObservableList<Part> lookUpByPartName(String partialName) {
        ObservableList<Part> partName = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Part.getAllParts();

        for (Part part : allParts) {
            if(part.getName().contains(partialName)) {
                partName.add(part);
            }
        }

        return partName;

    }

    public ObservableList<Product> lookUpByProductName(String partialName) {
        ObservableList<Product> productName = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Product.getAllProducts();

        for (Product product : allProducts) {
            if(product.getName().contains(partialName)) {
                productName.add(product);
            }
        }

        return productName;

    }

    public static void updatePart (int index, Part selectedPart) {}
    public static void updateProduct (int index, Product selectedProduct) {}
    public static boolean deletePart (Part selectedPart) {
        return true;
    }
    public static boolean deleteProduct (Product selectedProduct) {
        return true;
    }







}
