/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import view.SignUp;
import view.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.User;

public class SignupController {
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
                String username = userView.getUsernameField().getText().trim();
                String email = userView.getEmailField().getText().trim();
                String password = userView.getPasswordField().getText();
                String confirmPassword = userView.getConfirmPasswordField().getText();
                
                // Validate input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(userView, "Please fill in all fields");
                    return;
                }
                
                // Validate email format
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    JOptionPane.showMessageDialog(userView, "Please enter a valid email address");
                    return;
                }
                
                // Check password match
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(userView, "Passwords do not match");
                    return;
                }
                
                // Create user object
                User user = new User(username, email, password);
                
                // Check if user exists
                if (userDao.checkUser(user)) {
                    JOptionPane.showMessageDialog(userView, "User already exists");
                    return;
                }
                
                // Attempt to sign up
               // Clear the form
                
                // Open login window and close signup
                Login loginPage = new Login();
                loginPage.setVisible(true);
                userView.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(userView, 
                    "Error during registration: " + ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } 
    }
}
