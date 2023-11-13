import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Boilermaker Bazaar Bonanza
 * <p>
 * This is the main class where all of the different classes come together. 
 * It stores objects of Sellers and Customers and keeps track of who is the active 
 * seller and customer through the variables activeCustomer and activeSeller. 
 *
 * @author Michael Wolf, Lab Sec 36
 * @author Pranay Nandkeolyar, Lab Sec 36
 * @author Jacob Stamper, Lab Sec 36
 * @author Benjamin Emini, Lab Sec 36
 * @author Simrat Thind, Lab Sec 36
 * @version November 13th, 2023
 **/
public class Marketplace {
    private static ArrayList<Seller> sellers = new ArrayList<Seller>();
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    public static final String WELCOME = "Home Screen\n[1]Sign In\n[2]Create Account\n[3]Exit Program";
    public static final String CREATE_ACCOUNT_SCREEN = "Create Account:\n[1]Customer Account\n" +
            "[2]Seller Account\n[3]Go Back";
    public static final String CUSTOMER_HOME = "[1]Marketplace\n[2]Edit Account\n[3]Search For Product\n" +
            "[4]Store Dashboard\n[5]Shopping Cart\n[6]Purchase History\n[7]Delete Account\n[8]Sign Out";
    public static final String SELLER_HOME = "[1]Create Store\n[2]Edit Account\n[3]Access Store " +
            "List\n[4]Dashboard\n[5]Shopping Cart\n[6]Import Products\n[7]Export Products\n[8]Delete Account\n[9]Sign" +
            " Out";

    private static Seller activeSeller;
    private static Customer activeCustomer;

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        Marketplace marketplace = new Marketplace();
        marketplace.readFiles();

        int input = 0;
        int check = 0;
        String email = "";
        String password = "";

