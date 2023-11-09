import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String storeName;
    private String seller;
    private String filename;
    private List<Product> products;
    private List<Product> soldProducts; //keeping track of sold products


    public Store(String name, String seller) {
        this.storeName = name;
        this.seller = seller;
        this.products = new ArrayList<>();
        this.soldProducts = new ArrayList<>();
    }
    public void sell(Product product, Customer customer, int sellQuantity) throws IOException {
        try {


            File f = new File(filename);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            int counter = 0; // this is for seeing if the store hold the product it gets incremented when product is found
            if (product.getQuantity() > 0 && (product.getQuantity() - sellQuantity) > -1) {
                for (int i = 0; i < products.size(); i++) {
                    //iterating through the list to find matching product
                    if (products.get(i).equals(product)) {
                        counter++;
                        soldProducts.add(products.get(i)); // adding all sold products to an array list
                        printWriter.write(customer.getEmail() + "," + product.getName() + "," + product.getPrice()*sellQuantity + "," + sellQuantity); //writing Customer email, product name, product price, and product amount bought
                        product.setQuantity(product.getQuantity() - 1);
                        break;
                    }
                }

                if (counter == 0) {
                    System.out.println("This store does not sell this product");
                }
            } else {
                System.out.println("This item is out of stock");
                return;
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
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
