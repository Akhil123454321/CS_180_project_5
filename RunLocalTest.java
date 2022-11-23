import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Summer 2022</p>
 *
 * @author Purdue CS
 * @version June 13, 2022
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2022</p>
     *
     * @author Purdue CS
     * @version June 13, 2022
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testOne() {

            // Set the input
            String input = "2" + System.lineSeparator() + "mockUser" + System.lineSeparator() + "akhil123" + System.lineSeparator() +
                    "akhil123" + System.lineSeparator() + "akhil@purdue.edu" + System.lineSeparator() + "mockUser"
                    + System.lineSeparator() + "akhil123" + System.lineSeparator() + "2" + System.lineSeparator() + "8"
                    + System.lineSeparator() + "4" + System.lineSeparator() + "y" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to Marketplace!\n" +
                    "1. Login\n" +
                    "2. Sign Up\n" +
                    "3. Quit\n" +
                    "2\n" +
                    "Username: \n" +
                    "mockUser\n" +
                    "Password: \n" +
                    "akhil123\n" +
                    "Confirm Password: \n" +
                    "akhil123\n" +
                    "Email: \n" +
                    "akhil@purdue.edu\n" +
                    "Sign-Up Successful! Please Login with your new account!\n" +
                    "\n" +
                    "Username: \n" +
                    "mockUser\n" +
                    "Password: \n" +
                    "akhil123\n" +
                    "Log-in Successful!\n" +
                    "\n" +
                    "Are you a\n" +
                    "1. Seller?\n" +
                    "2. Customer?\n" +
                    "2\n" +
                    "\n" +
                    "------------------------\n" +
                    "Products for Sale:\n" +
                    "------------------------\n" +
                    "1. Name: Watch\n" +
                    "   Price: $19999.99\n" +
                    "   StoreName: Rolex\n" +
                    "\n" +
                    "2. Name: Watch\n" +
                    "   Price: $83.96\n" +
                    "   StoreName: Fossil\n" +
                    "\n" +
                    "3. Name: Shoes\n" +
                    "   Price: $39.08\n" +
                    "   StoreName: CrocsStore\n" +
                    "\n" +
                    "4. Name: Charms for Shoes\n" +
                    "   Price: $29.99\n" +
                    "   StoreName: Crocs\n" +
                    "\n" +
                    "5. Name: Watch\n" +
                    "   Price: $175.00\n" +
                    "   StoreName: Vincero\n" +
                    "\n" +
                    "6. Name: Running Shoes\n" +
                    "   Price: $29.99\n" +
                    "   StoreName: Adidas\n" +
                    "\n" +
                    "7. Name: Track Suit\n" +
                    "   Price: $35.98\n" +
                    "   StoreName: Adidas\n" +
                    "\n" +
                    "8. Name: Lined Neo Puff Winter Boots\n" +
                    "   Price: $48.74\n" +
                    "   StoreName: Crocs\n" +
                    "\n" +
                    "Enter int: \n" +
                    "8\n" +
                    "Name: Lined Neo Puff Winter Boots\n" +
                    "Description: Boots perfect for all your winter needs\n" +
                    "Quantity: 150\n" +
                    "Price: $48.74\n" +
                    "StoreName: Crocs\n" +
                    "Seller Email: pkukunuru@gmail.com\n" +
                    "\n" +
                    "1. Add item to Shopping Cart\n" +
                    "2. Return to Products\n" +
                    "3. Menu\n" +
                    "4. Quit\n" +
                    "4\n" +
                    "Are you sure you want to Exit? (Y/N)\n" +
                    "y\n";
            // Runs the program with the input values
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void tesstTwo() {

            // Set the input
            String input = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "user1password" +
                    System.lineSeparator() + "2" + System.lineSeparator() + "6" + System.lineSeparator() + "1" +
                    System.lineSeparator() + "5" + System.lineSeparator() + "2" + System.lineSeparator() + "12" +
                    System.lineSeparator() + "2" + System.lineSeparator() + "2" + System.lineSeparator() + "1" +
                    System.lineSeparator() + "6" + System.lineSeparator() + "4" + System.lineSeparator() + "y" +
                    System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to Marketplace!\n" +
                    "1. Login\n" +
                    "2. Sign Up\n" +
                    "3. Quit\n" +
                    "1\n" +
                    "Username: \n" +
                    "user\n" +
                    "Password: \n" +
                    "praneeth\n" +
                    "Log-in Successful!\n" +
                    "\n" +
                    "Are you a\n" +
                    "1. Seller?\n" +
                    "2. Customer?\n" +
                    "2\n" +
                    "\n" +
                    "------------------------\n" +
                    "Products for Sale:\n" +
                    "------------------------\n" +
                    "1. Name: Watch\n" +
                    "   Price: $19999.99\n" +
                    "   StoreName: Rolex\n" +
                    "\n" +
                    "2. Name: Watch\n" +
                    "   Price: $83.96\n" +
                    "   StoreName: Fossil\n" +
                    "\n" +
                    "3. Name: Shoes\n" +
                    "   Price: $39.08\n" +
                    "   StoreName: CrocsStore\n" +
                    "\n" +
                    "4. Name: Charms for Shoes\n" +
                    "   Price: $29.99\n" +
                    "   StoreName: Crocs\n" +
                    "\n" +
                    "5. Name: Watch\n" +
                    "   Price: $175.00\n" +
                    "   StoreName: Vincero\n" +
                    "\n" +
                    "6. Name: Running Shoes\n" +
                    "   Price: $29.99\n" +
                    "   StoreName: Adidas\n" +
                    "\n" +
                    "7. Name: Track Suit\n" +
                    "   Price: $35.98\n" +
                    "   StoreName: Adidas\n" +
                    "\n" +
                    "8. Name: Lined Neo Puff Winter Boots\n" +
                    "   Price: $48.74\n" +
                    "   StoreName: Crocs\n" +
                    "\n" +
                    "Enter int: \n" +
                    "6\n" +
                    "Name: Running Shoes\n" +
                    "Description: Women's Cloudfoam Pure-2.0 Running Shoe\n" +
                    "Quantity: 25\n" +
                    "Price: $29.99\n" +
                    "StoreName: Adidas\n" +
                    "Seller Email: pkukunuru@gmail.com\n" +
                    "\n" +
                    "1. Add item to Shopping Cart\n" +
                    "2. Return to Products\n" +
                    "3. Menu\n" +
                    "4. Quit\n" +
                    "1\n" +
                    "How many do you want to purchase?\n" +
                    "5\n" +
                    "Running Shoes added to Cart!\n" +
                    "1. Return to Products\n" +
                    "2. Menu\n" +
                    "3. Quit\n" +
                    "2\n" +
                    "------------------------\n" +
                    "Select an Option (1-13)\n" +
                    "------------------------\n" +
                    "Search by:\n" +
                    "1. Browse Products\n" +
                    "2. Product Name\n" +
                    "3. Store Name\n" +
                    "4. Item Description\n" +
                    "\n" +
                    "Sort By:\n" +
                    "5. Price (High to Low)\n" +
                    "6. Price (Low to High)\n" +
                    "7. Quantity Available (High to Low)\n" +
                    "8. Quantity Available (Low to High)\n" +
                    "\n" +
                    "Other Actions:\n" +
                    "9. Add item to Shopping Cart\n" +
                    "10. View Shopping Cart\n" +
                    "11. View Past Purchases\n" +
                    "12. Make a Purchase\n" +
                    "13. Remove Itemfrom Shopping Cart\n" +
                    "14. Export CSV file containing past purchases\n" +
                    "15. Edit Account\n" +
                    "16. Delete Account\n" +
                    "17. Exit\n" +
                    "12\n" +
                    "------------------------\n" +
                    "Select an Option (1-4)\n" +
                    "------------------------\n" +
                    "1. Purchase One Item\n" +
                    "2. Purchase Shopping Cart\n" +
                    "3. Return to Menu\n" +
                    "4. Quit\n" +
                    "2\n" +
                    "PURCHASE COMPLETED\n" +
                    "Do you want to \n" +
                    "1. Exit?\n" +
                    "2. Return to Menu?\n" +
                    "2\n" +
                    "------------------------\n" +
                    "Select an Option (1-13)\n" +
                    "------------------------\n" +
                    "Search by:\n" +
                    "1. Browse Products\n" +
                    "2. Product Name\n" +
                    "3. Store Name\n" +
                    "4. Item Description\n" +
                    "\n" +
                    "Sort By:\n" +
                    "5. Price (High to Low)\n" +
                    "6. Price (Low to High)\n" +
                    "7. Quantity Available (High to Low)\n" +
                    "8. Quantity Available (Low to High)\n" +
                    "\n" +
                    "Other Actions:\n" +
                    "9. Add item to Shopping Cart\n" +
                    "10. View Shopping Cart\n" +
                    "11. View Past Purchases\n" +
                    "12. Make a Purchase\n" +
                    "13. Remove Itemfrom Shopping Cart\n" +
                    "14. Export CSV file containing past purchases\n" +
                    "15. Edit Account\n" +
                    "16. Delete Account\n" +
                    "17. Exit\n" +
                    "1\n" +
                    "\n" +
                    "------------------------\n" +
                    "Products for Sale:\n" +
                    "------------------------\n" +
                    "1. Name: Watch\n" +
                    "   Price: $19999.99\n" +
                    "   StoreName: Rolex\n" +
                    "\n" +
                    "2. Name: Watch\n" +
                    "   Price: $83.96\n" +
                    "   StoreName: Fossil\n" +
                    "\n" +
                    "3. Name: Shoes\n" +
                    "   Price: $39.08\n" +
                    "   StoreName: CrocsStore\n" +
                    "\n" +
                    "4. Name: Charms for Shoes\n" +
                    "   Price: $29.99\n" +
                    "   StoreName: Crocs\n" +
                    "\n" +
                    "5. Name: Watch\n" +
                    "   Price: $175.00\n" +
                    "   StoreName: Vincero\n" +
                    "\n" +
                    "6. Name: Running Shoes\n" +
                    "   Price: $29.99\n" +
                    "   StoreName: Adidas\n" +
                    "\n" +
                    "7. Name: Track Suit\n" +
                    "   Price: $35.98\n" +
                    "   StoreName: Adidas\n" +
                    "\n" +
                    "8. Name: Lined Neo Puff Winter Boots\n" +
                    "   Price: $48.74\n" +
                    "   StoreName: Crocs\n" +
                    "\n" +
                    "Enter int: \n" +
                    "6\n" +
                    "Name: Running Shoes\n" +
                    "Description: Women's Cloudfoam Pure-2.0 Running Shoe\n" +
                    "Quantity: 20\n" +
                    "Price: $29.99\n" +
                    "StoreName: Adidas\n" +
                    "Seller Email: pkukunuru@gmail.com\n" +
                    "\n" +
                    "1. Add item to Shopping Cart\n" +
                    "2. Return to Products\n" +
                    "3. Menu\n" +
                    "4. Quit\n" +
                    "4\n" +
                    "Are you sure you want to Exit? (Y/N)\n" +
                    "y\n";

            // Runs the program with the input values
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void tesstThree() {

            // Set the input
            String input = "3" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to Marketplace!\n" +
                    "1. Login\n" +
                    "2. Sign Up\n" +
                    "3. Quit\n" +
                    "3\n";

            // Runs the program with the input values
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testFour() {

            // Set the input
            String input = "1" + System.lineSeparator() + "mockUser" + System.lineSeparator() + "akhil123" + System.lineSeparator() + "2" + System.lineSeparator() + "4" + System.lineSeparator() + "y" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to Marketplace!\n" +
                    "1. Login\n" +
                    "2. Sign Up\n" +
                    "3. Quit\n" +
                    "1\n" +
                    "Username: \n" +
                    "mockUser\n" +
                    "Password: \n" +
                    "akhil123\n" +
                    "Log-in Successful!\n" +
                    "\n" +
                    "Are you a\n" +
                    "1. Seller?\n" +
                    "2. Customer?\n" +
                    "2\n" +
                    "\n" +
                    "------------------------\n" +
                    "Products for Sale:\n" +
                    "------------------------\n" +
                    "THERE ARE NO PRODUCTS RIGHT NOW\n" +
                    "1. Add item to Shopping Cart\n" +
                    "2. Return to Products\n" +
                    "3. Menu\n" +
                    "4. Quit\n" +
                    "4\n" +
                    "Are you sure you want to Exit? (Y/N)\n" +
                    "y\n";

            // Runs the program with the input values
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testFive() {

            // Set the input
            String input = "1" + System.lineSeparator() + "mockUser" + System.lineSeparator() + "akhil123" + System.lineSeparator() + "1" + System.lineSeparator() + "1" + System.lineSeparator() + "galaxy buds" + System.lineSeparator() + "samsung" + System.lineSeparator() + "wireless earbuds for all devices" + System.lineSeparator() + "20" + System.lineSeparator() + "120" + System.lineSeparator() + "n" + System.lineSeparator() + "7" + System.lineSeparator() + "y" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to Marketplace!\n" +
                    "1. Login\n" +
                    "2. Sign Up\n" +
                    "3. Quit\n" +
                    "1\n" +
                    "Username: \n" +
                    "mockUser\n" +
                    "Password: \n" +
                    "akhil123\n" +
                    "Log-in Successful!\n" +
                    "\n" +
                    "Are you a\n" +
                    "1. Seller?\n" +
                    "2. Customer?\n" +
                    "1\n" +
                    "------------------------\n" +
                    "Select an Option (1-6)\n" +
                    "------------------------\n" +
                    "1. Add Product\n" +
                    "2. Remove Product\n" +
                    "3. Edit Product\n" +
                    "4. View Products in Shopping Cart\n" +
                    "5. View Revenue by Store\n" +
                    "6: Import CSV\n" +
                    "7. Exit\n" +
                    "1\n" +
                    "Input the name of the product\n" +
                    "galaxy buds\n" +
                    "Input the name of the store\n" +
                    "samsung\n" +
                    "Input a short description of the product\n" +
                    "wireless earbuds for all devices\n" +
                    "Input the quantity of the product\n" +
                    "20\n" +
                    "Input the price of the product\n" +
                    "120\n" +
                    "Do you want to add another product? Y/N\n" +
                    "n\n" +
                    "------------------------\n" +
                    "Select an Option (1-6)\n" +
                    "------------------------\n" +
                    "1. Add Product\n" +
                    "2. Remove Product\n" +
                    "3. Edit Product\n" +
                    "4. View Products in Shopping Cart\n" +
                    "5. View Revenue by Store\n" +
                    "6: Import CSV\n" +
                    "7. Exit\n" +
                    "7\n" +
                    "Are you sure you want to Exit? (Y/N)\n" +
                    "y\n";

            // Runs the program with the input values
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testSix() {

            // Set the input
            String input = "1" + System.lineSeparator() + "mockUser" + System.lineSeparator() + "akhil123" + System.lineSeparator() + "1" + System.lineSeparator() + "2" + System.lineSeparator() + "airpods" + System.lineSeparator() + "apple" + System.lineSeparator() + "7" + System.lineSeparator() + "y" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to Marketplace!\n" +
                    "1. Login\n" +
                    "2. Sign Up\n" +
                    "3. Quit\n" +
                    "1\n" +
                    "Username: \n" +
                    "mockUser\n" +
                    "Password: \n" +
                    "akhil123\n" +
                    "Log-in Successful!\n" +
                    "\n" +
                    "Are you a\n" +
                    "1. Seller?\n" +
                    "2. Customer?\n" +
                    "1\n" +
                    "------------------------\n" +
                    "Select an Option (1-6)\n" +
                    "------------------------\n" +
                    "1. Add Product\n" +
                    "2. Remove Product\n" +
                    "3. Edit Product\n" +
                    "4. View Products in Shopping Cart\n" +
                    "5. View Revenue by Store\n" +
                    "6: Import CSV\n" +
                    "7. Exit\n" +
                    "2\n" +
                    "Enter the name of the product that you would like to remove:\n" +
                    "airpods\n" +
                    "Enter the store name of the product\n" +
                    "apple\n" +
                    "------------------------\n" +
                    "Select an Option (1-6)\n" +
                    "------------------------\n" +
                    "1. Add Product\n" +
                    "2. Remove Product\n" +
                    "3. Edit Product\n" +
                    "4. View Products in Shopping Cart\n" +
                    "5. View Revenue by Store\n" +
                    "6: Import CSV\n" +
                    "7. Exit\n" +
                    "7\n" +
                    "Are you sure you want to Exit? (Y/N)\n" +
                    "y\n";

            // Runs the program with the input values
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Make sure your output matches the expected format",
                    expected.trim(), output.trim());
        }

    }
}


