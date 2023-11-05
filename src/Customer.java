import java.io.*;
import java.util.*;

public class Customer extends Person {
    private ArrayList<Product> cart;
    private ArrayList<Product> purchaseHistory;

    private ArrayList<String> stores;


    public Customer(String email, String password, ArrayList<Product> cart,
                    ArrayList<Product> purchaseHistory, ArrayList<String> stores) {
        super(email, password, "Customer");
        this.cart = cart;
        this.purchaseHistory = purchaseHistory;
        this.stores = stores;
    }

    public Customer(String email, String password) {
        super(email, password, "Customer");

        this.cart = new ArrayList<Product>();
        this.purchaseHistory = new ArrayList<Product>();
        this.stores = new ArrayList<String>();
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(ArrayList<Product> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public ArrayList<String> getStores() {
        return stores;
    }

    public void setStores(ArrayList<String> stores) {
        this.stores = stores;
    }

    public void addToCart(Product p) {
        this.cart.add(p);
    }

    public void removeFromCart(Product p) {
        try {
            cart.remove(p);
        } catch (Exception e) {
            System.out.println("This product is not in the cart");
        }
    }

    public void buyFromCart() {
        for (Product p : cart) {
            buy(p);
        }
    }

    public void buy(Product p) {
        if (p.getQuantity() < 1) {
            System.out.println("No quanity left!");
            return;
        }
        purchaseHistory.add(p);
        if (!inList(p.getStoreSelling())) {
            stores.add(p.getStoreSelling());
        }
    }

    private boolean inList(String val) {
        for (String name : stores) {
            if (name.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfProductsPurchased() {
        return purchaseHistory.size();
    }

    public void createPurchaseHistory() {
        String filename = super.getEmail() + "_purchase_history.txt";
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Product p : purchaseHistory) {
                pw.println("Purchased: Product: " + p.toString());
            }
        } catch (IOException e) {
            System.out.println("Write File Error");
            e.printStackTrace();
        }
    }

    public ArrayList<Product> searchName(ArrayList<Product> markeplace, String name) {
        ArrayList<Product> searchResults = new ArrayList<Product>();
        for (Product p : markeplace) {
            if (p.getName().equals(name)) {
                searchResults.add(p);
            }
        }
        if (searchResults.size() == 0) {
            System.out.println("No search results!");
        }
        return searchResults;
    }

    public ArrayList<Product> searchStore(ArrayList<Product> markeplace, String store) {
        ArrayList<Product> searchResults = new ArrayList<Product>();
        for (Product p : markeplace) {
            if (p.getStoreSelling().equals(store)) {
                searchResults.add(p);
            }
        }
        if (searchResults.size() == 0) {
            System.out.println("No search results!");
        }
        return searchResults;
    }

    public ArrayList<Product> searchDescription(ArrayList<Product> markeplace, String description) {
        ArrayList<Product> searchResults = new ArrayList<Product>();
        for (Product p : markeplace) {
            if (p.getDescription().equals(description)) {
                searchResults.add(p);
            }
        }
        if (searchResults.size() == 0) {
            System.out.println("No search results!");
        }
        return searchResults;
    }

    public ArrayList<Product> sortPrice(ArrayList<Product> marketplace) {
        // Create a custom comparator to compare products by price
        Comparator<Product> priceComparator = new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                // Compare the prices of the two products
                return Double.compare(product1.getPrice(), product2.getPrice());
            }
        };

        // Sort the marketplace ArrayList using the custom comparator
        Collections.sort(marketplace, priceComparator);

        // Return the sorted ArrayList
        return marketplace;
    }

    public ArrayList<Product> sortQuantity(ArrayList<Product> marketplace) {
        // Create a custom comparator to compare products by price
        Comparator<Product> priceComparator = new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                // Compare the prices of the two products
                return Double.compare(product1.getQuantity(), product2.getQuantity());
            }
        };

        // Sort the marketplace ArrayList using the custom comparator
        Collections.sort(marketplace, priceComparator);

        // Return the sorted ArrayList
        return marketplace;
    }

    public void logIn(String email, String password) {
        if (this.getEmail().equals(email)) {
            if (this.getPassword().equals(password)) {
                System.out.println("Success, logged in");
            } else {
                System.out.println("Incorrect password!");
            }
        }

    }

    public String toString() {
        return super.toString() + String.format(",%s,%s,%s",
                cart.toString(),purchaseHistory.toString(), stores.toString());
    }
}
