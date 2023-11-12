import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    private String storeName;
    private String seller;
    private String storeFile;
    private ArrayList<Product> products;
    private ArrayList<Product> soldProducts; //keeping track of sold products


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

    public void setSeller(Seller seller) {
        this.seller = String.valueOf(seller);
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
        File f = new File(storeFile);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
        BufferedReader bfr = new BufferedReader(new FileReader(f));

        String line = bfr.readLine(); // skipping the first line because it is the store name
        while (true) {
            line = bfr.readLine();
            String[] theProduct = line.split(";");
            if (line == null || line.isEmpty() || line.isBlank()) {
                pw.println(name + ";" + quantity + ";" + price + ";" + description);
                break;
            }

            if (theProduct[0].equals(name)) {
                System.out.println("This product already exists");
                break;
            }

        }
    }

    // Method to edit a product's information
    public void editProduct(String oldProductName, String newProductName, String description, double price, int quantity) {
        try {
            File f = new File(storeFile);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            int counter = 0; // this is for seeing if the store hold the product it gets incremented when product is found
            String line = bfr.readLine(); // skipping the first line because it is the store name
            while (line != null) {
                line = bfr.readLine();
                String[] theProduct = line.split(";");

                if (theProduct[0].equals(oldProductName)) {
                    counter++;
                    pw.write(newProductName + ";" + quantity + ";" + price + ";" + description);
                } else {
                    pw.write(line);
                }
            }

            if (counter > 0) {
                System.out.println("Product changed!");
            } else {
                System.out.println("There is no product with that name");
            }
        } catch(IOException e){
            e.printStackTrace();
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
