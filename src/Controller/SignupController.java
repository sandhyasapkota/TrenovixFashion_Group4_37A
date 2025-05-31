/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import view.SignUp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.User;
import view.Login;

/**
 *
 * @author sandhya sapkota
 */

public class SignupController {
    private final UserDao userDao = new UserDao();
    private final SignUp userView;
    private final Validation validation = new Validation();

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
                String name = userView.getUsernameField().getText();
                String email = userView.getEmailField().getText();
                String password = userView.getPasswordField().getText();
                String security_ans = userView.getSecurity_AnsField().getText();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || password.isEmpty()|| security_ans.isEmpty()) {
            JOptionPane.showMessageDialog(userView, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if (!password.equals(password)) {
            JOptionPane.showMessageDialog(userView, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(!validation.isValidEmail(email)){
            JOptionPane.showMessageDialog(userView, "Input valid email");
        }else if(!validation.isValidusername(name)){
            JOptionPane.showMessageDialog(userView, "Input valid username");
        }else if(!validation.isValidPassword(password)){
            JOptionPane.showMessageDialog(userView, "Input valid Password");
        }else{
                User user = new User(name, email, password, security_ans);
                boolean check = userDao.checkUser(user);
                if (check) {
                    JOptionPane.showMessageDialog(userView, "User already exists");

                }else {
                    userDao.signUp(user);
                    JOptionPane.showMessageDialog(userView, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Login login = new Login();
                    login.setVisible(true);
                    userView.dispose();
                          
                }
        }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(userView, "Error: " + ex.getMessage());
            }
        } 
    }
}