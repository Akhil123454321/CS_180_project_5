import java.io.*;
import java.util.ArrayList;
/**
 * User.java
 *
 * This class provides the basic frame for each user, with things such as username, password, and email. The customer
 * class extends this class.
 *
 * @author Drew Balaji, Praneeth Kukunuru, Akhil Kasturi, Yash Prabhu, L28
 *
 * @version 1.5, 11/15/2022
 *
 */
public class User {
    private String userName;
    private String password;
    private String accountType;
    private String email;
    public User(String userName, String password, String accountType) {
        this.userName = userName;
        this.password = password;
        this.accountType = accountType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public User() {
        this.userName = "";
        this.password = "";
    }

    public String getName() {
        return userName;
    }
    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> contents = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                contents.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contents;
    }
    public boolean login(String userNameL, String passwordL) {
        boolean accountExists = false;
        ArrayList<String> accounts = readFile("accounts.txt");
        for (String account : accounts) {
            String[] accountSplit = account.split("/");
            if (accountSplit[0].equals(userNameL) && accountSplit[1].equals(passwordL)) {
                accountExists = true;
                break;
            }
        }
        return accountExists;
    }
    public boolean signUp(String userNameS, String passwordS, String accountTypeS) {
        boolean addedAccount = false;
        boolean existingAccount = false;
        ArrayList<String> accounts = readFile("accounts.txt");
        for (String account : accounts) {
            if (account.split("/")[0].equals(userNameS)) {
                existingAccount = true;
                break;
            }
        }

        if (!existingAccount) {
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream("accounts.txt", true));
                pw.println(toString());
                pw.close();
                addedAccount = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addedAccount;
    }
    public String toString() {
        return String.format("%s/%s/%s", userName, password, accountType);
    }
}
