/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.*;
import view.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author sandhya sapkota
 */
public class LoginController {
    private final LoginDao loginDao = new LoginDao();
    private final Login login;
    private final Overview admindashboard = new Overview();
    private final Home homepage = new Home();
   
    

    public LoginController(Login login) {
        this.login = login;
        login.addLoginListener(new LoginListener());
    }
    public void open() {
        login.setVisible(true);
    }

    public void close() {
        this.login.dispose();
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String username = login.L_username_field().getText();
                String password = new String(login.L_password_field().getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(login, "Please fill in all fields.");
                    return;
                }

                User user = new User(username, password);
                boolean isExist = loginDao.userlogin(user);

                
                if (username.equals("admin") && password.equals("root")) {
                    admindashboard.setVisible(true);
                    admindashboard.setLocationRelativeTo(null);
                    admindashboard.addLogoutListener(evt -> {
                        admindashboard.dispose();
                        login.setVisible(true);
                        login.setLocationRelativeTo(null);
                    });
                    login.dispose();
                }
                else if (loginDao.userlogin(user)) {
                    User fullUser = loginDao.getUserByUsername(user.getUsername());
                    if (fullUser != null) {
                        model.Session.currentUserId = fullUser.getId(); // This is now user_id
                    }
                    JOptionPane.showMessageDialog(login, "Login successful! Welcome, " + user.getUsername());
                    homepage.setVisible(true);
                    login.dispose();
                } else {
                    JOptionPane.showMessageDialog(login, "Invalid username or password");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(login, "Error: " + ex.getMessage());
            }
        }
    }
    class LogoutBtnActionPerformed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            admindashboard.dispose();
            login.setVisible(true);
            LoginController loginController = new LoginController(login);
            loginController.open();
        }
    }
}
