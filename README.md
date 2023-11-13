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
- **Marketplace** - This is the main class where all of the different classes come together. It stores objects of Sellers and Customers and keeps track of who is the active seller and customer through the variables activeCustomer and activeSeller. When it starts it welcomes the user and allows them to either create an account or sign in. Then it takes the user through either the customer's home screen or the seller's home screen displaying different options that the user can choose. This class starts the program and has everything in a loop that will keep the program running until the user stops it. For saving the data the user enters, Marketplace reads and writes to the Accounts.txt file to save data and makes lists based on the file. **Testing** We check to see if the user data is successfully saved to the files. After making accounts and saving them to the files, we can navigate the home screens of each seller and customer to see if they work. **Relation with classes** Marketplace creates customer and seller objects, displays their home screen and calls their methods. It can create accounts, manage stores, and store data for these classes. Marketplace also manages data within the Store and product classes to edit or create products at stores. Marketplace also handles sales and the shopping cart class for managing sales.

- **Database** - Database is a crucial class in our program. Its main functionality is reading and storing data from all of the different files there are. it reads through Accounts.txt and fills information in lists of customers and sellers. It also writes data to files so all the other classes don't have to worry about it. **Testing** We needed to verify that it would read the information from the files correctly and correctly inputs the data from the files into the lists. It also needed to handle different situations like sellers with multiple stores. For writing data we needed to make sure that it wrote the correct information like account information. Once we made sure it wrote all of the data correctly we knew that it could save things to your computer. **Relation with other classes** Database works closely with customer and seller classes to read and write their data. Then through those classes it also stores information about the Store and Product classes. This class handles the persistence of data for most of the program.


- **Person** - Holds the email, password, and account type information for all users that have created an account. This info is stored in Accounts.txt and is used to identify returning accounts and to create new accounts. This class has methods for checking the formatting of new emails and passwords, identifying duplicate emails, as well as for editing an account's email and password (UI included). The person class is the superclass of both customer and seller classes, which inherit these fields and methods.<br>**Testing**: By putting a create account/log in interface at the beginning of the main method, we could repeatedly create new Person objects and check if their account info was saved. Indeed, no duplicate emails were allowed, the formatting had to be correct, and old accounts were saved and could be accessed at any later time.
- **Customer** - A subclass of Person. Contains basic account information as well as basic customer information, such as their current shopping cart, a list of products they have purchased, and a list of stores they have bought from. This information is stored in Customers.txt so user data persists. This class has UI and backend handling to display all of the products in the marketplace, search for a specific product in a marketplace, and sort the marketplace by products bought overall or just from the current user. Additionally, customers can add, purchase, and remove items from or to a shopping cart.<br>**Testing**: By creating different stores and sellers using the respective classes, we could test the functionality of searching, sorting, and buying classes. After attempting to do these tasks for various different sellers, stores, and products, we were able to verify that the program performed as expected for the user input and that user data persisted between runs.
  
- **CartObject** - CartObject is a small subclass of the product class that handles the shopping cart for customers. Its constructor has all of the attributes of a product as well as how many are being added to the cart. It also has a to-string method printing all of the attributes in the constructor out. **Testing** Testing needed to be done to check if the methods worked like the toString method. **Relation** The CartObject class works with the product class because it is a super class of it and it stores information and helps make a shopping cart of products. 
 
- **Sales** - The sales class is meant to store information and handle prodcut sales for the seller an dthe customer. There are multiple constructors for different classes with differnet levels of information to store. There are all of the different getter methods like seller email and customer email to name a few. This is used for accesing information in differnt classes where transactions are being made. **Testing** For testing we need to make sure the constructers initialize properly and the methods return the right values. We also need to test if the toString method has the right string which is formatted correctly. **Relation with other classes** The sales class is used to record sales of products made between the customer and the store. Also the sales class helps print data for the seller to view in their dashboard. 

- Seller - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
- Store - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
- Product - A detailed description of the class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project.
# Submissions:
- Vocareum:
- BrightSpace:
