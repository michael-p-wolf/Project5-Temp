import java.io.*;

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

        return String.format("%s,%s,%s", this.email, this.password, this.accountType);
    }

    // Checks if email is in valid format
    // Valid Format: no spaces, one "@", name and domain exist, and exactly one "." not at beginning/end of domain
    public static boolean isValid(String email) {

        // Checks if there are no spaces
        if (!(email.contains(" "))) {

            // Checks if there is exactly one "@"
            if ((email.split("@", -1).length == 2)) {

                // Checks if name and domain exist
                if (((email.split("@", -1)[0]) != "") && ((email.split("@", -1)[1]) != "")) {

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

    // Checks if email is already in Accounts.txt
    public static boolean isDuplicate(String email) {

        try {
            File file = new File("Accounts.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                String filedEmail = line.split(",", 0)[0];
                if (filedEmail.equals(email)) {
                    bfr.close();
                    return true;
                }
                line = bfr.readLine();
            }

            bfr.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Adds the information of a Person object to Accounts.txt
    public void writeAccountToFile() {

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
}