        do {
            switch (firstScreen(scan)) {
                case 1:
                    loginScreen(scan);
                    marketplace.writeFiles();
                    break;

                case 2:
                    createAccountScreen(scan);
                    marketplace.writeFiles();
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid Input!");
            }
        } while (true);
    }

    public Marketplace() {

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
        boolean emailExists = false;
        do {
            System.out.println("Email:");
            String email = scan.nextLine();
            System.out.println("Password:");
            String pass = scan.nextLine();
            System.out.printf("Login with email: %s and password: %s?\n[1]Confirm\n[2]Cancel\n", email, pass);
            try {
                int input = Integer.parseInt(scan.nextLine());
                if (input == 1) {
                    for (int i = 0; i < customers.size(); i++) {
                        if (customers.get(i).getEmail().equals(email)) {
                            emailExists = true;
                            if (customers.get(i).getPassword().equals(pass)) {
                                activeCustomer = customers.get(i);
                                Customer update  = customerHome(scan, activeCustomer);
                                activeCustomer.updateCustomer(update);
                                return;
                            } else {
                                System.out.println("Your password is incorrect!");
                            }
                        }
                    }
                    for (int i = 0; i < sellers.size(); i++) {
                        if (sellers.get(i).getEmail().equals(email)) {
                            emailExists = true;
                            if (sellers.get(i).getPassword().equals(pass)) {
                                activeSeller = sellers.get(i);
                                Seller update = sellerHome(scan, activeSeller);
                                activeSeller.updateSeller(update);
                                return;
                            }
                            else {
                                System.out.println("Your password is incorrect!");
                            }
                        }
                    }
                    if(!emailExists) {
                        System.out.println("This email is not associated with an account!");
                        return;
                    }
                } else if (input == 2) {
                    return;
                } else {
                    System.out.println("Invalid input!");
                }

            } catch (Exception e) {
                System.out.println("Invalid Input!");
                return;
            }
        } while (true);
    }
    public static void createAccountScreen (Scanner scan) throws IOException {
        String email;
        String pass;
        String confirmPass;
        do {
            System.out.println(CREATE_ACCOUNT_SCREEN);
            try {
                int input = Integer.parseInt(scan.nextLine());
                if (input == 3) {
                    return;
                }
                System.out.println("Email:");
                email = scan.nextLine();

                if (Person.accountOnFile(email, sellers, customers)) {
                    System.out.println("This email is already associated with an account!");
                    return;
                }

                if (!Person.isValidFormat(email)) {
                    System.out.println("Your email isn't in the correct format.\n" +
                            "No spaces and no semicolons, must contain '@' and '.'");
                    return;
                }
                System.out.println("Password:");
                pass = scan.nextLine();

                if (Person.invalidPassword(pass)) {
                    System.out.println("Incorrect password format!\nNo spaces or semicolons.");
                    return;
                }

                System.out.println("Confirm Password");
                confirmPass = scan.nextLine();
                if (!pass.equals(confirmPass)) {
                    System.out.println("Passwords do not match!");
                    return;
                }

                switch (input) {
                    case 1:
                        Customer currentCustomer = new Customer(email, pass, "C");
                        customers.add(currentCustomer);
                        Customer update = customerHome(scan, currentCustomer);
                        currentCustomer.updateCustomer(update);
                        return;
                    case 2:
                        Seller currentSeller = new Seller(email, pass, "S");
                        sellers.add(currentSeller);
                        Seller update2 = sellerHome(scan, currentSeller);
                        currentSeller.updateSeller(update2);
                        return;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                        break;
                }

            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);

    }

    public static Customer customerHome(Scanner scan, Customer customer) {
        int input = 0;
        do {
            System.out.println(customer.getEmail() + " Customer Homepage\n" + CUSTOMER_HOME);
            try {
                input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        customer.displayMarket(scan, sellers);
                        break;
                    case 2:
                        customer.editAccount(scan, sellers, customers);
                        break;
                    case 3:
                        customer.search(scan, sellers);
                        break;
                    case 4:
                        customer.storeDashboard(scan, sellers);
                        break;
                    case 5:
                        customer.shoppingCart(scan, sellers);
                        break;
                    case 6:
                        System.out.println();
                        customer.printHistory(scan, sellers);
                        break;
                    case 7:
                        customer.deleteAccount(scan, customers, sellers);
                        break;
                    case 8:
                        return customer;
                    default: System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }

        } while (true);
    }

    public static Seller sellerHome(Scanner scan, Seller seller) {
        int input = 0;
        do {
            System.out.println(seller.getEmail() + " Seller Homepage\n" + SELLER_HOME);
            try {
                String inputString = scan.nextLine();
                input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        seller.createStore(scan, sellers);
                        break;
                    case 2:
                        seller.editAccount(scan, sellers, customers);
                        break;
                    case 3:
                        seller.storeInterface(scan);
                        break;
                    case 4:
                        seller.dashboard(scan);
                        break;
                    case 5:
                        seller.shoppingCart(scan, customers);
                        break;
                    case 6:
                        seller.importProducts(scan);
                        break;
                    case 7:
                        seller.exportProducts(scan);
                        break;
                    case 8:
                        seller.deleteAccount(scan, sellers);
                        break;
                    case 9:
                        return seller;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }

        } while (true);

    }

    public void readFiles () throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(new File("Accounts.txt")));
        String line = bfr.readLine();
        while (line != null) {
            String[] split = line.split(";");
            String email = split[0];


            if (split[2].equals("C")) {
                Customer currentCustomer = new Customer(email, split[1], "C");

                BufferedReader bfrcart = new BufferedReader(new FileReader(new File(email + "Cart.txt")));
                String cartLine = bfrcart.readLine();

                while (cartLine != null) {
                    String[] cartSplit = cartLine.split(";");
                    int quantity = Integer.parseInt(cartSplit[4]);
                    double price = Double.parseDouble(cartSplit[3]);
                    CartObject currentCartObject = new CartObject(cartSplit[0], cartSplit[1], cartSplit[2], price, quantity);
                    currentCustomer.addToCart(currentCartObject);
                    cartLine = bfrcart.readLine();
                }

                BufferedReader bfrHistory = new BufferedReader(new FileReader(new File(email + "History.txt")));
                String historyLine = bfrHistory.readLine();

                while (historyLine != null) {
                    String[] historySplit = historyLine.split(";");
                    int quantity = Integer.parseInt(historySplit[3]);
                    double price = Double.parseDouble(historySplit[4]);
                    Product currentProduct = new Product(historySplit[0], historySplit[1], historySplit[2], quantity, price);
                    currentCustomer.addToHistory(currentProduct);
                    historyLine = bfrHistory.readLine();
                }

                customers.add(currentCustomer);


            } else if (split[2].equals("S")) {
                Seller currentSeller = new Seller(email, split[1], "S");

                BufferedReader bfr2 = new BufferedReader(new FileReader(new File("Sellers.txt")));

                String storeLine = bfr2.readLine();

                while (storeLine != null) {

                    String[] split2 = storeLine.split(";");
                    if (split2[0].equals(currentSeller.getEmail())) {

                        for (int i = 1; i < split2.length; i++) {
                            Store currentStore = new Store(split2[i]);

                            BufferedReader bfr3 = new BufferedReader(new FileReader(new File(currentStore.getStoreName() + ".txt")));
                            BufferedReader bfr4 = new BufferedReader(new FileReader(new File(currentStore.getStoreName() + "Sold.txt")));
                            BufferedReader bfr5 = new BufferedReader(new FileReader(new File("Sales.txt")));

                            String productLine = bfr3.readLine();

                            while (productLine != null) {
                                String[] split3 = productLine.split(";");
                                int quantity = Integer.parseInt(split3[3]);
                                double price = Double.parseDouble(split3[4]);
                                Product currentProduct = new Product(split3[0], split3[1], split3[2], quantity, price);

                                currentStore.addProduct(currentProduct);

                                productLine = bfr3.readLine();
                            }

                            String soldLine = bfr4.readLine();

                            while (soldLine != null) {
                                String[] split4 = soldLine.split(";");
                                int quantity = Integer.parseInt(split4[3]);
                                double price = Double.parseDouble(split4[4]);
                                Product currentProduct = new Product(split4[0], split4[1], split4[2], quantity, price);

                                currentStore.addSoldProduct(currentProduct);

                                soldLine = bfr4.readLine();
                            }



                            currentSeller.addStore(currentStore);

                            String salesLine = bfr5.readLine();

                            while (salesLine != null) {
                                String[] salesSplit = salesLine.split(";");
                                double price = Double.parseDouble(salesSplit[4]);
                                int quantity = Integer.parseInt(salesSplit[5]);
                                if (salesSplit[0].equals(currentSeller.getEmail()) && salesSplit[2].equals(currentStore.getStoreName())) {
                                    currentSeller.addSales(new Sales(salesSplit[0], salesSplit[1], salesSplit[2], salesSplit[3], price, quantity));
                                }
                                salesLine = bfr5.readLine();
                            }
                        }
                    }

                    storeLine = bfr2.readLine();
                }
                sellers.add(currentSeller);
            }
            line = bfr.readLine();
        }
    }

    public void writeFiles() throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Accounts.txt")));
        PrintWriter pws = new PrintWriter(new BufferedWriter(new FileWriter("sellers.txt")));
        PrintWriter pwSales = new PrintWriter(new BufferedWriter(new FileWriter("Sales.txt")));

        for (int i = 0; i < customers.size(); i++) {
            pw.println(String.format("%s;%s;C", customers.get(i).getEmail(), customers.get(i).getPassword()));

            File f = new File(customers.get(i).getEmail() + "Cart.txt");
            PrintWriter pwCart = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            for (int j = 0; j < customers.get(i).getCart().size(); j++) {
                pwCart.println(customers.get(i).getCart().get(j).toCartString());
            }
            pwCart.flush();
            pwCart.close();

            File g = new File(customers.get(i).getEmail() + "History.txt");
            PrintWriter pwHistory = new PrintWriter(new BufferedWriter(new FileWriter(g)));
            for (int j = 0; j < customers.get(i).getPurchaseHistory().size(); j++) {
                String s = customers.get(i).getPurchaseHistory().get(j).toString();
                pwHistory.println(customers.get(i).getPurchaseHistory().get(j).toString());
            }
            pwHistory.flush();
            pwHistory.close();
        }

        for (int i = 0; i < sellers.size(); i++) {
            pw.println(String.format("%s;%s;S", sellers.get(i).getEmail(), sellers.get(i).getPassword()));

            String sellerOutput = sellers.get(i).getEmail();
            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                sellerOutput += ";" + sellers.get(i).getStores().get(j).getStoreName();
            }
            pws.println(sellerOutput);



            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                String storeName = sellers.get(i).getStores().get(j).getStoreName();
                File f = new File(storeName + ".txt");
                PrintWriter pwStore = new PrintWriter(new BufferedWriter(new FileWriter(f)));

                File g = new File(storeName + "Sold.txt");
                PrintWriter pwSold = new PrintWriter(new BufferedWriter(new FileWriter(g)));

                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    pwStore.println(sellers.get(i).getStores().get(j).getProducts().get(k).toString());
                }
                for (int k = 0; k < sellers.get(i).getStores().get(j).getSoldProducts().size(); k++) {
                    pwSold.println(sellers.get(i).getStores().get(j).getSoldProducts().get(k).toString());
                }
                pwStore.flush();
                pwStore.close();
                pwSold.flush();
                pwSold.close();
            }

            for (int j = 0; j < sellers.get(i).getSales().size(); j++) {
                pwSales.println(sellers.get(i).getSales().get(j).toString());
            }
        }

        pwSales.flush();
        pwSales.close();

        pws.flush();
        pws.close();

        pw.flush();
        pw.close();
    }
}
