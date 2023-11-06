import java.io.*;
import java.util.*;

/**
 * Project 4 -- Customer
 *
 * This class represents a Customer
 * who can buy products from the marketplace
 *
 * @author Pranay Nandkeolyar, lab sec 36
 *
 * @version November 5, 2023
 *
 */
public class Customer extends Person {
    // this represents the current cart for the customer
    private ArrayList<Product> cart;
    // this represents the list of all products the user has bought
    private ArrayList<Product> purchaseHistory;
    // this represents a list of all the store names
    // the user has bought from
    private ArrayList<String> stores;

    /**
     * Creates a Customer Object
     * @param email - the user's email
     * @param password - the user's password
     */
    public Customer(String email, String password) {
        // we use our super constructor
        super(email, password, "Customer");
        File file = new File("Accounts.txt");

        // if the file exists
        // we get the preexisting data for this user
        if (file.exists()) {
            try (BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"))) {
                String line = bfr.readLine();
                while (line != null) {
                    String[] info = line.split(";");
                    // if this user is the user we are creating
                    if (info[2].equals("Customer") && info[0].equals(email)) {
                        // we get the cart, purchase history, and store information
                        // from the file
                        this.cart = convert(info[3]);
                        this.purchaseHistory = convert(info[4]);
                        String s = info[5].substring(1, info[5].length() - 1);
                        String[] items = s.split(", ");
                        ArrayList<String> stores = new ArrayList<>();
                        Collections.addAll(stores, items);
                        this.stores = stores;
                    }
                    line = bfr.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // no existing data so we create blank array lists
            this.cart = new ArrayList<>();
            this.purchaseHistory = new ArrayList<>();
            this.stores = new ArrayList<>();
        }
    }

    /**
     * Converts an ArrayList of Products'
     * String representation back to
     * an ArrayList of Products
     * @param line - the toString representation
     * @return products - An ArrayList of Products
     */
    private ArrayList<Product> convert(String line) {
        String l = line.substring(1, line.length() - 1);
        // we use comma space to differentiate between products, not fields
        // within products
        String[] items = l.split(", ");
        ArrayList<Product> ans = new ArrayList<>();
        for (String s : items) {
            // only a comma to differentiate fields
            String[] data = s.split(",");
            Product p3 = new Product(data[0], data[1], data[2], Integer.parseInt(data[4]), Double.parseDouble(data[3]));
            ans.add(p3);
        }
        return ans;
    }

    /**
     * Returns the user's cart
     * @return - the user's cart
     */
    public ArrayList<Product> getCart() {
        return cart;
    }

    /**
     * Resets the user's cart
     * @param cart - the user's new cart
     */
    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    /**
     * Gets the user's product purchase-history
     * @return the user's purchase history
     */
    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;
    }

    /**
     * Gets the user's store purchase-history
     * @return the user's purchase
     */
    public ArrayList<String> getStores() {
        return stores;
    }

    /**
     * Adds an item to the user's cart
     * @param p - the product to add
     */
    public void addToCart(Product p) {
        this.cart.add(p);
        try {
            // we update the database with the new information
            rewriteLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes an item from the user's cart
     * @param p - the product to remove
     */
    public void removeFromCart(Product p) {
        try {
            cart.remove(p);
            // we update the database with the new information
            rewriteLine();
        } catch (Exception e) {
            // if there is an error, we tell the user the product is not
            // in the cart
            System.out.println("This product is not in the cart");
        }
    }

    /**
     * Rewrite's the line in Accounts.txt
     * to update the cart information
     */
    private void rewriteLine() throws IOException {
        File file = new File("Accounts.txt");
        if (file.exists()) {
            BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Accounts.txt")));
            String line = bfr.readLine();
            while (line != null) {
                String[] data = line.split(";");
                // we only change the line for this user
                if (data[2].equals("Customer") && data[0].equals(this.getEmail())) {
                    // we do this by updating the value to the new cart
                    data[3] = cart.toString();
                    line = Arrays.toString(data);
                }
                // we overwrite all lines in the file
                pw.println(line);
            }
        }
    }

    /**
     * Buys the entire cart
     */
    public void buyFromCart() {
        // buys each product
        for (Product p : cart) {
            buy(p);
        }
        // resets the cart
        this.cart = new ArrayList<>();
        try {
            // rewrite the cart information
            rewriteLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Buys a product and updates
     * the database with the new information
     * @param p - the product to remove
     */
    public void buy(Product p) {
        // If the product is out of stock, we let the user know and
        // stop the method
        if (p.getQuantity() < 1) {
            System.out.println(p.getName() + " is out of stock!");
            return;
        } else {
            // we update the product and store history with the new information
            purchaseHistory.add(p);
            if (!inList(p.getStoreSelling())) {
                stores.add(p.getStoreSelling());
            }
            // we update the database if it exists
            File file = new File("Accounts.txt");
            if (file.exists()) {
                try {
                    BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"));
                    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Accounts.txt")));
                    String line = bfr.readLine();
                    while (line != null) {
                        String[] data = line.split(";");
                        // we get the correct customer
                        if (data[2].equals("Customer") && data[0].equals(this.getEmail())) {
                            data[4] = purchaseHistory.toString();
                            data[5] = stores.toString();
                            // we rewrite the history
                            line = Arrays.toString(data);
                        }
                        pw.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if a store name is in our list
     *
     * @param val - the product to remove
     * @return - a boolean representing whether the value is in the list
     */
    private boolean inList(String val) {
        for (String name : stores) {
            if (name.equals(val)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Writes the customer's purchase history to a file
     *
     */
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

    /**
     * Searches the marketplace
     * for a list of products by name
     *
     * @param marketplace - the list of products in the marketplace
     * @param name - the name of the product we are searching for
     * @return - An ArrayList of Products matching the search criteria
     */
    public ArrayList<Product> searchName(ArrayList<Product> marketplace, String name) {
        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product p : marketplace) {
            if (p.getName().contains(name)) {
                searchResults.add(p);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No search results!");
        }
        return searchResults;
    }

    /**
     * Searches the marketplace
     * for a list of products by store
     *
     * @param marketplace - the list of products in the marketplace
     * @param store - the name of the product we are searching for
     * @return - An ArrayList of Products matching the search criteria
     */
    public ArrayList<Product> searchStore(ArrayList<Product> marketplace, String store) {
        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product p : marketplace) {
            if (p.getStoreSelling().contains(store)) {
                searchResults.add(p);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No search results!");
        }
        return searchResults;
    }

    /**
     * Searches the marketplace
     * for a list of products by name
     *
     * @param marketplace - the list of products in the marketplace
     * @param description - the name of the product we are searching for
     * @return - An ArrayList of Products matching the search criteria
     */
    public ArrayList<Product> searchDescription(ArrayList<Product> marketplace, String description) {
        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product p : marketplace) {
            if (p.getDescription().contains(description)) {
                searchResults.add(p);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No search results!");
        }
        return searchResults;
    }

    /**
     * Sorts the marketplace
     * by price
     *
     * @param marketplace - the list of products in the marketplace
     * @return - A sorted ArrayList of Products based on the price
     */
    public ArrayList<Product> sortPrice(ArrayList<Product> marketplace) {
        // Create a custom comparator to compare products by price
        Comparator<Product> priceComparator = new Comparator<>() {
            public int compare(Product product1, Product product2) {
                // Compare the prices of the two products
                return Double.compare(product1.getPrice(), product2.getPrice());
            }
        };
        // Sort the marketplace ArrayList using the custom comparator
        marketplace.sort(priceComparator);
        // Return the sorted ArrayList
        return marketplace;
    }

    /**
     * Sorts the marketplace
     * by quantity left
     *
     * @param marketplace - the list of products in the marketplace
     * @return - A sorted ArrayList of Products based on the stock left
     */
    public ArrayList<Product> sortQuantity(ArrayList<Product> marketplace) {
        // Create a custom comparator to compare products by stock
        Comparator<Product> priceComparator = new Comparator<>() {
            public int compare(Product product1, Product product2) {
                // Compare the stock of the two products
                return Double.compare(product1.getQuantity(), product2.getQuantity());
            }
        };
        // Sort the marketplace ArrayList using the custom comparator
        marketplace.sort(priceComparator);
        return marketplace;
    }


    /**
     * Sorts the marketplace
     * by store based on each store's sales
     *
     * @param marketplace - the list of products in the marketplace
     * @return - A sorted ArrayList of Stores based on its sales
     */
    public ArrayList<Store> sortSold(ArrayList<Store> marketplace) {
        // Create a custom comparator to compare stores by sales
        Comparator<Store> priceComparator = new Comparator<>() {
            public int compare(Store store1, Store store2) {
                // Compare sales prices of the two stores
                return Integer.compare(store1.getSoldProducts().size(), store2.getSoldProducts().size());
            }
        };
        // Sort the marketplace ArrayList using the custom comparator
        marketplace.sort(priceComparator);
        // Return the sorted ArrayList
        return marketplace;
    }

    /**
     * Represents the customer as a string
     * @return - A string representation of the customer
     */
    public String toString() {
        // uses the Person toString() along with our extra fields
        return super.toString() + String.format(";%s;%s;%s",
                cart.toString(),purchaseHistory.toString(), stores.toString());
    }
}
