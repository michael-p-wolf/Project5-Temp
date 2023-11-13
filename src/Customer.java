import java.io.*;
import java.util.*;
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
                    System.out.printf("Purchase %d of %s for $%.2f?\n[1]Confirm\n[2]Cancel",quantity, p.getName(), totalCost);
                    try {
                        int input = Integer.parseInt(scan.nextLine());
                        switch (input) {
                            case 1:
                                p.setQuantity(p.getQuantity()-quantity);
                                this.purchaseHistory.add(p);
                                store.getSoldProducts().add(p);
                                seller.getSales().add(new Sales(seller.getEmail(), this.getEmail(), store.getStoreName(), p.getName(), p.getPrice(), quantity));
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
                    System.out.printf("Add %d of %s for $%.2f to cart?\n[1]Confirm\n[2]Cancel",quantity, p.getName(), totalCost);
                    try {
                        int input = Integer.parseInt(scan.nextLine());
                        switch (input) {
                            case 1:
                                p.setQuantity(p.getQuantity()-quantity);
                                this.cart.add(new CartObject(p.getName(),p.getStoreSelling(),p.getDescription(),p.getPrice(),quantity));
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
                cart.toString(),purchaseHistory.toString(), stores.toString());
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
            for (int i = 0; i < sellers.size(); i++) {
                for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                        System.out.println("[" + (productCount + 2) + "]" + sellers.get(i).getStores().get(j).getProducts().get(k).getName() + "\nStore:" + sellers.get(i).getStores().get(j).getStoreName() + "\nPrice: " + sellers.get(i).getStores().get(j).getProducts().get(k).getPrice());
                        productCount++;
                    }
                }
            }
            if (productCount != 0) {
                System.out.println("[1]Go Back");
                try {
                    int input = Integer.parseInt(scan.nextLine());
                    if (input == 1) {
                        return;
                    } else if (input <= productCount + 2) {
                        productCount = 0;
                        for (int i = 0; i < sellers.size(); i++) {
                            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                                    if (productCount == input - 2) {
                                        this.productPage(scan, sellers.get(i).getStores().get(j).getProducts().get(k), sellers.get(i), sellers.get(i).getStores().get(j));
                                        return;
                                    }
                                    productCount++;
                                }
                            }
                        }
                    } else {
                        System.out.println("Invalid input!");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                }
            }
            else {
                System.out.println("No items are available currently.");
                return;
            }

        } while (true);
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
                        addToCart(product,scan);
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
        int resultsCount = 0;
        for (int i = 0; i < sellers.size(); i++) {
            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    System.out.println("[" + (resultsCount + 2) + "]" + sellers.get(i).getStores().get(j).getProducts().get(k).getName() + "\nStore:" + sellers.get(i).getStores().get(j).getStoreName() + "\nPrice: " + sellers.get(i).getStores().get(j).getProducts().get(k).getPrice());
                    resultsCount++;
                }
            }
        }
        if (resultsCount != 0) {
            System.out.println("What would you like to search for?");
            String search = scan.nextLine();
            System.out.println("[1]Go Back");
            for (int i = 0; i < sellers.size(); i++) {
                for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                        String name = sellers.get(i).getStores().get(j).getProducts().get(k).getName();
                        String description = sellers.get(i).getStores().get(j).getProducts().get(k).getDescription();
                        String storeName = sellers.get(i).getStores().get(j).getStoreName();
                        if (name.contains(search) || description.contains(search) || storeName.contains(search)) {
                            System.out.println("[" + (resultsCount + 2) + "]" + name);
                            resultsCount++;
                        }
                    }
                }
            }
            try {
                int input = Integer.parseInt(scan.nextLine());
                if (input == 1) {
                    return;
                } else if (input <= resultsCount + 2) {
                    resultsCount = 0;
                    for (int i = 0; i < sellers.size(); i++) {
                        for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                            for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                                if (resultsCount == input - 2) {
                                    this.productPage(scan, sellers.get(i).getStores().get(j).getProducts().get(k), sellers.get(i), sellers.get(i).getStores().get(j));
                                    break;
                                }
                                resultsCount++;
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } else {
            System.out.println("No products are available currently");
            return;
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
                System.out.println("[" + (index + 1) + "]" + cart.get(i).getName() + "\nQuantity: " + cart.get(i).getCartQuantity() + "\nTotal Cost: " + totalPrice);
            }
            int totalInput = index+cart.size() + 2;
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
                    System.out.println("Purchase entire cart?\n[1]Confirm\n[2]Cancel");try {
                        int input2 = Integer.parseInt(scan.nextLine());
                        switch (input2) {
                            case 1:
                                this.purchaseCart();
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
                    this.cartProductPage(scan, this.cart.get(input),sellers);
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
        for (int i = 0; i < sellers.size(); i++) {
            for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    String productName = sellers.get(i).getStores().get(j).getProducts().get(k).getName();
                    String storeSelling = sellers.get(i).getStores().get(j).getProducts().get(k).getStoreSelling();
                    if (o.getName().equals(productName) && o.getStoreSelling().equals(storeSelling)) {
                        sellers.get(i).getStores().get(j).getProducts().get(k).setQuantity(sellers.get(i).getStores().get(j).getProducts().get(k).getQuantity() + o.getCartQuantity());
                    }
                }
            }
        }
    }
    public void emptyCart(ArrayList<Seller> sellers) {
        for (int i = 0; i < this.cart.size(); i++) {
            this.removeFromCart(this.cart.get(i),sellers);
        }
    }
    public void purchaseCart() {
        for (int i = 0; i < this.cart.size(); i++) {
            this.purchaseHistory.add(this.cart.get(i));
        }
        this.cart = new ArrayList<CartObject>();
    }
    public void purchaseFromCart(CartObject o) {
        this.purchaseHistory.add(new Product(o.getName(), o.getStoreSelling(), o.getDescription(), o.cartQuantity, o.getPrice()));
        this.cart.remove(o);
    }
    public void cartProductPage (Scanner scan, CartObject o, ArrayList<Seller> sellers) {
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
                                    purchaseFromCart(o);
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

    public void printHistory (Scanner scan, ArrayList<Seller> sellers) {
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
        switch(input) {
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
                                        ArrayList<Sales> talliedSales = this.salesByStore(sellers);
                                        Collections.sort(talliedSales);
                                        for (int i = 0; i < talliedSales.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\nTotal Revenue: %.2f",
                                                    talliedSales.get(i).getStoreName(), talliedSales.get(i).getSellerEmail(),
                                                    talliedSales.get(i).getQuantity(),talliedSales.get(i).getProductPrice());
                                        }
                                        System.out.println("Press ENTER to continue.\n");
                                        scan.nextLine();
                                        return;
                                    case 2:
                                        talliedSales = this.salesByStore(sellers);
                                        Collections.sort(talliedSales, Collections.reverseOrder());
                                        for (int i = 0; i < talliedSales.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\nTotal Revenue: %.2f",
                                                    talliedSales.get(i).getStoreName(), talliedSales.get(i).getSellerEmail(),
                                                    talliedSales.get(i).getQuantity(),talliedSales.get(i).getProductPrice());
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
                    case 2:
                        do {
                            System.out.println("How would you like to sort stores (personal)?\n[1]Ascending\n[2]Descending\n[3]Cancel");
                            try {
                                int input2 = Integer.parseInt(scan.nextLine());
                                switch (input2) {
                                    case 1:
                                        ArrayList<Sales> talliedSales = this.personalSalesByStore();
                                        Collections.sort(talliedSales);
                                        for (int i = 0; i < talliedSales.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\nTotal Revenue: %.2f",
                                                    talliedSales.get(i).getStoreName(), talliedSales.get(i).getSellerEmail(),
                                                    talliedSales.get(i).getQuantity(),talliedSales.get(i).getProductPrice());
                                        }
                                        System.out.println("Press ENTER to continue.\n");
                                        scan.nextLine();
                                        return;
                                    case 2:
                                        talliedSales = this.personalSalesByStore();
                                        Collections.sort(talliedSales, Collections.reverseOrder());
                                        for (int i = 0; i < talliedSales.size(); i++) {
                                            System.out.printf("Store: %s\nOwner: %s\nTotal Quantity: %d\nTotal Revenue: %.2f",
                                                    talliedSales.get(i).getStoreName(), talliedSales.get(i).getSellerEmail(),
                                                    talliedSales.get(i).getQuantity(),talliedSales.get(i).getProductPrice());
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

    public ArrayList<Sales> salesByStore (ArrayList<Seller> sellers) {
        int totalSales = 0;
        double totalRevenue = 0;
        String currentSellerName = "";
        String currentStoreName ="";
        ArrayList<Sales> talliedSales = new ArrayList<Sales>();
        for (int i = 0; i < sellers.size(); i++) {
            currentSellerName = sellers.get(i).getEmail();
            for (int j = 0; j < sellers.get(i).getSales().size(); j++) {
                totalSales += sellers.get(i).getSales().get(j).getQuantity();
                totalRevenue += (sellers.get(i).getSales().get(j).getQuantity() * sellers.get(i).getSales().get(j).getProductPrice());
                currentStoreName = sellers.get(i).getStores().get(j).getStoreName();
            }
            talliedSales.add(new Sales(currentSellerName, currentStoreName, totalRevenue, totalSales));
        }
        return talliedSales;
    }

    public ArrayList<Sales> personalSalesByStore () {
        ArrayList<Sales> talliedSales = new ArrayList<Sales>();
        ArrayList<String> storeName = new ArrayList<String>();
        int totalQuantity = 0;
        double totalRevenue = 0;
        String currentSellerName = "";
        String currentStoreName ="";
        for (int i = 0; i < this.purchaseHistory.size(); i++) {
            if (storeName.indexOf(this.purchaseHistory.get(i).getName()) == -1) {
                storeName.add(this.purchaseHistory.get(i).getName());
            }
        }
        for (int i = 0; i < storeName.size(); i++) {
            currentStoreName = storeName.get(i);
            for (int j = 0; j < this.purchaseHistory.size(); j++) {
                if (storeName.get(i).equals(this.purchaseHistory.get(j).getStoreSelling())) {
                    totalQuantity += this.purchaseHistory.get(j).getQuantity();
                    totalRevenue += this.purchaseHistory.get(j).getPrice() * this.purchaseHistory.get(j).getQuantity();
                }
                talliedSales.add(new Sales("", currentStoreName,totalRevenue, totalQuantity));
            }
        }
        return talliedSales;
    }
}
