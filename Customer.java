import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Customer.java
 *
 * This class makes the customer side of the project and allows the user to buy products, put them
 * into a shopping cart, look at purchase history, and check the cart. Like the seller class, the customer
 * can write and read from a csv file, which in this case is a shopping cart file.
 *
 * @author Drew Balaji, Praneeth Kukunuru, Akhil Kasturi, Yash Prabhu, L28
 *
 * @version 1.5, 11/15/2022
 *
 */
public class Customer extends User {
    private String name;
    private String password;
    private String productName;
    private int quantity;
    private double price;
    private ArrayList<String[]> alreadyInCart = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    private static ArrayList<String> shoppingData = new ArrayList<>();

    public Customer(String name, String password) {
        super(name, password, "customer");
        this.productName = "";
        this.quantity = 0;
        this.price = 0;
        try {
            File f = new File("ShoppingCart");
            FileOutputStream fout = null;
            if (!f.exists()) {
                fout = new FileOutputStream("ShoppingCart");
                fout.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                data.add(line);
                line = br.readLine();
            }
            br.close();
            if (data != null) {
                for (String datum : data) {
                    if (datum.split(";")[0].equalsIgnoreCase(name)) {
                        alreadyInCart.add(datum.split(";"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] itemsInShoppingCart(String storeName) {
        try {
            File f = new File("ShoppingCart");
            FileOutputStream fout = null;
            if (!f.exists()) {
                fout = new FileOutputStream("ShoppingCart");
                fout.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                shoppingData.add(line);
                line = br.readLine();
            }
            br.close();
            ArrayList<String> matchingStoreName = new ArrayList<>();
            if (shoppingData != null) {
                for (String datum : shoppingData) {
                    if (datum.split(";")[6].equalsIgnoreCase(storeName)) {
                        matchingStoreName.add(datum.split(";")[0] + ";" + datum.split(";")[2] + ";" +
                                datum.split(";")[3] + ";" + datum.split(";")[4] + ";" +
                                datum.split(";")[5] + ";" + datum.split(";")[6]);
                    }
                }
            }
            return matchingStoreName.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] itemsInPurchaseHistory(String storeName) {
        try {
            File f = new File("PurchaseHistory");
            FileOutputStream fout = null;
            if (!f.exists()) {
                fout = new FileOutputStream("PurchaseHistory");
                fout.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> newshoppingData = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                newshoppingData.add(line);
                line = br.readLine();
            }
            br.close();
            ArrayList<String> matchingStoreName = new ArrayList<>();
            if (newshoppingData != null) {
                for (String datum : newshoppingData) {
                    if (datum.split(";")[6].equalsIgnoreCase(storeName)) {
                        matchingStoreName.add(datum.split(";")[0] + ";" + datum.split(";")[2] + ";" +
                                datum.split(";")[3] + ";" + datum.split(";")[4] + ";" +
                                datum.split(";")[5] + ";" + datum.split(";")[6]);
                    }
                }
            }
            return matchingStoreName.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString(String[] strings) {
        String returning = "";
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                returning += strings[0];
            } else {
                returning += ";" + strings[i];
            }
        }
        return returning;
    }

    public void addToShoppingCart(String[] product) {
        alreadyInCart.add(product);
        data.add(toString(product));
    }

    public void removeFromShoppingCart(String[] productNameR) {
        alreadyInCart.remove(productNameR);
        data.remove(toString(productNameR));
    }

    public void saveShoppingCart() {
        try {
            File f = new File("ShoppingCart");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
            for (int i = 0; i < data.size(); i++) {
                bw.write(data.get(i));
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getAlreadyInCart() {
        return alreadyInCart;
    }

    public String toStringForShoppingCartShow(String[] strings) {
        return String.format("Name: %s\nDescription: %s\nQuantity: %d\nPrice: $%.2f\nStoreName: %s\n", strings[2],
                strings[3], Integer.parseInt(strings[4]), Double.parseDouble(strings[5]), strings[6]);
    }

    public void purchaseOneProduct(String[] strings) {
        try {
            File f = new File("ShoppingCart");
            for (String[] datum : alreadyInCart) {
                if (Arrays.equals(datum, strings)) {
                    removeFromShoppingCart(datum);
                }
            }
            saveShoppingCart();

            File nextFile = new File("PurchaseHistory");
            FileOutputStream fout = null;
            if (!f.exists()) {
                fout = new FileOutputStream("PurchaseHistory");
                fout.close();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(nextFile, true));
            bw.write(toString(strings));
            bw.write("\n");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void completePurchase() {
        try {
            File nextFile = new File("PurchaseHistory");
            FileOutputStream fout = null;
            if (!nextFile.exists()) {
                fout = new FileOutputStream("PurchaseHistory");
                fout.close();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(nextFile, true));
            for (int i = 0; i < data.size(); i++) {
                bw.write(data.get(i));
                bw.write("\n");
            }
            bw.close();

            File f = new File("ShoppingCart");
            for (int i = 0; i < alreadyInCart.size(); i++) {
                removeFromShoppingCart(alreadyInCart.get(i));
                i -= 1;
            }
            alreadyInCart.clear();
            saveShoppingCart();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] viewPastPurchases(String username, String passwordV) {
        try {
            File f = new File("PurchaseHistory");
            FileOutputStream fout = null;
            if (!f.exists()) {
                fout = new FileOutputStream("PurchaseHistory");
                fout.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            ArrayList<String> purchaseHistory = new ArrayList<>();
            while (line != null) {
                purchaseHistory.add(line);
                line = br.readLine();
            }
            br.close();
            ArrayList<String> purchaseMatching = new ArrayList<>();
            for (String purchase : purchaseHistory) {
                if (purchase.split(";")[0].equals(username) && purchase.split(";")[1].equals(passwordV)) {
                    purchaseMatching.add(purchase);
                }
            }
            if (purchaseMatching.size() == 0) {
                return null;
            } else {
                return purchaseMatching.toArray(new String[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void exportCSV(String username, String passwordE, String filePath) {

        ArrayList<String> purchases = new ArrayList<String>();
        try {
            File f = new File("PurchaseHistory");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (true) {
                if (line == null) {
                    break;
                }
                if (username.equalsIgnoreCase(line.split(";")[0]) && passwordE.equalsIgnoreCase(line.split(";")[1])) {
                    purchases.add(line);
                }
                line = bfr.readLine();
            }
            bfr.close();

        } catch (IOException e) {
            System.out.println("Sorry! There was an error exporting your purchase history.");
        }
        try {
            File f = new File(filePath);
            boolean uselessVariable = f.createNewFile();
            FileWriter fos = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fos);
            for (String s : purchases) {
                pw.println(s);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Sorry! There was an error exporting your purchase history.");
        }
    }




}
