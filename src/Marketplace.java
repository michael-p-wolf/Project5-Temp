import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Scanner;
public class Marketplace {
    private static ArrayList<Seller> sellers;
    private static ArrayList<Customer> customers;
    public static final String WELCOME = "Home Screen\n[1] Sign In\n[2] Create Account\n[3] Exit";
    public static final String CREATE_ACCOUNT_SCREEN = "Create Account:\n[1]Customer Account\n[2]Seller Account\n[3]Go Back";
    public static final String CUSTOMER_HOME = "Customer Home Screen\n[1]Marketplace\n[2]Edit Account\n[3]Search For Product\n[4]Store Dashboard\n[5]Shopping Cart\n[6]Purchase History\n[7]Sign Out\n";
    public static final String SELLER_HOME = "Seller Home Screen\n[1]Create Store\n[2]Edit Account\n[2]Access Store List\n[4]Dashboard\n[5]Shopping Cart\n[6]Sign Out\n";

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
                    createAccountScreen(scan);
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
                        if (email.equals("Customer")/*Account Type = Customer */) {
                            //customerHome(scan, customer);
                        } else if (pass.equals("Seller")/*Account Type = Seller */) {
                            //run seller home
                        } else {/*Login failed*/
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
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);

    }

    public static void createAccountScreen (Scanner scan) {
        int input = 0;
        int input2 = 0;
        String email;
        String pass;
        do {
            System.out.println(CREATE_ACCOUNT_SCREEN);
            try {
                String inputString = scan.nextLine();
                input = Integer.parseInt(inputString);
                if (input == 1 || input == 2) {
                    System.out.println("Email:");
                    email = scan.nextLine();
                    if (!Person.isValid(email)) {
                        System.out.println("Invalid Email!");
                        break;
                    } else if (Person.isDuplicate(email)) {
                        System.out.println("Email is already in use!");
                        break;
                    }
                    System.out.println("Password:");
                    pass = scan.nextLine();
                    System.out.println("Confirm Password:");
                    if (scan.nextLine().equals(pass)) {
                        String input2String;
                        switch (input) {
                            case 1:
                                System.out.printf("Create customer account with email: %s?\n[1]Confirm\n[2]Cancel\n", email);
                                input2String = scan.nextLine();
                                try {
                                    input2 = Integer.parseInt(input2String);
                                    switch (input2) {
                                        case 1:
                                            System.out.println("Success! Account created");
                                            customers.add(new Customer(email,pass));
                                            //sign in with the email and password
                                            //customerHome(scan, customer^);
                                        case 2: return;
                                        default:
                                            System.out.println("Invalid Input!");
                                            break;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid Input!");
                                }
                            case 2:
                                System.out.printf("Create seller account with email: %s?\n[1]Confirm\n[2]Cancel\n", email);
                                input2String = scan.nextLine();
                                try {
                                    input2 = Integer.parseInt(input2String);
                                    switch (input2) {
                                        case 1:
                                            System.out.println("Success! Account created");
                                            //sellers.add(new Seller(email,pass));
                                            //sign in with the email and password
                                            //sellerHome(scan, seller^);
                                        case 2: return;
                                        default:
                                            System.out.println("Invalid Input!");
                                            break;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Invalid Input!");
                                }
                        }


                    } else {
                        System.out.println("Passwords did not match!");
                        break;
                    }
                } else if (input ==3) {

                } else {
                    System.out.println("Invalid Input!");
                }


                        //Create Seller Account
                        System.out.println("Email:");
                        email = scan.nextLine();
                        if (!Person.isValid(email)) {
                            System.out.println("Invalid Email!");
                            break;
                        }
                        System.out.println("Password:");
                        pass = scan.nextLine();
                        System.out.println("Confirm Password:");
                        if (scan.nextLine().equals(pass)) {
                            System.out.printf("Create seller account with email: %s?\n[1]Confirm\n[2]Cancel\n", email);
                            String input2String = scan.nextLine();
                            try {
                                input2 = Integer.parseInt(input2String);
                                switch (input2) {
                                    case 1:
                                        System.out.println("Success! Account created");
                                        //sellers.add(new Seller(email,pass));
                                        //sign in with the email and password
                                        //sellerHome(scan, seller^);
                                    case 2: return;
                                    default:
                                        System.out.println("Invalid Input!");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid Input!");
                            }

                        } else {
                            System.out.println("Passwords did not match!");
                        }




            } catch (Exception e){
                System.out.println("Invalid Input!");
            }
        } while (true);

    }
    public static void customerHome(Scanner scan, Customer customer) {
        int input = 0;
        do {
            System.out.println(CUSTOMER_HOME);
            try {
                String inputString = scan.nextLine();
                input = Integer.parseInt(inputString);
                switch (input) {
                    case 1:
                        ;
                    case 2:
                        ;
                    case 3:
                        ;
                    case 4:
                        ;
                    case 5:
                        ;
                    case 6:
                        ;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
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
                        ;
                    case 2:
                        ;
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