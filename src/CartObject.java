/**
 * Boilermaker Bazaar Bonanza
 * <p>
 * CartObject is a small subclass of the product class that handles the shopping cart
 * for customers. Its constructor has all the attributes of a product as well as
 * how many are being added to the cart.
 *
 * @author Michael Wolf, Lab Sec 36
 * @author Pranay Nandkeolyar, Lab Sec 36
 * @author Jacob Stamper, Lab Sec 36
 * @author Benjamin Emini, Lab Sec 36
 * @author Simrat Thind, Lab Sec 36
 * @version November 13th, 2023
 **/

public class CartObject extends Product {
    int cartQuantity;

    public CartObject(String name, String storeSelling, String description, double price, int cartQuantity) {
        super(name, storeSelling, description, price);
        this.cartQuantity = cartQuantity;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public String toCartString() {
        return String.format("%s;%s;%s;%.2f;%d", super.getName(), super.getStoreSelling(), super.getDescription(), super.getPrice(), this.cartQuantity);
    }
}
