import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String storeName;
    private Seller seller;
    private String filename;
    private List<Product> products; // keeping tracks fo products
    private List<Product> soldProducts; //keeping track of sold products

    public Store(String storeName, String owner, String filename, List<Product> products) {
        this.storeName = storeName;
        this.seller = seller;
        this.filename = filename;
        this.products = products;
        this.soldProducts = new ArrayList<>();
    }

    public void sell(Product product) {
        if (product.getQuantity() > 0) {
            for (int i = 0; i < products.size(); i++) {
                //iterating through the list to find matching product
                if (products.get(i).equals(product)) {
                    soldProducts.add(products.get(i));
                    products.remove(i);
                    break;
                }
            }
        } else {
            System.out.println("This item is out of stock");
            return;
        }

    }

}
