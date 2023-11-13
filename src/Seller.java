import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Seller extends Person {
    private ArrayList<Store> stores;
    private Store currentStore;
    private ArrayList<Sales> sales;
    //Constructor for a seller object
    public Seller(String email, String password, String accountType) {
        super(email, password, accountType);
        this.stores = new ArrayList<Store>();
        this.sales = new ArrayList<Sales>();
    }
    public ArrayList<Store> getStores() {
        return this.stores;
    }
    //returns the string representation of a seller
    public String toString() {
        String toWrite = this.getEmail();
        for (int i = 0; i < this.stores.size(); i++) {
            toWrite += (";" + stores.get(i).getStoreName());
        }
        return toWrite;
    }
    //Allows the seller to create a new store
    public void createStore(Scanner scan, ArrayList<Seller> sellers) {
        boolean exists = false;
        do {
            System.out.println("Enter Store Name:");
            String storeName = scan.nextLine();
            for (int i = 0; i < sellers.size(); i++) {
                for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    if (sellers.get(i).getStores().get(j).getStoreName().equals(storeName)) {
                        System.out.println("A store with this name already exists!");
                        exists = true;
                    }
                }
            }
            if (!exists) {
                do {
                    System.out.println("Create new store: " + storeName + "?\n[1]Confirm\n[2]Cancel");
                    try{
                        int input = Integer.parseInt(scan.nextLine());
                        switch (input) {
                            case 1:
                                this.stores.add(new Store(storeName));
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid input!");
                        }
                    } catch (Exception e){
                        System.out.println("Invalid input!");
                        return;
                    }
                } while (true);

            }

        } while (true);
    }
    //The next four methods allow the user to add and remove stores and sales
    public void removeStore(Store store) {
        this.stores.remove(store);
    }

    public void addStore (Store store) {
        this.stores.add(store);
    }

    public void addSales (Sales sale) {
        this.sales.add(sale);
    }

    public ArrayList<Sales> getSales() {
        return sales;
    }
    //Updates the seller based on changes that were made
    public void updateSeller(Seller update) {
        this.setEmail(update.getEmail());
        this.setPassword(update.getPassword());
        for (int i = 0; i < this.stores.size(); i++) {
            this.stores.get(i).updateStore(update.getStores().get(i));
        }

    }
    //allows the seller to view a dashboard of sales
    public void salesDashboard (Scanner scan, int input) {
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getSellerEmail().equals(this.getEmail()) && sales.get(i).getStoreName().equals(stores.get(input).getStoreName())) {
                String storeName = sales.get(i).getStoreName();
                String customerEmail = sales.get(i).getCustomerEmail();
                String productName = sales.get(i).getProductName();
                double price = sales.get(i).getProductPrice();
                int quantity = sales.get(i).getQuantity();
                double revenue = price * quantity;
                int num = i;

                System.out.printf("Sale #" + (num+1) + " for " + storeName +
                        ":\nCustomer: %s\nProduct: %s\nPrice: %f\nQuantity: %d\nTotal Revenue: %f\nPress Enter to go back.",customerEmail, productName, price, quantity, revenue);

            }
        }
        System.out.println("Press ENTER to go back");
        scan.nextLine();
    }
    //Allows the seller to create a new product
    public void createProduct(Scanner scan, Store store) {
        System.out.println("Enter Product Name:");
        String name = scan.nextLine();
        System.out.println("Enter Product Description:");
        String description = scan.nextLine();
        double price;
        int quantity;
        try {
            System.out.println("Enter Product Price:");
            price = Double.parseDouble(scan.nextLine());
            System.out.println("Enter Quantity Available:");
            quantity = Integer.parseInt(scan.nextLine());
            System.out.println("Create new Product: " + name + "?\n[1]Confirm\n[2]Cancel");
            int input = Integer.parseInt(scan.nextLine());
            switch (input) {
                case 1:
                    store.addProduct(new Product(name, store.getStoreName(), description, quantity, price));
                case 2:
                    return;
                default:
                    System.out.println("Invalid Input");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }
    }
    //Allows the seller to delete a store
    public void deleteStore (Scanner scan, Store store) {
        System.out.println("Are you sure you want to delete: " + store.getStoreName() + "?\n[1]Confirm\n[2]Cancel");
        try {
            int input = Integer.parseInt(scan.nextLine());
            switch (input) {
                case 1:
                    this.stores.remove(store);
                case 2:
                    return;
                case 3:
                    System.out.println("Invalid input! Store was not deleted");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Store was not deleted");
        }

    }
    //Allows the seller to view a dashboard of sales based on the number of sales per product or customer
    public void dashboard(Scanner scan) {
        ArrayList<Sales> sales = this.sales;
        do {
            try {
                System.out.println("Which dashboard would you like to view?\n[1]Number of items per " +
                        "customer\n[2]Number of sales per product\n[3]Cancel");
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        boolean again = true;
                        do {
                            System.out.println("How would you like to sort customers?\n[1]Ascending\n[2]Descending\n[3]Cancel");
                            try {
                                int input2 = Integer.parseInt(scan.nextLine());
                                ArrayList<Sales> salesList = new ArrayList<Sales>();
                                ArrayList<String> customerEmails = new ArrayList<String>();
                                for (int i = 0; i < sales.size(); i++) {
                                    if (!customerEmails.contains(sales.get(i).getCustomerEmail())) {
                                        customerEmails.add(sales.get(i).getCustomerEmail());
                                    }
                                }
                                int total = 0;
                                for (int i = 0; i < customerEmails.size(); i++) {
                                    for (int j = 0; j < sales.size(); j++) {
                                        if (sales.get(j).getCustomerEmail().equals(customerEmails.get(i))) {
                                            total += sales.get(j).getQuantity();
                                        }
                                    }
                                    salesList.add(new Sales(customerEmails.get(i), total));
                                }
                                boolean somethingToPrint = false;
                                switch (input2) {
                                    case 1:
                                        Collections.sort(salesList);
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getCustomerEmail() + salesList.get(i).getQuantity());
                                            somethingToPrint = true;
                                        }
                                        if (!somethingToPrint) {
                                            System.out.println("No customers have purchased your items yet.");
                                        }
                                        System.out.println("Press ENTER to go back.");
                                        scan.nextLine();
                                        break;
                                    case 2:
                                        Collections.sort(salesList, Collections.reverseOrder());
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getCustomerEmail() + salesList.get(i).getQuantity());
                                            somethingToPrint = true;
                                        }
                                        if (!somethingToPrint) {
                                            System.out.println("No customers have purchased your items yet.");
                                        }
                                        System.out.println("Press ENTER to go back.");
                                        scan.nextLine();
                                        break;
                                    case 3:
                                        again = false;
                                        break;
                                    default:
                                        System.out.println("Invalid Input!");
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid Input!");
                            }
                        } while (again);
                        break;
                    case 2:
                        again = true;
                        do {
                            System.out.println("How would you like to sort product?\n[1]Ascending\n[2]Descending\n[3]Cancel");
                            try {
                                int input2 = Integer.parseInt(scan.nextLine());
                                ArrayList<Sales> salesList = new ArrayList<>();
                                ArrayList<String> productNames = new ArrayList<>();

                                for (int i = 0; i < sales.size(); i++) {
                                    if (!productNames.contains(sales.get(i).getProductName())) {
                                        productNames.add(sales.get(i).getProductName());
                                    }
                                }

                                for (int i = 0; i < productNames.size(); i++) {

                                    int total = 0;
                                    for (Sales sale : sales) {
                                        if (sale.getProductName().equals(productNames.get(i))) {
                                            total += sale.getQuantity();
                                        }
                                    }
                                    Product dummyProduct = new Product(productNames.get(i), "", "", 0, 0);
                                    salesList.add(new Sales(this.getEmail(),total, dummyProduct));

                                }
                                boolean somethingToPrint = false;
                                switch (input2) {
                                    case 1:
                                        Collections.sort(salesList);
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getProductName() + salesList.get(i).getQuantity());
                                            somethingToPrint = true;
                                        }
                                        if (!somethingToPrint) {
                                            System.out.println("No customers have purchased your products yet.");
                                        }
                                        System.out.println("Press ENTER to go back.");
                                        scan.nextLine();
                                        break;
                                    case 2:
                                        Collections.sort(salesList, Collections.reverseOrder());
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getProductName() + salesList.get(i).getQuantity());
                                            somethingToPrint = true;
                                        }
                                        if (!somethingToPrint) {
                                            System.out.println("No customers have purchased your products yet.");
                                        }
                                        System.out.println("Press ENTER to go back.");
                                        scan.nextLine();
                                        break;
                                    case 3:
                                        again = false;
                                        break;
                                    default:
                                        System.out.println("Invalid Input!");
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid Input!");
                            }
                        } while (again);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);

    }
    //Allows the user to view the number of items currently in customer shopping carts
    public void shoppingCart(Scanner scan, ArrayList<Customer> customers) {
        System.out.println("Number of products currently in customers shopping carts");
        String output = "";
        int totalQuantity;
        for (Store store : stores) {
            for (Product p : store.getProducts()) {
                totalQuantity = 0;
                for (Customer customer : customers) {
                    for (CartObject cart : customer.getCart()) {
                        if (cart.getName().equals(p.getName())) {
                            totalQuantity += cart.getCartQuantity();
                        }
                    }

                }
                output += "\n" + p.getName() + "\nTotal in cart: " + totalQuantity + "\n";
            }

        }
        System.out.println(output + "Press ENTER to return");
        String input = scan.nextLine();

    }
    //Allows the user to view and interact with all of their stores
    public void storeInterface(Scanner scan) {
        do {
            System.out.println("[1]Go Back\n-Stores:");
            if (!this.stores.isEmpty()) {
                ArrayList<Store> stores = this.stores;
                for (int i = 0; i < stores.size(); i++) {
                    int num = i;
                    System.out.println("[" + (num + 2) + "]" + stores.get(i).getStoreName());
                }
            }
            else System.out.println("You currently have no stores.\nCreate one first");
            try {
                int input = Integer.parseInt(scan.nextLine());
                if (!(input <= stores.size()+2)) {
                    System.out.println("Invalid input!");
                } else if (input == 1){
                    return;
                } else {
                    do {
                        String productNames = "";
                        for (int i = 0; i < stores.get(input-2).getProducts().size(); i++) {
                            int num2 = i;
                            productNames += "[" + (num2+5) + "]" + stores.get(input-2).getProducts().get(i).getName() + "\n";
                        }
                        System.out.println(stores.get(input-2).getStoreName() + "\n[1]Go Back\n[2]Sales\n[3]Create Product\n" +
                                "[4]Delete Store\n" + productNames);
                        try {
                            int input2 = Integer.parseInt(scan.nextLine());
                            if (input2 == 1) {
                                break;
                            } else if (input2 == 2) {
                                salesDashboard(scan, input-2);
                            } else if(input2 == 3) {
                                createProduct(scan, stores.get(input-2));
                                break;
                            } else if (input2 == 4) {
                                deleteStore(scan, stores.get(input-2));
                            } else if (input2 <= (stores.get(input-2).getProducts().size() + 5)) {
                                do {
                                    System.out.println("Store: " + stores.get(input-2).getStoreName() + "\nProduct: " + stores.get(input-2).getProducts().get(input2 - 5).getName()
                                            + "\nDescription: " + stores.get(input-2).getProducts().get(input2 - 5).getDescription()
                                            + "\nQuantity: " + stores.get(input-2).getProducts().get(input2 - 5).getQuantity()
                                            + "\nPrice: " + stores.get(input-2).getProducts().get(input2 - 5).getPrice()
                                            +"\n[1]Edit Product\n[2]Delete Product\n[3]Go Back");
                                    try {
                                        int input3 = Integer.parseInt(scan.nextLine());
                                        switch (input3) {
                                            case 1:
                                                stores.get(input-2).getProducts().get(input2 - 5).editProduct(scan);
                                                break;
                                            case 2:
                                                stores.get(input-2).removeProduct(scan, stores.get(input-2).getProducts().get(input2 - 5));
                                                break;
                                            case 3:
                                                break;
                                            default:
                                                System.out.println("Invalid Input!");
                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Invalid input!");
                                        e.printStackTrace();
                                    }
                                } while (true);

                            } else {
                                System.out.println("Invalid input");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            e.printStackTrace();
                        }
                    } while (true);
                }

            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);

    }

    //Allows the user to delete their account

    // Imports products from a file to the seller's stores
    // Must be in the format shown in CSVGuide.txt
    public void importProducts(Scanner scan) {
        System.out.println("Enter the filename to import products from:");
        String filename = scan.nextLine();
        File file = new File(filename);
        try {
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader bfr = new BufferedReader(fr);

                ArrayList<String> fileContents = new ArrayList<>();
                String line = bfr.readLine();

                while (line != null) {
                    if (line.split(",", -1).length != 3) {
                        System.out.println("This file is not formatted correctly.");
                        bfr.close();
                        return;
                    }
                    fileContents.add(line);
                    line = bfr.readLine();
                }
                for (int i = 0; i < this.getStores().size(); i++) {
                    Store currentStore = this.getStores().get(i);
                    for (int j = 0; j < fileContents.size(); j++) {
                        String currentLine = fileContents.get(j);
                        String fileStoreName = currentLine.split(",", -1)[0];
                        String fileProductName = currentLine.split(",", -1)[1];
                        int fileQuantity = Integer.parseInt(currentLine.split(",", -1)[2]);
                        if (currentStore.getStoreName().equals(fileStoreName)) {
                            for (int k = 0; k < currentStore.getProducts().size(); k++) {
                                Product currentProduct = currentStore.getProducts().get(k);
                                if (currentProduct.getName().equals(fileProductName)) {
                                    currentProduct.setQuantity(currentProduct.getQuantity() + fileQuantity);
                                }
                            }
                        }
                    }
                }
                System.out.println("Products imported from " + filename);
                bfr.close();
            } else {
                System.out.println("The file could not be located");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error has occured.\nCheck your file formatting.");
        }
    }

    // Exports products from the seller's stores to a file
    public void exportProducts(Scanner scan) {
        System.out.println("Enter the filename to export products to:");
        String filename = scan.nextLine();
        File file = new File(filename);
        try {
            if (!file.exists()) {
                ArrayList<Product> sellersProducts = new ArrayList<>();
                ArrayList<Product> chosenProducts = new ArrayList<>();
                ArrayList<Integer> numToRemove = new ArrayList<>();
                System.out.println("Your Products:");

                // Add all of user's products to arraylist
                for (int i = 0; i < this.getStores().size(); i++) {
                    Store currentStore = this.getStores().get(i);
                    for (int j = 0; j < currentStore.getProducts().size(); j++) {
                        Product currentProduct = currentStore.getProducts().get(j);;
                        sellersProducts.add(currentProduct);
                    }
                }
                if (sellersProducts.size() > 0) {
                    while (true) {
                        String output = "";
                        for (int i = 0; i < sellersProducts.size(); i++) {
                            output += String.format("\n[%d]%s", i + 3, sellersProducts.get(i).getName());
                        }
                        System.out.println("Select the product you'd like to export:\n[1]Exit\n[2]Export" +
                                " to File" + output);
                        int input = Integer.parseInt(scan.nextLine());
                        if (input == 1)
                            return;
                        else if (input == 2) {
                            if (chosenProducts.isEmpty()) {
                                System.out.println("Please select some products first.");
                            } else {
                                break;
                            }
                        } else {
                            // Tracks product quantity without changing product quantity
                            Product selectedProduct = sellersProducts.get(input - 3);
                            int productQuantity = selectedProduct.getQuantity();
                            for (int i = 0; i < chosenProducts.size(); i++) {
                                if (chosenProducts.get(i).equals(selectedProduct)) {
                                    productQuantity -= numToRemove.get(i);
                                }
                            }
                            System.out.println("How many of this product do you want to export?\nCurrent " +
                                    "Quantity: " + productQuantity);
                            int toRemove = Integer.parseInt(scan.nextLine());
                            if (productQuantity >= toRemove) {
                                chosenProducts.add(selectedProduct);
                                numToRemove.add(toRemove);
                            } else {
                                System.out.println("You cannot export more than your current stock");
                            }
                        }
                    }
                }
                else {
                    System.out.println("You currently have no products.");
                    return;
                }
                FileOutputStream fos = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(fos);
                for (int i = 0; i < chosenProducts.size(); i++) {
                    Product currentProduct = chosenProducts.get(i);
                    int productQuantity = currentProduct.getQuantity();
                    System.out.println(productQuantity);
                    boolean gate = false;
                    for (int j = 0; j < chosenProducts.size(); j++) {
                        if (chosenProducts.get(i).equals(currentProduct)) {
                            gate = true;
                        }
                    }
                    if (gate) {
                        productQuantity -= numToRemove.get(i);
                    }
                    currentProduct.setQuantity(productQuantity);
                    pw.println(String.format("%s,%s,%d", currentProduct.getStoreSelling(), currentProduct.getName(),
                            numToRemove.get(i)));
                }
                System.out.println("Products exported to " + filename);
                pw.close();
            } else {
                System.out.println("This file already exists.");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error has occurred.\nCheck your file formatting.");
        }
    }

    public void deleteAccount(Scanner scan, ArrayList<Seller> sellers) {
        do {
            System.out.println("Enter Password: ");
            String pass = scan.nextLine();
            if (!pass.equals(this.getPassword())) {
                System.out.println("Incorrect password!");
                break;
            }
            System.out.println("Are you sure you want to delete your account? This action cannot be undone.\n[1]Confirm\n[2]Cancel");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        for (int i = 0; i < this.getStores().size(); i++) {
                            for (int j = 0; j < this.stores.get(i).getProducts().size(); j++) {
                                this.stores.get(i).getProducts().remove(this.stores.get(i).getProducts().get(j));

                            }
                        }
                        for (int i = 0; i < this.getStores().size(); i++) {
                            removeStore(this.getStores().get(i));
                        }
                        sellers.remove(this);
                        return;
                    case 2:
                        break;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }
}