import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Seller extends Person {
    private List<Store> stores;

    public Seller(String email, String password) throws IOException {
        super(email, password, "Seller"); // Call the super constructor
        this.stores = new ArrayList<>();
        File sellerFile = new File("sellers.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(sellerFile)));
        pw.write(email + ";");
        pw.flush();
        pw.close();
    }

    public List<Store> getStores() {
        return stores;
    }

    public void addStore(Store store) {
        stores.add(store);
    }

    public void removeStore(Store store) {
        stores.remove(store);
    }

    public void addProduct(Store store, Product product) {
        store.addProduct(product);
    }

    public void removeProduct(Store store, Product product) {
        store.removeProduct(product);
    }

    public void createStore(String storeName) throws IOException {
        Store newStore = new Store(storeName, this.getEmail());
        File sellerFile = new File("sellers.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(sellerFile)));
        BufferedReader bfr = new BufferedReader(new FileReader(sellerFile));
        String line = bfr.readLine();
        while(line != null) {
            String[] split = line.split(";", 0);
            if(split[0].equals(super.getEmail())) {
                pw.write(newStore.getStoreName() + ";");
                break;
            }
            line = bfr.readLine();
        }
        pw.flush();
        pw.close();
        stores.add(newStore);
    }

    public void removeStore(String storeName) {
        Store storeToRemove = null;
        for (Store store : stores) {
            if (store.getStoreName().equals(storeName)) {
                storeToRemove = store;
                break;
            }
        }
        if (storeToRemove != null) {
            stores.remove(storeToRemove);
        }
    }

    public Store findStore(String storeName) {
        for (Store store : stores) {
            if (store.getStoreName().equals(storeName)) {
                return store;
            }
        }
        return null;
    }
}
