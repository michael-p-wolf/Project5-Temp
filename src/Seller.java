import java.util.List;

public class Seller {
    private int sellerId;
    private String username;
    private String password;
    private String storeName;
    private List<Product> products; // A list to manage the seller's products

    // Constructor and getter methods for attributes

    // Method to create, edit, and delete products
    public void createProduct(String name, String description, int quantity, double price, String filename) {
        // Create a new Product object and add it to the products list
        Product newProduct = new Product(name, description, quantity, price, filename);
        products.add(newProduct);
    }

    public void editProduct(int productId, String name, String description, int quantity, double price, String filename) {
        // Find and update the product with the given productId
    }

    public void deleteProduct(String name) {
        // Find and remove the product with the given productId
    }

    // Need a Method to view sales history???

}
