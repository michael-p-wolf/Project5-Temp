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
            return String.format("%s;%s;%s;%.2f;%d", super.getName(), super.getStoreSelling(), super.getDescription(), super.getPrice(),this.cartQuantity);
    }
}
