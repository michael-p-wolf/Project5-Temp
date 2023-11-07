import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Project 4
 * Person Class - Holds Account Info for Customers and Sellers
 *
 * @author Jacob Stamper L36
 * @version 11/2/23
 */

public class Person {

    private String email;
    private String password;
    private final String accountType;

    public Person(String email, String password, String accountType) {

        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public Person(Person person) {

        this.email = person.email;
        this.password = person.password;
        this.accountType = person.accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getEmail(), person.getEmail());
    }

    public String getEmail() {

        return email;
    }

    public String getPassword() {

        return password;
    }

    public String getAccountType() {

        return accountType;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String toString() {

        return String.format("%s;%s;%s", this.email, this.password, this.accountType);
    }

    // Checks if email is in valid format
    // Valid Format: no spaces, no semicolons, one "@", name and domain exist,
    // and exactly one "." in domain that cannot at beginning/end of domain
    public static boolean isValidFormat(String email) {

        // Checks if there are no semicolons (will break system if they do)
        if (!(email.contains(" ")) && !(email.contains(";"))) {

            // Checks if there is exactly one "@"
            if ((email.split("@", -1).length == 2)) {

                // Checks if name and domain exist
                if ((!(email.split("@", -1)[0]).isEmpty()) &&
                        (!(email.split("@", -1)[1]).isEmpty())) {

                    String domain = email.split("@", -1)[1];

                    // Checks if "." is at the beginning or end of email
                    if (!(domain.charAt(0) == '.') && !(domain.charAt(domain.length() - 1) == '.')) {

                        // Checks if only one "." in domain
                        if (domain.split("\\.", -1).length == 2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Adds the information of the calling Person object to Accounts.txt
    public void saveAccount() {

        try {
            File file = new File("Accounts.txt");
            FileOutputStream fos = new FileOutputStream(file, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    // Removes the Person object that called the method from Accounts.txt
    public void deleteAccount() {

        ArrayList<String> accountInfo = new ArrayList<>();
        String accountToRemove = this.toString();

        try {
            // Gets all info in Accounts.txt except account of "this"
            File file = new File("Accounts.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (!(line.equals(accountToRemove)))
                    accountInfo.add(line);
                line = bfr.readLine();
            }

            // Prints all info in Accounts.txt except account of "this"
            FileOutputStream fos = new FileOutputStream(file, false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < accountInfo.size(); i++) {
                pw.println(accountInfo.get(i));
            }
            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Updates the Account.txt file with updated account info
    // Used in login sequence; don't use otherwise
    // Instead use deleteAccount and saveAccount
    public void updateAccount(String oldAccount) {

        ArrayList<String> accountInfo = new ArrayList<>();

        try {
            // Gets all info in Accounts.txt except account of "this"
            File file = new File("Accounts.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(oldAccount)) {
                    accountInfo.add(this.toString());
                }
                else {
                    accountInfo.add(line);
                }
                line = bfr.readLine();
            }

            // Prints all info in Accounts.txt except account of "this"
            FileOutputStream fos = new FileOutputStream(file, false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < accountInfo.size(); i++) {
                pw.println(accountInfo.get(i));
            }
            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieves the account info for a specific email
    // Returns an empty string if the account is not registered
    public static String retrieveAccountInfo(String email) {

        try {
            File file = new File("Accounts.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                String filedEmail = line.split(";", 0)[0];
                if (filedEmail.equals(email)) {
                    bfr.close();
                    return line;
                }
                line = bfr.readLine();
            }
            bfr.close();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    // Allows the user to change the email and password of their own account
    public void editAccount(Scanner scanner) {

        boolean exit = false;
        while (!exit) {
            String oldAccount = this.toString();
            System.out.println("\nWhat would you like to edit?\n[1] Email\n[2] Password\n[3] Exit");
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
                            this.updateAccount(oldAccount);
                            System.out.println("\nYour email has been changed.");
                        } else if (Person.isValidFormat(newEmail))
                            System.out.println("\nThis email is already taken.");
                        else
                            System.out.println("\nYour password isn't in the correct format." +
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
                                this.updateAccount(oldAccount);
                                System.out.println("\nYour password has been changed.");
                            }
                            else
                                System.out.println("\nYour password isn't in the correct format.\n" +
                                        " No spaces and no semicolons.");
                        }
                        else
                            System.out.println("\nPassword is incorrect.");
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("\nInvalid Input");
                        scanner.nextLine();
                }
            }
        }
    }

    // Runs at the start of program
    // Allows user to log in to an existing account or create a new one
    public static Person loginSequence(Scanner scanner) {

        String email = "";
        String password = "";
        String accountType = "";
        String accountOnFile = "";

        while (true) {
            System.out.println("\nWould you like to:\n[1] Log In\n[2] Create Account");
            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        // Log In
                        // If email is on file, proceed
                        while (true) {
                            System.out.println("\nType \"back\" to go back.\nEnter your email:");
                            email = scanner.nextLine().toLowerCase();
                            if (email.equalsIgnoreCase("back"))
                                break;
                            accountOnFile = Person.retrieveAccountInfo(email);
                            if (!accountOnFile.isEmpty()) {
                                // If password matches account info, proceed
                                System.out.println("\nEnter your password:");
                                password = scanner.nextLine();
                                if (accountOnFile.split(";", -1)[1].equals(password)) {
                                    return new Person(accountOnFile.split(";", -1)[0],
                                            accountOnFile.split(";", -1)[1],
                                            accountOnFile.split(";", -1)[2]);
                                } else
                                    System.out.println("\nYour password is incorrect.");
                            } else
                                System.out.println("\nThis email isn't associated to any account.");
                        }
                        break;

                    case 2:
                        // Create Account
                        // If input is empty, go back
                        // If email is in valid format and is not registered, proceed
                        while (true) {
                            System.out.println("\nType \"back\" to go back.\nEnter your email:");
                            email = scanner.nextLine().toLowerCase();
                            if (email.equalsIgnoreCase("back"))
                                break;
                            accountOnFile = Person.retrieveAccountInfo(email);
                            if (Person.isValidFormat(email) && (accountOnFile.isEmpty())) {
                                // If input is empty, go back
                                // If password is in valid format, proceed
                                System.out.println("\nEnter your password:");
                                password = scanner.nextLine();
                                if (!(password.contains(" ")) && !(password.contains(";")) && !(password.isEmpty())) {
                                    // If input is empty, go back
                                    // If account type is input correctly, proceed
                                    System.out.println("\nEnter your account type:\n[1] Customer\n[2] Seller");
                                    if (scanner.hasNextInt()) {
                                        switch (scanner.nextInt()) {
                                            case 1:
                                                accountType = "C";
                                                break;
                                            case 2:
                                                accountType = "S";
                                                break;
                                            default:
                                        }
                                    }
                                    scanner.nextLine();
                                    if (accountType.equals("C") || accountType.equals("S")) {
                                        Person person = new Person(email.toLowerCase(), password, accountType);
                                        person.saveAccount();
                                        return person;
                                    } else
                                        System.out.println("\nInvalid Input");
                                } else
                                    System.out.println("\nYour password isn't in the correct format.\n" +
                                            "No spaces and no semicolons.");
                            } else if (Person.isValidFormat(email))
                                System.out.println("\nThis email is already associated with an account.");
                            else
                                System.out.println("\nYour email isn't in the correct format.\n" +
                                        "No spaces and no semicolons.");
                        }
                        break;
                    default:
                        System.out.println("\nInvalid Input");
                }
            } else {
                System.out.println("\nInvalid Input");
                scanner.nextLine();
            }
        }
    }
}