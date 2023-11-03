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
`               product.setName(updatedName);
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
                return
            }
        }
        System.out.println("Product not found. Deletion failed.");
    }

    // Need a Method to view sales history???

}
