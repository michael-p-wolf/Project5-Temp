import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Seller extends Person {
    private List<Store> stores;

    public Seller(String email, String password) throws IOException {
        super(email, password, "Seller"); // Call the super constructor
        this.stores = new ArrayList<>();
        File sellerFile = new File("sellers.txt");
        PrintWriter pw = new PrintWriter(new FileOutputStream(sellerFile,true));
        BufferedReader bfr = new BufferedReader(new FileReader("sellers.txt"));

        String line = bfr.readLine();
        boolean random = false;
        while(line != null) {
            String[] split = line.split(";");
            if(split[0].equals(super.getEmail())) {
                random = true;
                break;
            }
            line = bfr.readLine();
        }
        if(random == false) {
            pw.append("\n" + email);
        }
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

    public String toString() {

        String toWrite = this.getEmail();
        for (int i = 0; i < this.stores.size(); i++) {
            toWrite += (";" + stores.get(i).getStoreName());
        }
        return toWrite;
    }

    /**
    public void createStore(String storeName) throws IOException {
        Store newStore = new Store(storeName, this.getEmail());
        String oldPerson = this.toString();
        stores.add(newStore);
        Person.deleteAccount(oldPerson, "sellers.txt");
        Person.saveAccount(this.toString(), "sellers.txt");
    }
     **/


    public void createStore(String storeName) throws IOException {
        Store newStore = new Store(storeName, this.getEmail());
        stores.add(newStore);

        // Read all existing data
        List<String> lines = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("sellers.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] split = line.split(";");
                if (split.length > 0 && split[0].equals(super.getEmail())) {
                    // Append the new store name to the existing line
                    line += ";" + newStore.getStoreName();
                }
                lines.add(line);
                line = bfr.readLine();
            }
        }

        // Write the updated data back to the file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("sellers.txt", false))) {
            for (String line : lines) {
                pw.println(line);
            }
        }
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
