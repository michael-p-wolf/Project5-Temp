import java.io.*;

public class Store {
    private String storeName;
    private Seller seller;
    private String filename;
    private Product[] products;

    public Store(String storeName, String owner, String filename, Product[] products) {
        this.storeName = storeName;
        this.seller = seller;
        this.filename = filename;
        this.products = products;
    }

    public void writeFile() throws IOException {
        File f = new File(this.filename);
        PrintWriter pw = new PrintWriter(f);

        String productList = "";
        for (int i = 0; i < this.products.length; i++) {
            productList += products[i] + ",";
        }

        pw.println(String.format("Store: %s; Owner: %s; Products: %s", this.storeName, this.seller, productList));

    }


}
