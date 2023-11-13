public class Sales implements Comparable<Sales> {
    private String sellerEmail;
    private String customerEmail;
    private String storeName;
    private String productName;
    private double productPrice;
    private int quantity;

    private Product product;

    public Sales(String sellerEmail, String customerEmail, String storeName, String productName, double productPrice, int quantity) {
        this.sellerEmail = sellerEmail;
        this.customerEmail = customerEmail;
        this.storeName = storeName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public Sales(String sellerEmail, String storeName, double productPrice, int quantity) {
        this.sellerEmail = sellerEmail;
        this.storeName = storeName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public int compareTo(Sales sales) {
            return Double.compare(this.quantity, sales.quantity);
    }

    public Sales(String customerEmail,int quantity, Product product) {
        this.customerEmail = customerEmail;
        this.quantity = quantity;
        this.product = product;
    }

    public Sales(String customerEmail,int quantity) {
        this.customerEmail = customerEmail;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;

    }
    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString () {
        return String.format("%s;%s;%s;%s;%f;%d",this.sellerEmail, this.customerEmail, this.storeName, this.productName, this.productPrice, this.quantity);
    }
}
