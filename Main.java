import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.*;
import java.io.*;

public class Main {

    private static final String WELCOME = "Welcome to Marketplace!";
    private static final String LOGIN_WINDOW = "1. Login\n2. Sign Up\n3. Quit";
    private static final String USERNAME_PROMPT = "Username: ";
    private static final String PASSWORD_PROMPT = "Password: ";
    private static final String EMAIL_PROMPT = "Email: ";
    private static final String CONFIRM_PASSWORD_PROMPT = "Confirm Password: ";
    private static final String YES_NO = "1. Yes\n2. No";
    private static final String ERROR = "Error! Invalid Input!";
    private static final String LOGIN_FAILED = "Invalid Username and/or Password! Please Try Again!\n";
    private static final String LOGIN_SUCCESSFUL = "Log-in Successful!\n";
    private static final String SIGNUP_FAILED = "Sign-Up Unsuccessful! Please Try Again!\n";
    private static final String SIGNUP_SUCCESSFUL = "Sign-Up Successful! Please Login with your new account!\n";
    private static final String SELLER_OR_CUSTOMER = "Are you a\n1. Seller?\n2. Customer?";
    private static final String PRODUCTS_FOR_SALE = "\n------------------------\nProducts for " +
            "Sale:\n------------------------";
    private static final String SELECT_PRODUCT = "\n------------------------\nSelect a Product(Enter Product Number)" +
            "\n------------------------";
    private static final String MENU_BEFORE_SELECTION = "------------------------\nSelect an Option (1-4)" +
            "\n------------------------\n1. " + "Select a Product\n2. Go to Main Menu\n3. Browse Products" +
            "\n4. Quit";
    private static final String CUSTOMER_MENU = "------------------------\nSelect an Option (1-13)" +
            "\n------------------------\nSearch by:\n1. Browse Products\n2. " + "Product Name\n3. Store " + "Name\n4. Item" +
            " " + "Description\n\nSort By:\n5. Price (High to Low)\n6. Price (Low to High)\n7. Quantity Available " +
            "(High to " + "Low)\n8. Quantity Available (Low to High)\n\nOther Actions:\n9. Add item to Shopping " +
            "Cart\n10. View Shopping Cart\n11. View " + "Past " + "Purchases\n12. " + "Make a Purchase\n13. Remove Item" +
            "from Shopping Cart\n14. Export CSV file containing past purchases\n15. Edit Account\n16. Delete Account\n17. Exit";
    private static final String SEARCH_BY_PRODUCT_PROMPT = "Enter Product Name to Search for: ";
    private static final String SEARCH_BY_STORE_NAME_PROMPT = "Enter Store Name to Search for: ";
    private static final String SEARCH_BY_ITEM_DESC_PROMPT = "Enter Description to Search for: ";
    private static final String SHOPPING_CART_PROMPT = "Enter NAME of item to add to Shopping Cart: ";
    private static final String PURCHASE_PROMPT = "Enter NAME of item to Purchase: ";
    private static final String CONFIRM_ITEM_ADDED_TO_CART = "%s added to Cart!";
    private static final String SHOPPING_CART_OPTIONS = "------------------------\nSelect an Option (1-4)" +
            "\n------------------------\n" + "1. Purchase One Item" +
            "\n2. Purchase Shopping Cart\n3. Return to Menu\n4. Quit";

    private static final String SELLER_MENU = "------------------------\nSelect an Option (1-6)\n------------------------\n" +
            "1. Add Product\n" + "2. Remove Product\n" + "3. Edit Product\n" + "4. View Products in Shopping Cart\n" +
            "5. View Revenue by Store\n" + "6: Import CSV\n7. Exit";

    private static String AFTER_ADDING_TO_CART = "------------------------\nSelect an Option (1-4)\n------------------------" +
            "1. Go to Shopping Cart\n" + "2.  Return to Main Menu\n" + "3. Browse Products\n" + "4. Quit\n";
    private static String GET_NUM_TO_PURCHASE = "How many do you want to purchase?";


