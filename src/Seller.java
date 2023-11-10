import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Seller extends Person {
    private List<Store> stores;
    private Store currentStore;

    public Seller(String email, String password) throws IOException {
        super(email, password, "Seller"); // Call the super constructor
        this.stores = new ArrayList<>();
        File sellerFile = new File("sellers.txt");
        PrintWriter pw = new PrintWriter(new FileOutputStream(sellerFile, true));
        BufferedReader bfr = new BufferedReader(new FileReader("sellers.txt"));

        String line = bfr.readLine();
        boolean random = false;
        while (line != null) {
            String[] split = line.split(";");
            if (split[0].equals(super.getEmail())) {
                random = true;
                break;
            }
            line = bfr.readLine();
        }
        if (!random) {
            pw.append("\n" + email);
        }
        pw.flush();
        pw.close();
    }

    public List<Store> getStores() {
        return stores;
    }

    public String toString() {
        String toWrite = this.getEmail();
        for (int i = 0; i < this.stores.size(); i++) {
            toWrite += (";" + stores.get(i).getStoreName());
        }
        return toWrite;
    }

    public void createStore(String storeName) throws IOException {
        // Check if the store name is already taken
        if (isStoreNameExists(storeName)) {
            System.out.println("Store name already taken. Choose a different name.");
            return;
        }

        // Create a new store
        Store newStore = new Store(storeName, this.getEmail());
        stores.add(newStore);

        // Read all existing data
        List<String> sellerLines = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("sellers.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] split = line.split(";");
                if (split.length > 0 && split[0].equals(super.getEmail())) {
                    // Append the new store name to the existing line
                    line += ";" + newStore.getStoreName();
                }
                sellerLines.add(line);
                line = bfr.readLine();
            }
        }

        // Write the updated data back to the sellers.txt file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("sellers.txt", false))) {
            for (String line : sellerLines) {
                pw.println(line);
            }
        }
    }

    public void removeStore(String storeName) {
        // Check if the store name exists before attempting to remove
        if (!isStoreNameExists(storeName)) {
            System.out.println("Store not found. Check the store name and try again.");
            return;
        }

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

        // Update the seller's data in the sellers.txt file
        String sellerDataFileName = "sellers.txt";
        List<String> sellerLines = new ArrayList<>();

        try (BufferedReader bfr = new BufferedReader(new FileReader(sellerDataFileName))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] split = line.split(";");
                if (split.length > 0 && split[0].equals(getEmail())) {
                    // Remove the store name from the existing line
                    StringBuilder updatedLine = new StringBuilder();
                    for (int i = 0; i < split.length; i++) {
                        if (!split[i].equals(storeName)) {
                            updatedLine.append(split[i]);
                            if (i < split.length - 1) {
                                updatedLine.append(";");
                            }
                        }
                    }
                    line = updatedLine.toString();
                }
                sellerLines.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated data back to the sellers.txt file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(sellerDataFileName, false))) {
            for (String line : sellerLines) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isStoreNameExists(String storeName) {
        for (String line : getExistingStoreLines()) {
            if (line.equals(storeName)) {
                // Store name exists
                return true;
            }
        }
        return false;
    }

    private List<String> getExistingStoreLines() {
        List<String> existingStoreLines = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("store.txt"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                existingStoreLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingStoreLines;
    }

    public void switchCurrentStore(String storeName) {
        for (Store store : stores) {
            if (store.getStoreName().equals(storeName)) {
                currentStore = store;
                return;
            }
        }
        System.out.println("Store not found.");
    }
}
