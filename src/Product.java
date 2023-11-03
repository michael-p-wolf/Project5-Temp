public class Product {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String filename;

    public Product(String name, String description, int quantity, double price, String filename) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.filename = filename;
    }

    public String toString() {
        return String.format("%s,%s,%d,%f", this.name, this.description, this.price,this.quantity);
    }

}
