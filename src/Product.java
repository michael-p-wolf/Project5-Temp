import java.util.List;

public class Product {
    private String name;
    private String storeSelling;
    private String description;
    private int quantity;
    private double price;


    public Product(String name, String storeSelling, String description, int quantity, double price) {
        this.name = name;
        this.storeSelling = storeSelling;
        this.description = description;
        this.quantity = quantity;
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
        return String.format("%s,%s,%d,%f", this.name, this.description, this.price,this.quantity);
    }

}
