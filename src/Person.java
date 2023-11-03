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
}
