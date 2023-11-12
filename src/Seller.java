import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Seller extends Person {
    private List<Store> stores;
    private Store currentStore;

    public Seller(String email, String password) throws IOException {
        super(email, password, "S"); // Call the super constructor
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
            pw.append(email + "\n");
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

    public Store switchCurrentStore(String storeName) {
        for (Store store : stores) {
            if (store.getStoreName().equals(storeName)) {
                currentStore = store;
                return store;
            }
        }
        System.out.println("Store not found.");
        return null;
    }

    public void editAccount(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            String oldAccount = super.toString();
            String old = this.toString();
            System.out.println("What would you like to edit?\n[1] Email\n[2] Password\n[3] Exit");
            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        System.out.println("\nEnter your new email:");
                        String newEmail = scanner.nextLine().toLowerCase();
                        String accountOnFile = Person.retrieveAccountInfo(newEmail);
                        if (Person.isValidFormat(newEmail) && (accountOnFile.isEmpty())) {
                            this.setEmail(newEmail);
                            Person.deleteAccount(oldAccount,"Accounts.txt");
                            String newPerson = super.toString();
                            Person.saveAccount(newPerson, "Accounts.txt");
                            Person.deleteAccount(old, "sellers.txt");
                            Person.saveAccount(this.toString(), "sellers.txt");
                            System.out.println("\nYour email has been changed.");
                        } else if (Person.isValidFormat(newEmail))
                            System.out.println("\nThis email is already taken.");
                        else
                            System.out.println("\nYour email isn't in the correct format." +
                                    " No spaces and no semicolons.");
                        break;
                    case 2:
                        System.out.println("\nEnter your current password:");
                        if (scanner.nextLine().equals(this.getPassword())) {
                            System.out.println("\nEnter your new password:");
                            String newPassword = scanner.nextLine();
                            if (!(newPassword.contains(" ")) && !(newPassword.contains(";"))
                                    && !(newPassword.isEmpty())) {
                                this.setPassword(newPassword);
                                Person.deleteAccount(oldAccount, "Accounts.txt");
                                String newPerson = super.toString();
                                Person.saveAccount(newPerson, "Accounts.txt");
                                Person.deleteAccount(old, "sellers.txt");
                                Person.saveAccount(this.toString(), "sellers.txt");
                                System.out.println("Your password has been changed.");
                            } else
                                System.out.println("Your password isn't in the correct format.\n" +
                                        " No spaces and no semicolons.");
                        } else
                            System.out.println("Password is incorrect.");
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Input!");
                        scanner.nextLine();
                }
            } else {
                System.out.println("Invalid Input!");
            }
        }
    }
}