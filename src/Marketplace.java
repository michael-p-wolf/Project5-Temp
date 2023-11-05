import java.util.ArrayList;
import java.util.Scanner;

public class Marketplace {
    private ArrayList<Seller> sellers;
    //private ArrayList<Customer> customers;
    public static final String WELCOME = "Hello!\n[1] Sign In\n[2] Create Account\n[3] Exit";




    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int input = 0;
        int check = 0;
        String email = "";
        String password = "";

        do {
            System.out.println(WELCOME);
            input = scan.nextInt();
            scan.nextLine();
            if (input == 1) {
                System.out.println("Email:");
                email = scan.nextLine();
                System.out.println("Password:");
                password = scan.nextLine();
            } else if (input == 2) {
                System.out.println("[1] Create Seller Account\n[2] Create Buyer Account\n[3] Back");
                input = scan.nextInt();
                scan.nextLine();

                if (input == 1) {

                } else if (input == 2) {

                } else if (input == 3) {

                } else {
                    input = 0;
                }

            } else if (input == 3){
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Error! Invalid input!");
                input = 0;
            }
        } while (input == 0);
    }
}