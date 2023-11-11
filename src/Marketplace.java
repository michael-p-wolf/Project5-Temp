import javax.lang.model.type.ArrayType;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Marketplace {

    private static ArrayList<Product> marketplace = new ArrayList<>();
    private static ArrayList<Seller> sellers;
    private static ArrayList<Customer> customers;
    public static final String WELCOME = "Home Screen\n[1] Sign In\n[2] Create Account\n[3] Exit";
    public static final String CREATE_ACCOUNT_SCREEN = "Create Account:\n[1] Customer Account\n" +
            "[2] Seller Account\n[3] Go Back";
    public static final String CUSTOMER_HOME = "[1] View Marketplace\n[2] View Shopping Cart\n" +
            "[3] View Store\n[4] Edit Account\n[5] View Purchase History\n[6] Log Out";
    public static final String SELLER_HOME = "Seller Home Screen\n[1] My Stores\n[2] Edit Products\n" +
            "[3] Import Products\n[4] Export Products\n[5] Edit Account\n[6] Log Out";

    public static void main(String[] args) {

        // Test Cases

        Product basketball = new Product("basketball", "Academy", "round, orange",
                5, 29.99);
        Product phone = new Product("phone", "Apple", "expensive, durable",
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
                                System.out.println("Login failed!\n[1]Try Again\n[2]Exit");
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
                                    for (Product product : marketplace) {
                                        if (product.getName().equals(inputString)) {
                                            Product targetProduct = product;
                                            while (true) {
                                                System.out.printf("Product: %s\nStore: %s\nDescription: %s\nPrice: " +
                                                                "%.2f\nQuantity: %d\n\n", targetProduct.getName(),
                                                        targetProduct.getStoreSelling(), targetProduct.getDescription(),
                                                        targetProduct.getPrice(), targetProduct.getQuantity());
                                                System.out.println("What would you like to do?\n[1] Purchase Product\n[2] Add" +
                                                        " To Cart\n[3] Go Back");
                                                inputString = scan.nextLine();
                                                input = Integer.parseInt(inputString);
                                                while (true) {
                                                    // Purchase or Add to Cart
                                                    if (input == 1) { // Purchase
                                                        customer.buy(targetProduct);
                                                        System.out.println("Item Bought");
                                                        break;
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
                                }
                            }
                        }
                        // Search for Product
                        else if (input == 2) {
                            while (true) {
                                System.out.println("Search By:\n[1] Name\n[2] Store\n[3] Description\n[4] Go Back");
                                inputString = scan.nextLine();
                                input = Integer.parseInt(inputString);
                                System.out.println("Enter your search:");
                                inputString = scan.nextLine();
                                ArrayList<Product> searchResults = marketplace;

                                // Search by...
                                if (input == 1) { // Search by Name
                                    searchResults = customer.searchName(marketplace, inputString);
                                } else if (input == 2) { // Search by Store
                                    searchResults = customer.searchStore(marketplace, inputString);
                                } else if (input == 3) { // Search by Description
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
                                    System.out.println("What would you like to do?\n[1] Purchase Product\n[2] Add" +
                                            " To Cart\n[3] Go Back");
                                    inputString = scan.nextLine();
                                    input = Integer.parseInt(inputString);

                                    // Purchase or Add to Cart
                                    if (input == 1) { // Buy item
                                        customer.buy(targetProduct);
                                        System.out.println("Item Bought");
                                        break;
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
                        else if (input == 3) {
                            break;
                        }
                        else System.out.println("Invalid Input");
                    }
                }
                // View Shopping Cart
                else if (input == 2) {
                    while (true) {
                        System.out.println(customer.getCart());
                        System.out.println("Would you like to:\n[1] Purchase All Items\n[2] Remove Item\n[3] " +
                                "Go Back");
                        inputString = scan.nextLine();
                        input = Integer.parseInt(inputString);

                        // Buy All Items or Remove Individual Items
                        if (input == 1) { // Buy All Items
                            customer.buyFromCart();
                            System.out.println("Items Bought");
                        } else if (input == 2) { // Remove Item from Cart
                            System.out.println("Enter the name of the item you'd like to remove");
                            inputString = scan.nextLine();
                            for (Product product : customer.getCart()) {
                                if (product.getName().equals(inputString)) {
                                    customer.removeFromCart(product);
                                }
                            }
                        } else if (input == 3) { // Go Back
                            break;
                        } else
                            System.out.println("Invalid Input");
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
                    System.out.println(customer.getPurchaseHistory());
                    System.out.println("\nWould you like to export to file?\nType \"export\" to export to file");
                    inputString = scan.nextLine();

                    // Export To File
                    if (inputString.toLowerCase().equals("export")) {
                        customer.createPurchaseHistory();
                        System.out.println("Purchase History File Created");
                    }
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
        int input = 0;
        do {
            System.out.println(SELLER_HOME);
            try {
                String inputString = scan.nextLine();
                input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        try {
                            System.out.println("[1]Create new Store\n[2]Back");
                            int storeInput = Integer.parseInt(scan.nextLine());
                            switch (storeInput) {
                                case 1:
                                    String storeName = scan.nextLine();
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    System.out.println("Invalid input!");
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                        }
                        break;
                    case 2:
                        seller.editAccount(scan);
                        break;
                    case 3:
                        List<Store> stores = seller.getStores();
                        System.out.println("[1]Go Back");
                        for (int i = 0; i < stores.size(); i++) {
                            int index = i;
                            System.out.println("[" + (index+2) +"]" + stores.get(i).getStoreName());
                        }
                        try {
                            int storeInput = Integer.parseInt(scan.nextLine());
                            if (storeInput == 1) {
                                break;
                            } else if (storeInput <= stores.size() + 1) {
                                System.out.println(stores.get(storeInput-2).getStoreName() + "\n[1]");
                                break;
                            } else {
                                System.out.println("Invalid input!");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        ;
                    case 5:
                        ;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }

        } while (true);
    }
}