import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String storeName;
    private Seller seller;
    private String filename;
    private List<Product> products; // keeping tracks fo products
    private List<Product> soldProducts; //keeping track of sold products


    public Store(String storeName, Seller seller, String owner, String filename, List<Product> products) {
        this.storeName = storeName;
        this.seller = seller;
        this.filename = filename;
        this.products = products;
        this.soldProducts = new ArrayList<>();

    }

    public void sell(Product product, Customer customer) throws IOException {
        try {


            File f = new File(filename);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            int counter = 0; // this is for seeing if the store hold the product it gets incremented when product is found
            if (product.getQuantity() > 0) {
                for (int i = 0; i < products.size(); i++) {
                    //iterating through the list to find matching product
                    if (products.get(i).equals(product)) {
                        counter++;
                        soldProducts.add(products.get(i)); // adding all sold products to an array list
                        printWriter.write(customer.getEmail() + "," + product.getName() + "," + product.getPrice()); //writing Customer email, product name, product price
                        product.setQuantity(product.getQuantity() - 1);
                        break;
                    }
                }

                if (product.getQuantity() == 0) { //checking to see if the product is out of stock then removing it from the array list
                    products.remove(product);
                }

                if (counter == 0) {
                    System.out.println("This store does not sell this product");
                }
            } else {
                System.out.println("This item is out of stock");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
