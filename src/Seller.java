import java.util.ArrayList;
import java.util.List;

public class Seller extends Person{
    private int sellerId;
    private String email;
    private String password;
    private ArrayList<Store> stores;
    private List<Product> products; // A list to manage the seller's products
    private List<Product> soldProducts; //keeping track of sold products



    // Constructor and getter methods for attributes
    public Seller(int sellerId, String email, String password, ArrayList<Store> stores, List<Product> products) {
        super(email, password, "Seller");
        this.sellerId = sellerId;
        this.stores = stores;
        this.products = products;
        this.soldProducts = new ArrayList<>();
    }


    // Method to create a new product
    public void createProduct(String name, String storeSelling, String description, int quantity, double price) {
        // Create a new Product object and add it to the products list
        boolean foundProduct = false;
        for(Product product : products) {
            if(product.getName().equals(name)) {
                foundProduct = true;
                System.out.println("A product with the same name already exists. Please rename your Product!");
                return;
            }
        }
        if(!foundProduct) {
            Product newProduct = new Product(name, storeSelling, description, quantity, price);
            products.add(newProduct);
        } else  {
            System.out.println("A product with the same name already exists. Please rename your Product!");
        }
    }
    // method to edit a new product
    public void editProduct(String updatedName, String updatedStoreSelling, String updatedDescription, int updatedQuantity, double updatedPrice) {
        // Find and update the product with the given productId
        for(Product product: products) {
            if(product.getName().equals(updatedName)) {
               product.setName(updatedName);
               product.setStoreSelling(updatedStoreSelling);
               product.setDescription(updatedDescription);
               product.setQuantity(updatedQuantity);
               product.setPrice(updatedPrice);
               return; // exit the loop once the product is found and fixed
            }
        }
        System.out.println("Product not found. Editing failed.");
    }
    // method to delete a product
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

    // selling a product
    public void sell(Product product) {
        if (product.getQuantity() > 0) {
            for (int i = 0; i < products.size(); i++) {
                //iterating through the list to find matching product
                if (products.get(i).equals(product)) {
                    soldProducts.add(products.get(i));
                    products.remove(i);
                    break;
                }
            }
        } else {
            System.out.println("This item is out of stock");
            return;
        }

    }
    public void salesHistory() {

    }


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
