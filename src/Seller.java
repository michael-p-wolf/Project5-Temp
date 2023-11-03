import java.util.ArrayList;
import java.util.List;

public class Seller extends Person{
    private int sellerId;
    private String email;
    private String password;
    private ArrayList<Store> stores;
    private List<Product> products; // A list to manage the seller's products



    // Constructor and getter methods for attributes
    public Seller(int sellerId, String email, String password, ArrayList<Store> stores, List<Product> products) {
        super(email, password, "Seller");
        this.sellerId = sellerId;
        this.stores = stores;
        this.products = products;
    }


    // Method to create, edit, and delete products
    public void createProduct(String name, String description, int quantity, double price, String filename) {
        // Create a new Product object and add it to the products list
        boolean foundProduct = false;
        for(Product product : products) {
            if(product.getName().equals(name)) {

            }
        }
        Product newProduct = new Product(name, description, quantity, price, filename);
        products.add(newProduct);
    }

    public void editProduct(String updatedName, String updatedDescription, int updatedQuantity, double updatedPrice, String filename) {
        // Find and update the product with the given productId
        for(Product product: products) {
            if(product.getName().equals(updatedName)) {
                product.setName(updatedName);
                product.setDescription(updatedDescription);
                product.setQuantity(updatedQuantity);
                product.setPrice(updatedPrice);
                product.setFilename(filename);
                return; // exit the loop once the product is found and fixed
            }
        }
        System.out.println("Product not found. Editing failed.");
    }

    public void deleteProduct(String name) {
        // Find and remove the product with the given productId
        for(Product product : products) {
            if(product.getName().equals(name)) {
                products.remove(product);
                return;
            }
        }
        System.out.println("Product not found. Deletion failed.");
    }

    // Need a Method to view sales history???



    // Getters and Setters
    public int getSellerId() {
        return sellerId;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
