import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

<<<<<<< HEAD
        int input = 0;
        String email = "";
        String password = "";

        do {
            System.out.println("Hello!\n[1] Sign In\n[2] Create Account\n[3] Exit");
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
                    do {
                        System.out.println("Email:");
                        email = scan.nextLine();
                        System.out.println("Password:");
                        password = scan.nextLine();
                        System.out.println("Confirm Password:");
                        if (!scan.nextLine().equals(password)) {
                            System.out.println("Error! PasswordS must match!");
                        }
                    } while ()

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