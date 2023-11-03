import java.io.*;

/**
 * Project 4
 *
 * Person Class - Holds Account Info for Customers and Sellers
 *
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
    public static boolean isValid(String email) {

        int indexAt = email.indexOf("@");
        int indexDot = email.indexOf(".");

        // There must be a username
        // Username cannot contain "." or "@"
        // There must be something between "@" and "."
        if ((indexAt != 1) && (indexDot - indexAt > 1)) {

            // There must be non-space characters between "@" and "."
            if (!email.substring(indexAt + 1, indexDot - 2).contains(" ")) {
                return true;
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