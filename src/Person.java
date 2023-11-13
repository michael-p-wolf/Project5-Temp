import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
/**
 * Boilermaker Bazaar Bonanza
 * <p>
 * Holds the email, password, and account type information for all users 
 * that have created an account. This info is stored in Accounts.txt and is 
 * used to identify returning accounts and to create new accounts. 
 *
 * @author Michael Wolf, Lab Sec 36
 * @author Pranay Nandkeolyar, Lab Sec 36
 * @author Jacob Stamper, Lab Sec 36
 * @author Benjamin Emini, Lab Sec 36
 * @author Simrat Thind, Lab Sec 36
 * @version November 13th, 2023
 **/
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

    public static boolean isValidFormat(String email) {

        // Checks if there are no spaces or semicolons (will break system if they do)
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
    public static boolean invalidPassword (String pass) {
        if ((pass.contains(" ")) || (pass.contains(";")) || (pass.isEmpty())) {
            return true;
        }
        return false;
    }
    public void editAccount(Scanner scan, ArrayList<Seller> sellers, ArrayList<Customer> customers) {
        do {
            System.out.println("What would you like to edit?\n[1]Email\n[2]Password\n[3]Exit");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        System.out.println("Enter your new email:");
                        String email = scan.nextLine();
                        if (accountOnFile(email, sellers, customers)) {
                            System.out.println("This email is already associated with an account!");
                            break;
                        } else if (!Person.isValidFormat(email)) {
                            System.out.println("\nYour email isn't in the correct format." +
                                    " No spaces and no semicolons.");
                            break;
                        } else {

                            ArrayList<String> lines = new ArrayList<>();
                            try (BufferedReader bfr = new BufferedReader(new FileReader("Sales.txt"))) {
                                String line = bfr.readLine();
                                while (line != null) {
                                    lines.add(line);
                                    line = bfr.readLine();
                                }
                            }
                            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Sales.txt")))) {
                                for (String line : lines) {
                                    String[] data = line.split(";");
                                    if (this instanceof Customer && data[1].equals(this.getEmail())) {
                                        data[1] = email;
                                        line = Arrays.toString(data);
                                        line = line.substring(1, line.length() - 1);
                                        line = line.replaceAll(", ", ";");
                                    } else if (this instanceof Seller && data[1].equals(this.getEmail())) {
                                        data[0] = email;
                                        line = data.toString();
                                        line = line.substring(1, line.length() - 1);
                                        line = line.replaceAll(", ", ";");
                                    }
                                    pw.println(line);
                                }
                            }
                            for (Seller seller : sellers) {
                                for (Sales s : seller.getSales()) {
                                    if (s.getCustomerEmail().equals(this.getEmail())) {
                                        s.setCustomerEmail(email);
                                    }
                                }
                            }
                            this.email = email;
                            return;
                        }
                    case 2:
                        System.out.println("Enter your new password:");
                        String pass = scan.nextLine();
                        if (invalidPassword(pass)) {
                            System.out.println("Incorrect password format!\nNo spaces or semicolons.");
                            break;
                        }
                        System.out.println("Confirm new password:");
                        String confirmPass = scan.nextLine();

                        if (pass.equals(confirmPass)) {
                            this.password = pass;
                            return;
                        }
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        } while (true);
    }
    public static boolean accountOnFile (String email, ArrayList<Seller> sellers, ArrayList<Customer> customers) {
        for (int i = 0; i < sellers.size(); i++) {
            if (email.equals(sellers.get(i).getEmail())) {
                return true;
            }
        }
        for (int i = 0; i < customers.size(); i++) {
            if (email.equals(customers.get(i).getEmail())) {
                return true;
            }
        }
        return false;
    }
}
