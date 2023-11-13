import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Boilermaker Bazaar Bonanza
 * <p>
 * A subclass of Person. Contains basic account information as well as basic customer information,
 * such as their current shopping cart, a list of products they have purchased, and a list of stores
 * they have bought from. This information is stored in Customers.txt so user data persists.
 *
 * @author Michael Wolf, Lab Sec 36
 * @author Pranay Nandkeolyar, Lab Sec 36
 * @author Jacob Stamper, Lab Sec 36
 * @author Benjamin Emini, Lab Sec 36
 * @author Simrat Thind, Lab Sec 36
 * @version November 13th, 2023
 **/

public class Customer extends Person {
    private ArrayList<CartObject> cart;
    private ArrayList<Product> purchaseHistory;
    private ArrayList<String> stores;

    public Customer(String email, String password, String accountType) {
        super(email, password, accountType);
        this.cart = new ArrayList<CartObject>();
        this.purchaseHistory = new ArrayList<Product>();
    }

    public ArrayList<CartObject> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartObject> cart) {
        this.cart = cart;
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;
    }

    public ArrayList<String> getStores() {
        return stores;
    }

    public void addToCart(CartObject c) {
        this.cart.add(c);
    }

    public void buy(Product p, Scanner scan, Seller seller, Store store) {
        do {
            if (p.getQuantity() < 1) {
                System.out.println(p.getName() + " is out of stock!");
                return;
            }
            System.out.println(p.getName() + "\nHow many would you like to buy?");
            try {
                int quantity = Integer.parseInt(scan.nextLine());
                if (quantity <= p.getQuantity()) {
                    double totalCost = quantity * p.getPrice();
                    System.out.printf("Purchase %d of %s for $%.2f?\n[1]Confirm\n[2]Cancel\n", quantity, p.getName(), totalCost);
                    try {
                        int input = Integer.parseInt(scan.nextLine());
                        switch (input) {
                            case 1:
                                p.setQuantity(p.getQuantity() - quantity);
                                this.purchaseHistory.add(p);
                                store.getSoldProducts().add(p);

                                seller.getSales().add(new Sales(seller.getEmail(), this.getEmail(), store.getStoreName(), p.getName(), p.getPrice(), quantity));
                                System.out.println("Purchase Successful!");
                                return;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid input!");
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Not enough stock available!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void addToCart(Product p, Scanner scan) {
        do {
            if (p.getQuantity() < 1) {
                System.out.println(p.getName() + " is out of stock!");
                return;
            }
            System.out.println(p.getName() + "\nHow many would you like to add to cart?");
            try {
                int quantity = Integer.parseInt(scan.nextLine());
                if (quantity <= p.getQuantity()) {
                    double totalCost = quantity * p.getPrice();
                    System.out.printf("Add %d of %s for $%.2f to cart?\n[1]Confirm\n[2]Cancel\n", quantity, p.getName(), totalCost);
                    try {
                        int input = Integer.parseInt(scan.nextLine());
                        switch (input) {
                            case 1:
                                this.cart.add(new CartObject(p.getName(), p.getStoreSelling(), p.getDescription(), p.getPrice(), quantity));
                                return;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid input!");
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                    }
                } else {
                    System.out.println("Not enough stock available!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void addToHistory(Product product) {
        this.purchaseHistory.add(product);
    }

    public void createPurchaseHistory(ArrayList<Seller> sellers) {
        String filename = super.getEmail() + "History.txt";
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            for (Seller s : sellers) {
                sales.addAll(s.getSales());
            }
            for (Sales s : sales) {
                Product p = productInPurchaseHistory(s.getProductName());
                if (s.getCustomerEmail().equals(this.getEmail()) && p != null) {
                    String name = p.getName();
                    String description = p.getDescription();
                    String store = p.getStoreSelling();
                    double price = p.getPrice();
                    int quantity = s.getQuantity();
                    if (!(name.isEmpty() || description.isEmpty() || store.isEmpty() || price == 0 || quantity == 0)) {
                        String str = String.format("%s;%s;%s;%d;%f\n", name, store, description, quantity, price);
                        pw.printf("%s;%s;%s;%d;%f\n", name, store, description, quantity, price);
                    }
                }
            }
            pw.flush();
        } catch (IOException e) {
            System.out.println("Write File Error");
            e.printStackTrace();
        }
    }

    public String toString() {
        // uses the Person toString() along with our extra fields
        return super.toString() + String.format(";%s;%s;%s",
                cart.toString(), purchaseHistory.toString(), stores.toString());
    }

    public void updateCustomer(Customer update) {
        this.setEmail(update.getEmail());
        this.setPassword(update.getPassword());
        this.cart = update.getCart();
        this.purchaseHistory = update.getPurchaseHistory();
    }

    public void displayMarket(Scanner scan, ArrayList<Seller> sellers) {
        do {
            int productCount = 0;
            ArrayList<Product> products = new ArrayList<>();

            // Add all products to the 'products' list for sorting
            for (int i = 0; i < sellers.size(); i++) {
                for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    products.addAll(sellers.get(i).getStores().get(j).getProducts());
                }
            }

            // Sort products by quantity or price
            System.out.println("[1]Sort by Quantity");
            System.out.println("[2]Sort by Price");
            System.out.println("[3]Go Back");

            try {
                int sortOption = Integer.parseInt(scan.nextLine());

                switch (sortOption) {
                    case 1:
                        // Sort by quantity
                        Collections.sort(products, Comparator.comparingInt(Product::getQuantity));
                        break;
                    case 2:
                        // Sort by price
                        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
                        break;
                    case 3:
                        return; // Go back
                    default:
                        System.out.println("Invalid input!");
                        continue;
                }

                // Display sorted products
                for (Product product : products) {
                    System.out.println("[" + (productCount + 2) + "]" + product.getName() +
                            "\nStore:" + product.getStoreSelling() +
                            "\nPrice: " + product.getPrice() +
                            "\nQuantity " + product.getQuantity());

                    productCount++;
                }

                if (productCount != 0) {
                    System.out.println("[1]Go Back");
                    try {
                        int input = Integer.parseInt(scan.nextLine());
                        if (input == 1) {
                            return;
                        } else if (input <= productCount + 2) {
                            productCount = 0;
                            // Find the selected product after sorting
                            for (Product sortedProduct : products) {
                                if (productCount == input - 2) {
                                    // Assuming productPage method takes care of displaying details
                                    this.productPage(scan, sortedProduct, getSellerForProduct(sortedProduct, sellers), getStoreForProduct(sortedProduct, sellers));
                                    return;
                                }
                                productCount++;
                            }
                        } else {
                            System.out.println("Invalid input!");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                    }
                } else {
                    System.out.println("No items are available currently.");
                    return;
                }

            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    // Helper methods to get Seller and Store for a Product
    private Seller getSellerForProduct(Product product, ArrayList<Seller> sellers) {
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                if (store.getProducts().contains(product)) {
                    return seller;
                }
            }
        }
        return null; // Handle accordingly based on your requirements
    }

    private Store getStoreForProduct(Product product, ArrayList<Seller> sellers) {
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                if (store.getProducts().contains(product)) {
                    return store;
                }
            }
        }
        return null; // Handle accordingly based on your requirements
    }

    public void productPage(Scanner scan, Product product, Seller seller, Store store) {
        do {
            System.out.println(product.getName() + "\nStore: " + product.getStoreSelling() + "\nDescription: " + product.getDescription() + "\nQuantity: " + product.getQuantity() + "\nPrice: " + product.getPrice()
                    + "\n[1]Purchase Product\n[2]Add to Cart\n[3]Go Back");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        buy(product, scan, seller, store);
                        return;
                    case 2:
                        addToCart(product, scan);
                        return;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void search(Scanner scan, ArrayList<Seller> sellers) {
        while (true) {
            // Collect search results
            ArrayList<Product> searchResults = new ArrayList<>();
            int resultCount = 1;

            System.out.println("What would you like to search for?");
            String search = scan.nextLine();

            for (Seller seller : sellers) {
                for (Store store : seller.getStores()) {
                    for (Product product : store.getProducts()) {
                        if (product.getName().contains(search) || product.getDescription().contains(search) || store.getStoreName().contains(search)) {
                            System.out.println("[" + resultCount + "]" + product.getName() + "\nStore:" + store.getStoreName() + "\nPrice: " + product.getPrice());
                            searchResults.add(product);
                            resultCount++;
                        }
                    }
                }
            }

            if (searchResults.isEmpty()) {
                System.out.println("No products matching the search criteria.");
                return;
            }

            // Process user input
            System.out.println("[0] Go Back");
            System.out.println("Select a product:");

            try {
                int input = Integer.parseInt(scan.nextLine());
                if (input == 0) {
                    return;
                } else if (input <= searchResults.size()) {
                    Product selectedProduct = searchResults.get(input - 1);
                    Store store = getStoreFromName(selectedProduct.getStoreSelling(), sellers);
                    Seller s = getSellerFromSellerName(store.getSeller(), sellers);
                    this.productPage(scan, selectedProduct, s, store);
                    return;
                } else {
                    System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }


    //if a seller changes the price while the product is in the cart, the cart price will remain the same
    public void shoppingCart(Scanner scan, ArrayList<Seller> sellers) {
        do {
            int index = 0;
            double grandTotal = 0;
            for (int i = 0; i < cart.size(); i++) {
                index = i;
                double totalPrice = cart.get(i).getPrice() * cart.get(i).getCartQuantity();
                grandTotal += totalPrice;
                System.out.println("[" + (index + 1) + "]" + cart.get(i).getName() + "\nQuantity: " + cart.get(i).getCartQuantity() + "\nTotal Price: " + totalPrice);
            }
            int totalInput = index + cart.size() + 2;
            System.out.println("Grand Total: " + grandTotal + "\n[" + (totalInput - 1) + "]Purchase Cart\n[" + (totalInput) + "]Empty Cart\n[" + (totalInput + 1) + "]Go Back");
            try {
                int input = Integer.parseInt(scan.nextLine());
                if (input == totalInput + 1) {
                    return;
                } else if (input == (totalInput)) {
                    System.out.println("Empty Cart?\n[1]Confirm\n[2]Cancel");
                    try {
                        int input2 = Integer.parseInt(scan.nextLine());
                        switch (input2) {
                            case 1:
                                this.emptyCart(sellers);
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                    }
                } else if (input == (totalInput - 1)) {
                    System.out.println("Purchase entire cart?\n[1]Confirm\n[2]Cancel");
                    try {
                        int input2 = Integer.parseInt(scan.nextLine());
                        switch (input2) {
                            case 1:
                                this.purchaseCart(sellers);
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                    }
                } else if (input <= (totalInput - 2)) {
                    this.cartProductPage(scan, this.cart.get(input - 1), sellers);
                } else {
                    System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);
    }

    public void removeFromCart(CartObject o, ArrayList<Seller> sellers) {
        this.cart.remove(o);
    }

    public void emptyCart(ArrayList<Seller> sellers) {
        for (int i = 0; i < this.cart.size(); i++) {
            this.removeFromCart(this.cart.get(i), sellers);
        }
    }

    public void purchaseCart(ArrayList<Seller> sellers) {
        for (CartObject p : cart) {
            //this.purchaseHistory.add(p);
            Product p2 = getProductInCart(p.getName(), sellers);
            if (p2.getQuantity() - p.getQuantity() < 0) {
                System.out.println("Out of Stock!");
                return;
            }
            p2.setQuantity(p2.getQuantity() - p.getCartQuantity());
            this.purchaseHistory.add(p);
            Store store = getStoreFromName(p.getStoreSelling(), sellers);
            store.getSoldProducts().add(p);
            Seller seller = getSellerFromName(store.getStoreName(), sellers);
            seller.getSales().add(new Sales(seller.getEmail(), this.getEmail(), store.getStoreName(), p.getName(), p.getPrice(), p.getCartQuantity()));
        }
        this.cart = new ArrayList<CartObject>();
    }

    private Product getProductInCart(String name, ArrayList<Seller> sellers) {
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for (Product p : store.getProducts()) {
                    if (p.getName().equals(name)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    private Store getStoreFromName(String name, ArrayList<Seller> sellers) {
        for (Seller s : sellers) {
            for (Store store : s.getStores()) {
                if (store.getStoreName().equals(name)) {
                    store.setSellerEmail(s.getEmail());
                    return store;
                }
            }
        }
        return null;
    }


    private Seller getSellerFromName(String name, ArrayList<Seller> sellers) {
        for (Seller s : sellers) {
            for (Store store : s.getStores()) {
                if (store.getStoreName().equals(name)) {
                    return s;
                }
            }
        }
        return null;
    }

    private Seller getSellerFromSellerName(String name, ArrayList<Seller> sellers) {
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getEmail().equals(name)) {
                return sellers.get(i);
            }
        }
        return null;
    }

    public void purchaseFromCart(CartObject o, ArrayList<Seller> sellers) {
        this.cart.remove(o);
        //this.purchaseHistory.add(p);
        Product p2 = getProductInCart(o.getName(), sellers);
        if (p2.getQuantity() - o.getQuantity() < 0) {
            System.out.println("Out of Stock!");
            return;
        }
        p2.setQuantity(p2.getQuantity() - o.getCartQuantity());
        this.purchaseHistory.add(new Product(o.getName(), o.getStoreSelling(), o.getDescription(), o.cartQuantity, o.getPrice()));
        Store store = getStoreFromName(o.getStoreSelling(), sellers);
        store.getSoldProducts().add(o);
        Seller seller = getSellerFromName(store.getStoreName(), sellers);
        seller.getSales().add(new Sales(seller.getEmail(), this.getEmail(), store.getStoreName(), o.getName(), o.getPrice(), o.getCartQuantity()));
    }

    public void cartProductPage(Scanner scan, CartObject o, ArrayList<Seller> sellers) {
        String name = o.getName();
        String store = o.getStoreSelling();
        String description = o.getDescription();
        double price = o.getPrice();
        int cartQuantity = o.getCartQuantity();
        double totalPrice = price * cartQuantity;
        do {
            System.out.println(name + "\nStore: " + store + "\nDescription: " + description + "\nQuantity: " + cartQuantity + "\nTotal Price: " + totalPrice + "\n[1]Purchase Product\n[2]Remove From Cart\n[3]Go Back");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        System.out.println("Purchase from cart?\n[1]Confirm\n[2]Cancel");
                        try {
                            int input2 = Integer.parseInt(scan.nextLine());
                            switch (input2) {
                                case 1:
                                    purchaseFromCart(o, sellers);
                                    break;
                                case 2:
                                    return;
                                default:
                                    System.out.println("Invalid input!");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                        }
                        return;
                    case 2:
                        System.out.println("Purchase from cart?\n[1]Confirm\n[2]Cancel");
                        try {
                            int input2 = Integer.parseInt(scan.nextLine());
                            switch (input2) {
                                case 1:
                                    removeFromCart(o, sellers);
                                    break;
                                case 2:
                                    return;
                                default:
                                    System.out.println("Invalid input!");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                        }
                        return;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void deleteAccount(Scanner scan, ArrayList<Customer> customers, ArrayList<Seller> sellers) {
        do {
            System.out.println("Enter Password: ");
            String pass = scan.nextLine();
            if (!pass.equals(this.getPassword())) {
                System.out.println("Incorrect password!");
                return;
            }
            System.out.println("Are you sure you want to delete your account? This action cannot be undone.\n[1]Confirm\n[2]Cancel");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        for (int i = 0; i < this.getCart().size(); i++) {
                            removeFromCart(this.getCart().get(i), sellers);
                        }
                        this.cart = null;
                        customers.remove(this);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public void printHistory(Scanner scan, ArrayList<Seller> sellers) {
        ArrayList<Sales> sales = new ArrayList<Sales>();
        for (Seller s : sellers) {
            sales.addAll(s.getSales());
        }
        for (Sales s : sales) {
            Product p = productInPurchaseHistory(s.getProductName());
            if (s.getCustomerEmail().equals(this.getEmail()) && p != null) {
                String name = p.getName();
                String description = p.getDescription();
                String store = p.getStoreSelling();
                double price = p.getPrice();
                int quantity = s.getQuantity();
                System.out.printf("Name: %s\nDescription: %s\nStore Selling: %s\nPrice: %.2f\nQuantity Purchased: %d\n", name, description, store, price, quantity);
            }
        }
        System.out.println("[1] Go Back");
        System.out.println("[2] Export to file");
        String input = scan.nextLine();
        switch (input) {
            case "1":
                return;
            case "2":
                createPurchaseHistory(sellers);
            default:
                System.out.println("Invalid Input!");
                return;
        }
    }

    private Product productInPurchaseHistory(String productName) {
        for (Product p : purchaseHistory) {
            if (p.getName().equals(productName)) {
                return p;
            }
        }
        return null;
    }

    public void storeDashboard(Scanner scan, ArrayList<Seller> sellers) {
        do {
            System.out.println("Which Dashboard would you like to view?\n[1]Stores by products sold (for all " +
                    "customers)\n[2]Stores by products sold (for your account)\n[3]Cancel");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        do {
                            System.out.println("How would you like to sort stores (general)?\n[1]Ascending\n[2]Descending\n[3]Cancel");
                            try {
                                int input2 = Integer.parseInt(scan.nextLine());
                                switch (input2) {
                                    case 1:
                                        ArrayList<Store> talliedSales = this.salesByStore(sellers);
                                        Collections.sort(talliedSales);
                                        for (int i = 0; i < talliedSales.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\n",
                                                    talliedSales.get(i).getStoreName(), talliedSales.get(i).getSellerEmail(),
                                                    talliedSales.get(i).getSoldProducts().size());
                                        }
                                        System.out.println("Press ENTER to continue.\n");
                                        scan.nextLine();
                                        return;
                                    case 2:
                                        ArrayList<Store> talliedSales2 = this.salesByStore(sellers);
                                        Collections.sort(talliedSales2, Collections.reverseOrder());
                                        for (int i = 0; i < talliedSales2.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\n",
                                                    talliedSales2.get(i).getStoreName(), talliedSales2.get(i).getSellerEmail(),
                                                    talliedSales2.get(i).getSoldProducts().size());
                                        }
                                        System.out.println("Press ENTER to continue.\n");
                                        scan.nextLine();
                                        return;
                                    case 3:
                                        return;
                                    default:
                                        System.out.println("Invalid input!");
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid input!");
                            }
                        } while (true);
                    case 2:
                        do {
                            System.out.println("How would you like to sort stores (personal)?\n[1]Ascending\n[2]Descending\n[3]Cancel");
                            try {
                                int input2 = Integer.parseInt(scan.nextLine());
                                switch (input2) {
                                    case 1:
                                        ArrayList<Store> talliedSales = this.personalSalesByStore(sellers);
                                        Collections.sort(talliedSales);
                                        for (int i = 0; i < talliedSales.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\n",
                                                    talliedSales.get(i).getStoreName(), talliedSales.get(i).getSellerEmail(),
                                                    talliedSales.get(i).getSoldProducts().size());
                                        }
                                        System.out.println("Press ENTER to continue.\n");
                                        scan.nextLine();
                                        return;
                                    case 2:
                                        ArrayList<Store> talliedSales2 = this.personalSalesByStore(sellers);
                                        Collections.sort(talliedSales2, Collections.reverseOrder());
                                        for (int i = 0; i < talliedSales2.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\n",
                                                    talliedSales2.get(i).getStoreName(), talliedSales2.get(i).getSellerEmail(),
                                                    talliedSales2.get(i).getSoldProducts().size());
                                        }
                                        System.out.println("Press ENTER to continue.\n");
                                        scan.nextLine();
                                        return;
                                    case 3:
                                        return;
                                    default:
                                        System.out.println("Invalid input!");
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid input!");
                                e.printStackTrace();
                            }
                        } while (true);
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }

    public ArrayList<Store> salesByStore(ArrayList<Seller> sellers) {
        ArrayList<Store> stores = new ArrayList<>();
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                store.setSeller(seller);
                stores.add(store);
            }
        }
        return stores;
    }

    public ArrayList<Store> personalSalesByStore(ArrayList<Seller> sellers) {
        ArrayList<Store> stores = new ArrayList<>();
        for (Seller seller : sellers) {
            for (Store store : seller.getStores()) {
                for (Product p : this.getPurchaseHistory()) {
                    if (p.getStoreSelling().equals(store.getStoreName()) && !stores.contains(store)) {
                        store.setSeller(seller);
                        stores.add(store);
                    }
                }
            }
        }
        return stores;
    }
}
