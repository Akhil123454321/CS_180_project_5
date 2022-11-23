import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends JComponent implements Runnable {
    private int userLoggingInOrSigningUp = 0;
    JButton loginButton;
    JButton signUpButton;
    JTextField usernameField;
    JTextField passwordField;
    JTextField signingUpUsername;
    JTextField signingUpPassword;
    JTextField signingUpPasswordTwo;
    Socket socket = new Socket();
    BufferedReader reader;
    PrintWriter writer;
    String accepted = "no";
    Client client;
    

    public int getUserLoggingInOrSigningUp() {
        return userLoggingInOrSigningUp;
    }

    public Client() {
        try {
            try {
                socket = new Socket("localhost" , 1111);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.getMessage();
            }

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeReader() {
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeWriter() {
        writer.close();
    }


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                userLoggingInOrSigningUp = 0;
                writer.println("1");
                writer.println(client.usernameField.getText());
                writer.println(client.passwordField.getText());
                try {
                    accepted = reader.readLine();
                } catch (Exception r) {
                    r.printStackTrace();
                }
            }
            if (e.getSource() == signUpButton || client.signingUpPasswordTwo == null) {
                if (client.signingUpPassword == null) {
                    JOptionPane.showMessageDialog(null , "Password column cannot be empty" ,
                            "Error" , JOptionPane.ERROR_MESSAGE);
                }

                if (client.signingUpPassword.getText().length() >= 8) {
                    if (client.signingUpPassword.equals(client.signingUpPasswordTwo)) {
                        userLoggingInOrSigningUp = 1;
                        writer.println("2");
                        writer.println(client.signingUpUsername.getText());
                        writer.println(client.signingUpPassword.getText());
                        writer.println(client.signingUpPassword.getText());
                    } else {
                        JOptionPane.showMessageDialog(null , "Passwords should match each other" ,
                                "Error" , JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null , "Password should be more than 8 characters" ,
                            "Error" , JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Client());
    }




    public void run() {
        Socket socket = new Socket();
        try {

            /*
            Username
            Password              SignUp
            LogIn
             */
            client = new Client();
            JFrame frame = new JFrame("CS Marketplace");
            JPanel panel = new JPanel();
            Container content = frame.getContentPane();
            content.setLayout(new BorderLayout());
            usernameField = new JTextField(10);
            passwordField = new JTextField(10);

            loginButton = new JButton("Log in");
            loginButton.addActionListener(actionListener);
            signUpButton = new JButton("Sign Up");
            signUpButton.addActionListener(actionListener);
            JPanel test = new JPanel();
            panel.add(usernameField);
            panel.add(passwordField);
            panel.add(loginButton);
            JPanel panel2 = new JPanel();
            signingUpUsername = new JTextField(10);
            signingUpPassword = new JTextField(10);
            signingUpPasswordTwo = new JTextField(10);
            panel2.add(signingUpUsername);
            panel2.add(signingUpPassword);
            panel2.add(signingUpPasswordTwo);
            panel2.add(signUpButton);
            content.add(panel , BorderLayout.WEST);
            content.add(panel2 , BorderLayout.EAST);

            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);



            // This is where the login panel for the login and signup will come in
            //login -> 1 , signup -> 2
            
        } catch (Exception e) {
            e.printStackTrace();
        }




    }




}
