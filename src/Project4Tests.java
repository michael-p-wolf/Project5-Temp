import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Project4Tests {
    /**
     * because our parameters are printed by the console I have to use
     * outcontent to be able to capture what would have gotten printed!
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Store testStore;

    // Initialize testStore or any setup logic needed
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        testStore = new Store("TestStore");
    }

    // restores everything back to its original state
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testCreateProduct() {
        Scanner mockScanner = new Scanner("TestProduct\nTestDescription\n10.0\n5");
        try {
            testStore.createProduct(mockScanner.nextLine(), mockScanner.nextLine(), Double.parseDouble(mockScanner.nextLine()), Integer.parseInt(mockScanner.nextLine()));
        } catch (Exception e) {
            // Handle exceptions if needed
        }
        // Check if the product was added to the store
        assertEquals(1, testStore.getProducts().size());
        assertEquals("TestProduct", testStore.getProducts().get(0).getName());
    }

    @Test
    public void testEditProduct() {
        // Add a product to the store for testing
        testStore.addProduct(new Product("ExistingProduct", "TestStore", "Description", 10, 5.0));
        testStore.editProduct("ExistingProduct", "EditedProduct", "EditedDescription", 8.0, 15);
        // Check if the product was edited
        assertEquals("EditedProduct", testStore.getProducts().get(0).getName());
        assertEquals("EditedDescription", testStore.getProducts().get(0).getDescription());
        assertEquals(8.0, testStore.getProducts().get(0).getPrice(), 0.001);
        assertEquals(15, testStore.getProducts().get(0).getQuantity());
    }

    @Test
    public void testRemoveProduct() {
        // Add a product to the store for testing
        testStore.addProduct(new Product("ProductToRemove", "TestStore", "Description", 10, 5.0));
        Scanner mockScanner = new Scanner("1");
        testStore.removeProduct(mockScanner, testStore.getProducts().get(0));
        // Check if the product was removed
        assertEquals(0, testStore.getProducts().size());
    }

    @Test
    public void testInvalidInputInCreateProduct() {
        String input = "Invalid\nInvalid Input\n2";  // Simulate invalid input
        // Redirect System.in to provide input
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        // Create a Seller instance
        Seller testSeller = new Seller("test@example.com", "password", "seller");
        // Create a dummy Store and Scanner
        Store testStore = new Store("TestStore");
        testSeller.addStore(testStore);
        Scanner scan = new Scanner(System.in);
        testSeller.createProduct(scan, testStore);
        // Assert
        String expectedOutput = "Enter Product Name:\n" +
                "Enter Product Description:\n" +
                "Enter Product Price:\n" +
                "Enter Quantity Available:\n" +
                "Invalid input!\n";
        assertEquals(expectedOutput, outContent.toString());
    }
    @Test
    public void testCreateStore() {
        // Assuming you have a Scanner with mocked input
        String input = "NewStore\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Create a Seller instance
        Seller seller = new Seller("seller@example.com", "password", "S");

        // Create a list of Sellers for testing
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        // Call the method to be tested
        seller.createStore(new Scanner(System.in), sellers);

        // Check if the store was created
        Assertions.assertEquals(1, seller.getStores().size());
        Assertions.assertEquals("NewStore", seller.getStores().get(0).getStoreName());
    }

    @Test
    public static void testBuy() {
        // Create a customer
        Customer customer = new Customer("test@email.com", "password", "customer");

        // Create a product
        Product product = new Product("TestProduct", "TestStore", "Description", 10, 5.0);

        // Create a CartObject
        CartObject cartObject = new CartObject("TestProduct", "TestStore", "Description", 5.0, 3);

        // Create a Seller
        Seller seller = new Seller("seller@example.com", "sellerPassword", "S");

        // Create a Store
        Store store = new Store("TestStore");
        store.addProduct(product); // Add the product to the store

        // Add the store to the seller
        seller.addStore(store);

        // Simulate user adding the product to the cart
        customer.addToCart(cartObject);

        // Simulate user buying the product
        customer.buy(cartObject, new Scanner(System.in), seller, store);

        // Assert that the purchase history contains the bought product
        assert customer.getPurchaseHistory().size() == 1 : "Purchase history size mismatch";
        assert "TestProduct".equals(customer.getPurchaseHistory().get(0).getName()) : "Product name mismatch";

        // Assert other properties if needed
        assert "test@email.com".equals(customer.getEmail()) : "Email mismatch";
        assert "password".equals(customer.getPassword()) : "Password mismatch";
        assert "customer".equals(customer.getType()) : "Type mismatch";

        // Additional assertions if needed
    }
}
