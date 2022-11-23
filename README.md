# Marketplace - CS 180 Project 4
##### Brightspace report submission - Praneeth
##### Vocareum code submission - Drew

## How to Compile and Run the Code

- Compile the main.java file using the below terminal 
```
    javac main.java
```
- Run the main.java file using the below terminal command
```
    java main
```


## Details of each Class

#### Main.java
- This is the main java class which contains the entire program flow and is the point of the CLI application that the user will mainly interact with. 
- This class takes all the inputs from the users and calls the required methods from the other java classes.

#### Seller.java
- This contains all the methods pertaining to a seller. 
- It extends to Customer since the seller can perform certain customer related actions like buying products, viewing a shopping cart etc. 
- The methods included in this class are :-
      - initializeStores => This method gets all the store names of the products by reading the ProductsDatabase.csv file (which contains the details of all the products in the marketplace) and stores them in an array.
      -  importCSV => This method allows the seller to import a CSV file if they want to. This method takes a file name as input, and if the specified file exists, it will read the contents of it, create a new Product object with each line, and stores it in an array list. This method throws a FileNotFound exception. 
      -  viewSalesByStore => This method reads through the stores array that was created in the initializeStores method, and adds all the details of the products sold pertaining to each store to an array list. This array list is converted to a string array and returned. 
      -  writeToCSV => This method helps the user write a product to the ProductDatabase.csv file. 
      -  readSellerProducts => This method helps the seller find all the products they are selling in ProductDatabase.csv and adds them to an array list. The array list is then returned. 
      -  getSellerRev => This method returns the seller revenue
      -  setSellerRev => This method sets the seller revenue to the specified value
      -  add => This method adds a new product to the list of products
      -  edit => This method replaces an old product with the new product in the list of products
      -  write => This method helps write the details of a product to the ProductDatabase.csv in a specified format
      -  buyProduct => This method allows the seller to buy a product and performs the necessary operations (i.e. it calculates the seller revenue, it increases the total items sold etc.)

#### Customer.java
- This contains all the methods pertaining to a customer.
- It extends to User
- The methods included in the class are :-
        - itemsInShoppingCart => This method reads the ShoppingCart.txt file and stores all the products in the file in an array list. Then, all the products that are of the specified store name are stored in a separate array list and this is returned as a string array. 
        - itemsInPurchaseHistory => This method reads the PurchaseHistory.txt file and stores all the products in the file in an array list. Then, allthe products that are of the specified store name are stored in a separate array list and this is returned as a string array. 
        - toString => This method helps write the product in a specific format. 
        - addToShoppingCart => This method adds the specified product to the shopping cart
        - removeFromShoppingCart => This method removes the specifed product from the shopping cart
        - saveShoppingCart => This method writes all the contents in the shopping cart into a file called ShoppingCart.txt
        - getAlreadyInCart => This method returns the shopping cart
        - purchaseOneProduct => This method helps make a purchase of a product. If a product is in the shopping cart, then it is removed from the shopping cart and its details are written to the PurchaseHistory.txt file. 
        - completePurchase => This method completes a purchase, meaning all products in the shopping cart are removed one by one and written to the PurchaseHistory.txt file. 
        - viewPastPurchase => This method reads the PurchaseHistory.txt file and allows the customer to see their purchase history. 

#### User.java
- This contains all the methods pertaining to a general User
- The methods included in this class are :-
        - getName => Returns the name of the user
        - login => Performs the login functionality of the CLI application
        - signUp => Performs the sign up functionality of the CLI application
        - toString => Converts the user into a string of a specific format

#### Product.java
- This contains all the methods pertaining to a general product
- The methods included in this class are :-
        - setSellerName => Sets the name of the seller
        - setSellerPassword => Sets the password of the seller
        - getSellerName => Returns the name of the seller
        - getSellerPassword => Returns the password of the seller
        - setEmail =>  Sets the email of the user
        - getEmail => Returns the email of the user
        - toStringForProductDataBase => Converts the product object into a string based on the specifications required for the ProductDatabase.csv file
        - getNumberOfProductsSelected => Returns the number of products the user has selected from the marketplace
        - getProductsAvailable => Returns the total number of products available for purchasing
        - setNumberOfProductsSelected => Sets the number of products selected by the user to the value specified
        - setProductsAvailable => Sets the number of available products for purchasing to the value specified
        - getName => Returns the name of the product
        - setName => Sets the name of the product
        - getDescription => Returns the description of the product
        - setDescription => Sets the description of the product
        - getQuantity => Returns the quantity of the product
        - setQuantity => Sets the quantity of the product
        - getPrice => Returns the price of the product
        - setPrice => Sets the price of the product
        - getStoreName => Returns the name of the store to which the product belongs
        - setStoreName => Sets the name of the store
        - toStringForShoppingCart => Converts the product object into a string based on the specifications required to store the product in ShoppingCart.txt
        - toString => Converts the product object into a general string
        - initialString => Returns the initial string of the product before any changes were made (changes due to selling, discounts etc.)

