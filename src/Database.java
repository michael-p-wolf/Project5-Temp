import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;

public class Database {

    private static ArrayList<Seller> sellers = new ArrayList<Seller>();
    private static ArrayList<Customer> customers = new ArrayList<Customer>();

    public Database() {
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
                    int quantity = Integer.parseInt(cartSplit[3]);
                    double price = Double.parseDouble(cartSplit[4]);
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

                            String productLine = bfr3.readLine();

                            while (productLine != null) {
                                String[] split3 = productLine.split(";");
                                int quantity = Integer.parseInt(split3[3]);
                                double price = Double.parseDouble(split3[4]);
                                Product currentProduct = new Product(split3[0], split3[1], split3[2], quantity, price);

                                currentStore.addProduct(currentProduct);

                                productLine = bfr3.readLine();
                            }
                            currentSeller.addStore(currentStore);
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

        for (int i = 0; i < customers.size(); i++) {
            pw.println(String.format("%s;%s;C", customers.get(i).getEmail(), customers.get(i).getPassword()));

            File f = new File(customers.get(i).getEmail() + "Cart.txt");
            PrintWriter pwCart = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            for (int j = 0; j < customers.get(i).getCart().size(); j++) {
                pwCart.println(customers.get(i).getCart().get(j).toString());
            }
            pwCart.flush();
            pwCart.close();

            File g = new File(customers.get(i).getEmail() + "History.txt");
            PrintWriter pwHistory = new PrintWriter(new BufferedWriter(new FileWriter(g)));
            for (int j = 0; j < customers.get(i).getPurchaseHistory().size(); j++) {
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
                for (int k = 0; k < sellers.get(i).getStores().get(j).getProducts().size(); k++) {
                    pwStore.println(sellers.get(i).getStores().get(j).getProducts().get(k).toString());
                }
                pwStore.flush();
                pwStore.close();
            }
        }

        pws.flush();
        pws.close();

        pw.flush();
        pw.close();
    }
}
