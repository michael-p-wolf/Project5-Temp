import javax.lang.model.type.ArrayType;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Marketplace {

    private static ArrayList<Product> marketplace = new ArrayList<>();
    private static ArrayList<Store> storefront = new ArrayList<>();
    private static ArrayList<Seller> sellers;
    private static ArrayList<Customer> customers;
    public static final String WELCOME = "Home Screen\n[1] Sign In\n[2] Create Account\n[3] Exit";
    public static final String CREATE_ACCOUNT_SCREEN = "Create Account:\n[1] Customer Account\n" +
            "[2] Seller Account\n[3] Go Back";
    public static final String CUSTOMER_HOME = "[1] View Marketplace\n[2] View Shopping Cart\n" +
            "[3] View Store Dashboard\n[4] Edit Account\n[5] View Purchase History\n[6] Log Out";
    public static final String SELLER_HOME = "[1] My Stores\n[2] Edit Products\n" +
            "[3] Import Products\n[4] Export Products\n[5] Edit Account\n[6] Log Out";

    public static void main(String[] args) {

        // Test Cases

        Product basketball = new Product("basketball", "Academy", "round, orange",
                5, 29.99);
        Product phone = new Product("phone", "Apple", "expensive and durable",
                200, 1399.99);
        Product chair = new Product("chair", "IKEA", "wooden with 4 legs",
                15, 49.99);
        Product candyBar = new Product("candy bar", "Willy Wonka", "caramel and chocolate",
                60, 2.99);
        Product truck = new Product("truck", "Ford", "lifted 4WD truck",
                1, 24500.00);

        Marketplace.marketplace.add(basketball);
        Marketplace.marketplace.add(phone);
        Marketplace.marketplace.add(chair);
        Marketplace.marketplace.add(candyBar);
        Marketplace.marketplace.add(truck);

        try {
            Store storeOne = new Store("Store One", "e@e.com");
            Store storeTwo = new Store("Store Two", "e@e.com");
            storeOne.addProduct(basketball);
            storeOne.addProduct(phone);
            storeOne.addProduct(chair);
            storeTwo.addProduct(candyBar);
            storeTwo.addProduct(truck);
            storefront.add(storeOne);
            storefront.add(storeTwo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner scan = new Scanner(System.in);

        int input = 0;
        int check = 0;
        String email = "";
        String password = "";

        do {
            switch (firstScreen(scan)) {
                case 1:
                    loginScreen(scan);
                    break;

                case 2:
                    try {
                        createAccountScreen(scan);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid input!");
            }
        } while (true);
    }

    public static int firstScreen(Scanner scan) {
        System.out.println(WELCOME);
        String inputString = scan.nextLine();
        try {
            int input = Integer.parseInt(inputString);
            return input;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void loginScreen(Scanner scan) {
        do {
            System.out.println("Email:");
            String email = scan.nextLine();
            System.out.println("Password:");
            String pass = scan.nextLine();
            System.out.printf("Login with email: %s and password: %s?\n[1] Confirm\n[2] Cancel\n", email, pass);
            String inputString = scan.nextLine();
            try {
                int input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        try {
                            Person activeUser = Person.login(email, pass);
                            if (activeUser.getAccountType().equals("C")) {
                                Customer activeCustomer = new Customer(activeUser.getEmail(),activeUser.getPassword());
                                customerHome(scan, activeCustomer);
                            } else if (activeUser.getAccountType().equals("S")) {
                                Seller activeSeller = new Seller(activeUser.getEmail(),activeUser.getPassword());
                                sellerHome(scan, activeSeller);
                            } else if (activeUser == null){/*Login failed*/
                                System.out.println("Login failed!\n[1] Try Again\n[2] cExit");
                                String input2String = scan.nextLine();
                                try {
                                    int input2 = Integer.parseInt(input2String);
                                    switch (input2) {
                                        case 1: break;
                                        case 2: return;
                                        default:
                                            System.out.println("Invalid Input!");
                                            return;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input!");
                                    return;
                                }

                            }
                        } catch (NullPointerException e) {
                            return;
                        }

                    case 2:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                e.printStackTrace();
                return;
            }
        } while (true);
    }

    public static void createAccountScreen (Scanner scan) throws IOException {
        Person currentUser = Person.createAccount(scan);
        if (currentUser != null) {
            if (currentUser.getAccountType().equals("C")) {
                Customer currentCustomer = new Customer(currentUser.getEmail(),currentUser.getPassword());
                customerHome(scan, currentCustomer);
            } else if (currentUser.getAccountType().equals("S")) {
                Seller currentSeller = new Seller(currentUser.getEmail(),currentUser.getPassword());
                sellerHome(scan, currentSeller);
            }
        }
    }

    public static void customerHome(Scanner scan, Customer customer) {

        String inputString;
        int input;

        do {
            System.out.println(customer.getEmail() + " Customer Homepage\n" + CUSTOMER_HOME);
            try {
                inputString = scan.nextLine();
                input = Integer.parseInt(inputString);

                // View Marketplace
                if (input == 1) {
                    while (true) {
                        System.out.println("[1] Browse Products\n[2] Search\n[3] Go Back");
                        inputString = scan.nextLine();
                        input = Integer.parseInt(inputString);

                        // Browse Products
                        if (input == 1) {
                            ArrayList<Product> sortResults = marketplace;
                            while (true) {
                                System.out.println("Products:\n");
                                for (Product product : sortResults) {
                                    System.out.printf("Product: %s\nStore: %s\nDescription: %s\nPrice: %.2f\nQuantity: " +
                                                    "%d\n\n", product.getName(), product.getStoreSelling(),
                                            product.getDescription(), product.getPrice(), product.getQuantity());
                                }
                                System.out.println("Enter the product you'd like to view:\nOr sort by\n[1] " +
                                        "Price\n[2] Quantity\n[3] Go Back");
                                inputString = scan.nextLine();

                                // Sort or Search
                                if (inputString.equals("1")) { // Sort by Price
                                    sortResults = customer.sortPrice(marketplace);
                                } else if (inputString.equals("2")) { // Sort by Quantity
                                    sortResults = customer.sortQuantity(marketplace);
                                } else if (inputString.equals("3")) { // Go back
                                    break;
                                } else {

                                    // Choose Product
                                    for (int i = 0; i < marketplace.size(); i++) {
                                        Product product = marketplace.get(i);
                                        if (product.getName().equals(inputString)) {
                                            boolean done = false;
                                            while (!done) {
                                                System.out.printf("Product: %s\nStore: %s\nDescription: %s\nPrice: " +
                                                                "%.2f\nQuantity: %d\n\n", product.getName(),
                                                        product.getStoreSelling(), product.getDescription(),
                                                        product.getPrice(), product.getQuantity());
                                                System.out.println("What would you like to do?\n[1] Purchase Product\n[2] Add" +
                                                        " To Cart\n[3] Go Back");
                                                inputString = scan.nextLine();
                                                input = Integer.parseInt(inputString);
                                                // Purchase or Add to Cart
                                                if (input == 1) { // Purchase
                                                    customer.buy(product);
                                                    System.out.println("Item Bought");
                                                } else if (input == 2) { // Add To Cart
                                                    customer.addToCart(product);
                                                    System.out.println("Item Added to Cart");
                                                } else if (input == 3) { // Go Back
                                                    done = true;
                                                } else {
                                                    System.out.println("Invalid Input");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Search for Product
                        else if (input == 2) {
                            while (true) {
                                System.out.println("Search By:\n[1] Name\n[2] Store\n[3] Description\n[4] Go Back");
                                inputString = scan.nextLine();
                                input = Integer.parseInt(inputString);
                                ArrayList<Product> searchResults = marketplace;
                                // Search by...
                                if (input == 1) { // Search by Name
                                    System.out.println("Enter your search:");
                                    inputString = scan.nextLine();
                                    searchResults = customer.searchName(marketplace, inputString);
                                } else if (input == 2) { // Search by Store
                                    System.out.println("Enter your search:");
                                    inputString = scan.nextLine();
                                    searchResults = customer.searchStore(marketplace, inputString);
                                } else if (input == 3) { // Search by Description
                                    System.out.println("Enter your search:");
                                    inputString = scan.nextLine();
                                    searchResults = customer.searchDescription(marketplace, inputString);
                                } else if (input == 4) { // Go Back
                                    break;
                                } else
                                    System.out.println("Invalid Input");
                                System.out.println("Products:\n");
                                for (Product product : searchResults) {
                                    System.out.printf("Product: %s\nStore: %s\nDescription: %s\nPrice: %.2f\nQuantity: " +
                                                    "%d\n\n", product.getName(), product.getStoreSelling(),
                                            product.getDescription(), product.getPrice(), product.getQuantity());
                                }
                                if (searchResults.size() == 0)
                                    System.out.println("No results");
                                else if (searchResults.size() == 1) {
                                    Product targetProduct = searchResults.get(0);
                                    while (true) {
                                        System.out.println("What would you like to do?\n[1] Purchase Product\n[2] Add" +
                                                " To Cart\n[3] Go Back");
                                        inputString = scan.nextLine();
                                        input = Integer.parseInt(inputString);

                                        // Purchase or Add to Cart
                                        if (input == 1) { // Buy item
                                            System.out.println("How many would you like to buy?");
                                            int count = 0;
                                            inputString = scan.nextLine();
                                            input = Integer.parseInt(inputString);
                                            for (int i = 1; i <= input; i++) {
                                                customer.buy(targetProduct);
                                                count++;
                                            }
                                            if (count < 1) {
                                                System.out.println("Purchase could not be made");
                                            }
                                            else if (count == 1) {
                                                System.out.println("Item Bought");
                                                break;
                                            }
                                            else {
                                                System.out.println(count + "Items Bought");
                                                break;
                                            }
                                        } else if (input == 2) { // Add To Cart
                                            customer.addToCart(targetProduct);
                                            System.out.println("Item Added to Cart");
                                            break;
                                        } else if (input == 3) { // Go Back
                                            break;
                                        } else System.out.println("Invalid Input");
                                    }
                                }
                            }
                        }
                        else if (input == 3) {
                            break;
                        }
                        else System.out.println("Invalid Input");
                    }
                }
                // View Shopping Cart
                else if (input == 2) {
                    while (true) {
                        if (customer.getCart().size() != 0) {
                            System.out.println(customer.getCart());
                            System.out.println("Would you like to:\n[1] Purchase All Items\n[2] Remove Item\n[3] " +
                                    "Go Back");
                            inputString = scan.nextLine();
                            input = Integer.parseInt(inputString);

                            // Buy All Items or Remove Individual Items
                            if (input == 1) { // Buy All Items
                                if (customer.getCart().size() != 0) {
                                    customer.buyFromCart();
                                    System.out.println("Items Bought");
                                } else {
                                    System.out.println("Cart is empty");
                                }
                            } else if (input == 2) { // Remove Item from Cart
                                System.out.println("Enter the name of the item you'd like to remove");
                                inputString = scan.nextLine();
                                boolean found = false;
                                for (int i = 0; i < customer.getCart().size(); i++) {
                                    Product product = customer.getCart().get(i);
                                    if (product.getName().equals(inputString)) {
                                        customer.removeFromCart(product);
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Product not in Cart");
                                }
                            } else if (input == 3) { // Go Back
                                break;
                            } else {
                                break;
                            }
                        }
                        else {
                            System.out.println("There are no items in your cart.");
                            break;
                        }
                    }
                }
                // View Store Dashboard
                else if (input == 3) {
                    System.out.println("[Print Store Dashboard Here]");
                }
                // Edit Account
                else if (input == 4) {
                        customer.editAccount(scan);
                }
                // View Purchase History
                else if (input == 5) {
                    if (customer.getPurchaseHistory().size() != 0) {
                        System.out.println(customer.getPurchaseHistory());
                        System.out.println("Would you like to export to file?\n[1] Yes\n[2] Go Back");
                        inputString = scan.nextLine();
                        input = Integer.parseInt(inputString);

                        // Export To File
                        if (inputString.equalsIgnoreCase("yes")) {
                            customer.createPurchaseHistory();
                            System.out.println("Purchase History File Created");
                        }
                    }
                    else System.out.println("You haven't purchased any items yet.");
                }
                // Log Out
                else if (input == 6) {
                    break;
                }
                else
                    System.out.println("Invalid Input!");
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                // e.printStackTrace();
            }
        } while (true);
    }

    public static void sellerHome(Scanner scan, Seller seller) {
        String inputString;
        int input;

        do {
            System.out.println(seller.getEmail() + " Seller Homepage\n" + SELLER_HOME);
            try {
                inputString = scan.nextLine();
                input = Integer.parseInt(inputString);

                // View My Stores
                if (input == 1) {
                    // Print all seller's stores
                    while (true) {
                        System.out.println("[1] Create a New Store\n[2] Search My Stores\n[3] Go Back");
                        inputString = scan.nextLine();
                        input = Integer.parseInt(inputString);
                        // Add Store or Search for Store
                        if (input == 1) { // Add Store
                            System.out.println("Enter the new store name:\nOr enter \"return\" to go back");
                            String newName = scan.nextLine();
                            // If store exists, restart
                            // If not, create new store
                            boolean alreadyExists = false;
                            for (Store store : storefront) {
                                if (newName.equals(store.getStoreName())) {
                                    alreadyExists = true;
                                }
                            }
                            if (newName.equalsIgnoreCase("return")) {
                                // Go Back
                            } else if (alreadyExists) {
                                System.out.println("A store of this name already exists");
                            } else {
                                seller.createStore(newName);
                                System.out.println("Store Created");
                                System.out.println("[Make sure files change]");
                            }
                        }
                        else if (input == 2) { // Search My Stores
                            if (seller.getStores().size() != 0) {
                                System.out.println("My Stores:");
                                for (Store store : seller.getStores()) {
                                    System.out.println("-" + store.getStoreName());
                                }
                                System.out.println("Enter the name of the store you'd like to view\nOr enter \"return\" to " +
                                        "go back");
                                inputString = scan.nextLine();

                                // Choose Store
                                if (inputString.equalsIgnoreCase("return")) {
                                    break;
                                }
                                Store currentStore = seller.switchCurrentStore(inputString);
                                if (currentStore != null) {
                                    while (true) {
                                        System.out.println("What would you like to do?\n[1] Change Store Name\n[2] " +
                                                "View Store Sales\n[3] Delete Store\n[4] Edit Store Products\n[5] Go Back");
                                        inputString = scan.nextLine();
                                        input = Integer.parseInt(inputString);

                                        // Edit Store
                                        if (input == 1) { // Change Store Name
                                            System.out.println("Enter your new store name:\nOr enter \"return\" to " +
                                                            "go back");
                                            inputString = scan.nextLine();
                                            if (inputString.equalsIgnoreCase("return")) {
                                                System.out.println("Name Change Canceled");
                                                break;
                                            } else {
                                                currentStore.setStoreName(inputString);
                                                System.out.println("[Implement Name Change Here]");
                                                System.out.println("[Ensure files are changed]");
                                            }
                                        } else if (input == 2) { // View Store Sales
                                            System.out.println("[Print Store Dashboard Here]");
                                        } else if (input == 3) { // Delete Store
                                            System.out.println("Are you sure you want to delete this " +
                                                    "product?\nEnter \"yes\" to confirm");
                                            inputString = scan.nextLine();
                                            if (inputString.equalsIgnoreCase("yes")) {
                                                seller.removeStore(currentStore.getStoreName());
                                                System.out.println("Product Deleted");
                                                System.out.println("[Ensure files are changed]");
                                            }
                                            else System.out.println("Deletion aborted");
                                        } else if (input == 4) { // Edit Store Products
                                            System.out.println("\n");
                                            if (currentStore.getProducts().size() > 0) {
                                                for (Product product : currentStore.getProducts()) {
                                                    if (currentStore.getProducts().size() != 0) {
                                                        System.out.printf("Product: %s\nStore: %s\nDescription: %s\nPrice: " +
                                                                        "%.2f\nQuantity: %d\n\n", product.getName(),
                                                                product.getStoreSelling(), product.getDescription(),
                                                                product.getPrice(), product.getQuantity());
                                                    }
                                                }
                                            }
                                            System.out.println("\n");
                                            System.out.println("\nEnter the name of the product you'd like to " +
                                                    "edit\n[1] Add Product\n[2] Go Back");
                                            inputString = scan.nextLine();

                                            // Add Product Or Choose Product
                                            if (inputString.equalsIgnoreCase("1")) {
                                                System.out.println("Enter the product's name:");
                                                String productName = scan.nextLine();
                                                System.out.println("Enter the product's description:");
                                                String description = scan.nextLine();
                                                System.out.println("Enter the product's price:");
                                                double price = scan.nextInt();
                                                System.out.println("How many of this product are " +
                                                        "available:");
                                                int quantity = scan.nextInt();
                                                currentStore.addProduct(new Product(productName,
                                                        seller.getEmail(),
                                                        description, quantity, price));
                                                System.out.println("Product added");
                                                System.out.println("[Ensure files are changed]");;
                                            }
                                            else if (inputString.equalsIgnoreCase("2")) {
                                                break;
                                            } else {
                                                for (int i = 0; i < currentStore.getProducts().size(); i++) {
                                                    Product currentProduct = currentStore.getProducts().get(i);
                                                    if (inputString.equals(currentProduct.getName())) {
                                                        while (true) {
                                                            System.out.println("What would you like to do?\n[1] " +
                                                                    "Add Product\n[2] Change Product Name\n" +
                                                                    "[3] Change Description\n[4] Change " +
                                                                    "Price\n [5] Change Quantity\n[6] View Shopping Cart " +
                                                                    "Stats\n[7] Delete Product\n[8] Go Back");
                                                            // Edit Product
                                                            inputString = scan.nextLine();
                                                            input = Integer.parseInt(inputString);
                                                            if (input == 1) { // Add Product
                                                                System.out.println("Enter the product's name:");
                                                                String productName = scan.nextLine();
                                                                System.out.println("Enter the product's description:");
                                                                String description = scan.nextLine();
                                                                System.out.println("Enter the product's price:");
                                                                double price = scan.nextInt();
                                                                System.out.println("How many of this product are " +
                                                                        "available:");
                                                                int quantity = scan.nextInt();
                                                                currentStore.addProduct(new Product(productName,
                                                                        seller.getEmail(),
                                                                        description, quantity, price));
                                                                System.out.println("Product added");
                                                                System.out.println("[Ensure files are changed]");
                                                            } else if (input == 2) { // Change Product Name
                                                                System.out.println("Enter the new product name:");
                                                                inputString = scan.nextLine();
                                                                currentProduct.setName(inputString);
                                                                System.out.println("Name changed");
                                                                System.out.println("[Ensure files are changed]");
                                                            } else if (input == 3) { // Change Description
                                                                System.out.println("Enter the new description:");
                                                                inputString = scan.nextLine();
                                                                currentProduct.setName(inputString);
                                                                System.out.println("Description changed");
                                                                System.out.println("[Ensure files are changed]");
                                                            } else if (input == 4) { // Change Price
                                                                System.out.println("Enter the new price:");
                                                                inputString = scan.nextLine();
                                                                currentProduct.setName(inputString);
                                                                System.out.println("Price changed");
                                                                System.out.println("[Ensure files are changed]");
                                                            } else if (input == 5) { // Change Quantity
                                                                System.out.println("Enter the new quantity:");
                                                                inputString = scan.nextLine();
                                                                currentProduct.setName(inputString);
                                                                System.out.println("Quantity changed");
                                                                System.out.println("[Ensure files are changed]");
                                                            } else if (input == 6) { // View Shopping Cart Stats
                                                                System.out.println("[Implement Shopping Cart Stats Here]");
                                                            } else if (input == 7) { // Delete Product
                                                                System.out.println("Are you sure you want to delete this " +
                                                                        "product?\nEnter \"yes\" to confirm");
                                                                inputString = scan.nextLine();
                                                                if (inputString.equalsIgnoreCase("yes")) {
                                                                    currentStore.removeProduct(currentProduct);
                                                                    System.out.println("Product Deleted");
                                                                    System.out.println("[Ensure files are changed]");
                                                                }
                                                                else System.out.println("Deletion aborted");
                                                            } else if (input == 8) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (input == 5) {
                                            break;
                                        } else System.out.println("Invalid Input");
                                    }
                                }
                            } else System.out.println("You currently have no stores.\nCreate one first.");
                        }
                        else if (input == 3)
                            break;
                        else System.out.println("Invalid Input");
                    }
                }
                // Edit Products
                else if (input == 2) {
                    boolean hasAnyProducts = false;
                    for (Store store : seller.getStores()) {
                        for (Product product : store.getProducts()) {
                            if (store.getProducts().size() != 0) {
                                System.out.printf("Product: %s\nStore: %s\nDescription: %s\nPrice: %.2f\nQuantity: " +
                                                "%d\n", product.getName(), product.getStoreSelling(),
                                        product.getDescription(), product.getPrice(), product.getQuantity());
                                hasAnyProducts = true;
                            }
                        }
                    }
                    if (hasAnyProducts) {
                        while (true) {
                            System.out.println("Enter the name of the product you'd like to edit\nOr enter " +
                                    "\"return\" to go back");
                            inputString = scan.nextLine();

                            // Choose Product
                            if (inputString.equalsIgnoreCase("return")) {
                                break;
                            } else {
                                for (int i = 0; i < seller.getStores().size(); i++) {
                                    Store currentStore = seller.getStores().get(i);
                                    for (int j = 0; j < currentStore.getProducts().size(); j++) {
                                        Product currentProduct = currentStore.getProducts().get(j);
                                        if (inputString.equals(currentProduct.getName())) {
                                            while (true) {
                                                System.out.println("What would you like to do?\n[1] " +
                                                        "Add Product\n[2] Change Product Name\n" +
                                                        "[3] Change Description\n[4] Change " +
                                                        "Price\n [5] Change Quantity\n[6] View Shopping Cart " +
                                                        "Stats\n[7] Delete Product\n[8] Go Back");
                                                // Edit Product
                                                inputString = scan.nextLine();
                                                input = Integer.parseInt(inputString);
                                                if (input == 1) { // Add Product
                                                    currentStore.addProduct(currentProduct);
                                                    System.out.println("[Ensure files are changed]");
                                                } else if (input == 2) { // Change Product Name
                                                    System.out.println("Enter the new product name");
                                                    inputString = scan.nextLine();
                                                    currentProduct.setName(inputString);
                                                    System.out.println("Name changed");
                                                    System.out.println("[Ensure files are changed]");
                                                } else if (input == 3) { // Change Description
                                                    System.out.println("Enter the new description");
                                                    inputString = scan.nextLine();
                                                    currentProduct.setName(inputString);
                                                    System.out.println("Description changed");
                                                    System.out.println("[Ensure files are changed]");
                                                } else if (input == 4) { // Change Price
                                                    System.out.println("Enter the new price");
                                                    inputString = scan.nextLine();
                                                    currentProduct.setName(inputString);
                                                    System.out.println("Price changed");
                                                    System.out.println("[Ensure files are changed]");
                                                } else if (input == 5) { // Change Quantity
                                                    System.out.println("Enter the new quantity");
                                                    inputString = scan.nextLine();
                                                    currentProduct.setName(inputString);
                                                    System.out.println("Quantity changed");
                                                    System.out.println("[Ensure files are changed]");
                                                } else if (input == 6) { // View Shopping Cart Stats
                                                    System.out.println("[Implement Shopping Cart Stats Here]");
                                                } else if (input == 7) { // Delete Product
                                                    System.out.println("Are you sure you want to delete this " +
                                                            "product?\nEnter \"yes\" to confirm");
                                                    inputString = scan.nextLine();
                                                    if (inputString.equalsIgnoreCase("yes")) {
                                                        currentStore.removeProduct(currentProduct);
                                                        System.out.println("Product Deleted");
                                                        System.out.println("[Ensure files are changed]");
                                                    }
                                                } else if (input == 8) {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else System.out.println("You currently have no products\nAdd one to a store first");
                }
                // Import Products
                else if (input == 3) {
                    System.out.println("Enter the filename of the file to import from:\nOr type \"return\" to go " +
                            "back");
                    inputString = scan.nextLine();
                    if (inputString.equalsIgnoreCase("return")) {
                        // Go Back
                    } else {
                        System.out.println("[Implement Import Here]");
                    }
                }
                // Export Products
                else if (input == 4) {
                    System.out.println("Enter the filename of the file to export to:\nOr type \"return\" to go " +
                            "back");
                    inputString = scan.nextLine();
                    if (inputString.equalsIgnoreCase("return")) {
                        // Go Back
                    } else {
                        System.out.println("[Implement Export Here]");
                    }
                }
                // Edit Account
                else if (input == 5) {
                    seller.editAccount(scan);
                }
                // Log Out
                else if (input == 6) {
                    break;
                }
                else
                    System.out.println("Invalid Input!");
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                // e.printStackTrace();
            }
        } while (true);
    }

}