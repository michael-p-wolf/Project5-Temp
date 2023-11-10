import javax.lang.model.type.ArrayType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Marketplace {
    private static ArrayList<Seller> sellers;
    private static ArrayList<Customer> customers;
    public static final String WELCOME = "Home Screen\n[1] Sign In\n[2] Create Account\n[3] Exit";
    public static final String CREATE_ACCOUNT_SCREEN = "Create Account:\n[1]Customer Account\n[2]Seller Account\n[3]Go Back";
    public static final String CUSTOMER_HOME = "[1]Marketplace\n[2]Edit Account\n[3]Search For Product\n[4]Store Dashboard\n[5]Shopping Cart\n[6]Purchase History\n[7]Delete Account\n[8]Sign Out";
    public static final String SELLER_HOME = "Seller Home Screen\n[1]Create Store\n[2]Edit Account\n[3]Access Store List\n[4]Dashboard\n[5]Shopping Cart\n[6]Sign Out\n";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int input = 0;
        int check = 0;
        String email = "";
        String password = "";

        do {
            switch (firstScreen(scan)) {
                case 1:
                    loginScreen(scan);
                    break;


                case 2:
                    try {
                        createAccountScreen(scan);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid input!");
            }
        } while (true);
    }

    public static int firstScreen(Scanner scan) {
        System.out.println(WELCOME);
        String inputString = scan.nextLine();
        try {
            int input = Integer.parseInt(inputString);
            return input;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void loginScreen(Scanner scan) {
        do {
            System.out.println("Email:");
            String email = scan.nextLine();
            System.out.println("Password:");
            String pass = scan.nextLine();
            System.out.printf("Login with email: %s and password: %s?\n[1]Confirm\n[2]Cancel\n", email, pass);
            String inputString = scan.nextLine();
            try {
                int input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        try {
                            Person activeUser = Person.login(email, pass);
                            if (activeUser.getAccountType().equals("C")) {
                                Customer activeCustomer = new Customer(activeUser.getEmail(),activeUser.getPassword());
                                customerHome(scan, activeCustomer);
                            } else if (activeUser.getAccountType().equals("S")) {
                                Seller activeSeller = new Seller(activeUser.getEmail(),activeUser.getPassword());
                                sellerHome(scan, activeSeller);
                            } else if (activeUser == null){/*Login failed*/
                                System.out.println("Login failed!\n[1]Try Again\n[2]Exit");
                                String input2String = scan.nextLine();
                                try {
                                    int input2 = Integer.parseInt(input2String);
                                    switch (input2) {
                                        case 1: break;
                                        case 2: return;
                                        default:
                                            System.out.println("Invalid Input!");
                                            return;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid input!");
                                    return;
                                }

                            }
                        } catch (NullPointerException e) {
                            return;
                        }

                    case 2:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                e.printStackTrace();
                return;
            }
        } while (true);

    }


    public static void createAccountScreen (Scanner scan) throws IOException {
        Person currentUser = Person.createAccount(scan);
        if (currentUser != null) {
            if (currentUser.getAccountType().equals("C")) {
                Customer currentCustomer = new Customer(currentUser.getEmail(),currentUser.getPassword());
                customerHome(scan, currentCustomer);
            } else if (currentUser.getAccountType().equals("S")) {
                Seller currentSeller = new Seller(currentUser.getEmail(),currentUser.getPassword());
                sellerHome(scan, currentSeller);
            }
        }

    }
    public static void customerHome(Scanner scan, Customer customer) {
        int input = 0;
        do {
            System.out.println(customer.getEmail() + " Customer Homepage\n" + CUSTOMER_HOME);
            try {
                String inputString = scan.nextLine();
                input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        ;
                    case 2:
                        customer.editAccount(scan);
                        break;
                    case 3:
                        ;
                    case 4:
                        ;
                    case 5:
                        ;
                    case 6:
                        ;
                    case 7:
                        //How do I call the person toString for a customer?
                        //customer.deleteAccount(customer.toString());
                    case 8:
                        return;
                    default: System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
                e.printStackTrace();
            }

        } while (true);
    }

    public static void sellerHome(Scanner scan, Seller seller) {
        int input = 0;
        do {
            System.out.println(SELLER_HOME);
            try {
                String inputString = scan.nextLine();
                input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        String createStoreName = scan.nextLine();
                        seller.createStore(createStoreName);
                        break;
                    case 2:
                        String removeStoreName = scan.nextLine();
                        seller.removeStore(removeStoreName);
                        break;
                    case 3:
                        ;
                    case 4:
                        ;
                    case 5:
                        ;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }

        } while (true);
    }
}