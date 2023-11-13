# Project 4
Made by L36 Group 2
  <br>
<details>
<summary>How to Run and Compile 🚀</summary>
  <br>
<ol>
  <li>Compile</li>
  <li>Run</li>
  <li>Great success</li>
</ol>

</details>
  
# Class Descriptions:
- **Marketplace** - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
- **Database** - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
- **Person** - Holds the email, password, and account type information for all users that have created an account. This info is stored in Accounts.txt and is used to identify returning accounts and to create new accounts. This class has methods for checking the formatting of new emails and passwords, identifying duplicate emails, as well as for editing an account's email and password (UI included). The person class is the superclass of both customer and seller classes, which inherit these fields and methods.<br>**Testing**: By putting a create account/log in interface at the beginning of the main method, we could repeatedly create new Person objects and check if their account info was saved. Indeed, no duplicate emails were allowed, the formatting had to be correct, and old accounts were saved and could be accessed at any later time.
- **Customer** - A subclass of Person. Contains basic account information as well as basic customer information, such as the their current shopping cart, a list of products they have purchased, and a list of stores they have bought from. This information is stored in Customers.txt so user data persists. This class has UI and backend handling to display all of the products in the marketplace, search for a specific product in a marketplace, and sort the marketplace by products bought from overall or just from the current user. Additionally, customers can add, purchase, and remove items from or to a shopping cart.<br>**Testing**: By creating different stores and sellers using the respective classes, we could test the functionality of searching, sorting, and buying classes. After attempting to do these tasks for various different sellers, stores, and products, we were able to verfiy that the program preformed as expected for the user input and that user data persisted between runs.
- **CartObject** - A Cart Object is the representation of a product when it exists in a customers cart.  It is a subclass of the product class and only has a few simple methods.  The constructor contains a call to the super class as well as the quantity which is currently in the customers cart.  There is also a toCartString method to allow for file writing at the end of the program.<br> **Testing**: By adding products to cart, a new cart object is created and the quantity of the product is decreased.  This is verified by looking at a product page and seeing the decreased quantity.  When a cart object is removed from the cart, the corresponding product has it's quantity increased by the amount removed from the cart.
- **Sales** - Sales objects keep track of all the sales that have been made through the marketplace.  A sale holds information about the product, quantity, buyer, seller, and store.  This allows sellers to view a store dashboard and allows customers to view purchase history. <br>**Testing**: This class was tested by purchasing several products from different customers and stores through different sellers and verifying that correct information was found in the sellers store dashboard and customers purchase history.
- **Seller** -
- Store - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
- Product - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
# Submissions:
- Vocareum:
- BrightSpace:
