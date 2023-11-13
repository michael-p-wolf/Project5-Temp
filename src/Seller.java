import java.io.*;
import java.util.*;

public class Seller extends Person {
    private ArrayList<Store> stores;
    private Store currentStore;
    private ArrayList<Sales> sales;

    public Seller(String email, String password, String accountType) {
        super(email, password, accountType);
        this.stores = new ArrayList<Store>();
        this.sales = new ArrayList<Sales>();
    }
    public ArrayList<Store> getStores() {
        return this.stores;
    }

    public String toString() {
        String toWrite = this.getEmail();
        for (int i = 0; i < this.stores.size(); i++) {
            toWrite += (";" + stores.get(i).getStoreName());
        }
        return toWrite;
    }
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

    public void updateSeller(Seller update) {
        this.setEmail(update.getEmail());
        this.setPassword(update.getPassword());
        for (int i = 0; i < this.stores.size(); i++) {
            this.stores.get(i).updateStore(update.getStores().get(i));
        }

    }

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

                    System.out.printf("Sale #" + (num+2) + " for " + storeName +
                            ":\nCustomer: %s\nProduct: %s\nPrice: %f\nQuantity: %d\nTotal Revenue: %f\nPress Enter to go back.",customerEmail, productName, price, quantity, revenue);

                }
            }
            System.out.println("Press ENTER to go back");
            scan.nextLine();
    }

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
//Sellers can view a dashboard that lists statistics for each of their stores.
//Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales.
//Sellers can choose to sort the dashboard.
    public void dashboard(Scanner scan) {
        ArrayList<Sales> sales = this.sales;
        do {
            try {
                System.out.println("Which dashboard would you like to view?\n[1]Number of items per customer\n[2]Number of sales per product");
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
                                switch (input2) {
                                    case 1:
                                        Collections.sort(salesList);
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getCustomerEmail() + salesList.get(i).getQuantity());
                                        }
                                        System.out.println("Press ENTER to go back.");
                                        scan.nextLine();
                                        break;
                                    case 2:
                                        Collections.sort(salesList, Collections.reverseOrder());
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getCustomerEmail() + salesList.get(i).getQuantity());
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
                                ArrayList<Sales> salesList = new ArrayList<Sales>();
                                ArrayList<String> productNames = new ArrayList<String>();
                                for (int i = 0; i < sales.size(); i++) {
                                    if (!productNames.contains(sales.get(i).getProductName())) {
                                        productNames.add(sales.get(i).getProductName());
                                    }
                                }
                                int total = 0;
                                for (int i = 0; i < productNames.size(); i++) {
                                    for (int j = 0; j < sales.size(); j++) {
                                        if (sales.get(j).getProductName().equals(productNames.get(i))) {
                                            total += sales.get(j).getQuantity();
                                        }
                                    }
                                    salesList.add(new Sales(total, productNames.get(i)));
                                }
                                switch (input2) {
                                    case 1:
                                        Collections.sort(salesList);
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getProductName() + salesList.get(i).getQuantity());
                                        }
                                        System.out.println("Press ENTER to go back.");
                                        scan.nextLine();
                                        break;
                                    case 2:
                                        Collections.sort(salesList, Collections.reverseOrder());
                                        for (int i = 0; i < salesList.size(); i++) {
                                            System.out.println(salesList.get(i).getProductName() + salesList.get(i).getQuantity());
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
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);

    }

    public void shoppingCart(Scanner scan, ArrayList<Customer> customers) {
        System.out.println("Number of products currently in shopping customers shopping carts");
        String output = "";
        int totalQuantity;
        for (int i = 0; i < this.stores.size(); i++) {
            for (int j = 0; j < this.stores.get(i).getProducts().size(); j++) {
                totalQuantity = 0;
                for (int k = 0; k < customers.size(); k++) {
                    for (int l = 0; l < customers.get(k).getCart().size(); l++) {
                        if (this.stores.get(i).getProducts().get(j).equals(customers.get(k).getCart().get(l))) {
                            totalQuantity += customers.get(k).getCart().get(l).getQuantity();
                        }
                    }

                }
                output += "\n" + this.stores.get(i).getProducts().get(j).getName() + "\nTotal in cart: " + totalQuantity + "\n";
            }

        }
        System.out.println(output + "Press ENTER to return");
    }
    public void storeInterface(Scanner scan) {
        do {
            System.out.println("[1]Go Back");
            ArrayList<Store> stores = this.stores;
            for (int i = 0; i < stores.size(); i++) {
                int num = i;
                System.out.println("[" + (num+2) + "]" + stores.get(i).getStoreName());
            }
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

    public void deleteAccount(Scanner scan, ArrayList<Seller> sellers) {
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
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);


    }
}


