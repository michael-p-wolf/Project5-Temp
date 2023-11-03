import java.util.List;

public class Product {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String filename;
    private List<Store> storesSelling;

    public Product(String name, String description, int quantity, double price, String filename) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public String toString() {
        return String.format("%s,%s,%d,%f", this.name, this.description, this.price,this.quantity);
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
}
