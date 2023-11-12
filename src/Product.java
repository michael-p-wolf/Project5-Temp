import java.util.List;
import java.util.Scanner;

public class Product {
    private String name;
    private String storeSelling;
    private String purchaser;
    private String description;
    private int quantity;
    private double price;
    private int cartQuantity;


    public Product(String name, String storeSelling, String description, int quantity, double price) {
        this.name = name;
        this.storeSelling = storeSelling;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
    public Product(String name, String storeSelling, String description, double price) {
        this.name = name;
        this.storeSelling = storeSelling;
        this.description = description;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStoreSelling(String storeSelling) {
        this.storeSelling = storeSelling;
    }

    public String getStoreSelling() {
        return storeSelling;
    }

    public String toString() {
        return String.format("%s;%s;%s;%d;%.2f", this.name, this.storeSelling, this.description, this.quantity,this.price);
    }

    public void editProduct(Scanner scan) {
        do {
            System.out.println(this.name + "\n[1]Edit Price\n[2]Edit Name\n[3]Edit Quantity\n[4]Edit Description\n[5]Go Back");
            try {
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        this.editPrice(scan);
                        return;
                    case 2:
                        this.editName(scan);
                        return;
                    case 3:
                        this.editQuantity(scan);
                        return;
                    case 4:
                        this.editDescription(scan);
                        return;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid Input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);
    }

    public void editPrice(Scanner scan) {
        do {
            System.out.println("What would you like to change the price to?");
            try {
                double price = Double.parseDouble(scan.nextLine());
                System.out.println("Set price of " + this.name + " to " + price +"?\n[1]Confirm\n[2]Cancel");
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        this.setPrice(price);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);
    }

    public void editName(Scanner scan) {
        do {
            System.out.println("What would you like to change the name to?");
            try {
                String name = scan.nextLine();
                System.out.println("Set name of " + this.name + " to " + name +"?\n[1]Confirm\n[2]Cancel");
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        this.setName(name);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);
    }

    public void editQuantity(Scanner scan) {
        do {
            System.out.println("What would you like to change the quantity to?");
            try {
                int quantity = Integer.parseInt(scan.nextLine());
                System.out.println("Set quantity of " + this.name + " to " + quantity +"?\n[1]Confirm\n[2]Cancel");
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        this.setQuantity(quantity);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);
    }

    public void editDescription(Scanner scan) {
        do {
            System.out.println("What would you like to change the Description to?");
            try {
                String description = scan.nextLine();
                System.out.println("Set description of " + this.name + " to " + description +"?\n[1]Confirm\n[2]Cancel");
                int input = Integer.parseInt(scan.nextLine());
                switch (input) {
                    case 1:
                        this.setDescription(description);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        } while (true);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product p = (Product) obj;
            return p.getName().equals(this.name);
        } else {
            return false;
        }
    }

    public void updateProduct (Product update) {
        this.name = update.getName();
        this.storeSelling = update.storeSelling;
        this.description = update.description;
        this.quantity = update.quantity;
        this.price = update.price;
    }
}
