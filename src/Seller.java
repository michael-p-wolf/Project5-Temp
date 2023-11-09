import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Seller extends Person {
    private List<Store> stores;

    public Seller(String email, String password) {
        super(email, password, "S"); // Call the super constructor
        File file = new File("Accounts.txt");

        if (file.exists()) {
            try (BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"))) {
                String line = bfr.readLine();
                while (line != null) {
                    String[] info = line.split(";");
                    // if this user is the user we are creating
                    if (info[2].equals("S") && info[0].equals(email)) {
                        // but account doesn't hold any of the info so what do i do
                    }
                    line = bfr.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.stores = new ArrayList<>();
        }
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

    public void createStore(String storeName, String fileName) {
        Store newStore = new Store(storeName, this.getEmail(), fileName);
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
