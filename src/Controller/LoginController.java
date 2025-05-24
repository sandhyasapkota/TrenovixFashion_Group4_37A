package controller;

import dao.UserDao;
import view.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.SignUp;
import java.util.logging.Logger;
import java.util.logging.Level;
import view.AdminDashboard;

public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private final UserDao userDao = new UserDao();
    private final Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addSignUpListener(new SignUpListener());
    }

    public void open() {
        loginView.setVisible(true);
    }

    public void close() {
        loginView.dispose();
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String username = loginView.getUsernameField().getText().trim();
                String password = new String(loginView.getPasswordField().getPassword());
                
                LOGGER.info("Attempting login for user: " + username);
                
                if (username.isEmpty() || password.isEmpty()) {
                    LOGGER.warning("Empty username or password field");
                    JOptionPane.showMessageDialog(loginView, 
                        "Please enter both username and password", 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userDao.verifyLogin(username, password)) {
                    LOGGER.info("Login successful for user: " + username);
                    JOptionPane.showMessageDialog(loginView, 
                        "Login successful!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Close login window
                    loginView.dispose();
                    
                    // Open admin dashboard
                    AdminDashboard dashboard = new AdminDashboard();
                    dashboard.setLocationRelativeTo(null);
                    dashboard.setVisible(true);
                } else {
                    LOGGER.warning("Login failed for user: " + username);
                    JOptionPane.showMessageDialog(loginView, 
                        "Invalid username or password", 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error during login: " + ex.getMessage(), ex);
                JOptionPane.showMessageDialog(loginView, 
                    "Error during login: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.dispose();
            SignUp signupView = new SignUp(loginView);
            SignupController signupController = new SignupController(signupView);
            signupView.setLocationRelativeTo(null);
            signupController.open();
        }
    }
} 