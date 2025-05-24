/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import view.SignUp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.User;
import java.sql.SQLException;
import view.Login;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author sasdfgh
 */
public class SignupController {
    private static final Logger LOGGER = Logger.getLogger(SignupController.class.getName());
    private final UserDao userDao = new UserDao();
    private final SignUp userView;

    public SignupController(SignUp userView) {
        this.userView = userView;
        userView.addAddUserListener(new AddUserListener());
    }
    public void open() {
        userView.setVisible(true);
    }

    public void close() {
        this.userView.dispose();
    }

    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get form data
                String name = userView.getUsernameField().getText().trim();
                String email = userView.getEmailField().getText().trim();
                String password = new String(userView.getPasswordField().getPassword());
                
                LOGGER.info("Processing signup for username: " + name + ", email: " + email);
                
                // Validate input fields
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    LOGGER.warning("Empty fields detected in signup form");
                    JOptionPane.showMessageDialog(userView, 
                        "All fields are required!", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate passwords match
                if (!userView.validatePasswords()) {
                    LOGGER.warning("Password mismatch during signup");
                    JOptionPane.showMessageDialog(userView, 
                        "Passwords do not match!", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create user and check if exists
                User user = new User(name, email, password);
                
                try {
                    boolean userExists = userDao.checkUser(user);
                    if (userExists) {
                        LOGGER.warning("Attempted to create duplicate user: " + name);
                        JOptionPane.showMessageDialog(userView, 
                            "User already exists", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    userDao.signUp(user);
                    LOGGER.info("Successfully created new user: " + name);
                    
                    // Show confirmation with credentials
                    String message = String.format(
                        "Account created successfully!\n\n" +
                        "Your login credentials:\n" +
                        "Username: %s\n" +
                        "Password: %s\n\n" +
                        "Please save these credentials for logging in.",
                        name, password
                    );
                    
                    // Use a more noticeable message dialog
                    int choice = JOptionPane.showConfirmDialog(
                        userView, 
                        message + "\n\nWould you like to proceed to login?",
                        "Registration Successful!", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    userView.dispose();
                    
                    // If user clicks Yes, open login window
                    if (choice == JOptionPane.YES_OPTION) {
                        LOGGER.info("User chose to proceed to login");
                        Login loginView = new Login();
                        loginView.setLocationRelativeTo(null);
                        loginView.setVisible(true);
                    }
                    
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Database error during signup", ex);
                    JOptionPane.showMessageDialog(userView, 
                        "Database error: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Unexpected error during signup", ex);
                JOptionPane.showMessageDialog(userView, 
                    "Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } 
    }
}