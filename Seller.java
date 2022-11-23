import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Seller.java
 * <p>
 * This class will implement the seller side of the program, while writing the various methods needed
 * by the seller in order to execute their tasks, such as adding or editing products. The program
 * will also be able to write and read from a csv file, ProductDatabase, which serves as the main
 * point where the products are stored. Extends the customer class as the seller can also act as
 * a customer.
 *
 * @author Drew Balaji, Praneeth Kukunuru, Akhil Kasturi, Yash Prabhu, L28
 * @version 1.5, 11/15/2022
 */
public class Seller extends Customer {
    private ArrayList<Product> itemsToSell = new ArrayList<>();
    private ArrayList<Product> data = new ArrayList<>();
    private double sellerRev;
    private String name;
    private String password;
    private int quantity;
    private double discount;
    private double price;
    private String storeName;
    private String nameP;
    private int itemsSold;
    private String[] stores;

    public Seller(String name, String password) {
        super(name, password);
        this.name = name;
        this.password = password;
        this.itemsSold = 0;
        try {
            File f = new File("ProductDatabase.csv");
            FileOutputStream fout = null;
            if (!f.exists()) {
                fout = new FileOutputStream("ProductDatabase.csv");
                fout.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                String[] productSpecs = line.split(",");
                Product product = new Product(productSpecs[2], productSpecs[3], Integer.parseInt(productSpecs[4]),
                        Double.parseDouble(productSpecs[5]), productSpecs[6], "Seller");
                if (productSpecs[0].equals(name)) {
                    itemsToSell.add(product);
                }
                data.add(product);
                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeStores() {
        ArrayList<String> storeNames = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("ProductDatabase.csv"));
            String line = br.readLine();
            while (line != null) {
                if (line.split(",")[0].equals(name) && !storeNames.contains(line.split(",")[6])) {
                    storeNames.add(line.split(",")[6]);
                }

                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stores = storeNames.toArray(new String[0]);
    }

    public void importCSV(String fileName) throws FileNotFoundException {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                throw new FileNotFoundException();
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> csvContents = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                csvContents.add(line);
                line = br.readLine();
            }
            br.close();
            String listOfProducts = "";
            for (int i = 0; i < csvContents.size(); i++) {
                boolean alreadyExists = false;
                int index = 0;
                for (int j = 0; j < itemsToSell.size(); j++) {
                    if (csvContents.get(i).contains(itemsToSell.get(j).getName())) {
                        alreadyExists = true;
                        index = j;
                    }
                }
                if (!alreadyExists) {
                    String[] productSpecs = csvContents.get(i).split(",");
                    Product product = new Product(productSpecs[0], productSpecs[1], Integer.parseInt(productSpecs[2]),
                            Double.parseDouble(productSpecs[3]), productSpecs[4], "Seller");
                    itemsToSell.add(product);
                    data.add(product);
                } else {
                    String[] productSpecs = csvContents.get(i).split(",");
                    if (itemsToSell.get(index).getStoreName().equals(productSpecs[4])) {
                        Product product = new Product(productSpecs[0], productSpecs[1],
                                Integer.parseInt(productSpecs[2]) + itemsToSell.get(index).getQuantity(),
                                Double.parseDouble(productSpecs[3]), productSpecs[4], "Seller");
                        itemsToSell.set(index, product);
                    }
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public String[] viewSalesByStore() {
        initializeStores();
        ArrayList<String> salesByStore = new ArrayList<>();
        for (String store : stores) {
            String[] purchases = itemsInPurchaseHistory(store);
            if (purchases == null) {
                purchases = new String[0];
            }
            String addingToArray = store + "\n";
            double sales = 0;
            for (int i = 0; i < purchases.length; i++) {
                sales += Double.parseDouble(purchases[i].split(";")[3]) *
                        Double.parseDouble(purchases[i].split(";")[4]);
            }
            addingToArray += String.format("Sales by %s: %.2f\n", store, sales);
            for (int i = 0; i < purchases.length; i++) {
                String[] purchaseDetails = purchases[i].split(";");
                addingToArray += String.format("Name: %s  Product Purchased: %s  Price: %.2f  Quantity Purchased: %d",
                        purchaseDetails[0], purchaseDetails[1], Double.parseDouble(purchaseDetails[4]),
                        Integer.parseInt(purchaseDetails[3]));
                addingToArray += "\n";
            }
            salesByStore.add(addingToArray);
        }
        return salesByStore.toArray(new String[0]);
    }

    public void save() {
        File f = new File("ProductDatabase.csv");
        if (!(f.exists())) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter pw = new PrintWriter("ProductDatabase.csv");
            for (int i = 0; i < data.size(); i++) {
                pw.print(data.get(i).toStringForProductDataBase(name, password, getEmail()) + "\n");
            }
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToCSV(Product product) {
        File f = new File("ProductDatabase.csv");
        if (!(f.exists())) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter pw = new PrintWriter("ProductDatabase.csv");
            pw.print(product.toStringForProductDataBase(name, password, getEmail()) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readSellerProducts(String nameR) {
        ArrayList<String> sellerProducts = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("ProductDatabase.csv"));
            String line = br.readLine();
            while (line != null) {
                if (line.contains(nameR)) {
                    sellerProducts.add(line);
                }

                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sellerProducts;
    }

    public double getSellerRev() {
        return sellerRev;
    }

    public void setSellerRev(double sellerRev) {
        this.sellerRev = sellerRev;
    }

    public void remove(String nameR, String storeNameR) {
        for (int i = 0; i < itemsToSell.size(); i++) {
            if (itemsToSell.get(i).getName().equals(nameR) && itemsToSell.get(i).getStoreName().equals(storeNameR)) {
                itemsToSell.remove(i);
            }
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equals(nameR) && data.get(i).getStoreName().equals(storeNameR)) {
                data.remove(i);
            }
        }
    }

    public void add(Product product) {
        itemsToSell.add(product);
        data.add(product);
    }

    public void edit(Product oldProduct, Product newProduct) {
        for (int i = 0; i < itemsToSell.size(); i++) {
            if (itemsToSell.get(i) == oldProduct) {
                itemsToSell.set(i, newProduct);
                break;
            }
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == oldProduct) {
                data.set(i, newProduct);
                break;
            }
        }
    }

    public void write() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("ProductDatabase.csv"), false);
            for (int b = 0; b < itemsToSell.size(); b++) {
                pw.print(name + ",");
                pw.print(password + ",");
                pw.print(itemsToSell.get(b).getName() + ",");
                pw.print(itemsToSell.get(b).getDescription() + ",");
                pw.print(itemsToSell.get(b).getQuantity() + ",");
                pw.print(itemsToSell.get(b).getPrice() + ",");
                pw.print(itemsToSell.get(b).getStoreName() + "\n");
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double buyProduct(Product product) {
        for (int i = 0; i < itemsToSell.size(); i++) {
            if (itemsToSell.get(i) == product) {
                setSellerRev(getSellerRev() + product.getPrice());
                itemsSold++;
                return sellerRev;
            }
        }
        System.out.println("This product isn't in the database");
        return -1;
    }

    public void list(Scanner scanner) {
        System.out.println("Enter the name of the store to search");
        String ss = scanner.nextLine();
        for (int i = 0; i < itemsToSell.size(); i++) {
            if (itemsToSell.get(i).getStoreName().equals(ss)) {
                JOptionPane.showMessageDialog(null, itemsToSell.get(i), "Seller", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public String[] getStores() {
        return stores;
    }

}
