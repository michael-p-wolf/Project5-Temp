import java.util.ArrayList;
import java.util.List;

public class Seller extends Person {
    private List<Store> stores;

    public Seller(String email, String password) {
        super(email, password, "S"); // Call the super constructor
        this.stores = new ArrayList<>();
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
