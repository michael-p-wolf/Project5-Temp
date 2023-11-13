import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

/**
 * Boilermaker Bazaar Bonanza
 * <p>
 * This a set of test cases to test invalid and valid
 * inputs for customer and seller and their various methods
 * and interactions with other classes
 *
 * @author Michael Wolf, Lab Sec 36
 * @author Pranay Nandkeolyar, Lab Sec 36
 * @author Jacob Stamper, Lab Sec 36
 * @author Benjamin Emini, Lab Sec 36
 * @author Simrat Thind, Lab Sec 36
 * @version November 13th, 2023
 **/

public class Project4Tests {
    // because our parameters are printed by the console I have to use
    // outcontent to be able to capture what would have gotten printed
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
    public void testInvalidInput() {
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
        Seller seller = new Seller("seller@example.com", "password", "S");
        // Create a list of Sellers for testing
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(seller);
        seller.createStore(new Scanner(System.in), sellers);
        Assertions.assertEquals(1, seller.getStores().size());
        Assertions.assertEquals("NewStore", seller.getStores().get(0).getStoreName());
    }

    @Test
    public void testCustomer() {
        // Create a customer
        Customer customer = new Customer("test@email.com", "password", "customer");

        // Create products, sellers, and a store
        Product product1 = new Product("Product1", "Store1", "Description1", 10, 5.0);
        Product product2 = new Product("Product2", "Store2", "Description2", 8, 8.0);

        CartObject cartObject1 = new CartObject("Product1", "Store1", "Description1", 5.0, 3);
        CartObject cartObject2 = new CartObject("Product2", "Store2", "Description2", 8.0, 2);

        Seller seller1 = new Seller("seller1@example.com", "seller1Password", "S");
        Seller seller2 = new Seller("seller2@example.com", "seller2Password", "S");

        Store store1 = new Store("Store1");
        store1.addProduct(product1);
        seller1.addStore(store1);

        Store store2 = new Store("Store2");
        store2.addProduct(product2);
        seller2.addStore(store2);

        ArrayList<Seller> sellers = new ArrayList<>(Arrays.asList(seller1, seller2));

        // Add items to the customer's cart
        customer.addToCart(cartObject1);
        customer.addToCart(cartObject2);

        // Purchase the items in the cart
        customer.purchaseCart(sellers);

        // Assert that the purchase history contains the bought products
        assertEquals(2, customer.getPurchaseHistory().size());
        assertEquals("Product1", customer.getPurchaseHistory().get(0).getName());
        assertEquals("Product2", customer.getPurchaseHistory().get(1).getName());

        // Assert that the cart is empty after purchase
        assertEquals(0, customer.getCart().size());

        // Assert that product quantities in stores are updated
        assertEquals(7, getProductQuantity(store1, "Product1"));
        assertEquals(6, getProductQuantity(store2, "Product2"));
    }

    // Helper method to get the quantity of a product in a store
    private int getProductQuantity(Store store, String productName) {
        for (Product product : store.getProducts()) {
            if (product.getName().equals(productName)) {
                return product.getQuantity();
            }
        }
        return 0;  // Product not found, handle accordingly
        CartObject cartObject1 = new CartObject("Product1", "Store1", "Description1", 5.0, 3);
        CartObject cartObject2 = new CartObject("Product2", "Store2", "Description2", 8.0, 2);
        Seller seller1 = new Seller("seller1@example.com", "seller1Password", "S");
        Seller seller2 = new Seller("seller2@example.com", "seller2Password", "S");
        Store store1 = new Store("Store1");
        store1.addProduct(product1);
        seller1.addStore(store1);
        Store store2 = new Store("Store2");
        store2.addProduct(product2);
        seller2.addStore(store2);
        ArrayList<Seller> sellers = new ArrayList<>(Arrays.asList(seller1, seller2));
        customer.addToCart(cartObject1);
        customer.addToCart(cartObject2);
        customer.purchaseCart(sellers);

        assertEquals(2, customer.getPurchaseHistory().size());
        assertEquals("Product1", customer.getPurchaseHistory().get(0).getName());
        assertEquals("Product2", customer.getPurchaseHistory().get(1).getName());
        assertEquals(0, customer.getCart().size());
        assertEquals(7, getProductQuantity(store1, "Product1"));
        assertEquals(6, getProductQuantity(store2, "Product2"));
    }

    // Helper method to get the quantity of a product in a store
    private int getProductQuantity(Store store, String productName) {
        for (Product product : store.getProducts()) {
            if (product.getName().equals(productName)) {
                return product.getQuantity();
            }
        }
        return 0;
    }

    @Test
    public void testInvalidInput2() {
        String input = "Invalid\nInvalid Input\n2";  // Simulate invalid input
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        // Create a dummy Product, Seller, and Store
        Seller testSeller = new Seller("test@example.com", "password", "seller");
        Store testStore = new Store("TestStore");
        testSeller.addStore(testStore);
        Scanner scan = new Scanner(System.in);
        testSeller.createProduct(scan, testStore);

        String expectedOutput = "Enter Product Name:\n" +
                "Enter Product Description:\n" +
                "Enter Product Price:\n" +
                "Enter Quantity Available:\n" +
                "Invalid input!\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
