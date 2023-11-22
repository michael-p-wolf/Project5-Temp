import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Boilermaker Bazaar Bonanza
 * <p>
 * The store class is a crucial part of the program that manages and stores
 * its list of products and allows customers to buy them. It has a list of
 * products and sold products to help keep track of everything going on in the marketplace.
 *
 * @author Michael Wolf, Lab Sec 36
 * @author Pranay Nandkeolyar, Lab Sec 36
 * @author Jacob Stamper, Lab Sec 36
 * @author Benjamin Emini, Lab Sec 36
 * @author Simrat Thind, Lab Sec 36
 * @version November 13th, 2023
 **/
public class Store implements Comparable<Store> {
    private String storeName;
    private String seller;
    private String storeFile;
    private ArrayList<Product> products;
    private ArrayList<Product> soldProducts; //keeping track of sold products
    private String sellerEmail;


    public int compareTo(Store store) {
        return Integer.compare(this.soldProducts.size(), store.getSoldProducts().size());
    }


    public Store(String name) {
        this.storeName = name;
        this.products = new ArrayList<>();
        this.soldProducts = new ArrayList<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSeller() {
        return seller;
    }

    public void setSellerEmail(String email) {
        this.seller = email;
    }

    public void setSeller(Seller seller) {
        this.seller = String.valueOf(seller);
        this.sellerEmail = String.valueOf(seller).substring(0, String.valueOf(seller).indexOf(";"));
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getFilename() {
        return storeFile;
    }

    public void setFilename(String filename) {
        this.storeFile = filename;
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ArrayList<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    // Method to add a product to the store
    public void createProduct(String name, String description, double price, int quantity) throws IOException {
        Product product = new Product(name, this.storeName, description, quantity, price);
        products.add(product);
    }

    // Method to edit a product's information
    public void editProduct(String oldProductName, String newProductName, String description, double price, int quantity) {
        for (Product product : products) {
            if (product.getName().equals(oldProductName)) {
                product.setName(newProductName);
                product.setDescription(description);
                product.setPrice(price);
                product.setQuantity(quantity);
            }
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addSoldProduct(Product product) {
        soldProducts.add(product);
    }

    public void removeProduct(Scanner scan, Product product) {
        do {
            System.out.println("Are you sure you want to remove " + product.getName() + "?\n[1]confirm\n[2]cancel");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        products.remove(product);
                        return;
                    case 2:
                        return;
                    case 3:
                        System.out.println("Invalid input!");
                }

            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }

        } while (true);

    }

    public void updateStore(Store update) {
        this.storeName = update.getStoreName();
        this.seller = update.getSeller();

        for (int i = 0; i < this.products.size(); i++) {
            this.products.get(i).updateProduct(update.getProducts().get(i));
        }
        for (int i = 0; i < this.soldProducts.size(); i++) {
            this.soldProducts.get(i).updateProduct(update.getSoldProducts().get(i));

        }

    }


}
