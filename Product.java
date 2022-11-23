/**
 * Product.java
 *
 * This class will instantiate the products that are used throughout the program,
 * and provide each product with a frame to make each of them are the same.
 *
 * @author Drew Balaji, Praneeth Kukunuru, Akhil Kasturi, Yash Prabhu, L28
 *
 * @version 1.5, 11/15/2022
 *
 */
public class Product {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String storeName;
    private int productsAvailable;
    private int numberOfProductsSelected;
    private double discount = 0.0;
    private int limiter = 0;
    private String email;
    private String sellerName;
    private String sellerPassword;

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setSellerPassword(String sellerPassword) {
        this.sellerPassword = sellerPassword;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerPassword() {
        return sellerPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String toStringForProductDataBase(String username , String password , String emailT) {
        return String.format("%s,%s,%s,%s,%d,%.2f,%s,%s" , username , password , name , description ,
                productsAvailable , price , storeName , emailT);
    }

    public int getNumberOfProductsSelected() {
        return numberOfProductsSelected;
    }

    public int getProductsAvailable() {
        return productsAvailable;
    }


    public void setNumberOfProductsSelected(int numberOfProductsSelected) {
        this.numberOfProductsSelected = numberOfProductsSelected;
    }

    public void setProductsAvailable(int productsAvailable) {
        this.productsAvailable = productsAvailable;
    }

    public Product(String name, String description, int quantity, double price, String storeName,
                   String userOrSeller) {
        this.name = name;
        this.description = description;
        if (userOrSeller.equalsIgnoreCase("Seller")) {
            this.quantity = quantity;
            this.productsAvailable = quantity;
        } else {
            this.numberOfProductsSelected = quantity;
        }
        this.quantity = quantity;
        this.price = price;
        this.storeName = storeName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String[] toStringForShoppingCart(String username, String password) {
        String[] newString = new String[7];
        newString[0] = username;
        newString[1] = password;
        newString[2] = name;
        newString[3] = description;
        if (numberOfProductsSelected != 0) {
            newString[4] = Integer.toString(numberOfProductsSelected);
        } else {
            newString[4] = "Out of Stock";
        }
        newString[5] = Double.toString(price);
        newString[6] = storeName;

        return newString;
    }



    public String toString() {
        return String.format("Name: %s\nDescription: %s\nQuantity: %d\nPrice: $%.2f\nStoreName: %s\nSeller Email:" +
                        " %s\n", name , description , quantity , price , storeName , email);
    }

    public String initialString() {
        return String.format("Name: %s\n   Price: $%.2f\n   StoreName: %s\n", name , price , storeName);
    }


}
