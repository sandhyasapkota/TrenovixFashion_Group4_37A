/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDao;
import view.Signup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.User;

     
/**s
 *
 * @author sahki
 */
public class SignupController {
    private final UserDao userDao = new UserDao();
    private final Signup userView;
    private final Validation validation = new Validation();

    public SignupController(Signup userView) {
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
                String password = new String(userView.getPasswordField().getPassword());
                String security_ans = userView.getSecurity_AnsField().getText();
//                String securityquestion = userView.getSecurityQuestion().getText();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || security_ans.isEmpty()) {
            JOptionPane.showMessageDialog(userView, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if(!validation.isValidEmail(email)){
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
//                    System.out.println(userView.getSecurityQuestion().);
                    System.out.println();
                }
        }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(userView, "Error: " + ex.getMessage());
            }
        } 
    }
}
