import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer extends Person {
    private ArrayList<Product> cart;
    private ArrayList<Product> purchaseHistory;

    private ArrayList<Store> stores;

    public Customer(String email, String password, int customerID) {
        super(email, password, "Customer");
        this.cart = new ArrayList<>();
        this.purchaseHistory = new ArrayList<>();
        this.stores = stores;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(ArrayList<Product> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void addToCart(Product p) {
        this.cart.add(p);
    }

    public void removeFromCart(Product p) {
        try {
            cart.remove(p);
        } catch (Exception e) {
            System.out.println("This product is not in the cart");
        }
    }

    public void buy(Product p) {
        purchaseHistory.add(p);
    }
    // check



    // buy method
}
