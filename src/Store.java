import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String storeName;
    private String seller;
    private String storeFile;
    private List<Product> products;
    private List<Product> soldProducts; //keeping track of sold products


    public Store(String name, String seller) throws IOException {
        this.storeName = name;
        this.seller = seller;
        this.products = new ArrayList<>();
        this.soldProducts = new ArrayList<>();
        this.storeFile = storeName + ".txt";

        File file = new File(storeFile);

        PrintWriter pw = new PrintWriter(new FileOutputStream(storeFile,true));
        BufferedReader bfr = new BufferedReader(new FileReader(storeFile));

        pw.println(storeName);
        pw.flush();

        for (int i = 0; i < products.size(); i++) {
            Product temp = products.get(i);
            pw.println(temp.getName() + ";" + temp.getQuantity() + ";" + temp.getPrice() + ";" + temp.getDescription());
            pw.flush();
        }

        pw.close();
    }
    public void sell(Product product, int sellQuantity) throws IOException {
        try {
            File f = new File(storeFile);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            int counter = 0; // this is for seeing if the store hold the product it gets incremented when product is found
            String line = bfr.readLine(); // skipping the first line because it is the store name
            while (line != null) {
                line = bfr.readLine();
                if (line == null) {
                    System.out.println("This store does not have this product");
                    break;
                }

                String[] theProduct = line.split(";");

                if (theProduct[0].equals(product.getName())) {
                    counter++;
                    if(Integer.parseInt(theProduct[1]) >= sellQuantity) {
                        int quantity = Integer.parseInt(theProduct[1]) - sellQuantity;
                        pw.write(theProduct[0] + ";" + quantity + ";" + theProduct[2] + ";" + theProduct[3]);
                    } else {
                        System.out.println("There is not enough stock of this product");
                        pw.write(line);
                    }
                } else {
                    pw.write(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }
    // Method to add a product to the store
    public void createProduct(String name, String description, double price, int quantity) {
        Product product = new Product(name, this.storeName, description, quantity, price);
        products.add(product);
    }

    // Method to edit a product's information
    public void editProduct(String productName, String description, double price, int quantity) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setDescription(description);
                product.setPrice(price);
                product.setQuantity(quantity);
            }
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

}
