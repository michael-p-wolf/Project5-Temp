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
    public static final String CREATE_ACCOUNT_SCREEN = "Create Account:\n[1] Customer Account\n[2] Seller " +
            "Account\n[3] Go Back";


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
    // and at least one "." in domain, but none can be at beginning/end of domain
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

                        // Checks if at least one "." in domain
                        if (domain.split("\\.", -1).length > 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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

    // Adds the account info to Accounts.txt
    // Use this.toString() to add a Person object's info
    public static void saveAccount(String accountInfo, String fileName) {

        try {
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(accountInfo);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Removes the account's info from Accounts.txt
    // Use this.toString() to remove a Person object's account info
    public static void deleteAccount(String accountToRemove, String fileName) {

        ArrayList<String> allAccountInfo = new ArrayList<>();

        try {
            // Gets all info in Accounts.txt except account of "this"
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (!(line.equals(accountToRemove)))
                    allAccountInfo.add(line);
                line = bfr.readLine();
            }

            // Prints all info in Accounts.txt except account of "this"
            FileOutputStream fos = new FileOutputStream(file, false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < allAccountInfo.size(); i++) {
                pw.println(allAccountInfo.get(i));
            }
            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runs at the start of program
    // Allows user to log in to an existing account or create a new one


    public static Person login(String email, String pass) {
        do {
            String accountType = "";
            String accountOnFile = "";
            // Log In
            // If email is on file, proceed
            accountOnFile = Person.retrieveAccountInfo(email);
            if (!accountOnFile.isEmpty()) {
                // If password matches account info, proceed
                if (accountOnFile.split(";", -1)[1].equals(pass)) {
                    return new Person(accountOnFile.split(";", -1)[0],
                            accountOnFile.split(";", -1)[1],
                            accountOnFile.split(";", -1)[2]);
                } else {
                    System.out.println("Your password is incorrect.");
                    return null;
                }
            } else {
                System.out.println("This email isn't associated to any account.");
                return null;
            }

        } while (true);
    }

    public static Person createAccount(Scanner scanner) {
        String accountType = "";
        String accountOnFile = "";
        String inputString = "";
        String input2String = "";
        int input = 0;
        int input2 = 0;
        String email = "";
        String pass = "";
        String confirmPass = "";

        System.out.println(CREATE_ACCOUNT_SCREEN);
        try {
            inputString = scanner.nextLine();
            input = Integer.parseInt(inputString);

            if (input == 1 || input == 2) {
                System.out.println("Email:");
                email = scanner.nextLine();
                accountOnFile = Person.retrieveAccountInfo(email);
                if (Person.isValidFormat(email) && (accountOnFile.isEmpty())) {
                    // If input is empty, go back
                    // If password is in valid format, proceed
                    System.out.println("Password:");
                    pass = scanner.nextLine();
                    if (!(pass.contains(" ")) && !(pass.contains(";")) && !(pass.isEmpty())) {
                        System.out.println("Confirm Password:");
                        confirmPass = scanner.nextLine();
                        if (pass.equals(confirmPass)) {
                            switch (input) {
                                case 1:
                                    System.out.printf("Create customer account with email: %s?\n[1] " +
                                            "Confirm\n[2] Cancel\n", email);
                                    try {
                                        input2String = scanner.nextLine();
                                        input2 = Integer.parseInt(input2String);
                                        switch (input2) {
                                            case 1:
                                                accountType = "C";
                                                Person person = new Person(email.toLowerCase(), pass, accountType);
                                                Person.saveAccount(person.toString(), "Accounts.txt");
                                                return person;
                                            case 2:
                                                return null;
                                            default:
                                                System.out.println("Invalid Input");
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Invalid Input!");
                                    }

                                case 2:
                                    System.out.printf("Create seller account with email: %s?\n[1] " +
                                            "Confirm\n[2] Cancel\n", email);
                                    try {
                                        input2String = scanner.nextLine();
                                        input2 = Integer.parseInt(input2String);
                                        switch (input2) {
                                            case 1:
                                                accountType = "S";
                                                Person person = new Person(email.toLowerCase(), pass, accountType);
                                                Person.saveAccount(person.toString(), "Accounts.txt");
                                                return person;
                                            case 2:
                                                return null;
                                            default:
                                                System.out.println("Invalid Input");
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Invalid Input!");
                                    }
                            }
                        }
                        // If input is empty, go back
                        // If account type is input correctly, proceed

                    } else {
                        System.out.println("Your password isn't in the correct format.\n" +
                                "No spaces and no semicolons.");
                    }

                } else if (Person.isValidFormat(email)) {
                    System.out.println("This email is already associated with an account.");
                } else {
                    System.out.println("Your email isn't in the correct format.\n" +
                            "No spaces and no semicolons, must contain '@' and '.'");
                }
            } else if (input == 3) {
                return null;
            } else {
                System.out.println("Invalid Input!");
            }
        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
        return null;
    }
}