    /**
     * getUserInput()
     * prompts user repeatedly for input until valid input is given.
     *
     * @param message     input prompt message
     * @param validInputs string array containing all valid inputs
     * @param scanner     scanner object used to retrieve user input
     * @return string containing user input
     */
    public static String getUserInput(String message, String[] validInputs, Scanner scanner) {
        String input;
        all:
        while (true) {
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            input = scanner.nextLine();
            if (validInputs == null || validInputs.length == 0) {
                break;
            }
            for (String s : validInputs) {
                if (s.equalsIgnoreCase(input)) {
                    break all;
                }
            }
            System.out.println(ERROR);
        }

        return input;
    }
    public static String getEmail(String username , String password) {
        try {
            File f = new File("Accounts.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> data = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                data.add(line);
                line = br.readLine();
            }
            for (String string : data) {
                if (string.split(",")[0].equals(username) & string.split(",")[1].equals(password)) {
                    return string.split(",")[2];
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return "Email not in file";
    }

    public static void editFiles(String fileName , String oldUsername , String oldPassword ,
                                 String username , String password) {
        ArrayList<String> replacingProducts = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(new File(
                fileName)))) {
            String line = bfr.readLine();
            while (line != null) {
                if (line.split(",")[0].equalsIgnoreCase(oldUsername) &&
                        line.split(",")[1].equals(oldPassword)) {
                    line.replaceAll(oldUsername , username);
                    line.replaceAll(oldPassword , password);
                }
                replacingProducts.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File
                    (fileName) , false));
            for (String string : replacingProducts) {
                bw.write(string);
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void save(ArrayList<Product> products) {
        try {
            File f = new File("ProductDatabase.csv");
            if (!(f.exists())) {
                f.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
            for (Product product : products) {
                bw.write(product.toStringForProductDataBase(product.getSellerName() ,
                        product.getSellerPassword() , product.getEmail()));
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checkLogIn()
     * Using username and password user attempted to log in with,
     * method checks if user successfully logged in.
     * NOTE: Accounts.txt contains all usernames and passwords for all active accounts.
     * NOTE: Each line in Accounts.txt has format username,password (WITH NO SPACE)
     *
     * @param username username user provided during login
     * @param password password user provided during login
     * @return whether user is logged in or not.
     */
    public static boolean checkLogIn(String username, String password) {
        try {
            File f = new File("Accounts.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            // Looping through Accounts.txt file
            String line = bfr.readLine();
            while (true) {
                if (line == null) {
                    break;
                }
                // Checking login info--username is case insensitive while password is case sensitive
                if (username.equalsIgnoreCase(line.split(",")[0]) && password.equalsIgnoreCase(line.split(",")[1])) {
                    return true;
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * getProducts()
     * IMPORTANT/PLEASE READ: Each line in Products.txt should have format itemName,desc,quantity,price,storeName.
     * Also, each line in Products.txt is comma-separated with NO SPACES!
     *
     * @return a list of Product objects where each object corresponds to a product in the marketplace (in Products
     * .txt)
     */
    public static ArrayList<Product> getProducts(String userOrSeller) {
        ArrayList<Product> products = new ArrayList<Product>();
        String line;
        String[] lineArr;
        try {
            File f = new File("ProductDatabase.csv");
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            line = bfr.readLine();
            while (true) {
                if (line == null) {
                    break;
                }
                lineArr = line.split((","));
                Product p = new Product(lineArr[2], lineArr[3], Integer.parseInt(lineArr[4]),
                        Double.parseDouble(lineArr[5]), lineArr[6], userOrSeller);
                p.setEmail(lineArr[7]);
                p.setSellerName(lineArr[0]);
                p.setSellerPassword(lineArr[1]);
                p.setProductsAvailable(Integer.parseInt(lineArr[4]));
                products.add(p);
                line = bfr.readLine();
            }

        } catch (Exception e) {
            System.out.println("Error Retrieving Market Data!");
            e.printStackTrace();
        }
        return products;
    }

    /**
     * checkSignUp()
     * If username and password are valid, and if confirmPassword matches password,
     * then signup is successful and user's account is added to Accounts.txt.
     *
     * @param username        username user provided while signing up
     * @param password        password user provided while signing up
     * @param confirmPassword password confirmation user provided while signing up
     * @param email           takes email for communication between customer and seller
     * @return whether or not account can be created with given info.
     */
    public static boolean checkSignUp(String username, String password, String confirmPassword, String email) {
        try {
            File f = new File("Accounts.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            // Looping through Accounts.txt file
            String line = bfr.readLine();
            while (true) {
                if (line == null) {
                    break;
                }
                // Checking login info--username is case insensitive while password is case sensitive
                if (username.equalsIgnoreCase(line.split(",")[0])) {
                    System.out.printf("Sorry. A user with username \"%s\" already exists!\n", username);
                    return false;
                }
                line = bfr.readLine();
            }
        } catch (IOException e) {
        }
        // boolean flag
        boolean errorFound = false;

        // checking if username or passwords contain comma(s)
        if (username.contains(",")) {
            System.out.println("Please do not use special character \',\' in username!");
            errorFound = true;
        }
        if (password.contains(",")) {
            System.out.println("Please do not use special character \',\' in password!");
            errorFound = true;
        }

        // Checking password length
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters!");
            errorFound = true;
        } else if (password.length() > 20) {
            System.out.println("Password should not exceed 20 characters!");
            errorFound = true;
        }

        // Again, Passwords are case sensitive. Hence, the use of equals(), not equalsIgnoreCase().
        if (!password.equalsIgnoreCase(confirmPassword)) {
            System.out.println("Password and Password Confirmation do not match!");
            errorFound = true;
        }

        // Checking if username and/or password already exist
        if (checkLogIn(username, password)) {
            System.out.printf("Sorry. A user with username \"%s\" already exists!\n", username);
            errorFound = true;
        }
        if (!(email.contains("@"))) {
            errorFound = true;
        }

        // If there were any errors found, return false (as in the signup was unsuccessful).
        if (errorFound) {
            return false;
        }

        // Opening Accounts.txt File in APPEND MODE. User's account will be added to end of file.
        // In case of FileNotFoundException,

        try {
            File f = new File("Accounts.txt");
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(username + "," + password + ", " + email);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry! There was an error creating your account! Please Try Again!");
            return false;
        }

        // If compiler reaches this pt, then signup was successful. Thus, return true.
        return true;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket;
            BufferedReader reader;
            PrintWriter writer;
            serverSocket = new ServerSocket(1111);
            Socket socket = serverSocket.accept();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            //User user = new User();

            // WELCOME STATEMENT
            System.out.println(WELCOME);

        /*
           Asking user if they want to login or signup.
           NOTE: Will be accepting numbers as well as the words "login" and "signup."
         */
            String[] validInputs = {"1", "2", "3", "login", "signup", "Quit"};
            //String loginInput = getUserInput(LOGIN_WINDOW, validInputs, scanner);
            String loginInput = reader.readLine();

            // Regardless of if user wants to log in or sign up, they will need to type in username & password.
            String username = "";
            String password = "";
            String email = "";
            String confirmPassword;


        /*
        This do-while loop contains all the login and signup logic.
        If user indicated they wanted to login, then they are prompted for their username and password.
        The checkLogIn() method validates the login by checking if user's login input matches an existing account
        in Accounts.txt.
        If user indicated they wanted to signup, then they are prompted for their username, password, and password
        confirmation (reentering password).
        The checkSignUp() method validates the signup. If method finds an error, then user is notified of the
        specific errors, the method returns false, and they are prompted to signup again. If the method finds no error,
        user is added to the end Accounts.txt.
        IMPORTANT: After the user signs up for an account, they are prompted to login with their new account
        credentials.
         */
            boolean loginSuccessful = false;
            do {
                loginSuccessful = false;
                if (loginInput.equalsIgnoreCase("3") || loginInput.equalsIgnoreCase("QUIT")) {
                    return;
                }
                // If user wants to LOGIN
                if (loginInput.equalsIgnoreCase("1") || loginInput.equalsIgnoreCase("LOGIN")) {
                    /*
                    System.out.println(USERNAME_PROMPT);
                    username = scanner.nextLine();
                    System.out.println(PASSWORD_PROMPT);
                    password = scanner.nextLine();

                     */
                    username = reader.readLine();
                    password = reader.readLine();
                    if (checkLogIn(username, password)) {
                        //System.out.println(LOGIN_SUCCESSFUL);
                        writer.println("yes");
                        loginSuccessful = true;
                    } else {
                        System.out.println(LOGIN_FAILED);
                        writer.println("no");
                        /*
                        System.out.println("Do you want to \n1. Sign Up\n2. Continue Log In\n3. Quit");
                        String leaveLogIn = scanner.nextLine();
                        if (leaveLogIn.equalsIgnoreCase("1") || leaveLogIn.equalsIgnoreCase("Sign Up")) {
                            loginInput = "2";
                            loginSuccessful = true;
                        } else if (leaveLogIn.equalsIgnoreCase("3") || leaveLogIn.equalsIgnoreCase("Quit")) {
                            return;
                        }

                         */
                    }
                }

                // If user wants to SIGNUP
                // NOTE: user will not be able to proceed until they provide valid input.
                while (true) {
                    if (loginInput.equalsIgnoreCase("2") || loginInput.equalsIgnoreCase("SIGNUP")) {
                        System.out.println(USERNAME_PROMPT);
                        username = scanner.nextLine();
                        System.out.println(PASSWORD_PROMPT);
                        password = scanner.nextLine();
                        System.out.println(CONFIRM_PASSWORD_PROMPT);
                        confirmPassword = scanner.nextLine();
                        System.out.println(EMAIL_PROMPT);
                        email = scanner.nextLine();
                        while (!(email.contains("@"))) {
                            System.out.println("Invalid email has been entered");
                            System.out.println("Re-enter the email address: ");
                            email = scanner.nextLine();
                        }
                        if (!checkSignUp(username, password, confirmPassword, email)) {
                            System.out.println(SIGNUP_FAILED);
                            continue;
                        } else {
                            System.out.println(SIGNUP_SUCCESSFUL);
                            loginInput = "1";
                        }
                    }
                    break;
                }
            } while (!loginSuccessful);

            email = getEmail(username , password);

            //Asking user if they are a seller or customer
            validInputs[2] = "SELLER";
            validInputs[3] = "CUSTOMER";
            String sellerOrBuyer = getUserInput(SELLER_OR_CUSTOMER, validInputs, scanner);

        /*
        If user indicates they are a customer, then they are shown a list of products as seen in Products.txt.
        User can then choose to filter products by certain parameters as defined in CUSTOMER_MENU.
         */
            ArrayList<Product> products = getProducts(sellerOrBuyer);

            if (sellerOrBuyer.equalsIgnoreCase("1") || sellerOrBuyer.equalsIgnoreCase("SELLER")) {
                Seller seller = new Seller(username, password);
                seller.setEmail(email);
                boolean oof = true;
                while (oof) {
                    System.out.println(SELLER_MENU);
                    String res = scanner.nextLine();
                    switch (res) {
                        case "1":
                            String moreProducts = "";
                            do {
                                System.out.println("Input the name of the product");
                                String nameP = scanner.nextLine();
                                System.out.println("Input the name of the store");
                                String store = scanner.nextLine();
                                System.out.println("Input a short description of the product");
                                String description = scanner.nextLine();
                                boolean error = false;
                                int quantity = 0;
                                do {
                                    System.out.println("Input the quantity of the product");
                                    try {
                                        quantity = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println(ERROR);
                                        error = true;
                                    }
                                } while (error);
                                double price = 0;
                                error = false;
                                do {
                                    System.out.println("Input the price of the product");
                                    try {
                                        price = scanner.nextDouble();
                                        scanner.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println(ERROR);
                                        error = true;
                                    }
                                } while (error);
                                Product product = new Product(nameP, description, quantity, price, store, "Seller");
                                seller.add(product);
                                System.out.println("Do you want to add another product? Y/N");
                                moreProducts = scanner.nextLine();
                            } while (moreProducts.equalsIgnoreCase("Y"));
                            seller.save();
                            break;
                        case "2":
                            System.out.println("Enter the name of the product that you would like to remove:");
                            String name = scanner.nextLine();
                            System.out.println("Enter the store name of the product");
                            String storeName = scanner.nextLine();
                            seller.remove(name, storeName);
                            seller.save();
                            break;
                        case "3":
                            System.out.println("Enter the name of the product that needs to be replaced");
                            String oldName = scanner.nextLine();
                            System.out.println("Input the name of the product");
                            String namePr = scanner.nextLine();
                            System.out.println("Input the name of the store");
                            String storeN = scanner.nextLine();
                            System.out.println("Input a short description of the product");
                            String descriptionN = scanner.nextLine();
                            boolean error = false;
                            int quantityN = 0;
                            do {
                                System.out.println("Input the quantity of the product");
                                try {
                                    quantityN = scanner.nextInt();
                                    scanner.nextLine();
                                } catch (InputMismatchException e) {
                                    System.out.println(ERROR);
                                    error = true;
                                }
                            } while (error);
                            error = false;
                            double priceN = 0;
                            do {
                                System.out.println("Input the price of the product");
                                try {
                                    priceN = scanner.nextDouble();
                                    scanner.nextLine();
                                } catch (InputMismatchException e) {
                                    System.out.println(ERROR);
                                    error = true;
                                }
                            } while (error);
                            Product productNew = new Product(namePr, descriptionN, quantityN, priceN, storeN, "Seller");
                            Product oldProduct = null;
                            for (int i = 0; i < products.size(); i++) {
                                if (products.get(i).getName().equalsIgnoreCase(oldName)) {
                                    oldProduct = products.get(i);
                                }
                            }
                            seller.edit(oldProduct, productNew);
                            seller.save();
                            break;
                        case "4":
                            System.out.println("Enter the name of the store to search");
                            String stName = scanner.nextLine();
                            String[] toPrint = Customer.itemsInShoppingCart(stName);
                            for (int i = 0; i < toPrint.length; i++) {
                                String[] purchaseDetails = toPrint[i].split(";");
                                System.out.printf("Name: %s Product in Cart: %s Price: %.2f Quantity Selected: %d\n" ,
                                        purchaseDetails[0] , purchaseDetails[1] , Double.parseDouble(purchaseDetails[4]) ,
                                        Integer.parseInt(purchaseDetails[3]));
                            }
                            break;
                        case "5":
                            String[] sales = seller.viewSalesByStore();
                            for (int i = 0; i < sales.length; i++) {
                                System.out.println(sales[i]);
                            }
                            break;
                        case "6":
                            boolean forCSV = false;
                            while (!forCSV) {
                                System.out.println("Enter file path");
                                String fileName = scanner.nextLine();
                                try {
                                    seller.importCSV(fileName);
                                    forCSV = true;
                                } catch (FileNotFoundException e) {
                                    System.out.println("1. Input a proper filepath\n2. Return to Menu");
                                    if (scanner.nextLine().equals("2")) {
                                        break;
                                    } else {
                                        forCSV = false;
                                    }
                                }
                            }
                        case "7":
                            validInputs = new String[4];
                            validInputs[0] = "YES";
                            validInputs[1] = "Y";
                            validInputs[2] = "NO";
                            validInputs[3] = "N";
                            String input = getUserInput("Are you sure you want to Exit? (Y/N)", validInputs, scanner);
                            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                                return;
                            }
                            break;

                        default:
                            System.out.println("Please enter a valid input!");
                    }
                    //}
                }
            }
            if (sellerOrBuyer.equalsIgnoreCase("2") || sellerOrBuyer.equalsIgnoreCase("CUSTOMER")) {

                Customer c = new Customer(username, password);
                c.setEmail(email);

            /*
            Printing products from Products.txt one by one.
            NOTE: I implemented a 1.5s (1500ms) delay while printing products. Feel free to remove this delay if
                    you'd like.
             */

                do {
                    boolean looping = true;
                    while (looping) {
                        System.out.println(PRODUCTS_FOR_SALE);
                        String selection = "";
                        for (Product p : products) {
                            System.out.printf(Integer.toString(products.indexOf(p) + 1) + ". ");
                            System.out.println(p.initialString());

                        }
                        if (products.size() == 0) {
                            System.out.println("THERE ARE NO PRODUCTS RIGHT NOW");
                        }
                        selection = "0";
                        selectionx:
                        if (products.size() != 0) {
                            while (true) {
                                try {
                                    System.out.println("Enter int: ");
                                    selection = scanner.nextLine();
                                    if (Integer.parseInt(selection) <= products.size()) {
                                        break selectionx;
                                    } else {
                                        System.out.println(ERROR);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println(ERROR);
                                }
                            }
                        }
                        try {
                            System.out.println(products.get(Integer.parseInt(selection) - 1).toString());
                        } catch (IndexOutOfBoundsException e) {
                            e.getMessage();
                        }
                        block:
                        while (true) {
                            System.out.println("1. Add item to Shopping Cart\n2. Return to Products\n3. Menu\n4. Quit");
                            String inputAfterSelectiveShow = scanner.nextLine();
                            if (inputAfterSelectiveShow.equalsIgnoreCase("1")) {
                                validInputs = null;
                                for (Product p : products) {
                                    if (p.getName().toLowerCase().equalsIgnoreCase(selection.toLowerCase())) {
                                        int quantityAvail = p.getQuantity();
                                        validInputs = new String[quantityAvail];
                                        for (int i = 0; i <= quantityAvail; i++) {
                                            validInputs[i] = Integer.toString(i + 1);
                                        }
                                    }
                                }
                                //System.out.println();
                                int numberOfProducts = Integer.parseInt(getUserInput(GET_NUM_TO_PURCHASE, validInputs, scanner));
                                products.get(Integer.parseInt(selection) - 1).setNumberOfProductsSelected(numberOfProducts);
                                if (products.get(Integer.parseInt(selection) - 1).getNumberOfProductsSelected() > products.get(Integer.parseInt(selection) - 1).getProductsAvailable()) {
                                    products.get(Integer.parseInt(selection) - 1).setNumberOfProductsSelected(0);
                                    System.out.printf("%d units are not available. There are only %d available units\n"
                                            , numberOfProducts, products.get(Integer.parseInt(selection) - 1).getProductsAvailable());
                                    break;
                                } else {
                                    c.addToShoppingCart(products.get(Integer.parseInt(selection) - 1).toStringForShoppingCart(username, password));
                                    System.out.printf(CONFIRM_ITEM_ADDED_TO_CART + "\n", products.get(Integer.parseInt(selection) - 1).getName());
                                    c.saveShoppingCart();
                                    System.out.println("1. Return to Products\n2. Menu\n3. Quit");
                                    String afterBuying = scanner.nextLine();
                                    if (afterBuying.equalsIgnoreCase("2")) {
                                        looping = false;
                                    } else if (afterBuying.equalsIgnoreCase("3")) {
                                        validInputs = new String[4];
                                        validInputs[0] = "YES";
                                        validInputs[1] = "Y";
                                        validInputs[2] = "NO";
                                        validInputs[3] = "N";
                                        String input = getUserInput("Are you sure you want to Exit? (Y/N)", validInputs, scanner);
                                        if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                                            c.saveShoppingCart();
                                            return;
                                        }
                                    }
                                }
                                break block;


                            } else if (inputAfterSelectiveShow.equalsIgnoreCase("2")) {
                                break block;
                            } else if (inputAfterSelectiveShow.equalsIgnoreCase("3")) {
                                looping = false;
                                break block;
                            } else if (inputAfterSelectiveShow.equalsIgnoreCase("4")) {
                                validInputs = new String[4];
                                validInputs[0] = "YES";
                                validInputs[1] = "Y";
                                validInputs[2] = "NO";
                                validInputs[3] = "N";
                                String input = getUserInput("Are you sure you want to Exit? (Y/N)", validInputs, scanner);
                                if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                                    c.saveShoppingCart();
                                    return;
                                }
                                break;
                            } else {
                                System.out.println(ERROR);
                            }
                        }
                    }
                /*
                Getting customer input (customer can choose btwm 14 options shown in CUSTOMER_MENU).
                */
                    menu:
                    do {
                        validInputs = new String[17];
                        for (int i = 0; i < validInputs.length; i++) {
                            validInputs[i] = Integer.toString(i + 1);
                        }
                        String input = getUserInput(CUSTOMER_MENU, validInputs, scanner).toLowerCase();

            /*
            Performing an action based on what customer input is.
             */
                        validInputs = null;
                        ArrayList<Product> validProducts = new ArrayList<Product>();
                        switch (input) {
                            case "1":
                                break menu;
                            case "2":

                                input = getUserInput(SEARCH_BY_PRODUCT_PROMPT, validInputs, scanner).toLowerCase();
                                for (Product p : products) {
                                    //System.out.println(p.getName().toLowerCase());
                                    if (p.getName().toLowerCase().contains(input)) {
                                        validProducts.add(p);
                                    }
                                }
                                if (validProducts.size() == 0) {

                                    System.out.printf("Sorry! There were no Products with names containing \"%s.\"\n"
                                            , input);
                                } else {
                                    System.out.println("\n----------------------------------------------");
                                    System.out.printf("List of Products with Names containing \"%s.\"\n", input);
                                    System.out.println("-----------------------------------------------");
                                    for (Product p : validProducts) {
                                        System.out.println(p.toString());
                                    }
                                }


                                break;
                            case "3":
                                input = getUserInput(SEARCH_BY_STORE_NAME_PROMPT, validInputs, scanner).toLowerCase();
                                for (Product p : products) {
                                    if (p.getStoreName().toLowerCase().contains(input)) {
                                        validProducts.add(p);
                                    }
                                }

                                if (validProducts.size() == 0) {
                                    System.out.printf("Sorry! There were no Products from Store Names containing \"%s.\"\n", input);
                                } else {
                                    System.out.println("\n----------------------------------------------");
                                    System.out.printf("List of Products from Stores containing \"%s.\"\n", input);
                                    System.out.println("-----------------------------------------------");
                                    for (Product p : validProducts) {
                                        System.out.println(p.toString());
                                    }
                                }

                                break;
                            case "4":
                                input = getUserInput(SEARCH_BY_ITEM_DESC_PROMPT, validInputs, scanner).toLowerCase();
                                for (Product p : products) {
                                    if (p.getDescription().toLowerCase().contains(input)) {
                                        validProducts.add(p);
                                    }
                                }

                                if (validProducts.size() == 0) {
                                    System.out.printf("Sorry! There were no Products with Item Descriptions containing \"%s.\"\n",
                                            input);
                                } else {
                                    System.out.println("\n----------------------------------------------");
                                    System.out.printf("List of Products with Item Descriptions containing \"%s.\"\n", input);
                                    System.out.println("-----------------------------------------------");
                                    for (Product p : validProducts) {
                                        System.out.println(p.toString());
                                    }
                                }

                                break;
                            case "5":
                                for (int i = 0; i < products.size() - 1; i++) {
                                    int minIndex = i;
                                    for (int j = i + 1; j < products.size(); j++) {
                                        if (products.get(minIndex).getPrice() < products.get(j).getPrice()) {
                                            minIndex = j;
                                        }
                                    }
                                    Product temp = products.get(minIndex);
                                    products.set(minIndex, products.get(i));
                                    products.set(i, temp);
                                }
                                System.out.println("\n-----------------------------------------------");
                                System.out.printf("List of Products by Price (High to Low)\n");
                                System.out.println("-----------------------------------------------");
                                for (Product p : products) {
                                    System.out.println(p.toString());
                                }
                                break;
                            case "6":
                                for (int i = 0; i < products.size() - 1; i++) {
                                    int minIndex = i;
                                    for (int j = i + 1; j < products.size(); j++) {
                                        if (products.get(minIndex).getPrice() > products.get(j).getPrice()) {
                                            minIndex = j;
                                        }
                                    }
                                    Product temp = products.get(minIndex);
                                    products.set(minIndex, products.get(i));
                                    products.set(i, temp);
                                }
                                System.out.println("\n-----------------------------------------------");
                                System.out.printf("List of Products by Price (Low to High)\n");
                                System.out.println("-----------------------------------------------");
                                for (Product p : products) {
                                    System.out.println(p.toString());
                                }
                                break;
                            case "7":
                                for (int i = 0; i < products.size() - 1; i++) {
                                    int minIndex = i;
                                    for (int j = i + 1; j < products.size(); j++) {
                                        if (products.get(minIndex).getQuantity() < products.get(j).getQuantity()) {
                                            minIndex = j;
                                        }
                                    }
                                    Product temp = products.get(minIndex);
                                    products.set(minIndex, products.get(i));
                                    products.set(i, temp);
                                }
                                System.out.println("\n-----------------------------------------------------");
                                System.out.printf("List of Products by Quantity Available (High to Low)\n");
                                System.out.println("------------------------------------------------------");
                                for (Product p : products) {
                                    System.out.println(p.toString());
                                }
                                break;
                            case "8":
                                for (int i = 0; i < products.size() - 1; i++) {
                                    int minIndex = i;
                                    for (int j = i + 1; j < products.size(); j++) {
                                        if (products.get(minIndex).getQuantity() > products.get(j).getQuantity()) {
                                            minIndex = j;
                                        }
                                    }
                                    Product temp = products.get(minIndex);
                                    products.set(minIndex, products.get(i));
                                    products.set(i, temp);
                                }
                                System.out.println("\n---------------------------------------------------");
                                System.out.printf("List of Products by Quantity Available (Low to High)\n");
                                System.out.println("------------------------------------------------------");
                                for (Product p : products) {
                                    System.out.println(p.toString());
                                }
                                break;
                            case "9":
                                input = getUserInput(SHOPPING_CART_PROMPT, validInputs, scanner).toLowerCase();
                                boolean foundItem = false;
                                System.out.println("How many do you want to purchase?");
                                int numberOfProducts = scanner.nextInt();
                                scanner.nextLine();
                                for (Product p : products) {
                                    if (p.getName().toLowerCase().equalsIgnoreCase(input)) {
                                        foundItem = true;
                                        p.setNumberOfProductsSelected(numberOfProducts);
                                        if (p.getNumberOfProductsSelected() > p.getProductsAvailable()) {
                                            p.setNumberOfProductsSelected(0);
                                            System.out.printf("%d units are not available. There are only %d available units\n"
                                                    , numberOfProducts, p.getProductsAvailable());
                                            break;
                                        } else {
                                            c.addToShoppingCart(p.toStringForShoppingCart(username, password));
                                            System.out.printf(CONFIRM_ITEM_ADDED_TO_CART + "\n", p.getName());
                                            c.saveShoppingCart();
                                        }
                                    }
                                }

                                if (!foundItem) {
                                    System.out.printf("Sorry! There were no Products with names containing \"%s.\"\n",
                                            input);
                                }
                                break;
                            case "10":
                                System.out.println("---------------");
                                System.out.println("SHOPPING CART:");
                                System.out.println("---------------");
                                String returning = "";
                                for (String[] strings : c.getAlreadyInCart()) {
                                    returning += c.toStringForShoppingCartShow(strings) + "\n";
                                }
                                System.out.println(returning);
                                validInputs = new String[14];
                                for (int i = 0; i < validInputs.length; i++) {
                                    validInputs[i] = Integer.toString(i + 1);
                                }
                                input = getUserInput(SHOPPING_CART_OPTIONS, validInputs, scanner);

                                if (input.equalsIgnoreCase("1")) {
                                    validInputs = null;
                                    all:
                                    while (true) {
                                        input = getUserInput("Enter Product NAME:", validInputs, scanner);

                                        for (String[] s : c.getAlreadyInCart()) {
                                            if (s[2].equalsIgnoreCase(input)) {
                                                //s[3] = Integer.toString(Integer.parseInt())
                                                products = getProducts("Customer");
                                                String[] temp = null;
                                                for (Product product : products) {
                                                    if (product.getName().equalsIgnoreCase(input)) {
                                                        c.purchaseOneProduct(product.toStringForShoppingCart(username, password));
                                                        product.setProductsAvailable(product.getProductsAvailable() -
                                                                product.getNumberOfProductsSelected());
                                                        product.setQuantity(product.getQuantity() -
                                                                product.getNumberOfProductsSelected());
                                                        save(products);
                                                    }

                                                }

                                                break all;
                                            }
                                        }

                                    }


                                }

                                if (input.equalsIgnoreCase("2") ||
                                        input.equalsIgnoreCase("Purchase Shopping Cart")) {
                                    for (Product product : products) {
                                        for (String[] strings : c.getAlreadyInCart()) {
                                            if (product.getName().equalsIgnoreCase(strings[2]) &&
                                                    product.getNumberOfProductsSelected() <= product.getProductsAvailable()) {
                                                product.setProductsAvailable(product.getProductsAvailable() -
                                                        product.getNumberOfProductsSelected());
                                                product.setQuantity(product.getQuantity() -
                                                        product.getNumberOfProductsSelected());
                                            }
                                        }
                                    }
                                    c.completePurchase();
                                    System.out.println("PURCHASE COMPLETED");
                                    System.out.println("Do you want to \n1. Exit?\n2. Return to Menu?");
                                    String nextLine = scanner.nextLine();
                                    if (nextLine.equalsIgnoreCase("1") ||
                                            nextLine.equalsIgnoreCase("Exit")) {
                                        c.saveShoppingCart();
                                        save(products);
                                        return;
                                    }

                                }
                                if (input.equalsIgnoreCase("4") ||
                                        input.equalsIgnoreCase("Quit")) {
                                    c.saveShoppingCart();
                                    save(products);
                                    return;
                                }
                                break;
                            case "11":
                                String[] purchaseHistory = c.viewPastPurchases(username, password);
                                for (int i = 0; i < purchaseHistory.length; i++) {
                                    String[] toBePrinted = purchaseHistory[i].split(";");
                                    Product product = new Product(toBePrinted[2], toBePrinted[3], Integer.parseInt(toBePrinted[4]),
                                            Double.parseDouble(toBePrinted[5]), toBePrinted[6], "Customer");
                                    System.out.println(product.toString());
                                }

                                break;

                            case "12":
                                input = getUserInput(SHOPPING_CART_OPTIONS, validInputs, scanner);

                                if (input.equalsIgnoreCase("1")) {
                                    validInputs = null;
                                    all:
                                    while (true) {
                                        input = getUserInput("Enter Product NAME:", validInputs, scanner);

                                        for (String[] s : c.getAlreadyInCart()) {
                                            if (s[2].equalsIgnoreCase(input)) {
                                                //s[3] = Integer.toString(Integer.parseInt())
                                                products = getProducts("Customer");
                                                String[] temp = null;
                                                for (Product product : products) {
                                                    if (product.getName().equalsIgnoreCase(input)) {
                                                        c.purchaseOneProduct(product.toStringForShoppingCart(username, password));
                                                        product.setProductsAvailable(product.getProductsAvailable() -
                                                                product.getNumberOfProductsSelected());
                                                        product.setQuantity(product.getQuantity() -
                                                                product.getNumberOfProductsSelected());
                                                        save(products);
                                                    }

                                                }

                                                break all;
                                            }
                                        }

                                    }


                                }

                                if (input.equalsIgnoreCase("2") ||
                                        input.equalsIgnoreCase("Purchase Shopping Cart")) {
                                    for (Product product : products) {

                                        for (String[] strings : c.getAlreadyInCart()) {
                                            if (product.getName().equalsIgnoreCase(strings[2]) &&
                                                    product.getNumberOfProductsSelected() <= product.getProductsAvailable()) {
                                                product.setProductsAvailable(product.getProductsAvailable() -
                                                        product.getNumberOfProductsSelected());
                                                product.setQuantity(product.getQuantity() -
                                                        product.getNumberOfProductsSelected());
                                            }
                                        }
                                    }
                                    c.completePurchase();
                                    save(products);
                                    System.out.println("PURCHASE COMPLETED");
                                    System.out.println("Do you want to \n1. Exit?\n2. Return to Menu?");
                                    String nextLine = scanner.nextLine();
                                    if (nextLine.equalsIgnoreCase("1") ||
                                            nextLine.equalsIgnoreCase("Exit")) {
                                        c.saveShoppingCart();
                                        return;
                                    }

                                }
                                if (input.equalsIgnoreCase("4") ||
                                        input.equalsIgnoreCase("Quit")) {
                                    c.saveShoppingCart();
                                    return;
                                }
                                break;
                            case "13":
                                validInputs = null;
                                System.out.println("Enter NAME of product to remove!");
                                String itemToRemove = scanner.nextLine();
                                ArrayList<String[]> cart = c.getAlreadyInCart();
                                boolean found = false;
                                for (int i = 0; i < cart.size(); i++) {
                                    if (cart.get(i)[2].toLowerCase().contains(itemToRemove.toLowerCase())) {
                                        found = true;
                                        cart.remove(i);
                                        System.out.println("Item Removed from Shopping Cart");
                                        c.saveShoppingCart();
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("The item you wanted to remove is not in your shopping cart!");
                                }
                                break;
                            case "14":
                                System.out.println("Enter Filepath: ");
                                String filePath = scanner.nextLine();
                                c.exportCSV(username, password, filePath);
                                System.out.println("PURCHASE HISTORY FILE GENERATED");
                                break;
                            case "15":
                                String oldUsername = username;
                                String oldPassword = password;
                                validInputs = new String[4];
                                validInputs[0] = "1";
                                validInputs[1] = "username";
                                validInputs[2] = "2";
                                validInputs[3] = "password";
                                String thingToChange = getUserInput("Would you like to change your\n1. username?\n2.password?", validInputs, scanner);
                                if (thingToChange.equalsIgnoreCase("1") || thingToChange.equalsIgnoreCase("username")) {
                                    System.out.println("Enter new username: ");
                                    username = scanner.nextLine();
                                    thingToChange = "1";
                                }
                                if (thingToChange.equalsIgnoreCase("2") || thingToChange.equalsIgnoreCase("password")) {
                                    System.out.println("Enter new password: ");
                                    password = scanner.nextLine();
                                    thingToChange = "2";
                                }
                                ArrayList<String> lines = new ArrayList<String>();
                                try (BufferedReader bfr = new BufferedReader(new FileReader(new File("Accounts.txt")))) {
                                    String line = bfr.readLine();
                                    while (true) {
                                        if (line == null) {
                                            break;
                                        }

                                        if (thingToChange.equals("1") || thingToChange.equals("2")) {
                                            line = username + "," + password;
                                        }
                                        lines.add(line);
                                        line = bfr.readLine();
                                    }
                                    bfr.close();
                                } catch (IOException e) {
                                    System.out.println("Error Editing Account Information");
                                }

                                try (PrintWriter pw = new PrintWriter(new FileWriter(new File("Accounts.txt")))) {
                                    for (String s: lines) {
                                        pw.println(s);
                                    }
                                    pw.close();
                                } catch (IOException e) {
                                    System.out.println("Error Editing Account Information!");
                                }

                                editFiles("ProductDatabase.csv" , oldUsername , oldPassword , username , password);
                                editFiles("PurchaseHistory" , oldUsername , oldPassword , username , password);
                                editFiles("ShoppingCart" , oldUsername , oldPassword , username , password);
                                break;
                            case "16":
                                validInputs = new String[4];
                                validInputs[0] = "YES";
                                validInputs[1] = "Y";
                                validInputs[2] = "NO";
                                validInputs[3] = "N";
                                String confirm = getUserInput("Are you sure you want to DELETE your account (Y/N)?", validInputs, scanner);
                                if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("YES")) {
                                    ArrayList<String> newlines = new ArrayList<String>();
                                    try (BufferedReader bfr = new BufferedReader(new FileReader(new File("Accounts.txt")))) {
                                        String line = bfr.readLine();
                                        while (true) {
                                            if (line == null) {
                                                break;
                                            }

                                            if (!line.split(",")[0].equalsIgnoreCase(username) &&
                                                    !line.split(",")[1].equals(password)) {
                                                newlines.add(line);
                                            }
                                            line = bfr.readLine();
                                        }
                                        bfr.close();
                                    } catch (IOException e) {
                                        System.out.println("Error Editing Account Information");
                                    }

                                    try (PrintWriter pw = new PrintWriter(new FileWriter(new File("Accounts.txt")))) {
                                        for (String s: newlines) {
                                            pw.println(s);
                                        }
                                        pw.close();
                                    } catch (IOException e) {
                                        System.out.println("Error Editing Account Information!");
                                    }
                                }
                            case "17":
                                validInputs = new String[4];
                                validInputs[0] = "YES";
                                validInputs[1] = "Y";
                                validInputs[2] = "NO";
                                validInputs[3] = "N";
                                input = getUserInput("Are you sure you want to Exit? (Y/N)", validInputs, scanner);
                                if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                                    c.saveShoppingCart();
                                    return;
                                }
                                break;

                            default:
                                System.out.println("Please enter a valid option");

                        }
                    } while (true);

                } while (true);


            }
            System.out.println("Thank you for using the Marketplace!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
